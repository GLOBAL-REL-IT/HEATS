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
                    + "eqpt_tech_add, eqpt_tech_delete, eqpt_mon_add, eqpt_mon_delete, eqpt_vi_mon_add, eqpt_vi_mon_delete, eqpt_family_add_global, eqpt_rel_test_group_add_global, "
                    + "bef_loading_priority, bef_loading_hw_replace, bef_loading_sf_recall, bef_loading_hw_register, bef_loading_hw_finalize, bef_loading_vm, bef_loading_ft, bef_loading_release, bef_loading_return_defective, "
                    + "unloading_hw_return, unloading_ionic, unloading_vm, unloading_ft, unloading_release_close) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
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

            ps.setString(28, useraccessControl.getBefLoadingPriority());
            ps.setString(29, useraccessControl.getBefLoadingHwReplace());
            ps.setString(30, useraccessControl.getBefLoadingSfRecall());
            ps.setString(31, useraccessControl.getBefLoadingHwRegister());
            ps.setString(32, useraccessControl.getBefLoadingHwFinalize());
            ps.setString(33, useraccessControl.getBefLoadingVm());
            ps.setString(34, useraccessControl.getBefLoadingFt());
            ps.setString(35, useraccessControl.getBefLoadingRelease());
            ps.setString(36, useraccessControl.getBefLoadingReturnDefective());

            ps.setString(37, useraccessControl.getUnloadingHwReturn());
            ps.setString(38, useraccessControl.getUnloadingIonic());
            ps.setString(39, useraccessControl.getUnloadingVm());
            ps.setString(40, useraccessControl.getUnloadingFt());
            ps.setString(41, useraccessControl.getUnloadingReleaseClose());
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
                    + "eqpt_family_add_global = ?, eqpt_rel_test_group_add_global = ?, "
                    + "bef_loading_priority = ?, bef_loading_hw_replace = ?, bef_loading_sf_recall = ?, bef_loading_hw_register = ?, bef_loading_hw_finalize = ?, bef_loading_vm = ?, bef_loading_ft = ?, "
                    + "bef_loading_release = ?, bef_loading_return_defective = ?, "
                    + "unloading_hw_return = ?, unloading_ionic = ?, unloading_vm = ?, unloading_ft = ?, unloading_release_close = ? "
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

            ps.setString(28, useraccessControl.getBefLoadingPriority());
            ps.setString(29, useraccessControl.getBefLoadingHwReplace());
            ps.setString(30, useraccessControl.getBefLoadingSfRecall());
            ps.setString(31, useraccessControl.getBefLoadingHwRegister());
            ps.setString(32, useraccessControl.getBefLoadingHwFinalize());
            ps.setString(33, useraccessControl.getBefLoadingVm());
            ps.setString(34, useraccessControl.getBefLoadingFt());
            ps.setString(35, useraccessControl.getBefLoadingRelease());
            ps.setString(36, useraccessControl.getBefLoadingReturnDefective());

            ps.setString(37, useraccessControl.getUnloadingHwReturn());
            ps.setString(38, useraccessControl.getUnloadingIonic());
            ps.setString(39, useraccessControl.getUnloadingVm());
            ps.setString(40, useraccessControl.getUnloadingFt());
            ps.setString(41, useraccessControl.getUnloadingReleaseClose());

            ps.setString(42, useraccessControl.getUserId());
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
                    "DELETE FROM user_access_control WHERE id = ? "
            );
            ps.setString(1, useraccessControlId);
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
        String sql = "SELECT * FROM user_access_control WHERE id = ? ";
        UserAccessControl useraccessControl = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, useraccessControlId);
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
                useraccessControl.setEqptAdd(rs.getString("eqpt_add"));
                useraccessControl.setEqptEdit(rs.getString("eqpt_edit"));
                useraccessControl.setEqptDelete(rs.getString("eqpt_delete"));
                useraccessControl.setEqptFamilyAdd(rs.getString("eqpt_family_add"));
                useraccessControl.setEqptFamilyDelete(rs.getString("eqpt_family_delete"));
                useraccessControl.setEqptRelTestGroupAdd(rs.getString("eqpt_rel_test_group_add"));
                useraccessControl.setEqptRelTestGroupDelete(rs.getString("eqpt_rel__test_group_delete"));
                useraccessControl.setEqptTechAdd(rs.getString("eqpt_tech_add"));
                useraccessControl.setEqptTechDelete(rs.getString("eqpt_tech_delete"));
                useraccessControl.setEqptMonAdd(rs.getString("eqpt_mon_add"));
                useraccessControl.setEqptMonDelete(rs.getString("eqpt_mon_delete"));
                useraccessControl.setEqptViMonAdd(rs.getString("eqpt_vi_mon_add"));
                useraccessControl.setEqptViMonDelete(rs.getString("eqpt_vi_mon_delete"));
                useraccessControl.setEqptFamilyAddGlobal(rs.getString("eqpt_family_add_global"));
                useraccessControl.setEqptRelTestGroupAddGlobal(rs.getString("eqpt_rel_test_group_add_global"));
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

    public UserAccessControl getUserAccessControlByUserId(String userId) {
        String sql = "SELECT * FROM user_access_control WHERE user_id = ? ";
        UserAccessControl useraccessControl = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
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
                useraccessControl.setEqptAdd(rs.getString("eqpt_add"));
                useraccessControl.setEqptEdit(rs.getString("eqpt_edit"));
                useraccessControl.setEqptDelete(rs.getString("eqpt_delete"));
                useraccessControl.setEqptFamilyAdd(rs.getString("eqpt_family_add"));
                useraccessControl.setEqptFamilyDelete(rs.getString("eqpt_family_delete"));
                useraccessControl.setEqptRelTestGroupAdd(rs.getString("eqpt_rel_test_group_add"));
                useraccessControl.setEqptRelTestGroupDelete(rs.getString("eqpt_rel__test_group_delete"));
                useraccessControl.setEqptTechAdd(rs.getString("eqpt_tech_add"));
                useraccessControl.setEqptTechDelete(rs.getString("eqpt_tech_delete"));
                useraccessControl.setEqptMonAdd(rs.getString("eqpt_mon_add"));
                useraccessControl.setEqptMonDelete(rs.getString("eqpt_mon_delete"));
                useraccessControl.setEqptViMonAdd(rs.getString("eqpt_vi_mon_add"));
                useraccessControl.setEqptViMonDelete(rs.getString("eqpt_vi_mon_delete"));
                useraccessControl.setEqptFamilyAddGlobal(rs.getString("eqpt_family_add_global"));
                useraccessControl.setEqptRelTestGroupAddGlobal(rs.getString("eqpt_rel_test_group_add_global"));
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

    public UserAccessControl getUserAccessControlByLoginId(String loginId) {
        String sql = "SELECT u.*, l.login_id FROM user_ldap l "
                + "LEFT JOIN user_access_control u ON l.id = u.user_id "
                + "WHERE l.login_id = ? ";
        UserAccessControl useraccessControl = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loginId);
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
                useraccessControl.setEqptAdd(rs.getString("eqpt_add"));
                useraccessControl.setEqptEdit(rs.getString("eqpt_edit"));
                useraccessControl.setEqptDelete(rs.getString("eqpt_delete"));
                useraccessControl.setEqptFamilyAdd(rs.getString("eqpt_family_add"));
                useraccessControl.setEqptFamilyDelete(rs.getString("eqpt_family_delete"));
                useraccessControl.setEqptRelTestGroupAdd(rs.getString("eqpt_rel_test_group_add"));
                useraccessControl.setEqptRelTestGroupDelete(rs.getString("eqpt_rel__test_group_delete"));
                useraccessControl.setEqptTechAdd(rs.getString("eqpt_tech_add"));
                useraccessControl.setEqptTechDelete(rs.getString("eqpt_tech_delete"));
                useraccessControl.setEqptMonAdd(rs.getString("eqpt_mon_add"));
                useraccessControl.setEqptMonDelete(rs.getString("eqpt_mon_delete"));
                useraccessControl.setEqptViMonAdd(rs.getString("eqpt_vi_mon_add"));
                useraccessControl.setEqptViMonDelete(rs.getString("eqpt_vi_mon_delete"));
                useraccessControl.setEqptFamilyAddGlobal(rs.getString("eqpt_family_add_global"));
                useraccessControl.setEqptRelTestGroupAddGlobal(rs.getString("eqpt_rel_test_group_add_global"));

                useraccessControl.setBefLoadingPriority(rs.getString("bef_loading_priority"));
                useraccessControl.setBefLoadingHwReplace(rs.getString("bef_loading_hw_replace"));
                useraccessControl.setBefLoadingSfRecall(rs.getString("bef_loading_sf_recall"));
                useraccessControl.setBefLoadingHwRegister(rs.getString("bef_loading_hw_register"));
                useraccessControl.setBefLoadingHwFinalize(rs.getString("bef_loading_hw_finalize"));
                useraccessControl.setBefLoadingVm(rs.getString("bef_loading_vm"));
                useraccessControl.setBefLoadingFt(rs.getString("bef_loading_ft"));
                useraccessControl.setBefLoadingRelease(rs.getString("bef_loading_release"));
                useraccessControl.setBefLoadingReturnDefective(rs.getString("bef_loading_return_defective"));

                useraccessControl.setUnloadingHwReturn(rs.getString("unloading_hw_return"));
                useraccessControl.setUnloadingIonic(rs.getString("unloading_ionic"));
                useraccessControl.setUnloadingVm(rs.getString("unloading_vm"));
                useraccessControl.setUnloadingFt(rs.getString("unloading_ft"));
                useraccessControl.setUnloadingReleaseClose(rs.getString("unloading_release_close"));
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
        String sql = "SELECT count(id) AS count FROM user_access_control WHERE user_id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
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
