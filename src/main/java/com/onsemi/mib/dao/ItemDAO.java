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
import com.onsemi.mib.model.Item;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertHardwareDetail(Item hardwaredetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item (spts_pkid, item_type, sub_type, item_id, item_name, assembly_id, rack, shelf, on_hand_qty, production_staging_qty, production_qty, repair_qty, other_qty, quarantine_qty, external_clean_qty, external_reclean_qty, internal_clean_qty, internal_reclean_qty, storage_factory_qty, other_onsemi_qty, vendor_qty, total_qty, unit_cost, total_cost, status, alu_hrs, movement_alu_hrs, min_qty, max_qty, pm_ww1, pm_ww2, expiration_date, is_critical, is_consumable, downtime_value, downtime_unit, implementation_cost, manpower_value, manpower_unit, complexity, model, manufacturer, equipment_type, equipment_model, equipment_manufacturer, stress_type, remarks, flag, created_by, created_date, site_name, item_usage) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, hardwaredetail.getSptsPkid());
            ps.setString(2, hardwaredetail.getItemType());
            ps.setString(3, hardwaredetail.getSubType());
            ps.setString(4, hardwaredetail.getItemId());
            ps.setString(5, hardwaredetail.getItemName());
            ps.setString(6, hardwaredetail.getAssemblyId());
            ps.setString(7, hardwaredetail.getRack());
            ps.setString(8, hardwaredetail.getShelf());
            ps.setString(9, hardwaredetail.getOnHandQty());
            ps.setString(10, hardwaredetail.getProductionStagingQty());
            ps.setString(11, hardwaredetail.getProductionQty());
            ps.setString(12, hardwaredetail.getRepairQty());
            ps.setString(13, hardwaredetail.getOtherQty());
            ps.setString(14, hardwaredetail.getQuarantineQty());
            ps.setString(15, hardwaredetail.getExternalCleanQty());
            ps.setString(16, hardwaredetail.getExternalRecleanQty());
            ps.setString(17, hardwaredetail.getInternalCleanQty());
            ps.setString(18, hardwaredetail.getInternalRecleanQty());
            ps.setString(19, hardwaredetail.getStorageFactoryQty());
            ps.setString(20, hardwaredetail.getOtherOnsemiQty());
            ps.setString(21, hardwaredetail.getVendorQty());
            ps.setString(22, hardwaredetail.getTotalQty());
            ps.setString(23, hardwaredetail.getUnitCost());
            ps.setString(24, hardwaredetail.getTotalCost());
            ps.setString(25, hardwaredetail.getStatus());
            ps.setString(26, hardwaredetail.getAluHrs());
            ps.setString(27, hardwaredetail.getMovementAluHrs());
            ps.setString(28, hardwaredetail.getMinQty());
            ps.setString(29, hardwaredetail.getMaxQty());
            ps.setString(30, hardwaredetail.getPmWw1());
            ps.setString(31, hardwaredetail.getPmWw2());
            ps.setString(32, hardwaredetail.getExpirationDate());
            ps.setString(33, hardwaredetail.getIsCritical());
            ps.setString(34, hardwaredetail.getIsConsumable());
            ps.setString(35, hardwaredetail.getDowntimeValue());
            ps.setString(36, hardwaredetail.getDowntimeUnit());
            ps.setString(37, hardwaredetail.getImplementationCost());
            ps.setString(38, hardwaredetail.getManpowerValue());
            ps.setString(39, hardwaredetail.getManpowerUnit());
            ps.setString(40, hardwaredetail.getComplexity());
            ps.setString(41, hardwaredetail.getModel());
            ps.setString(42, hardwaredetail.getManufacturer());
            ps.setString(43, hardwaredetail.getEquipmentType());
            ps.setString(44, hardwaredetail.getEquipmentModel());
            ps.setString(45, hardwaredetail.getEquipmentManufacturer());
            ps.setString(46, hardwaredetail.getStressType());
            ps.setString(47, hardwaredetail.getRemarks());
            ps.setString(48, hardwaredetail.getFlag());
            ps.setString(49, hardwaredetail.getCreatedBy());
            ps.setString(50, hardwaredetail.getSiteName());
            ps.setString(51, hardwaredetail.getItemUsage());
