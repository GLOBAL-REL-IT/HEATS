package com.onsemi.mib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.LDAPUser;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.model.User;
import com.onsemi.mib.model.UserAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    private final DataSource dataSource;

    public UserDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_INSERT_USER = "INSERT INTO user_ldap (login_id, password, group_id, is_active, created_by, created_time) VALUES (?,?,?,'1',?,NOW())";
    private static final String SQL_INSERT_PROFILE = "INSERT INTO user_profile (user_id, fullname, email) VALUES (?,?,?)";
    private static final String SQL_UPDATE_USER = "UPDATE user_ldap SET group_id = ?, is_active = ?, modified_by = ?, modified_time = NOW() WHERE id = ?";
    private static final String SQL_UPDATE_GROUP = "UPDATE user_ldap SET group_id = ? WHERE id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM user_ldap WHERE id = ?";
    private static final String SQL_DELETE_PROFILE = "DELETE FROM user_profile WHERE user_id = ?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE user_ldap SET password = ?, modified_by = ?, modified_time = NOW() WHERE id = ? ";

    public QueryResult insertUser(User user) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getLoginId());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getGroupId());
                ps.setString(4, user.getCreatedBy());
                queryResult.setResult(ps.executeUpdate());
                if (queryResult.getResult() == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            String generatedId = String.valueOf(rs.getInt(1));
                            queryResult.setGeneratedKey(generatedId);
                            try (PreparedStatement profilePs = conn.prepareStatement(SQL_INSERT_PROFILE)) {
                                profilePs.setString(1, generatedId);
                                profilePs.setString(2, user.getFullname());
                                profilePs.setString(3, user.getEmail());
                                profilePs.executeUpdate();
                            }
                        }
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error inserting user", e);
        }
        return queryResult;
    }

    public QueryResult updateUser(User user) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_USER)) {
            ps.setString(1, user.getGroupId());
            ps.setString(2, user.getIsActive());
            ps.setString(3, user.getModifiedBy());
            ps.setString(4, user.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error updating user", e);
        }
        return queryResult;
    }

    public QueryResult updateGroup(String userId, String groupId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_GROUP)) {
            ps.setString(1, groupId);
            ps.setString(2, userId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error updating user", e);
        }
        return queryResult;
    }

    public QueryResult deleteUser(String userId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int affectedRows = 0;
                try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_USER)) {
                    ps.setString(1, userId);
                    affectedRows = ps.executeUpdate();
                }
                if (affectedRows > 0) {
                    try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_PROFILE)) {
                        ps.setString(1, userId);
                        ps.executeUpdate();
                    }
                }
                conn.commit();
                queryResult.setResult(affectedRows);
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            queryResult.setResult(0);
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error deleting user: {}", userId, e);
        }
        return queryResult;
    }

    public QueryResult updatePassword(User user) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_PASSWORD)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getModifiedBy());
            ps.setString(3, user.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error updating user", e);
        }
        return queryResult;
    }

    private static final String SQL_GET_USER = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM user_ldap u LEFT JOIN user_group ug ON (u.group_id = ug.id) LEFT JOIN user_profile up ON (u.id = up.user_id) WHERE u.id = ? ORDER BY up.fullname";
    private static final String SQL_GET_USER_BY_LOGIN_ID = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name, up.fullname, up.email FROM user_ldap u LEFT JOIN user_group ug ON (u.group_id = ug.id) LEFT JOIN user_profile up ON (u.id = up.user_id) WHERE u.login_id = ? ORDER BY up.fullname";
    private static final String SQL_GET_USER_BY_FULLNAME = "SELECT * FROM user_ldap WHERE CONCAT(firstname,' ',lastname) = ?";
    private static final String SQL_GET_USER_DETAILS = "SELECT * FROM user_ldap u, user_group g, user_profile p WHERE u.id = p.user_id AND u.group_id = g.id AND u.id = ? ORDER BY u.id ASC";
    private static final String SQL_GET_USER_ACCESS = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name, uac.* FROM user_ldap u LEFT JOIN user_group ug ON (u.group_id = ug.id) LEFT JOIN user_access_control uac ON (u.id = uac.user_id) WHERE u.id = ?";
    private static final String SQL_GET_USER_DETAILS_BY_ID = "SELECT u.*, ug.code AS group_code, ug.name AS group_name FROM user_ldap u LEFT JOIN user_group ug ON (u.group_id = ug.id) WHERE u.id = ?";

    public User getUser(String userId) {
        User user = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_USER)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving user with ID: {}", userId, e);
        }
        return user;
    }

    public User getUserByLoginId(String loginId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_USER_BY_LOGIN_ID)) {
            ps.setString(1, loginId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
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
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting user by loginId: {}", loginId, e);
        }
        return null;
    }

    public UserAccess getUserDetails(String userId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_USER_DETAILS)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserAccess userAccess = new UserAccess();
                    userAccess.setId(rs.getString("id"));
                    userAccess.setLoginId(rs.getString("login_id"));
                    userAccess.setPassword(rs.getString("password"));
                    userAccess.setIsActive(rs.getString("is_active"));
                    userAccess.setUserCreatedBy(rs.getString("created_by"));
                    userAccess.setUserCreatedTime(rs.getString("created_time"));
                    userAccess.setUserModifiedBy(rs.getString("modified_by"));
                    userAccess.setUserModifiedTime(rs.getString("modified_time"));
                    userAccess.setGroupId(rs.getString("group_id"));
                    userAccess.setGroupCode(rs.getString("code"));
                    userAccess.setGroupName(rs.getString("name"));
                    userAccess.setGroupCreatedBy(rs.getString("created_by"));
                    userAccess.setGroupCreatedTime(rs.getString("created_time"));
                    userAccess.setGroupModifiedBy(rs.getString("modified_by"));
                    userAccess.setGroupModifiedTime(rs.getString("modified_time"));
                    userAccess.setProfileId(rs.getString("p.id"));
                    userAccess.setFullname(rs.getString("fullname"));
                    userAccess.setProfileModifiedBy(rs.getString("p.modified_by"));
                    userAccess.setProfileModifiedTime(rs.getString("p.modified_time"));
                    userAccess.setSrEmail(rs.getString("sr_email"));
                    userAccess.setHwEmailStencil(rs.getString("hw_email_stencil"));
                    userAccess.setHwEmailSparePart(rs.getString("hw_email_sparepart"));
                    userAccess.setFeaturesTestEmail(rs.getString("features_test_email"));
                    userAccess.setFeaturesTrackGts(rs.getString("features_track_gts"));
                    userAccess.setFeaturesTrackInventory(rs.getString("features_track_inventory"));
                    userAccess.setFeaturesCreateGts(rs.getString("features_create_gts"));
                    return userAccess;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting user details for userId: {}", userId, e);
        }
        return null;
    }

    public LDAPUser getUserByFullName(String fullname) {
        LDAPUser user = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_USER_BY_FULLNAME)) {
            ps.setString(1, fullname);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving user by fullname: {}", fullname, e);
        }
        return user;
    }

    public LDAPUser getUserAccess(String userId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_USER_ACCESS)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LDAPUser userAccess = new LDAPUser();
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
                    userAccess.setItemAdd(rs.getString("item_add"));
                    userAccess.setItemEdit(rs.getString("item_edit"));
                    userAccess.setItemDelete(rs.getString("item_delete"));
                    userAccess.setItemActivityConfig(rs.getString("item_activity_config"));
                    userAccess.setItemActivityAdd(rs.getString("item_activity_add"));
                    userAccess.setItemActivityEdit(rs.getString("item_activity_edit"));
                    userAccess.setItemHardwareAdd(rs.getString("item_hardware_add"));
                    userAccess.setItemHardwareEdit(rs.getString("item_hardware_edit"));
                    userAccess.setItemHardwareDelete(rs.getString("item_hardware_delete"));
                    userAccess.setItemMovementAdd(rs.getString("item_movement_add"));
                    userAccess.setItemSfRecall(rs.getString("item_sf_recall"));
                    userAccess.setEqptAdd(rs.getString("eqpt_add"));
                    userAccess.setEqptEdit(rs.getString("eqpt_edit"));
                    userAccess.setEqptDelete(rs.getString("eqpt_delete"));
                    userAccess.setEqptFamilyAdd(rs.getString("eqpt_family_add"));
                    userAccess.setEqptFamilyDelete(rs.getString("eqpt_family_delete"));
                    userAccess.setEqptRelTestGroupAdd(rs.getString("eqpt_rel_test_group_add"));
                    userAccess.setEqptRelTestGroupDelete(rs.getString("eqpt_rel__test_group_delete"));
                    userAccess.setEqptTechAdd(rs.getString("eqpt_tech_add"));
                    userAccess.setEqptTechDelete(rs.getString("eqpt_tech_delete"));
                    userAccess.setEqptMonAdd(rs.getString("eqpt_mon_add"));
                    userAccess.setEqptMonDelete(rs.getString("eqpt_mon_delete"));
                    userAccess.setEqptViMonAdd(rs.getString("eqpt_vi_mon_add"));
                    userAccess.setEqptViMonDelete(rs.getString("eqpt_vi_mon_delete"));
                    userAccess.setEqptFamilyAddGlobal(rs.getString("eqpt_family_add_global"));
                    userAccess.setEqptRelTestGroupAddGlobal(rs.getString("eqpt_rel_test_group_add_global"));
                    userAccess.setBefLoadingPriority(rs.getString("bef_loading_priority"));
                    userAccess.setBefLoadingHwReplace(rs.getString("bef_loading_hw_replace"));
                    userAccess.setBefLoadingSfRecall(rs.getString("bef_loading_sf_recall"));
                    userAccess.setBefLoadingHwRegister(rs.getString("bef_loading_hw_register"));
                    userAccess.setBefLoadingHwFinalize(rs.getString("bef_loading_hw_finalize"));
                    userAccess.setBefLoadingVm(rs.getString("bef_loading_vm"));
                    userAccess.setBefLoadingFt(rs.getString("bef_loading_ft"));
                    userAccess.setBefLoadingRelease(rs.getString("bef_loading_release"));
                    userAccess.setBefLoadingReturnDefective(rs.getString("bef_loading_return_defective"));
                    userAccess.setUnloadingHwReturn(rs.getString("unloading_hw_return"));
                    userAccess.setUnloadingIonic(rs.getString("unloading_ionic"));
                    userAccess.setUnloadingVm(rs.getString("unloading_vm"));
                    userAccess.setUnloadingFt(rs.getString("unloading_ft"));
                    userAccess.setUnloadingReleaseClose(rs.getString("unloading_release_close"));
                    return userAccess;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting user access for userId: {}", userId, e);
        }
        return null;
    }

    public LDAPUser getUserDetailById(String userId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_USER_DETAILS_BY_ID)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LDAPUser userAccess = new LDAPUser();
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
                    return userAccess;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting user details for userId: {}", userId, e);
        }
        return null;
    }

    private static final String SQL_GET_USER_LIST = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM user_ldap u LEFT JOIN user_group ug ON u.group_id = ug.id LEFT JOIN user_profile up ON u.id = up.user_id ";
    private static final String SQL_GET_SR_EMAIL_SHIPPING_LIST = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_email_shipping = 'Active' OR login_id = ?";
    private static final String SQL_GET_SR_EMAIL_RETRIEVE_LIST = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_email_retrieve = 'Active' OR login_id = ?";
    private static final String SQL_GET_SR_EMAIL_RETRIEVE_SHIP_FAIL_LIST = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_retrieve_ship_fail = 'Active' OR login_id = ? ";
    private static final String SQL_GET_HW_EMAIL_RETRIEVE_SHIP_FAIL_LIST = "SELECT DISTINCT(email) FROM user_ldap WHERE hw_retrieve_ship_fail = 'Active' OR login_id = ?";
    private static final String SQL_GET_SR_EMAIL_SHIP_TO_RL = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_email_ship_to_rl = 'Active' OR login_id = ? ";
    private static final String SQL_GET_HW_EMAIL_SHIP_TO_RL = "SELECT DISTINCT(email) FROM user_ldap WHERE hw_email_ship_to_rl = 'Active' OR login_id = ? ";
    private static final String SQL_GET_EMAIL_SCRAP = "SELECT DISTINCT(email) FROM user_ldap WHERE sr_scrap = 'Active'";
    private static final String SQL_GET_HW_EMAIL_SHIPPING_LIST = "SELECT DISTINCT(email) FROM user_ldap WHERE hw_email_shipping = 'Active' OR login_id = ? ";
    private static final String SQL_GET_HW_EMAIL_RETRIEVE_LIST = "SELECT DISTINCT(email) FROM user_ldap WHERE hw_email_retrieve = 'Active' OR login_id = ? ";

    public List<User> getUserList(String groupId) {
        String filterGroupId = "";
        boolean hasGroupFilter = groupId != null && !groupId.trim().isEmpty();
        if (hasGroupFilter) {
            filterGroupId = "WHERE u.group_id = ? ";
        }
        String sql = SQL_GET_USER_LIST + filterGroupId + "ORDER BY up.fullname";
        List<User> userList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            if (hasGroupFilter) {
                ps.setString(1, groupId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
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
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting user list", e);
        }
        return userList;
    }

    public List<LDAPUser> getSREmailShippingList(String requestorId) {
        List<LDAPUser> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SR_EMAIL_SHIPPING_LIST)) {
            ps.setString(1, requestorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LDAPUser user = new LDAPUser();
                    user.setEmail(rs.getString("email"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving SR email shipping list for requestorId: {}", requestorId, e);
        }
        return list;
    }

    public List<LDAPUser> getSREmailRetrieveList(String requestorId) {
        List<LDAPUser> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SR_EMAIL_RETRIEVE_LIST)) {
            ps.setString(1, requestorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LDAPUser user = new LDAPUser();
                    user.setEmail(rs.getString("email"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving SR email retrieve list for requestorId: {}", requestorId, e);
        }
        return list;
    }

    public List<LDAPUser> getSREmailRetrieveShipFailList(String requestorId) {
        List<LDAPUser> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SR_EMAIL_RETRIEVE_SHIP_FAIL_LIST)) {
            ps.setString(1, requestorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LDAPUser user = new LDAPUser();
                    user.setEmail(rs.getString("email"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving SR email retrieve ship fail list for requestorId: {}", requestorId, e);
        }
        return list;
    }

    public List<LDAPUser> getHWEmailRetrieveShipFailList(String requestorId) {
        List<LDAPUser> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HW_EMAIL_RETRIEVE_SHIP_FAIL_LIST)) {
            ps.setString(1, requestorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LDAPUser user = new LDAPUser();
                    user.setEmail(rs.getString("email"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving HW email retrieve ship fail list for requestorId: {}", requestorId, e);
        }
        return list;
    }

    public List<LDAPUser> getSREmailShipToRL(String requestorId) {
        List<LDAPUser> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SR_EMAIL_SHIP_TO_RL)) {
            ps.setString(1, requestorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LDAPUser user = new LDAPUser();
                    user.setEmail(rs.getString("email"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving SR email ship to RL list for requestorId: {}", requestorId, e);
        }
        return list;
    }

    public List<LDAPUser> getHWEmailShipToRL(String requestorId) {
        List<LDAPUser> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HW_EMAIL_SHIP_TO_RL)) {
            ps.setString(1, requestorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LDAPUser user = new LDAPUser();
                    user.setEmail(rs.getString("email"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving HW email ship to RL list for requestorId: {}", requestorId, e);
        }
        return list;
    }

    public List<LDAPUser> getSREmailAutoScrapList() {
        List<LDAPUser> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_EMAIL_SCRAP)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LDAPUser user = new LDAPUser();
                    user.setEmail(rs.getString("email"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving scrap email list ", e);
        }
        return list;
    }

    public List<LDAPUser> getHWEmailShippingList(String requestorId) {
        List<LDAPUser> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HW_EMAIL_SHIPPING_LIST)) {
            ps.setString(1, requestorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LDAPUser user = new LDAPUser();
                    user.setEmail(rs.getString("email"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving HW email shipping list for requestorId: {}", requestorId, e);
        }
        return list;
    }

    public List<LDAPUser> getHWEmailRetrieveList(String requestorId) {
        List<LDAPUser> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HW_EMAIL_RETRIEVE_LIST)) {
            ps.setString(1, requestorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LDAPUser user = new LDAPUser();
                    user.setEmail(rs.getString("email"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving HW email retrieve list for requestorId: {}", requestorId, e);
        }
        return list;
    }

    private static final String SQL_GET_COUNT_BY_GROUP_ID = "SELECT COUNT(id) AS count FROM user_ldap WHERE group_id = ?";

    public Integer getCountByGroupId(String groupId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BY_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting count by groupId: {}", groupId, e);
        }
        return 0;
    }

}