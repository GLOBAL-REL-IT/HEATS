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
import com.onsemi.mib.model.Equipment;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EquipmentDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEquipment(Equipment equipment) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO equipment (spts_pkid, equipment_id, family_pkid, rel_test_group_pkid, current_status, equipment_type, equipment_manufacturer, equipment_model, cbms_type, "
                    + "remarks, equip_tech_pkid, equip_capability, equip_monitoring_pkid, vi_monitoring_pkid, created_by, created_date, flag, "
                    + "slot_qty, rack_qty, zone_per_rack, tray_qty_per_rack, basket_qty_per_rack, tray_qty_per_zone, basket_qty_per_zone) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, equipment.getSptsPkid());
            ps.setString(2, equipment.getEquipmentId());
            ps.setString(3, equipment.getFamilyPkid());
            ps.setString(4, equipment.getRelTestGroupPkid());
            ps.setString(5, equipment.getCurrentStatus());
            ps.setString(6, equipment.getEquipmentType());
            ps.setString(7, equipment.getEquipmentManufacturer());
            ps.setString(8, equipment.getEquipmentModel());
            ps.setString(9, equipment.getCbmsType());
            ps.setString(10, equipment.getRemarks());
            ps.setString(11, equipment.getEquipTechPkid());
            ps.setString(12, equipment.getEquipCapability());
            ps.setString(13, equipment.getEquipMonitoringPkid());
            ps.setString(14, equipment.getViMonitoringPkid());
            ps.setString(15, equipment.getCreatedBy());
            ps.setString(16, equipment.getFlag());
            ps.setString(17, equipment.getSlot());
            ps.setString(18, equipment.getRackTotal());
            ps.setString(19, equipment.getZonePerRack());
            ps.setString(20, equipment.getTrayQtyPerRack());
            ps.setString(21, equipment.getBasketQtyPerRack());
            ps.setString(22, equipment.getTrayQtyPerZone());
            ps.setString(23, equipment.getBasketQtyPerZone());
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

    public QueryResult updateEquipment(Equipment equipment) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment SET spts_pkid = ?, equipment_id = ?, family_pkid = ?, rel_test_group_pkid = ?, current_status = ?, equipment_type = ?, equipment_manufacturer = ?, equipment_model = ?, cbms_type = ?, remarks = ?, equip_tech_pkid = ?, equip_capability = ?, equip_monitoring_pkid = ?, vi_monitoring_pkid = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, equipment.getSptsPkid());
            ps.setString(2, equipment.getEquipmentId());
            ps.setString(3, equipment.getFamilyPkid());
            ps.setString(4, equipment.getRelTestGroupPkid());
            ps.setString(5, equipment.getCurrentStatus());
            ps.setString(6, equipment.getEquipmentType());
            ps.setString(7, equipment.getEquipmentManufacturer());
            ps.setString(8, equipment.getEquipmentModel());
            ps.setString(9, equipment.getCbmsType());
            ps.setString(10, equipment.getRemarks());
            ps.setString(11, equipment.getEquipTechPkid());
            ps.setString(12, equipment.getEquipCapability());
            ps.setString(13, equipment.getEquipMonitoringPkid());
            ps.setString(14, equipment.getViMonitoringPkid());
            ps.setString(15, equipment.getCreatedBy());
            ps.setString(16, equipment.getCreatedDate());
            ps.setString(17, equipment.getFlag());
            ps.setString(18, equipment.getId());
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

    public QueryResult updateEquipmentBySptsPkid(Equipment equipment) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment SET spts_pkid = ?, equipment_id = ?, family_pkid = ?, rel_test_group_pkid = ?, current_status = ?, equipment_type = ?, "
                    + "equipment_manufacturer = ?, equipment_model = ?, cbms_type = ?, remarks = ?, equip_tech_pkid = ?, equip_capability = ?, equip_monitoring_pkid = ?, "
                    + "vi_monitoring_pkid = ?, flag = ?, "
                    + "slot_qty = ?, rack_qty = ?, zone_per_rack = ?, tray_qty_per_rack = ?, basket_qty_per_rack = ?, tray_qty_per_zone = ?, basket_qty_per_zone = ? "
                    + "WHERE spts_pkid = ?"
            );
            ps.setString(1, equipment.getSptsPkid());
            ps.setString(2, equipment.getEquipmentId());
            ps.setString(3, equipment.getFamilyPkid());
            ps.setString(4, equipment.getRelTestGroupPkid());
            ps.setString(5, equipment.getCurrentStatus());
            ps.setString(6, equipment.getEquipmentType());
            ps.setString(7, equipment.getEquipmentManufacturer());
            ps.setString(8, equipment.getEquipmentModel());
            ps.setString(9, equipment.getCbmsType());
            ps.setString(10, equipment.getRemarks());
            ps.setString(11, equipment.getEquipTechPkid());
            ps.setString(12, equipment.getEquipCapability());
            ps.setString(13, equipment.getEquipMonitoringPkid());
            ps.setString(14, equipment.getViMonitoringPkid());
            ps.setString(15, equipment.getFlag());
            ps.setString(16, equipment.getSlot());
            ps.setString(17, equipment.getRackTotal());
            ps.setString(18, equipment.getZonePerRack());
            ps.setString(19, equipment.getTrayQtyPerRack());
            ps.setString(20, equipment.getBasketQtyPerRack());
            ps.setString(21, equipment.getTrayQtyPerZone());
            ps.setString(22, equipment.getBasketQtyPerZone());
            ps.setString(23, equipment.getSptsPkid());
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

    public QueryResult deleteEquipment(String equipmentId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment WHERE id = '" + equipmentId + "'"
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

    public Equipment getEquipment(String equipmentId) {
        String sql = "SELECT * FROM equipment WHERE id = '" + equipmentId + "'";
        Equipment equipment = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipment = new Equipment();
                equipment.setId(rs.getString("id"));
                equipment.setSptsPkid(rs.getString("spts_pkid"));
                equipment.setEquipmentId(rs.getString("equipment_id"));
                equipment.setFamilyPkid(rs.getString("family_pkid"));
                equipment.setRelTestGroupPkid(rs.getString("rel_test_group_pkid"));
                equipment.setCurrentStatus(rs.getString("current_status"));
                equipment.setEquipmentType(rs.getString("equipment_type"));
                equipment.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                equipment.setEquipmentModel(rs.getString("equipment_model"));
                equipment.setCbmsType(rs.getString("cbms_type"));
                equipment.setRemarks(rs.getString("remarks"));
                equipment.setEquipTechPkid(rs.getString("equip_tech_pkid"));
                equipment.setEquipCapability(rs.getString("equip_capability"));
                equipment.setEquipMonitoringPkid(rs.getString("equip_monitoring_pkid"));
                equipment.setViMonitoringPkid(rs.getString("vi_monitoring_pkid"));
                equipment.setCreatedBy(rs.getString("created_by"));
                equipment.setCreatedDate(rs.getString("created_date"));
                equipment.setFlag(rs.getString("flag"));
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
        return equipment;
    }

    public List<Equipment> getEquipmentList() {
        String sql = "SELECT * FROM equipment ORDER BY id ASC";
        List<Equipment> equipmentList = new ArrayList<Equipment>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Equipment equipment;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipment = new Equipment();
                equipment.setId(rs.getString("id"));
                equipment.setSptsPkid(rs.getString("spts_pkid"));
                equipment.setEquipmentId(rs.getString("equipment_id"));
                equipment.setFamilyPkid(rs.getString("family_pkid"));
                equipment.setRelTestGroupPkid(rs.getString("rel_test_group_pkid"));
                equipment.setCurrentStatus(rs.getString("current_status"));
                equipment.setEquipmentType(rs.getString("equipment_type"));
                equipment.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                equipment.setEquipmentModel(rs.getString("equipment_model"));
                equipment.setCbmsType(rs.getString("cbms_type"));
                equipment.setRemarks(rs.getString("remarks"));
                equipment.setEquipTechPkid(rs.getString("equip_tech_pkid"));
                equipment.setEquipCapability(rs.getString("equip_capability"));
                equipment.setEquipMonitoringPkid(rs.getString("equip_monitoring_pkid"));
                equipment.setViMonitoringPkid(rs.getString("vi_monitoring_pkid"));
                equipment.setCreatedBy(rs.getString("created_by"));
                equipment.setCreatedDate(rs.getString("created_date"));
                equipment.setFlag(rs.getString("flag"));
                equipmentList.add(equipment);
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
        return equipmentList;
    }

    public List<Equipment> getEquipmentListByRelTestGroupPkid(String relTestGroupPkid) {
        String sql = "SELECT eq.*, fa.family_name, rel.rel_test_group_name, "
                + "IF(eq.current_status = '1', 'Active','Inactive') AS statusName, IF(eq.equipment_type = '1', 'Life','Environment') AS eqptType "
                + "FROM equipment eq "
                + "LEFT JOIN equipment_family fa ON fa.spts_pkid = eq.family_pkid "
                + "LEFT JOIN equipment_rel_test_group rel ON rel.spts_pkid = eq.rel_test_group_pkid "
                + "WHERE rel_test_group_pkid = '" + relTestGroupPkid + "' ORDER BY equipment_id ASC";
        List<Equipment> equipmentList = new ArrayList<Equipment>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Equipment equipment;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipment = new Equipment();
                equipment.setId(rs.getString("id"));
                equipment.setSptsPkid(rs.getString("spts_pkid"));
                equipment.setEquipmentId(rs.getString("equipment_id"));
                equipment.setFamilyPkid(rs.getString("family_pkid"));
                equipment.setRelTestGroupPkid(rs.getString("rel_test_group_pkid"));
                equipment.setCurrentStatus(rs.getString("current_status"));
                equipment.setEquipmentType(rs.getString("eqptType"));
                equipment.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                equipment.setEquipmentModel(rs.getString("equipment_model"));
                equipment.setCbmsType(rs.getString("cbms_type"));
                equipment.setRemarks(rs.getString("remarks"));
                equipment.setEquipTechPkid(rs.getString("equip_tech_pkid"));
                equipment.setEquipCapability(rs.getString("equip_capability"));
                equipment.setEquipMonitoringPkid(rs.getString("equip_monitoring_pkid"));
                equipment.setViMonitoringPkid(rs.getString("vi_monitoring_pkid"));
                equipment.setCreatedBy(rs.getString("created_by"));
                equipment.setCreatedDate(rs.getString("created_date"));
                equipment.setFlag(rs.getString("flag"));
                equipment.setFamilyName(rs.getString("fa.family_name"));
                equipment.setRelTestGroup(rs.getString("rel.rel_test_group_name"));
                equipment.setStatusName(rs.getString("statusName"));
                equipment.setEqptTypeName(rs.getString("eqptType"));
                equipmentList.add(equipment);
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
        return equipmentList;
    }

    public Integer getCountPkid(String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment inc WHERE inc.spts_pkid = '" + pkid + "'"
            );
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