//            ps.setString(50, hardwaredetail.getCreatedDate());
//            ps.setString(51, hardwaredetail.getModifedBy());
//            ps.setString(52, hardwaredetail.getModifiedDate());
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

    public QueryResult updateHardwareDetailFromSpts(Item hardwaredetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item SET item_type = ?, sub_type = ?, item_id = ?, item_name = ?, assembly_id = ?, rack = ?, shelf = ?, on_hand_qty = ?, production_staging_qty = ?, production_qty = ?, repair_qty = ?, other_qty = ?, quarantine_qty = ?, external_clean_qty = ?, external_reclean_qty = ?, internal_clean_qty = ?, internal_reclean_qty = ?, storage_factory_qty = ?, other_onsemi_qty = ?, vendor_qty = ?, total_qty = ?, unit_cost = ?, total_cost = ?, status = ?, alu_hrs = ?, movement_alu_hrs = ?, min_qty = ?, max_qty = ?, pm_ww1 = ?, pm_ww2 = ?, expiration_date = ?, is_critical = ?, is_consumable = ?, downtime_value = ?, downtime_unit = ?, implementation_cost = ?, manpower_value = ?, manpower_unit = ?, complexity = ?, model = ?, manufacturer = ?, equipment_type = ?, equipment_model = ?, equipment_manufacturer = ?, stress_type = ?, remarks = ?, flag = ?, site_name = ?, modifed_by = ?, modified_date = NOW() WHERE spts_pkid = ?"
            );
            ps.setString(1, hardwaredetail.getItemType());
            ps.setString(2, hardwaredetail.getSubType());
            ps.setString(3, hardwaredetail.getItemId());
            ps.setString(4, hardwaredetail.getItemName());
            ps.setString(5, hardwaredetail.getAssemblyId());
            ps.setString(6, hardwaredetail.getRack());
            ps.setString(7, hardwaredetail.getShelf());
            ps.setString(8, hardwaredetail.getOnHandQty());
            ps.setString(9, hardwaredetail.getProductionStagingQty());
            ps.setString(10, hardwaredetail.getProductionQty());
            ps.setString(11, hardwaredetail.getRepairQty());
            ps.setString(12, hardwaredetail.getOtherQty());
            ps.setString(13, hardwaredetail.getQuarantineQty());
            ps.setString(14, hardwaredetail.getExternalCleanQty());
            ps.setString(15, hardwaredetail.getExternalRecleanQty());
            ps.setString(16, hardwaredetail.getInternalCleanQty());
            ps.setString(17, hardwaredetail.getInternalRecleanQty());
            ps.setString(18, hardwaredetail.getStorageFactoryQty());
            ps.setString(19, hardwaredetail.getOtherOnsemiQty());
            ps.setString(20, hardwaredetail.getVendorQty());
            ps.setString(21, hardwaredetail.getTotalQty());
            ps.setString(22, hardwaredetail.getUnitCost());
            ps.setString(23, hardwaredetail.getTotalCost());
            ps.setString(24, hardwaredetail.getStatus());
            ps.setString(25, hardwaredetail.getAluHrs());
            ps.setString(26, hardwaredetail.getMovementAluHrs());
            ps.setString(27, hardwaredetail.getMinQty());
            ps.setString(28, hardwaredetail.getMaxQty());
            ps.setString(29, hardwaredetail.getPmWw1());
            ps.setString(30, hardwaredetail.getPmWw2());
            ps.setString(31, hardwaredetail.getExpirationDate());
            ps.setString(32, hardwaredetail.getIsCritical());
            ps.setString(33, hardwaredetail.getIsConsumable());
            ps.setString(34, hardwaredetail.getDowntimeValue());
            ps.setString(35, hardwaredetail.getDowntimeUnit());
            ps.setString(36, hardwaredetail.getImplementationCost());
            ps.setString(37, hardwaredetail.getManpowerValue());
            ps.setString(38, hardwaredetail.getManpowerUnit());
            ps.setString(39, hardwaredetail.getComplexity());
            ps.setString(40, hardwaredetail.getModel());
            ps.setString(41, hardwaredetail.getManufacturer());
            ps.setString(42, hardwaredetail.getEquipmentType());
            ps.setString(43, hardwaredetail.getEquipmentModel());
            ps.setString(44, hardwaredetail.getEquipmentManufacturer());
            ps.setString(45, hardwaredetail.getStressType());
            ps.setString(46, hardwaredetail.getRemarks());
            ps.setString(47, hardwaredetail.getFlag());
            ps.setString(48, hardwaredetail.getSiteName());
            ps.setString(49, hardwaredetail.getModifedBy());
            ps.setString(50, hardwaredetail.getSptsPkid());
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

    public QueryResult updateHardwareDetail(Item hardwaredetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item SET spts_pkid = ?, item_type = ?, sub_type = ?, item_id = ?, item_name = ?, assembly_id = ?, rack = ?, shelf = ?, on_hand_qty = ?, "
                    + "production_staging_qty = ?, production_qty = ?, repair_qty = ?, other_qty = ?, quarantine_qty = ?, external_clean_qty = ?, "
                    + "external_reclean_qty = ?, internal_clean_qty = ?, internal_reclean_qty = ?, storage_factory_qty = ?, other_onsemi_qty = ?, "
                    + "vendor_qty = ?, total_qty = ?, unit_cost = ?, total_cost = ?, status = ?, alu_hrs = ?, movement_alu_hrs = ?, min_qty = ?, "
                    + "max_qty = ?, pm_ww1 = ?, pm_ww2 = ?, expiration_date = ?, is_critical = ?, is_consumable = ?, downtime_value = ?, downtime_unit = ?, "
                    + "implementation_cost = ?, manpower_value = ?, manpower_unit = ?, complexity = ?, model = ?, manufacturer = ?, equipment_type = ?, "
                    + "equipment_model = ?, equipment_manufacturer = ?, stress_type = ?, remarks = ?, flag = ?, modifed_by = ?, modified_date = NOW(), item_usage = ? WHERE id = ?"
            );
            ps.setString(1, hardwaredetail.getSptsPkid());
            ps.setString(2, hardwaredetail.getItemType());
            ps.setString(3, hardwaredetail.getSubType());
            ps.setString(4, hardwaredetail.getItemId());
            ps.setString(5, hardwaredetail.getItemName());
            ps.setString(6, hardwaredetail.getAssemblyId());
            ps.setString(7, hardwaredetail.getRack());
            ps.setString(8, hardwaredetail.getShelf());
            ps.setString(9, hardwaredetail.getOnHandQty());
            ps.setString(10, hardwaredetail.getProductionStagingQty());
            ps.setString(11, hardwaredetail.getProductionQty());
            ps.setString(12, hardwaredetail.getRepairQty());
            ps.setString(13, hardwaredetail.getOtherQty());
            ps.setString(14, hardwaredetail.getQuarantineQty());
            ps.setString(15, hardwaredetail.getExternalCleanQty());
            ps.setString(16, hardwaredetail.getExternalRecleanQty());
            ps.setString(17, hardwaredetail.getInternalCleanQty());
            ps.setString(18, hardwaredetail.getInternalRecleanQty());
            ps.setString(19, hardwaredetail.getStorageFactoryQty());
            ps.setString(20, hardwaredetail.getOtherOnsemiQty());
            ps.setString(21, hardwaredetail.getVendorQty());
            ps.setString(22, hardwaredetail.getTotalQty());
            ps.setString(23, hardwaredetail.getUnitCost());
            ps.setString(24, hardwaredetail.getTotalCost());
            ps.setString(25, hardwaredetail.getStatus());
            ps.setString(26, hardwaredetail.getAluHrs());
            ps.setString(27, hardwaredetail.getMovementAluHrs());
            ps.setString(28, hardwaredetail.getMinQty());
            ps.setString(29, hardwaredetail.getMaxQty());
            ps.setString(30, hardwaredetail.getPmWw1());
            ps.setString(31, hardwaredetail.getPmWw2());
            ps.setString(32, hardwaredetail.getExpirationDate());
            ps.setString(33, hardwaredetail.getIsCritical());
            ps.setString(34, hardwaredetail.getIsConsumable());
            ps.setString(35, hardwaredetail.getDowntimeValue());
            ps.setString(36, hardwaredetail.getDowntimeUnit());
            ps.setString(37, hardwaredetail.getImplementationCost());
            ps.setString(38, hardwaredetail.getManpowerValue());
            ps.setString(39, hardwaredetail.getManpowerUnit());
            ps.setString(40, hardwaredetail.getComplexity());
            ps.setString(41, hardwaredetail.getModel());
            ps.setString(42, hardwaredetail.getManufacturer());
            ps.setString(43, hardwaredetail.getEquipmentType());
            ps.setString(44, hardwaredetail.getEquipmentModel());
            ps.setString(45, hardwaredetail.getEquipmentManufacturer());
            ps.setString(46, hardwaredetail.getStressType());
            ps.setString(47, hardwaredetail.getRemarks());
            ps.setString(48, hardwaredetail.getFlag());
            ps.setString(49, hardwaredetail.getModifedBy());
            ps.setString(50, hardwaredetail.getItemUsage());
            ps.setString(51, hardwaredetail.getId());
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

    public QueryResult updateHardwareDetail2(Item hardwaredetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item SET item_type = ?, sub_type = ?, item_id = ?, item_name = ?, assembly_id = ?, rack = ?, shelf = ?, on_hand_qty = ?, "
                    + "production_staging_qty = ?, production_qty = ?, repair_qty = ?, other_qty = ?, quarantine_qty = ?, external_clean_qty = ?, "
                    + "external_reclean_qty = ?, internal_clean_qty = ?, internal_reclean_qty = ?, storage_factory_qty = ?, other_onsemi_qty = ?, "
                    + "vendor_qty = ?, total_qty = ?, unit_cost = ?, total_cost = ?, status = ?, alu_hrs = ?, movement_alu_hrs = ?, min_qty = ?, "
                    + "max_qty = ?, pm_ww1 = ?, pm_ww2 = ?, expiration_date = ?, is_critical = ?, is_consumable = ?, downtime_value = ?, downtime_unit = ?, "
                    + "implementation_cost = ?, manpower_value = ?, manpower_unit = ?, complexity = ?, model = ?, manufacturer = ?, equipment_type = ?, "
                    + "equipment_model = ?, equipment_manufacturer = ?, stress_type = ?, remarks = ?, flag = ?, modifed_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, hardwaredetail.getItemType());
            ps.setString(2, hardwaredetail.getSubType());
            ps.setString(3, hardwaredetail.getItemId());
            ps.setString(4, hardwaredetail.getItemName());
            ps.setString(5, hardwaredetail.getAssemblyId());
            ps.setString(6, hardwaredetail.getRack());
            ps.setString(7, hardwaredetail.getShelf());
            ps.setString(8, hardwaredetail.getOnHandQty());
            ps.setString(9, hardwaredetail.getProductionStagingQty());
            ps.setString(10, hardwaredetail.getProductionQty());
            ps.setString(11, hardwaredetail.getRepairQty());
            ps.setString(12, hardwaredetail.getOtherQty());
            ps.setString(13, hardwaredetail.getQuarantineQty());
            ps.setString(14, hardwaredetail.getExternalCleanQty());
            ps.setString(15, hardwaredetail.getExternalRecleanQty());
            ps.setString(16, hardwaredetail.getInternalCleanQty());
            ps.setString(17, hardwaredetail.getInternalRecleanQty());
            ps.setString(18, hardwaredetail.getStorageFactoryQty());
            ps.setString(19, hardwaredetail.getOtherOnsemiQty());
            ps.setString(20, hardwaredetail.getVendorQty());
            ps.setString(21, hardwaredetail.getTotalQty());
            ps.setString(22, hardwaredetail.getUnitCost());
            ps.setString(23, hardwaredetail.getTotalCost());
            ps.setString(24, hardwaredetail.getStatus());
            ps.setString(25, hardwaredetail.getAluHrs());
            ps.setString(26, hardwaredetail.getMovementAluHrs());
            ps.setString(27, hardwaredetail.getMinQty());
            ps.setString(28, hardwaredetail.getMaxQty());
            ps.setString(29, hardwaredetail.getPmWw1());
            ps.setString(30, hardwaredetail.getPmWw2());
            ps.setString(31, hardwaredetail.getExpirationDate());
            ps.setString(32, hardwaredetail.getIsCritical());
            ps.setString(33, hardwaredetail.getIsConsumable());
            ps.setString(34, hardwaredetail.getDowntimeValue());
            ps.setString(35, hardwaredetail.getDowntimeUnit());
            ps.setString(36, hardwaredetail.getImplementationCost());
            ps.setString(37, hardwaredetail.getManpowerValue());
            ps.setString(38, hardwaredetail.getManpowerUnit());
            ps.setString(39, hardwaredetail.getComplexity());
            ps.setString(40, hardwaredetail.getModel());
            ps.setString(41, hardwaredetail.getManufacturer());
            ps.setString(42, hardwaredetail.getEquipmentType());
            ps.setString(43, hardwaredetail.getEquipmentModel());
            ps.setString(44, hardwaredetail.getEquipmentManufacturer());
            ps.setString(45, hardwaredetail.getStressType());
            ps.setString(46, hardwaredetail.getRemarks());
            ps.setString(47, hardwaredetail.getFlag());
            ps.setString(48, hardwaredetail.getModifedBy());
            ps.setString(49, hardwaredetail.getId());
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

    public QueryResult updateItemStatus(Item hardwaredetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item SET status = ? WHERE id = ?"
            );
            ps.setString(1, hardwaredetail.getStatus());
            ps.setString(2, hardwaredetail.getId());
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

    public QueryResult updateItemSPTSPKID(Item hardwaredetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item SET spts_pkid = ? WHERE id = ?"
            );
            ps.setString(1, hardwaredetail.getSptsPkid());
            ps.setString(2, hardwaredetail.getId());
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

    public QueryResult updateItemStatusAndFlag(Item hardwaredetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item SET status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, hardwaredetail.getStatus());
            ps.setString(2, hardwaredetail.getFlag());
            ps.setString(3, hardwaredetail.getId());
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

    public QueryResult deleteHardwareDetail(String hardwaredetailId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item WHERE id = '" + hardwaredetailId + "'"
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

    public Item getHardwareDetail(String hardwaredetailId) {
        String sql = "SELECT * FROM item WHERE id = '" + hardwaredetailId + "'";
        Item hardwaredetail = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setId(rs.getString("id"));
                hardwaredetail.setSptsPkid(rs.getString("spts_pkid"));
                hardwaredetail.setItemType(rs.getString("item_type"));
                hardwaredetail.setSubType(rs.getString("sub_type"));
                hardwaredetail.setItemId(rs.getString("item_id"));
                hardwaredetail.setItemName(rs.getString("item_name"));
                hardwaredetail.setAssemblyId(rs.getString("assembly_id"));
                hardwaredetail.setRack(rs.getString("rack"));
                hardwaredetail.setShelf(rs.getString("shelf"));
                hardwaredetail.setOnHandQty(rs.getString("on_hand_qty"));
                hardwaredetail.setProductionStagingQty(rs.getString("production_staging_qty"));
                hardwaredetail.setProductionQty(rs.getString("production_qty"));
                hardwaredetail.setRepairQty(rs.getString("repair_qty"));
                hardwaredetail.setOtherQty(rs.getString("other_qty"));
                hardwaredetail.setQuarantineQty(rs.getString("quarantine_qty"));
                hardwaredetail.setExternalCleanQty(rs.getString("external_clean_qty"));
                hardwaredetail.setExternalRecleanQty(rs.getString("external_reclean_qty"));
                hardwaredetail.setInternalCleanQty(rs.getString("internal_clean_qty"));
                hardwaredetail.setInternalRecleanQty(rs.getString("internal_reclean_qty"));
                hardwaredetail.setStorageFactoryQty(rs.getString("storage_factory_qty"));
                hardwaredetail.setOtherOnsemiQty(rs.getString("other_onsemi_qty"));
                hardwaredetail.setVendorQty(rs.getString("vendor_qty"));
                hardwaredetail.setTotalQty(rs.getString("total_qty"));
                hardwaredetail.setUnitCost(rs.getString("unit_cost"));
                hardwaredetail.setTotalCost(rs.getString("total_cost"));
                hardwaredetail.setStatus(rs.getString("status"));
                hardwaredetail.setAluHrs(rs.getString("alu_hrs"));
                hardwaredetail.setMovementAluHrs(rs.getString("movement_alu_hrs"));
                hardwaredetail.setMinQty(rs.getString("min_qty"));
                hardwaredetail.setMaxQty(rs.getString("max_qty"));
                hardwaredetail.setPmWw1(rs.getString("pm_ww1"));
                hardwaredetail.setPmWw2(rs.getString("pm_ww2"));
                hardwaredetail.setExpirationDate(rs.getString("expiration_date"));
                hardwaredetail.setIsCritical(rs.getString("is_critical"));
                hardwaredetail.setIsConsumable(rs.getString("is_consumable"));
                hardwaredetail.setDowntimeValue(rs.getString("downtime_value"));
                hardwaredetail.setDowntimeUnit(rs.getString("downtime_unit"));
                hardwaredetail.setImplementationCost(rs.getString("implementation_cost"));
                hardwaredetail.setManpowerValue(rs.getString("manpower_value"));
                hardwaredetail.setManpowerUnit(rs.getString("manpower_unit"));
                hardwaredetail.setComplexity(rs.getString("complexity"));
                hardwaredetail.setModel(rs.getString("model"));
                hardwaredetail.setManufacturer(rs.getString("manufacturer"));
                hardwaredetail.setEquipmentType(rs.getString("equipment_type"));
                hardwaredetail.setEquipmentModel(rs.getString("equipment_model"));
                hardwaredetail.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                hardwaredetail.setStressType(rs.getString("stress_type"));
                hardwaredetail.setRemarks(rs.getString("remarks"));
                hardwaredetail.setFlag(rs.getString("flag"));
                hardwaredetail.setCreatedBy(rs.getString("created_by"));
                hardwaredetail.setCreatedDate(rs.getString("created_date"));
                hardwaredetail.setModifedBy(rs.getString("modifed_by"));
                hardwaredetail.setModifiedDate(rs.getString("modified_date"));
                hardwaredetail.setItemUsage(rs.getString("item_usage"));
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
        return hardwaredetail;
    }

    public Item getHardwareDetailByPkid(String pkid) {
        String sql = "SELECT *,DATE_FORMAT(expiration_date,'%d-%M-%Y') AS expiration_date FROM item WHERE spts_pkid = '" + pkid + "'";
        Item hardwaredetail = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setId(rs.getString("id"));
                hardwaredetail.setSptsPkid(rs.getString("spts_pkid"));
                hardwaredetail.setItemType(rs.getString("item_type"));
                hardwaredetail.setSubType(rs.getString("sub_type"));
                hardwaredetail.setItemId(rs.getString("item_id"));
                hardwaredetail.setItemName(rs.getString("item_name"));
                hardwaredetail.setAssemblyId(rs.getString("assembly_id"));
                hardwaredetail.setRack(rs.getString("rack"));
                hardwaredetail.setShelf(rs.getString("shelf"));
                hardwaredetail.setOnHandQty(rs.getString("on_hand_qty"));
                hardwaredetail.setProductionStagingQty(rs.getString("production_staging_qty"));
                hardwaredetail.setProductionQty(rs.getString("production_qty"));
                hardwaredetail.setRepairQty(rs.getString("repair_qty"));
                hardwaredetail.setOtherQty(rs.getString("other_qty"));
                hardwaredetail.setQuarantineQty(rs.getString("quarantine_qty"));
                hardwaredetail.setExternalCleanQty(rs.getString("external_clean_qty"));
                hardwaredetail.setExternalRecleanQty(rs.getString("external_reclean_qty"));
                hardwaredetail.setInternalCleanQty(rs.getString("internal_clean_qty"));
                hardwaredetail.setInternalRecleanQty(rs.getString("internal_reclean_qty"));
                hardwaredetail.setStorageFactoryQty(rs.getString("storage_factory_qty"));
                hardwaredetail.setOtherOnsemiQty(rs.getString("other_onsemi_qty"));
                hardwaredetail.setVendorQty(rs.getString("vendor_qty"));
                hardwaredetail.setTotalQty(rs.getString("total_qty"));
                hardwaredetail.setUnitCost(rs.getString("unit_cost"));
                hardwaredetail.setTotalCost(rs.getString("total_cost"));
                hardwaredetail.setStatus(rs.getString("status"));
                hardwaredetail.setAluHrs(rs.getString("alu_hrs"));
                hardwaredetail.setMovementAluHrs(rs.getString("movement_alu_hrs"));
                hardwaredetail.setMinQty(rs.getString("min_qty"));
                hardwaredetail.setMaxQty(rs.getString("max_qty"));
                hardwaredetail.setPmWw1(rs.getString("pm_ww1"));
                hardwaredetail.setPmWw2(rs.getString("pm_ww2"));
                hardwaredetail.setExpirationDate(rs.getString("expiration_date"));
                hardwaredetail.setIsCritical(rs.getString("is_critical"));
                hardwaredetail.setIsConsumable(rs.getString("is_consumable"));
                hardwaredetail.setDowntimeValue(rs.getString("downtime_value"));
                hardwaredetail.setDowntimeUnit(rs.getString("downtime_unit"));
                hardwaredetail.setImplementationCost(rs.getString("implementation_cost"));
                hardwaredetail.setManpowerValue(rs.getString("manpower_value"));
                hardwaredetail.setManpowerUnit(rs.getString("manpower_unit"));
                hardwaredetail.setComplexity(rs.getString("complexity"));
                hardwaredetail.setModel(rs.getString("model"));
                hardwaredetail.setManufacturer(rs.getString("manufacturer"));
                hardwaredetail.setEquipmentType(rs.getString("equipment_type"));
                hardwaredetail.setEquipmentModel(rs.getString("equipment_model"));
                hardwaredetail.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                hardwaredetail.setStressType(rs.getString("stress_type"));
                hardwaredetail.setRemarks(rs.getString("remarks"));
                hardwaredetail.setFlag(rs.getString("flag"));
                hardwaredetail.setCreatedBy(rs.getString("created_by"));
                hardwaredetail.setCreatedDate(rs.getString("created_date"));
                hardwaredetail.setModifedBy(rs.getString("modifed_by"));
                hardwaredetail.setModifiedDate(rs.getString("modified_date"));
                hardwaredetail.setItemUsage(rs.getString("item_usage"));
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
        return hardwaredetail;
    }

    public Item getHardwareByPkid(String pkid) {
        String sql = "SELECT it.id, it.spts_pkid, it.item_type, it.sub_type, it.item_id, it.item_name, it.assembly_id, it.rack, it.shelf, " +
                        "it.on_hand_qty, it.production_staging_qty, it.production_qty, it.repair_qty, it.other_qty, it.quarantine_qty, " +
                        "it.external_clean_qty, it.external_reclean_qty, it.internal_clean_qty, it.internal_reclean_qty, it.storage_factory_qty, " +
                        "it.other_onsemi_qty, it.vendor_qty, it.total_qty, it.unit_cost, it.total_cost, it.status, it.alu_hrs, it.movement_alu_hrs, " +
                        "it.min_qty, it.max_qty, it.pm_ww1, it.pm_ww2, DATE_FORMAT(expiration_date,'%d-%M-%Y') AS expiration_date, it.is_critical, " +
                        "it.is_consumable, it.downtime_value, it.downtime_unit, it.implementation_cost, it.manpower_value, it.manpower_unit, " +
                        "it.complexity, it.model, it.manufacturer, it.equipment_type, it.equipment_model, it.equipment_manufacturer, " +
                        "it.stress_type, it.remarks, it.flag, it.created_by, it.created_date, it.modifed_by, it.modified_date, it.item_usage, " +
                        "cf.item_type AS config FROM item it " +
                        "LEFT JOIN item_hardware_config cf ON it.item_type = cf.item_type AND it.sub_type = cf.sub_type " +
                        "WHERE it.spts_pkid = '" + pkid + "'";
        LOGGER.info("SINI MASUK KE AMIK DATA ITEM");
        Item hardwaredetail = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setId(rs.getString("id"));
                hardwaredetail.setSptsPkid(rs.getString("spts_pkid"));
                hardwaredetail.setItemType(rs.getString("item_type"));
                hardwaredetail.setSubType(rs.getString("sub_type"));
                hardwaredetail.setItemId(rs.getString("item_id"));
                hardwaredetail.setItemName(rs.getString("item_name"));
                hardwaredetail.setAssemblyId(rs.getString("assembly_id"));
                hardwaredetail.setRack(rs.getString("rack"));
                hardwaredetail.setShelf(rs.getString("shelf"));
                hardwaredetail.setOnHandQty(rs.getString("on_hand_qty"));
                hardwaredetail.setProductionStagingQty(rs.getString("production_staging_qty"));
                hardwaredetail.setProductionQty(rs.getString("production_qty"));
                hardwaredetail.setRepairQty(rs.getString("repair_qty"));
                hardwaredetail.setOtherQty(rs.getString("other_qty"));
                hardwaredetail.setQuarantineQty(rs.getString("quarantine_qty"));
                hardwaredetail.setExternalCleanQty(rs.getString("external_clean_qty"));
                hardwaredetail.setExternalRecleanQty(rs.getString("external_reclean_qty"));
                hardwaredetail.setInternalCleanQty(rs.getString("internal_clean_qty"));
                hardwaredetail.setInternalRecleanQty(rs.getString("internal_reclean_qty"));
                hardwaredetail.setStorageFactoryQty(rs.getString("storage_factory_qty"));
                hardwaredetail.setOtherOnsemiQty(rs.getString("other_onsemi_qty"));
                hardwaredetail.setVendorQty(rs.getString("vendor_qty"));
                hardwaredetail.setTotalQty(rs.getString("total_qty"));
                hardwaredetail.setUnitCost(rs.getString("unit_cost"));
                hardwaredetail.setTotalCost(rs.getString("total_cost"));
                hardwaredetail.setStatus(rs.getString("status"));
                hardwaredetail.setAluHrs(rs.getString("alu_hrs"));
                hardwaredetail.setMovementAluHrs(rs.getString("movement_alu_hrs"));
                hardwaredetail.setMinQty(rs.getString("min_qty"));
                hardwaredetail.setMaxQty(rs.getString("max_qty"));
                hardwaredetail.setPmWw1(rs.getString("pm_ww1"));
                hardwaredetail.setPmWw2(rs.getString("pm_ww2"));
                hardwaredetail.setExpirationDate(rs.getString("expiration_date"));
                hardwaredetail.setIsCritical(rs.getString("is_critical"));
                hardwaredetail.setIsConsumable(rs.getString("is_consumable"));
                hardwaredetail.setDowntimeValue(rs.getString("downtime_value"));
                hardwaredetail.setDowntimeUnit(rs.getString("downtime_unit"));
                hardwaredetail.setImplementationCost(rs.getString("implementation_cost"));
                hardwaredetail.setManpowerValue(rs.getString("manpower_value"));
                hardwaredetail.setManpowerUnit(rs.getString("manpower_unit"));
                hardwaredetail.setComplexity(rs.getString("complexity"));
                hardwaredetail.setModel(rs.getString("model"));
                hardwaredetail.setManufacturer(rs.getString("manufacturer"));
                hardwaredetail.setEquipmentType(rs.getString("equipment_type"));
                hardwaredetail.setEquipmentModel(rs.getString("equipment_model"));
                hardwaredetail.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                hardwaredetail.setStressType(rs.getString("stress_type"));
                hardwaredetail.setRemarks(rs.getString("remarks"));
                hardwaredetail.setFlag(rs.getString("flag"));
                hardwaredetail.setCreatedBy(rs.getString("created_by"));
                hardwaredetail.setCreatedDate(rs.getString("created_date"));
                hardwaredetail.setModifedBy(rs.getString("modifed_by"));
                hardwaredetail.setModifiedDate(rs.getString("modified_date"));
                hardwaredetail.setItemUsage(rs.getString("item_usage"));
                hardwaredetail.setActivityId(rs.getString("config"));
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
        return hardwaredetail;
    }

    public List<Item> getHardwareDetailList() {
        String sql = "SELECT * FROM item ORDER BY id ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setId(rs.getString("id"));
                hardwaredetail.setSptsPkid(rs.getString("spts_pkid"));
                hardwaredetail.setItemType(rs.getString("item_type"));
                hardwaredetail.setSubType(rs.getString("sub_type"));
                hardwaredetail.setItemId(rs.getString("item_id"));
                hardwaredetail.setItemName(rs.getString("item_name"));
                hardwaredetail.setAssemblyId(rs.getString("assembly_id"));
                hardwaredetail.setRack(rs.getString("rack"));
                hardwaredetail.setShelf(rs.getString("shelf"));
                hardwaredetail.setOnHandQty(rs.getString("on_hand_qty"));
                hardwaredetail.setProductionStagingQty(rs.getString("production_staging_qty"));
                hardwaredetail.setProductionQty(rs.getString("production_qty"));
                hardwaredetail.setRepairQty(rs.getString("repair_qty"));
                hardwaredetail.setOtherQty(rs.getString("other_qty"));
                hardwaredetail.setQuarantineQty(rs.getString("quarantine_qty"));
                hardwaredetail.setExternalCleanQty(rs.getString("external_clean_qty"));
                hardwaredetail.setExternalRecleanQty(rs.getString("external_reclean_qty"));
                hardwaredetail.setInternalCleanQty(rs.getString("internal_clean_qty"));
                hardwaredetail.setInternalRecleanQty(rs.getString("internal_reclean_qty"));
                hardwaredetail.setStorageFactoryQty(rs.getString("storage_factory_qty"));
                hardwaredetail.setOtherOnsemiQty(rs.getString("other_onsemi_qty"));
                hardwaredetail.setVendorQty(rs.getString("vendor_qty"));
                hardwaredetail.setTotalQty(rs.getString("total_qty"));
                hardwaredetail.setUnitCost(rs.getString("unit_cost"));
                hardwaredetail.setTotalCost(rs.getString("total_cost"));
                hardwaredetail.setStatus(rs.getString("status"));
                hardwaredetail.setAluHrs(rs.getString("alu_hrs"));
                hardwaredetail.setMovementAluHrs(rs.getString("movement_alu_hrs"));
                hardwaredetail.setMinQty(rs.getString("min_qty"));
                hardwaredetail.setMaxQty(rs.getString("max_qty"));
                hardwaredetail.setPmWw1(rs.getString("pm_ww1"));
                hardwaredetail.setPmWw2(rs.getString("pm_ww2"));
                hardwaredetail.setExpirationDate(rs.getString("expiration_date"));
                hardwaredetail.setIsCritical(rs.getString("is_critical"));
                hardwaredetail.setIsConsumable(rs.getString("is_consumable"));
                hardwaredetail.setDowntimeValue(rs.getString("downtime_value"));
                hardwaredetail.setDowntimeUnit(rs.getString("downtime_unit"));
                hardwaredetail.setImplementationCost(rs.getString("implementation_cost"));
                hardwaredetail.setManpowerValue(rs.getString("manpower_value"));
                hardwaredetail.setManpowerUnit(rs.getString("manpower_unit"));
                hardwaredetail.setComplexity(rs.getString("complexity"));
                hardwaredetail.setModel(rs.getString("model"));
                hardwaredetail.setManufacturer(rs.getString("manufacturer"));
                hardwaredetail.setEquipmentType(rs.getString("equipment_type"));
                hardwaredetail.setEquipmentModel(rs.getString("equipment_model"));
                hardwaredetail.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                hardwaredetail.setStressType(rs.getString("stress_type"));
                hardwaredetail.setRemarks(rs.getString("remarks"));
                hardwaredetail.setFlag(rs.getString("flag"));
                hardwaredetail.setCreatedBy(rs.getString("created_by"));
                hardwaredetail.setCreatedDate(rs.getString("created_date"));
                hardwaredetail.setModifedBy(rs.getString("modifed_by"));
                hardwaredetail.setModifiedDate(rs.getString("modified_date"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getitemQuery(String query) {
        String sql = query;
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setId(rs.getString("it.id"));
                hardwaredetail.setSptsPkid(rs.getString("it.spts_pkid"));
                hardwaredetail.setItemType(rs.getString("it.item_type"));
                hardwaredetail.setSubType(rs.getString("it.sub_type"));
                hardwaredetail.setItemId(rs.getString("it.item_id"));
                hardwaredetail.setItemName(rs.getString("it.item_name"));
                hardwaredetail.setAssemblyId(rs.getString("it.assembly_id"));
                hardwaredetail.setRack(rs.getString("it.rack"));
                hardwaredetail.setShelf(rs.getString("it.shelf"));
                hardwaredetail.setOnHandQty(rs.getString("it.on_hand_qty"));
                hardwaredetail.setProductionStagingQty(rs.getString("it.production_staging_qty"));
                hardwaredetail.setProductionQty(rs.getString("it.production_qty"));
                hardwaredetail.setRepairQty(rs.getString("it.repair_qty"));
                hardwaredetail.setOtherQty(rs.getString("it.other_qty"));
                hardwaredetail.setQuarantineQty(rs.getString("it.quarantine_qty"));
                hardwaredetail.setExternalCleanQty(rs.getString("it.external_clean_qty"));
                hardwaredetail.setExternalRecleanQty(rs.getString("it.external_reclean_qty"));
                hardwaredetail.setInternalCleanQty(rs.getString("it.internal_clean_qty"));
                hardwaredetail.setInternalRecleanQty(rs.getString("it.internal_reclean_qty"));
                hardwaredetail.setStorageFactoryQty(rs.getString("it.storage_factory_qty"));
                hardwaredetail.setOtherOnsemiQty(rs.getString("it.other_onsemi_qty"));
                hardwaredetail.setVendorQty(rs.getString("it.vendor_qty"));
                hardwaredetail.setTotalQty(rs.getString("it.total_qty"));
                hardwaredetail.setUnitCost(rs.getString("it.unit_cost"));
                hardwaredetail.setTotalCost(rs.getString("it.total_cost"));
                hardwaredetail.setStatus(rs.getString("it.status"));
                hardwaredetail.setAluHrs(rs.getString("it.alu_hrs"));
                hardwaredetail.setMovementAluHrs(rs.getString("it.movement_alu_hrs"));
                hardwaredetail.setMinQty(rs.getString("it.min_qty"));
                hardwaredetail.setMaxQty(rs.getString("it.max_qty"));
                hardwaredetail.setPmWw1(rs.getString("it.pm_ww1"));
                hardwaredetail.setPmWw2(rs.getString("it.pm_ww2"));
                hardwaredetail.setExpirationDate(rs.getString("it.expiration_date"));
                hardwaredetail.setIsCritical(rs.getString("it.is_critical"));
                hardwaredetail.setIsConsumable(rs.getString("it.is_consumable"));
                hardwaredetail.setDowntimeValue(rs.getString("it.downtime_value"));
                hardwaredetail.setDowntimeUnit(rs.getString("it.downtime_unit"));
                hardwaredetail.setImplementationCost(rs.getString("it.implementation_cost"));
                hardwaredetail.setManpowerValue(rs.getString("it.manpower_value"));
                hardwaredetail.setManpowerUnit(rs.getString("it.manpower_unit"));
                hardwaredetail.setComplexity(rs.getString("it.complexity"));
                hardwaredetail.setModel(rs.getString("it.model"));
                hardwaredetail.setManufacturer(rs.getString("it.manufacturer"));
                hardwaredetail.setEquipmentType(rs.getString("it.equipment_type"));
                hardwaredetail.setEquipmentModel(rs.getString("it.equipment_model"));
                hardwaredetail.setEquipmentManufacturer(rs.getString("it.equipment_manufacturer"));
                hardwaredetail.setStressType(rs.getString("it.stress_type"));
                hardwaredetail.setRemarks(rs.getString("it.remarks"));
                hardwaredetail.setFlag(rs.getString("it.flag"));
                hardwaredetail.setCreatedBy(rs.getString("it.created_by"));
                hardwaredetail.setCreatedDate(rs.getString("it.created_date"));
                hardwaredetail.setModifedBy(rs.getString("it.modifed_by"));
                hardwaredetail.setModifiedDate(rs.getString("it.modified_date"));
                hardwaredetail.setVmId(rs.getString("vmId"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getHardwareDetailListByItemType(String itemType) {
        String sql = "SELECT * FROM item WHERE item_type = '" + itemType + "' AND STATUS <> 'Scrapped' AND flag = '1' ORDER BY item_id ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setId(rs.getString("id"));
                hardwaredetail.setSiteName(rs.getString("site_name"));
                hardwaredetail.setSptsPkid(rs.getString("spts_pkid"));
                hardwaredetail.setItemType(rs.getString("item_type"));
                hardwaredetail.setSubType(rs.getString("sub_type"));
                hardwaredetail.setItemId(rs.getString("item_id"));
                hardwaredetail.setItemName(rs.getString("item_name"));
                hardwaredetail.setAssemblyId(rs.getString("assembly_id"));
                hardwaredetail.setRack(rs.getString("rack"));
                hardwaredetail.setShelf(rs.getString("shelf"));
                hardwaredetail.setOnHandQty(rs.getString("on_hand_qty"));
                hardwaredetail.setProductionStagingQty(rs.getString("production_staging_qty"));
                hardwaredetail.setProductionQty(rs.getString("production_qty"));
                hardwaredetail.setRepairQty(rs.getString("repair_qty"));
                hardwaredetail.setOtherQty(rs.getString("other_qty"));
                hardwaredetail.setQuarantineQty(rs.getString("quarantine_qty"));
                hardwaredetail.setExternalCleanQty(rs.getString("external_clean_qty"));
                hardwaredetail.setExternalRecleanQty(rs.getString("external_reclean_qty"));
                hardwaredetail.setInternalCleanQty(rs.getString("internal_clean_qty"));
                hardwaredetail.setInternalRecleanQty(rs.getString("internal_reclean_qty"));
                hardwaredetail.setStorageFactoryQty(rs.getString("storage_factory_qty"));
                hardwaredetail.setOtherOnsemiQty(rs.getString("other_onsemi_qty"));
                hardwaredetail.setVendorQty(rs.getString("vendor_qty"));
                hardwaredetail.setTotalQty(rs.getString("total_qty"));
                hardwaredetail.setUnitCost(rs.getString("unit_cost"));
                hardwaredetail.setTotalCost(rs.getString("total_cost"));
                hardwaredetail.setStatus(rs.getString("status"));
                hardwaredetail.setAluHrs(rs.getString("alu_hrs"));
                hardwaredetail.setMovementAluHrs(rs.getString("movement_alu_hrs"));
                hardwaredetail.setMinQty(rs.getString("min_qty"));
                hardwaredetail.setMaxQty(rs.getString("max_qty"));
                hardwaredetail.setPmWw1(rs.getString("pm_ww1"));
                hardwaredetail.setPmWw2(rs.getString("pm_ww2"));
                hardwaredetail.setExpirationDate(rs.getString("expiration_date"));
                hardwaredetail.setIsCritical(rs.getString("is_critical"));
                hardwaredetail.setIsConsumable(rs.getString("is_consumable"));
                hardwaredetail.setDowntimeValue(rs.getString("downtime_value"));
                hardwaredetail.setDowntimeUnit(rs.getString("downtime_unit"));
                hardwaredetail.setImplementationCost(rs.getString("implementation_cost"));
                hardwaredetail.setManpowerValue(rs.getString("manpower_value"));
                hardwaredetail.setManpowerUnit(rs.getString("manpower_unit"));
                hardwaredetail.setComplexity(rs.getString("complexity"));
                hardwaredetail.setModel(rs.getString("model"));
                hardwaredetail.setManufacturer(rs.getString("manufacturer"));
                hardwaredetail.setEquipmentType(rs.getString("equipment_type"));
                hardwaredetail.setEquipmentModel(rs.getString("equipment_model"));
                hardwaredetail.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                hardwaredetail.setStressType(rs.getString("stress_type"));
                hardwaredetail.setRemarks(rs.getString("remarks"));
                hardwaredetail.setFlag(rs.getString("flag"));
                hardwaredetail.setCreatedBy(rs.getString("created_by"));
                hardwaredetail.setCreatedDate(rs.getString("created_date"));
                hardwaredetail.setModifedBy(rs.getString("modifed_by"));
                hardwaredetail.setModifiedDate(rs.getString("modified_date"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemListPendingVMFunctionalTest() {
        String sql = "SELECT it.id, it.item_type, it.sub_type, it.item_id, it.`status`, it.item_name, it.assembly_id, it.total_qty, it.flag, it.created_by, DATE_FORMAT(it.created_date,'%d %M %Y %h:%i %p') AS createdDate, con.id AS ActivityId "
                + "FROM item it LEFT JOIN item_activity_config con ON it.id = con.mib_item_id WHERE it.flag = '0'";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setId(rs.getString("id"));
                hardwaredetail.setItemType(rs.getString("item_type"));
                hardwaredetail.setSubType(rs.getString("sub_type"));
                hardwaredetail.setItemId(rs.getString("item_id"));
                hardwaredetail.setItemName(rs.getString("item_name"));
                hardwaredetail.setAssemblyId(rs.getString("assembly_id"));
                hardwaredetail.setTotalQty(rs.getString("total_qty"));
                hardwaredetail.setStatus(rs.getString("status"));
                hardwaredetail.setFlag(rs.getString("flag"));
                hardwaredetail.setCreatedBy(rs.getString("created_by"));
                hardwaredetail.setCreatedDate(rs.getString("createdDate"));
                hardwaredetail.setActivityId(rs.getString("ActivityId"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public Integer getCountPkid(String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item inc WHERE inc.spts_pkid = '" + pkid + "'"
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

    public Integer getCountItemWithFlagZero() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item inc WHERE inc.flag = '0'"
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

    public Integer getCountItemId(String itemId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item inc WHERE inc.item_id = '" + itemId + "'"
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

    public Integer getCountItemIdAndNotMibId(String itemId, String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item inc WHERE inc.item_id = '" + itemId + "' AND inc.id <> '" + id + "'"
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

    public List<Item> getDataTest(String id) {
        String sql = "SELECT * FROM item WHERE id = '" + id + "'";
        List<Item> itemList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item item;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                item = new Item();
                item.setId(rs.getString("id"));
                item.setSptsPkid(rs.getString("spts_pkid"));
                item.setItemId(rs.getString("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemType(rs.getString("item_type"));
                item.setSiteName(rs.getString("site_name"));
                item.setSubType(rs.getString("sub_type"));
                item.setAssemblyId(rs.getString("assembly_id"));
                item.setEquipmentType(rs.getString("equipment_type"));
                item.setEquipmentManufacturer(rs.getString("model"));
                item.setEquipmentModel(rs.getString("equipment_model"));
                item.setModel(rs.getString("model"));
                item.setStressType(rs.getString("stress_type"));
                item.setAluHrs(rs.getString("alu_hrs"));
                itemList.add(item);
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
        return itemList;
    }

    public List<Item> getItemAssemblyId(String assemblyId) {
        String sql = "SELECT DISTINCT(it.assembly_id) AS assemblyId, IF(it.assembly_id=\"" + assemblyId + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.assembly_id <> '' ORDER BY it.assembly_id ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setAssemblyId(rs.getString("assemblyId"));
                hardwaredetail.setSelected(rs.getString("selected"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemModel(String models) {
        String sql = "SELECT DISTINCT(it.model) AS models, IF(it.model=\"" + models + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.model <> '' ORDER BY it.model ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setModel(rs.getString("models"));
                hardwaredetail.setSelected(rs.getString("selected"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemManufacturer(String manufacturer) {
        String sql = "SELECT DISTINCT(it.manufacturer) AS manufacturer, IF(it.manufacturer=\"" + manufacturer + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.manufacturer <> '' ORDER BY it.manufacturer ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setManufacturer(rs.getString("manufacturer"));
                hardwaredetail.setSelected(rs.getString("selected"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemEqptType(String eqptType) {
        String sql = "SELECT DISTINCT(it.equipment_type) AS equipment_type, IF(it.equipment_type=\"" + eqptType + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.equipment_type <> '' ORDER BY it.equipment_type ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setEquipmentType(rs.getString("equipment_type"));
                hardwaredetail.setSelected(rs.getString("selected"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemEqptModel(String eqptModel) {
        String sql = "SELECT DISTINCT(it.equipment_model) AS equipment_model, IF(it.equipment_model=\"" + eqptModel + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.equipment_model <> '' ORDER BY it.equipment_model ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setEquipmentModel(rs.getString("equipment_model"));
                hardwaredetail.setSelected(rs.getString("selected"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemEqptManufacturer(String eqptManufacturer) {
        String sql = "SELECT DISTINCT(it.equipment_manufacturer) AS equipment_manufacturer, IF(it.equipment_manufacturer=\"" + eqptManufacturer + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.equipment_manufacturer <> '' ORDER BY it.equipment_manufacturer ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                hardwaredetail.setSelected(rs.getString("selected"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemStressType(String stressType) {
        String sql = "SELECT DISTINCT(it.stress_type) AS stress_type, IF(it.stress_type=\"" + stressType + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.stress_type <> '' ORDER BY it.stress_type ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setStressType(rs.getString("stress_type"));
                hardwaredetail.setSelected(rs.getString("selected"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemStatus() {
        String sql = "SELECT DISTINCT(it.status) AS status FROM item it WHERE it.status <> '' ORDER BY it.status ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setStatus(rs.getString("status"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemSubType() {
        String sql = "SELECT DISTINCT(it.sub_type) AS sub_type FROM item it WHERE it.sub_type <> '' ORDER BY it.sub_type ASC";
        List<Item> hardwaredetailList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item hardwaredetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwaredetail = new Item();
                hardwaredetail.setSubType(rs.getString("sub_type"));
                hardwaredetailList.add(hardwaredetail);
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
        return hardwaredetailList;
    }

    public List<Item> getItemSubType(String subType, String itemType) {
        String sql = "SELECT DISTINCT(it.sub_type) AS sub_type FROM item it WHERE item_type = '" + itemType + "' AND it.sub_type LIKE '%" + subType + "%' AND it.sub_type <> '' ORDER BY it.sub_type ASC";
        List<Item> itemList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item itemdetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemdetail = new Item();
                itemdetail.setSubType(rs.getString("sub_type"));
                itemList.add(itemdetail);
            }
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
        return itemList;
    }

    public List<Item> getItemSubType02(String subType, String itemType) {
        String sql = "SELECT DISTINCT(it.sub_type) AS subType, IF(sub_type=\"" + subType + "\",\"selected=''\",\"\") AS selected FROM item it WHERE item_type = '" + itemType + "' AND it.sub_type <> '' ORDER BY it.sub_type ASC";
        LOGGER.info("apo dioooo >>>>>> "+sql);
        List<Item> itemList = new ArrayList<Item>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Item itemdetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemdetail = new Item(
                        rs.getString("subType"),
                        rs.getString("selected")
                );
                itemList.add(itemdetail);
            }
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
        return itemList;
    }

}
