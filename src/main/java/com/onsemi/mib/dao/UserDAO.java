package com.onsemi.mib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.model.User;
import com.onsemi.mib.model.UserAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
    private final Connection conn;

    public UserDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
    }

    public QueryResult insertUser(User user) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO user_ldap (login_id, password, group_id, is_active, created_by, created_time) VALUES (?,?,?,'1',?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, user.getLoginId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getGroupId());
            ps.setString(4, user.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            if (queryResult.getResult() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
                }
                ps.close();
                ps = conn.prepareStatement(
                        "INSERT INTO user_profile (user_id, fullname, email) VALUES (?,?,?)"
                );
                ps.setString(1, queryResult.getGeneratedKey());
                ps.setString(2, user.getFullname());
                ps.setString(3, user.getEmail());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public QueryResult updateUser(User user) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE user_ldap SET email = ?, group_id = ?, is_active = ?, modified_by = ?, modified_time = NOW() WHERE id = ? "
            );
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getGroupId());
            ps.setString(3, user.getIsActive());
            ps.setString(4, user.getModifiedBy());
            ps.setString(5, user.getId());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public QueryResult updateGroup(String userId, String groupId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE user_ldap SET group_id = ? WHERE id = ? "
            );
            ps.setString(1, groupId);
            ps.setString(2, userId);
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public QueryResult deleteUser(String userId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM user_ldap WHERE id = ? "
            );
            ps.setString(1, userId);
            queryResult.setResult(ps.executeUpdate());
            ps.close();
            if (queryResult.getResult() == 1) {
                ps = conn.prepareStatement(
                        "DELETE FROM user_profile WHERE user_id = ? "
                );
                ps.setString(1, userId);
                queryResult.setResult(ps.executeUpdate());
                ps.close();
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public User getUser(String userId) {
        String sql = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM user_ldap u "
                    + "LEFT JOIN user_group ug ON (u.group_id = ug.id) "
                    + "LEFT JOIN user_profile up ON (u.id = up.user_id) "
                    + "WHERE u.id = ? ORDER BY up.fullname";
        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("login_id"),
                        rs.getString("password"),
                        rs.getString("group_id"),
                        rs.getString("is_active"),
                        rs.getString("created_by"),
                        rs.getString("created_time"),
                        rs.getString("modified_by"),
                        rs.getString("modified_time"),
                        rs.getString("group_code"),
                        rs.getString("group_name"),
                        rs.getString("fullname"),
                        rs.getString("email")
                );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return user;
    }

    public LDAPUser getUserByFullName(String fullname) {
        String sql = "SELECT * FROM user_ldap WHERE CONCAT(firstname,' ',lastname) = ? ";
        LDAPUser user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fullname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new LDAPUser();
                user.setId(rs.getString("id"));
                user.setLoginId(rs.getString("login_id"));
                user.setOncid(rs.getString("oncid"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setTitle(rs.getString("title"));
                user.setGroupId(rs.getString("group_id"));
                user.setIsActive(rs.getString("is_active"));
                user.setSrEmailRetrieve(rs.getString("sr_email_retrieve"));
                user.setScrap(rs.getString("sr_scrap"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return user;
    }

    public List<User> getUserList(String groupId) {
        String filterGroupId = "";
        if (!groupId.equals("")) {
            filterGroupId = "WHERE u.group_id = ? ";
        }
        String sql = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM user_ldap u "
                    + "LEFT JOIN user_group ug ON (u.group_id = ug.id) "
                    + "LEFT JOIN user_profile up ON (u.id = up.user_id) "
                    + filterGroupId
                    + "ORDER BY up.fullname";
        List<User> userList = new ArrayList<User>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, groupId);
            User user;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("login_id"),
                        rs.getString("password"),
                        rs.getString("group_id"),
                        rs.getString("is_active"),
                        rs.getString("created_by"),
                        rs.getString("created_time"),
                        rs.getString("modified_by"),
                        rs.getString("modified_time"),
                        rs.getString("group_code"),
                        rs.getString("group_name"),
                        rs.getString("fullname"),
                        rs.getString("email")
                );
                userList.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return userList;
    }

    public Integer getCountByGroupId(String groupId) {
        Integer count = null;
        String sql = "SELECT count(id) AS count FROM user_ldap WHERE group_id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, groupId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    //profile use
    public User getUserByLoginId(String loginId) {
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name, up.fullname, up.email FROM user_ldap u "
                    + "LEFT JOIN user_group ug ON (u.group_id = ug.id) LEFT JOIN user_profile up ON (u.id = up.user_id) "
                    + "WHERE u.login_id = ? ORDER BY up.fullname";
        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loginId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("login_id"),
                        rs.getString("password"),
                        rs.getString("group_id"),
                        rs.getString("is_active"),
                        rs.getString("created_by"),
                        rs.getString("created_time"),
                        rs.getString("modified_by"),
                        rs.getString("modified_time"),
                        rs.getString("group_code"),
                        rs.getString("group_name"),
                        rs.getString("fullname"),
                        rs.getString("email")
                );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return user;
    }

    public QueryResult updatePassword(User user) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE user_ldap SET password = ?, modified_by = ?, modified_time = NOW() WHERE id = ?"
            );
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getModifiedBy());
            ps.setString(3, user.getId());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public UserAccess getUserDetails(String userId) {
        String sql = "SELECT * FROM user_ldap u, user_group g, user_profile p "
                    + "WHERE u.id = p.user_id AND u.group_id = g.id AND u.id = ? "
                    + "ORDER BY u.id ASC ";
        UserAccess userAccess = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userAccess = new UserAccess();
                userAccess.setId(rs.getString("u.id"));
                userAccess.setLoginId(rs.getString("u.login_id"));
                userAccess.setPassword(rs.getString("u.password"));
                userAccess.setIsActive(rs.getString("u.is_active"));
                userAccess.setUserCreatedBy(rs.getString("created_by"));
                userAccess.setUserCreatedTime(rs.getString("u.created_time"));
                userAccess.setUserModifiedBy(rs.getString("u.modified_by"));
                userAccess.setUserModifiedTime(rs.getString("u.modified_time"));
                userAccess.setGroupId(rs.getString("g.id"));
                userAccess.setGroupCode(rs.getString("g.code"));
                userAccess.setGroupName(rs.getString("g.name"));
                userAccess.setGroupCreatedBy(rs.getString("g.created_by"));
                userAccess.setGroupCreatedTime(rs.getString("g.created_time"));
                userAccess.setGroupModifiedBy(rs.getString("g.modified_by"));
                userAccess.setGroupModifiedTime(rs.getString("g.modified_time"));
                userAccess.setProfileId(rs.getString("p.id"));
                userAccess.setFullname(rs.getString("p.fullname"));
                userAccess.setProfileModifiedBy(rs.getString("p.modified_by"));
                userAccess.setProfileModifiedTime(rs.getString("p.modified_time"));
                userAccess.setSrEmail(rs.getString("sr_email"));
                userAccess.setHwEmailStencil(rs.getString("hw_email_stencil"));
                userAccess.setHwEmailSparePart(rs.getString("hw_email_sparepart"));
                userAccess.setFeaturesTestEmail(rs.getString("features_test_email"));
                userAccess.setFeaturesTrackGts(rs.getString("features_track_gts"));
                userAccess.setFeaturesTrackInventory(rs.getString("features_track_inventory"));
                userAccess.setFeaturesCreateGts(rs.getString("features_create_gts"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return userAccess;
    }

    public LDAPUser getUserAccess(String userId) {
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name, uac.* FROM user_ldap u "
                    + "LEFT JOIN user_group ug ON (u.group_id = ug.id) "
                    + "LEFT JOIN user_access_control uac ON u.id = uac.user_id "
                    + "WHERE u.id = ? ";
        LDAPUser userAccess = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userAccess = new LDAPUser();
                userAccess.setId(rs.getString("u.id"));
                userAccess.setLoginId(rs.getString("u.login_id"));
                userAccess.setOncid(rs.getString("u.oncid"));
                userAccess.setFirstname(rs.getString("u.firstname"));
                userAccess.setLastname(rs.getString("u.lastname"));
                userAccess.setEmail(rs.getString("u.email"));
                userAccess.setTitle(rs.getString("u.title"));
                userAccess.setGroupId(rs.getString("u.group_id"));
                userAccess.setIsActive(rs.getString("u.is_active"));
                userAccess.setGroupCode(rs.getString("group_code"));
                userAccess.setGroupName(rs.getString("group_name"));
                userAccess.setItemAdd(rs.getString("uac.item_add"));
                userAccess.setItemEdit(rs.getString("uac.item_edit"));
                userAccess.setItemDelete(rs.getString("uac.item_delete"));
                userAccess.setItemActivityConfig(rs.getString("uac.item_activity_config"));
                userAccess.setItemActivityAdd(rs.getString("uac.item_activity_add"));
                userAccess.setItemActivityEdit(rs.getString("uac.item_activity_edit"));
                userAccess.setItemHardwareAdd(rs.getString("uac.item_hardware_add"));
                userAccess.setItemHardwareEdit(rs.getString("uac.item_hardware_edit"));
                userAccess.setItemHardwareDelete(rs.getString("uac.item_hardware_delete"));
                userAccess.setItemMovementAdd(rs.getString("uac.item_movement_add"));
                userAccess.setItemSfRecall(rs.getString("uac.item_sf_recall"));
                userAccess.setEqptAdd(rs.getString("uac.eqpt_add"));
                userAccess.setEqptEdit(rs.getString("uac.eqpt_edit"));
                userAccess.setEqptDelete(rs.getString("uac.eqpt_delete"));
                userAccess.setEqptFamilyAdd(rs.getString("uac.eqpt_family_add"));
                userAccess.setEqptFamilyDelete(rs.getString("uac.eqpt_family_delete"));
                userAccess.setEqptRelTestGroupAdd(rs.getString("uac.eqpt_rel_test_group_add"));
                userAccess.setEqptRelTestGroupDelete(rs.getString("uac.eqpt_rel__test_group_delete"));
                userAccess.setEqptTechAdd(rs.getString("uac.eqpt_tech_add"));
                userAccess.setEqptTechDelete(rs.getString("uac.eqpt_tech_delete"));
                userAccess.setEqptMonAdd(rs.getString("uac.eqpt_mon_add"));
                userAccess.setEqptMonDelete(rs.getString("uac.eqpt_mon_delete"));
                userAccess.setEqptViMonAdd(rs.getString("uac.eqpt_vi_mon_add"));
                userAccess.setEqptViMonDelete(rs.getString("uac.eqpt_vi_mon_delete"));
                userAccess.setEqptFamilyAddGlobal(rs.getString("uac.eqpt_family_add_global"));
                userAccess.setEqptRelTestGroupAddGlobal(rs.getString("uac.eqpt_rel_test_group_add_global"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return userAccess;
    }

    public LDAPUser getUserDetailById(String userId) {
        String sql = "SELECT u.*, ug.code AS group_code, ug.name AS group_name FROM user_ldap u "
                    + "LEFT JOIN user_group ug ON (u.group_id = ug.id) "
                    + "WHERE u.id = ? ";
        LDAPUser userAccess = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userAccess = new LDAPUser();
                userAccess.setId(rs.getString("id"));
                userAccess.setLoginId(rs.getString("login_id"));
                userAccess.setOncid(rs.getString("oncid"));
                userAccess.setFirstname(rs.getString("firstname"));
                userAccess.setLastname(rs.getString("lastname"));
                userAccess.setEmail(rs.getString("email"));
                userAccess.setTitle(rs.getString("title"));
                userAccess.setGroupId(rs.getString("group_id"));
                userAccess.setIsActive(rs.getString("is_active"));
                userAccess.setGroupCode(rs.getString("group_code"));
                userAccess.setGroupName(rs.getString("group_name"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return userAccess;
    }

    /* Sample Retention Email List */
    public List<LDAPUser> getSREmailShippingList(String requestorId) {
        String sql = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_email_shipping = 'Active' OR login_id = ? ";
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LDAPUser user = new LDAPUser();
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return list;
    }

    public List<LDAPUser> getSREmailRetrieveList(String requestorId) {
        String sql = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_email_retrieve = 'Active' OR login_id = ? ";
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LDAPUser user = new LDAPUser();
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return list;
    }

    public List<LDAPUser> getSREmailRetrieveShipFailList(String requestorId) {
        String sql = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_retrieve_ship_fail = 'Active' OR login_id = ? ";
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LDAPUser user = new LDAPUser();
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return list;
    }

    public List<LDAPUser> getHWEmailRetrieveShipFailList(String requestorId) {
        String sql = "SELECT DISTINCT(email) FROM user_ldap WHERE hw_retrieve_ship_fail = 'Active' OR login_id = ? ";
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LDAPUser user = new LDAPUser();
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return list;
    }

    public List<LDAPUser> getSREmailShipToRL(String requestorId) {
        String sql = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_email_ship_to_rl = 'Active' OR login_id = ? ";
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestorId);
            ResultSet rs = ps.executeQuery();
            LDAPUser user;
            while (rs.next()) {
                user = new LDAPUser();
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return list;
    }

    public List<LDAPUser> getHWEmailShipToRL(String requestorId) {
        String sql = "SELECT DISTINCT(email) FROM user_ldap WHERE hw_email_ship_to_rl = 'Active' OR login_id = ? ";
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestorId);
            ResultSet rs = ps.executeQuery();
            LDAPUser user;
            while (rs.next()) {
                user = new LDAPUser();
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return list;
    }

    public List<LDAPUser> getSREmailAutoScrapList() {
        String sql = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_scrap = 'Active' ";
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LDAPUser user = new LDAPUser();
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return list;
    }

    /* Spare Part & Hardware Email List */
    public List<LDAPUser> getHWEmailShippingList(String requestorId) {
        String sql = "SELECT DISTINCT(email) FROM user_ldap WHERE hw_email_shipping = 'Active' OR login_id = ? ";
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LDAPUser user = new LDAPUser();
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return list;
    }

    public List<LDAPUser> getHWEmailRetrieveList(String requestorId) {
        String sql = "SELECT DISTINCT(email) FROM user_ldap WHERE hw_email_retrieve = 'Active' OR login_id = ? ";
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LDAPUser user = new LDAPUser();
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return list;
    }

}