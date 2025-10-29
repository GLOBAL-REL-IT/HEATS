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
                    "UPDATE item SET spts_pkid = ?, item_type = ?, sub_type = ?, item_id = ?, item_name = ?, assembly_id = ?, rack = ?, shelf = ?, on_hand_qty = ?, production_staging_qty = ?, production_qty = ?, repair_qty = ?, other_qty = ?, quarantine_qty = ?, external_clean_qty = ?, external_reclean_qty = ?, internal_clean_qty = ?, internal_reclean_qty = ?, storage_factory_qty = ?, other_onsemi_qty = ?, vendor_qty = ?, total_qty = ?, unit_cost = ?, total_cost = ?, status = ?, alu_hrs = ?, movement_alu_hrs = ?, min_qty = ?, max_qty = ?, pm_ww1 = ?, pm_ww2 = ?, expiration_date = ?, is_critical = ?, is_consumable = ?, downtime_value = ?, downtime_unit = ?, implementation_cost = ?, manpower_value = ?, manpower_unit = ?, complexity = ?, model = ?, manufacturer = ?, equipment_type = ?, equipment_model = ?, equipment_manufacturer = ?, stress_type = ?, remarks = ?, flag = ?, created_by = ?, created_date = ?, modifed_by = ?, modified_date = ? WHERE id = ?"
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
            ps.setString(50, hardwaredetail.getCreatedDate());
            ps.setString(51, hardwaredetail.getModifedBy());
            ps.setString(52, hardwaredetail.getModifiedDate());
            ps.setString(53, hardwaredetail.getId());
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
    
    public List<Item> getDataTest(String id) {
        String sql = "SELECT * FROM item WHERE id = '"+id+"'";
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

}