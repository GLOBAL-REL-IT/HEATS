package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.mib.model.UserAccessControl;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccessControlDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccessControlDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public UserAccessControlDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertUserAccessControl(UserAccessControl useraccessControl) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO user_access_control (user_id, item_add, item_edit, item_delete, item_activity_config, item_activity_add, item_activity_edit, "
                    + "item_hardware_add, item_hardware_edit, item_hardware_delete, item_movement_add, item_sf_recall, eqpt_add, eqpt_edit, eqpt_delete, eqpt_family_add, eqpt_family_delete, eqpt_rel_test_group_add, eqpt_rel__test_group_delete, "
                    + "eqpt_tech_add, eqpt_tech_delete, eqpt_mon_add, eqpt_mon_delete, eqpt_vi_mon_add, eqpt_vi_mon_delete, eqpt_family_add_global, eqpt_rel_test_group_add_global) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, useraccessControl.getUserId());
            ps.setString(2, useraccessControl.getItemAdd());
            ps.setString(3, useraccessControl.getItemEdit());
            ps.setString(4, useraccessControl.getItemDelete());
            ps.setString(5, useraccessControl.getItemActivityConfig());
            ps.setString(6, useraccessControl.getItemActivityAdd());
            ps.setString(7, useraccessControl.getItemActivityEdit());
            ps.setString(8, useraccessControl.getItemHardwareAdd());
            ps.setString(9, useraccessControl.getItemHardwareEdit());
            ps.setString(10, useraccessControl.getItemHardwareDelete());
            ps.setString(11, useraccessControl.getItemMovementAdd());
            ps.setString(12, useraccessControl.getItemSfRecall());
            ps.setString(13, useraccessControl.getEqptAdd());
            ps.setString(14, useraccessControl.getEqptEdit());
            ps.setString(15, useraccessControl.getEqptDelete());
            ps.setString(16, useraccessControl.getEqptFamilyAdd());
            ps.setString(17, useraccessControl.getEqptFamilyDelete());
            ps.setString(18, useraccessControl.getEqptRelTestGroupAdd());
            ps.setString(19, useraccessControl.getEqptRelTestGroupDelete());
            ps.setString(20, useraccessControl.getEqptTechAdd());
            ps.setString(21, useraccessControl.getEqptTechDelete());
            ps.setString(22, useraccessControl.getEqptMonAdd());
            ps.setString(23, useraccessControl.getEqptMonDelete());
            ps.setString(24, useraccessControl.getEqptViMonAdd());
            ps.setString(25, useraccessControl.getEqptViMonDelete());
            ps.setString(26, useraccessControl.getEqptFamilyAddGlobal());
            ps.setString(27, useraccessControl.getEqptRelTestGroupAddGlobal());
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
            }
            rs.close();
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

    public QueryResult updateUserAccessControl(UserAccessControl useraccessControl) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE user_access_control SET user_id = ?, item_add = ?, item_edit = ?, item_delete = ?, item_activity_config = ?, item_activity_add = ?, item_activity_edit = ?, item_hardware_add = ?, item_hardware_edit = ?, item_hardware_delete = ?, item_movement_add = ?, item_sf_recall = ? WHERE id = ?"
            );
            ps.setString(1, useraccessControl.getUserId());
            ps.setString(2, useraccessControl.getItemAdd());
            ps.setString(3, useraccessControl.getItemEdit());
            ps.setString(4, useraccessControl.getItemDelete());
            ps.setString(5, useraccessControl.getItemActivityConfig());
            ps.setString(6, useraccessControl.getItemActivityAdd());
            ps.setString(7, useraccessControl.getItemActivityEdit());
            ps.setString(8, useraccessControl.getItemHardwareAdd());
            ps.setString(9, useraccessControl.getItemHardwareEdit());
            ps.setString(10, useraccessControl.getItemHardwareDelete());
            ps.setString(11, useraccessControl.getItemMovementAdd());
            ps.setString(12, useraccessControl.getItemSfRecall());
            ps.setString(13, useraccessControl.getId());
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

    public QueryResult updateUserAccessControlByUserId(UserAccessControl useraccessControl) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE user_access_control SET user_id = ?, item_add = ?, item_edit = ?, item_delete = ?, item_activity_config = ?, item_activity_add = ?, item_activity_edit = ?, item_hardware_add = ?, "
                    + "item_hardware_edit = ?, item_hardware_delete = ?, item_movement_add = ?, item_sf_recall = ?, eqpt_add = ?, eqpt_edit = ?, eqpt_delete = ?, eqpt_family_add = ?, eqpt_family_delete = ?, "
                    + "eqpt_rel_test_group_add = ?, eqpt_rel__test_group_delete = ?, eqpt_tech_add = ?, eqpt_tech_delete = ?, eqpt_mon_add = ?, eqpt_mon_delete = ?, eqpt_vi_mon_add = ?, eqpt_vi_mon_delete = ?, "
                    + "eqpt_family_add_global = ?, eqpt_rel_test_group_add_global = ? "
                    + "WHERE user_id = ?"
            );
            ps.setString(1, useraccessControl.getUserId());
            ps.setString(2, useraccessControl.getItemAdd());
            ps.setString(3, useraccessControl.getItemEdit());
            ps.setString(4, useraccessControl.getItemDelete());
            ps.setString(5, useraccessControl.getItemActivityConfig());
            ps.setString(6, useraccessControl.getItemActivityAdd());
            ps.setString(7, useraccessControl.getItemActivityEdit());
            ps.setString(8, useraccessControl.getItemHardwareAdd());
            ps.setString(9, useraccessControl.getItemHardwareEdit());
            ps.setString(10, useraccessControl.getItemHardwareDelete());
            ps.setString(11, useraccessControl.getItemMovementAdd());
            ps.setString(12, useraccessControl.getItemSfRecall());
            ps.setString(13, useraccessControl.getEqptAdd());
            ps.setString(14, useraccessControl.getEqptEdit());
            ps.setString(15, useraccessControl.getEqptDelete());
            ps.setString(16, useraccessControl.getEqptFamilyAdd());
            ps.setString(17, useraccessControl.getEqptFamilyDelete());
            ps.setString(18, useraccessControl.getEqptRelTestGroupAdd());
            ps.setString(19, useraccessControl.getEqptRelTestGroupDelete());
            ps.setString(20, useraccessControl.getEqptTechAdd());
            ps.setString(21, useraccessControl.getEqptTechDelete());
            ps.setString(22, useraccessControl.getEqptMonAdd());
            ps.setString(23, useraccessControl.getEqptMonDelete());
            ps.setString(24, useraccessControl.getEqptViMonAdd());
            ps.setString(25, useraccessControl.getEqptViMonDelete());
            ps.setString(26, useraccessControl.getEqptFamilyAddGlobal());
            ps.setString(27, useraccessControl.getEqptRelTestGroupAddGlobal());
            ps.setString(28, useraccessControl.getUserId());
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

    public QueryResult deleteUserAccessControl(String useraccessControlId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM user_access_control WHERE id = '" + useraccessControlId + "'"
            );
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

    public UserAccessControl getUserAccessControl(String useraccessControlId) {
        String sql = "SELECT * FROM user_access_control WHERE id = '" + useraccessControlId + "'";
        UserAccessControl useraccessControl = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                useraccessControl = new UserAccessControl();
                useraccessControl.setId(rs.getString("id"));
                useraccessControl.setUserId(rs.getString("user_id"));
                useraccessControl.setItemAdd(rs.getString("item_add"));
                useraccessControl.setItemEdit(rs.getString("item_edit"));
                useraccessControl.setItemDelete(rs.getString("item_delete"));
                useraccessControl.setItemActivityConfig(rs.getString("item_activity_config"));
                useraccessControl.setItemActivityAdd(rs.getString("item_activity_add"));
                useraccessControl.setItemActivityEdit(rs.getString("item_activity_edit"));
                useraccessControl.setItemHardwareAdd(rs.getString("item_hardware_add"));
                useraccessControl.setItemHardwareEdit(rs.getString("item_hardware_edit"));
                useraccessControl.setItemHardwareDelete(rs.getString("item_hardware_delete"));
                useraccessControl.setItemMovementAdd(rs.getString("item_movement_add"));
                useraccessControl.setItemSfRecall(rs.getString("item_sf_recall"));
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
        return useraccessControl;
    }

    public List<UserAccessControl> getUserAccessControlList() {
        String sql = "SELECT * FROM user_access_control ORDER BY id ASC";
        List<UserAccessControl> useraccessControlList = new ArrayList<UserAccessControl>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserAccessControl useraccessControl;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                useraccessControl = new UserAccessControl();
                useraccessControl.setId(rs.getString("id"));
                useraccessControl.setUserId(rs.getString("user_id"));
                useraccessControl.setItemAdd(rs.getString("item_add"));
                useraccessControl.setItemEdit(rs.getString("item_edit"));
                useraccessControl.setItemDelete(rs.getString("item_delete"));
                useraccessControl.setItemActivityConfig(rs.getString("item_activity_config"));
                useraccessControl.setItemActivityAdd(rs.getString("item_activity_add"));
                useraccessControl.setItemActivityEdit(rs.getString("item_activity_edit"));
                useraccessControl.setItemHardwareAdd(rs.getString("item_hardware_add"));
                useraccessControl.setItemHardwareEdit(rs.getString("item_hardware_edit"));
                useraccessControl.setItemHardwareDelete(rs.getString("item_hardware_delete"));
                useraccessControl.setItemMovementAdd(rs.getString("item_movement_add"));
                useraccessControl.setItemSfRecall(rs.getString("item_sf_recall"));
                useraccessControlList.add(useraccessControl);
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
        return useraccessControlList;
    }

    public Integer getCountByUserId(String userId) {
        Integer count = null;
        String sql = "SELECT count(id) AS count FROM user_access_control WHERE user_id = '" + userId + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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
}
