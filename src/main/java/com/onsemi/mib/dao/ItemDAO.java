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
//    private final Connection conn;
    private final DataSource dataSource;

    public ItemDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private String getGeneratedKey(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return String.valueOf(rs.getLong(1));
            }
            return null;
        }
    }

    private static final String SQL_INSERT_ITEM_DETAIL = "INSERT INTO item (spts_pkid, item_type, sub_type, item_id, item_name, assembly_id, rack, shelf, on_hand_qty, production_staging_qty, production_qty, repair_qty, other_qty, quarantine_qty, external_clean_qty, external_reclean_qty, internal_clean_qty, internal_reclean_qty, storage_factory_qty, other_onsemi_qty, vendor_qty, total_qty, unit_cost, total_cost, status, alu_hrs, movement_alu_hrs, min_qty, max_qty, pm_ww1, pm_ww2, expiration_date, is_critical, is_consumable, downtime_value, downtime_unit, implementation_cost, manpower_value, manpower_unit, complexity, model, manufacturer, equipment_type, equipment_model, equipment_manufacturer, stress_type, remarks, flag, created_by, created_date, site_name, item_usage) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?)";
    private static final String SQL_UDPATE_HARDWARE_DETAIL_FROM_SPTS = "UPDATE item SET item_type = ?, sub_type = ?, item_id = ?, item_name = ?, assembly_id = ?, rack = ?, shelf = ?, on_hand_qty = ?, production_staging_qty = ?, production_qty = ?, repair_qty = ?, other_qty = ?, quarantine_qty = ?, external_clean_qty = ?, external_reclean_qty = ?, internal_clean_qty = ?, internal_reclean_qty = ?, storage_factory_qty = ?, other_onsemi_qty = ?, vendor_qty = ?, total_qty = ?, unit_cost = ?, total_cost = ?, status = ?, alu_hrs = ?, movement_alu_hrs = ?, min_qty = ?, max_qty = ?, pm_ww1 = ?, pm_ww2 = ?, expiration_date = ?, is_critical = ?, is_consumable = ?, downtime_value = ?, downtime_unit = ?, implementation_cost = ?, manpower_value = ?, manpower_unit = ?, complexity = ?, model = ?, manufacturer = ?, equipment_type = ?, equipment_model = ?, equipment_manufacturer = ?, stress_type = ?, remarks = ?, flag = ?, site_name = ?, modifed_by = ?, modified_date = NOW() WHERE spts_pkid = ?";
    private static final String SQL_UPDATE_HARDWARE_DETAIL = "UPDATE item SET spts_pkid = ?, item_type = ?, sub_type = ?, item_id = ?, item_name = ?, assembly_id = ?, rack = ?, shelf = ?, on_hand_qty = ?, "
            + "production_staging_qty = ?, production_qty = ?, repair_qty = ?, other_qty = ?, quarantine_qty = ?, external_clean_qty = ?, "
            + "external_reclean_qty = ?, internal_clean_qty = ?, internal_reclean_qty = ?, storage_factory_qty = ?, other_onsemi_qty = ?, "
            + "vendor_qty = ?, total_qty = ?, unit_cost = ?, total_cost = ?, status = ?, alu_hrs = ?, movement_alu_hrs = ?, min_qty = ?, "
            + "max_qty = ?, pm_ww1 = ?, pm_ww2 = ?, expiration_date = ?, is_critical = ?, is_consumable = ?, downtime_value = ?, downtime_unit = ?, "
            + "implementation_cost = ?, manpower_value = ?, manpower_unit = ?, complexity = ?, model = ?, manufacturer = ?, equipment_type = ?, "
            + "equipment_model = ?, equipment_manufacturer = ?, stress_type = ?, remarks = ?, flag = ?, modifed_by = ?, modified_date = NOW(), item_usage = ? WHERE id = ?";
    private static final String SQL_UPDATE_HARDWARE_DETAIL_2 = "UPDATE item SET item_type = ?, sub_type = ?, item_id = ?, item_name = ?, assembly_id = ?, rack = ?, shelf = ?, on_hand_qty = ?, "
            + "production_staging_qty = ?, production_qty = ?, repair_qty = ?, other_qty = ?, quarantine_qty = ?, external_clean_qty = ?, "
            + "external_reclean_qty = ?, internal_clean_qty = ?, internal_reclean_qty = ?, storage_factory_qty = ?, other_onsemi_qty = ?, "
            + "vendor_qty = ?, total_qty = ?, unit_cost = ?, total_cost = ?, status = ?, alu_hrs = ?, movement_alu_hrs = ?, min_qty = ?, "
            + "max_qty = ?, pm_ww1 = ?, pm_ww2 = ?, expiration_date = ?, is_critical = ?, is_consumable = ?, downtime_value = ?, downtime_unit = ?, "
            + "implementation_cost = ?, manpower_value = ?, manpower_unit = ?, complexity = ?, model = ?, manufacturer = ?, equipment_type = ?, "
            + "equipment_model = ?, equipment_manufacturer = ?, stress_type = ?, remarks = ?, flag = ?, modifed_by = ?, modified_date = NOW() WHERE id = ?";
    private static final String SQL_UPDATE_ITEM_STATUS = "UPDATE item SET status = ? WHERE id = ? ";
    private static final String SQL_UPDATE_ITEM_SPTS_PKID = "UPDATE item SET spts_pkid = ? WHERE id = ? ";
    private static final String SQL_UPDATE_ITEM_STATUS_AND_FLAG = "UPDATE item SET status = ?, flag = ? WHERE id = ? ";
    private static final String SQL_DELETE_HARDWARE_DETAIL = "DELETE FROM item WHERE id = ? ";

    public QueryResult insertHardwareDetail(Item hardwaredetail) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_ITEM_DETAIL, Statement.RETURN_GENERATED_KEYS)) {
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
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    public QueryResult updateHardwareDetailFromSpts(Item hardwaredetail) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UDPATE_HARDWARE_DETAIL_FROM_SPTS)) {
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
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateHardwareDetail(Item hardwaredetail) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_HARDWARE_DETAIL)) {
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
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateHardwareDetail2(Item hardwaredetail) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_HARDWARE_DETAIL_2)) {
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
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateItemStatus(Item hardwaredetail) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ITEM_STATUS)) {
            ps.setString(1, hardwaredetail.getStatus());
            ps.setString(2, hardwaredetail.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateItemSPTSPKID(Item hardwaredetail) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ITEM_SPTS_PKID)) {
            ps.setString(1, hardwaredetail.getSptsPkid());
            ps.setString(2, hardwaredetail.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateItemStatusAndFlag(Item hardwaredetail) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ITEM_STATUS_AND_FLAG)) {
            ps.setString(1, hardwaredetail.getStatus());
            ps.setString(2, hardwaredetail.getFlag());
            ps.setString(3, hardwaredetail.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult deleteHardwareDetail(String hardwaredetailId) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_HARDWARE_DETAIL)) {
            ps.setString(1, hardwaredetailId);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    private static final String SQL_GET_HARDWARE_DETAIL = "SELECT * FROM item WHERE id = ?";
    private static final String SQL_GET_HARDWARE_DETAIL_BY_SPTS_PKID = "SELECT *,DATE_FORMAT(expiration_date,'%d-%M-%Y') AS expiration_date FROM item WHERE spts_pkid = ? ";
    private static final String SQL_GET_HARDWARE_BY_PKID = "SELECT it.id, it.spts_pkid, it.item_type, it.sub_type, it.item_id, it.item_name, it.assembly_id, it.rack, it.shelf, "
            + "it.on_hand_qty, it.production_staging_qty, it.production_qty, it.repair_qty, it.other_qty, it.quarantine_qty, "
            + "it.external_clean_qty, it.external_reclean_qty, it.internal_clean_qty, it.internal_reclean_qty, it.storage_factory_qty, "
            + "it.other_onsemi_qty, it.vendor_qty, it.total_qty, it.unit_cost, it.total_cost, it.status, it.alu_hrs, it.movement_alu_hrs, "
            + "it.min_qty, it.max_qty, it.pm_ww1, it.pm_ww2, DATE_FORMAT(expiration_date,'%Y-%m-%d') AS expiration_date, DATE_FORMAT(expiration_date,'%d-%M-%Y') AS expiration_date_view, it.is_critical, "
            + "it.is_consumable, it.downtime_value, it.downtime_unit, it.implementation_cost, it.manpower_value, it.manpower_unit, "
            + "it.complexity, it.model, it.manufacturer, it.equipment_type, it.equipment_model, it.equipment_manufacturer, "
            + "it.stress_type, it.remarks, it.flag, it.created_by, it.created_date, it.modifed_by, it.modified_date, it.item_usage, "
            + "cf.item_type AS config FROM item it "
            + "LEFT JOIN item_hardware_config cf ON it.item_type = cf.item_type AND IFNULL(it.sub_type, '') = cf.sub_type "
            + "WHERE it.spts_pkid = ? ";
    private static final String SQL_GET_HARDWARE_WITH_ACTIVITY_CONFIG = "SELECT it.id, it.spts_pkid, it.item_type, it.sub_type, it.item_id, it.item_name, it.assembly_id, it.rack, it.shelf, "
            + "it.on_hand_qty, it.production_staging_qty, it.production_qty, it.repair_qty, it.other_qty, it.quarantine_qty, "
            + "it.external_clean_qty, it.external_reclean_qty, it.internal_clean_qty, it.internal_reclean_qty, it.storage_factory_qty, "
            + "it.other_onsemi_qty, it.vendor_qty, it.total_qty, it.unit_cost, it.total_cost, it.status, it.alu_hrs, it.movement_alu_hrs, "
            + "it.min_qty, it.max_qty, it.pm_ww1, it.pm_ww2, DATE_FORMAT(expiration_date,'%Y-%m-%d') AS expiration_date, DATE_FORMAT(expiration_date,'%d-%M-%Y') AS expiration_date_view, it.is_critical, "
            + "it.is_consumable, it.downtime_value, it.downtime_unit, it.implementation_cost, it.manpower_value, it.manpower_unit, "
            + "it.complexity, it.model, it.manufacturer, it.equipment_type, it.equipment_model, it.equipment_manufacturer, "
            + "it.stress_type, it.remarks, it.flag, it.created_by, it.created_date, it.modifed_by, it.modified_date, it.item_usage, "
            + "cf.item_type AS config, con.mib_item_id AS configMibItemId, con.id AS activityConfigId FROM item it "
            + "LEFT JOIN item_hardware_config cf ON it.item_type = cf.item_type AND IFNULL(it.sub_type, '') = cf.sub_type "
            + "LEFT JOIN item_activity_config con ON it.id = con.mib_item_id "
            + "WHERE it.spts_pkid = ? ";

    public Item getHardwareDetail(String hardwaredetailId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HARDWARE_DETAIL)) {
            ps.setString(1, hardwaredetailId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentConfig(rs);
                }
            }
        }
        return null;
    }

    public Item getHardwareDetailByPkid(String spts_pkid) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HARDWARE_DETAIL_BY_SPTS_PKID)) {
            ps.setString(1, spts_pkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentConfig(rs);
                }
            }
        }
        return null;
    }

    public Item getHardwareByPkid(String pkid) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HARDWARE_BY_PKID)) {
            ps.setString(1, pkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentConfig2(rs);
                }
            }
        }
        return null;
    }

    public Item getHardwareByPkidLeftJoinWithActivityConfig(String pkid) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HARDWARE_WITH_ACTIVITY_CONFIG)) {
            ps.setString(1, pkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentActivityConfig(rs);
                }
            }
        }
        return null;
    }

    private Item mapComponentConfig(ResultSet rs) throws SQLException {
        Item hardwaredetail = new Item();
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
        return hardwaredetail;
    }

    private Item mapComponentConfig2(ResultSet rs) throws SQLException {
        Item hardwaredetail = new Item();
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
        return hardwaredetail;
    }

    private Item mapComponentActivityConfig(ResultSet rs) throws SQLException {
        Item hardwaredetail = new Item();
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
        hardwaredetail.setConfigMibItemId(rs.getString("configMibItemId"));
        hardwaredetail.setActivityConfigId(rs.getString("activityConfigId"));
        return hardwaredetail;
    }

    private static final String GET_COUNT_PKID = "SELECT COUNT(*) AS count FROM item inc WHERE inc.spts_pkid = ? ";
    private static final String GET_COUNT_ITEM_WITH_FLAG_ZERO = "SELECT COUNT(*) AS count FROM item inc WHERE inc.flag = '0'";
    private static final String GET_COUNT_ITEM_ID = "SELECT COUNT(*) AS count FROM item inc WHERE inc.item_id = ? ";
    private static final String GET_COUNT_ITEM_ID_AND_NOT_MIB_ID = "SELECT COUNT(*) AS count FROM item inc WHERE inc.item_id = ? AND inc.id <> ? ";

    public int getCountPkid(String pkid) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_COUNT_PKID)) {
            ps.setString(1, pkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for pkid={} ", pkid, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return 0;
    }

    public int getCountItemWithFlagZero() {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_COUNT_ITEM_WITH_FLAG_ZERO)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for getCountItemWithFlagZero ", e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return 0;
    }

    public int getCountItemId(String itemId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_COUNT_ITEM_ID)) {
            ps.setString(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for itemId={} ", itemId, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return 0;
    }

    public int getCountItemIdAndNotMibId(String itemId, String id) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_COUNT_ITEM_ID_AND_NOT_MIB_ID)) {
            ps.setString(1, itemId);
            ps.setString(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for itemId={}, id={} ", itemId, id, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return 0;
    }

    private static final String SQL_GET_MIB_ITEM_ID_BY_SPTS_PKID = "SELECT id FROM item WHERE spts_pkid = ? ";
    private static final String SQL_GET_SPTS_PKID_BY_MIB_ITEM_ID = "SELECT spts_pkid FROM item WHERE id = ? ";
    private static final String SQL_GET_ITEM_ID_BY_ID = "SELECT item_id FROM item WHERE id = ? ";

    public String getMibItemIdBySptsPkId(String sptsId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_MIB_ITEM_ID_BY_SPTS_PKID)) {
            ps.setString(1, sptsId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for sptsId={} ", sptsId, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return null;
    }

    public String getSptsPkIdByMibItemId(String itemId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SPTS_PKID_BY_MIB_ITEM_ID)) {
            ps.setString(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("spts_pkid");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for itemId={} ", itemId, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return null;
    }

    public String getItemIdById(String itemId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_ID_BY_ID)) {
            ps.setString(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("item_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for itemId={} ", itemId, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return null;
    }

    private static final String SQL_GET_DATA_TEST = "SELECT * FROM item WHERE spts_pkid = ? ";
    private static final String SQL_GET_ITEM_STATUS = "SELECT DISTINCT(it.status) AS status FROM item it WHERE it.status <> '' ORDER BY it.status ASC";
    private static final String SQL_GET_ITEM_SUB_TYPE = "SELECT DISTINCT(it.sub_type) AS sub_type FROM item it WHERE item_type = ? AND it.sub_type LIKE ? AND it.sub_type <> '' ORDER BY it.sub_type ASC";
    private static final String SQL_GET_ITEM_SUMMARY_BY_ASSEMBL_ID = "SELECT it.assembly_id, COUNT(it.assembly_id) AS qty FROM item it WHERE it.flag = '0' GROUP BY it.assembly_id ORDER BY it.assembly_id ";

    public List<Item> getDataTest(String id) {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_DATA_TEST)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
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
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list for id={} in getDataTest ", id, e);
        }
        return itemList;
    }

    public List<Item> getItemAssemblyId(String assemblyId) {
        String sql = "SELECT DISTINCT(it.assembly_id) AS assemblyId, "
                + "IF(it.assembly_id=\"" + assemblyId + "\",\"selected=''\",\"\") AS selected "
                + "FROM item it "
                + "WHERE it.assembly_id <> '' "
                + "ORDER BY it.assembly_id ASC";
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setAssemblyId(rs.getString("assemblyId"));
                item.setSelected(rs.getString("selected"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting assembly IDs", e);
        }
        return itemList;
    }

    public List<Item> getItemModel(String models) {
        String sql = "SELECT DISTINCT(it.model) AS models, IF(it.model=\"" + models + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.model <> '' ORDER BY it.model ASC";
        List<Item> itemList = new ArrayList<Item>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setModel(rs.getString("models"));
                item.setSelected(rs.getString("selected"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Models ", e);
        }
        return itemList;
    }

    public List<Item> getItemManufacturer(String manufacturer) {
        String sql = "SELECT DISTINCT(it.manufacturer) AS manufacturer, IF(it.manufacturer=\"" + manufacturer + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.manufacturer <> '' ORDER BY it.manufacturer ASC";
        List<Item> itemList = new ArrayList<Item>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setManufacturer(rs.getString("manufacturer"));
                item.setSelected(rs.getString("selected"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Manufacturers ", e);
        }
        return itemList;
    }

    public List<Item> getItemEqptType(String eqptType) {
        String sql = "SELECT DISTINCT(it.equipment_type) AS equipment_type, IF(it.equipment_type=\"" + eqptType + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.equipment_type <> '' ORDER BY it.equipment_type ASC";
        List<Item> itemList = new ArrayList<Item>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setEquipmentType(rs.getString("equipment_type"));
                item.setSelected(rs.getString("selected"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Equipment Type ", e);
        }
        return itemList;
    }

    public List<Item> getItemEqptModel(String eqptModel) {
        String sql = "SELECT DISTINCT(it.equipment_model) AS equipment_model, IF(it.equipment_model=\"" + eqptModel + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.equipment_model <> '' ORDER BY it.equipment_model ASC";
        List<Item> itemList = new ArrayList<Item>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setEquipmentModel(rs.getString("equipment_model"));
                item.setSelected(rs.getString("selected"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Equipment Model ", e);
        }
        return itemList;
    }

    public List<Item> getItemEqptManufacturer(String eqptManufacturer) {
        String sql = "SELECT DISTINCT(it.equipment_manufacturer) AS equipment_manufacturer, IF(it.equipment_manufacturer=\"" + eqptManufacturer + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.equipment_manufacturer <> '' ORDER BY it.equipment_manufacturer ASC";
        List<Item> itemList = new ArrayList<Item>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                item.setSelected(rs.getString("selected"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Equipment Manufacturer ", e);
        }
        return itemList;
    }

    public List<Item> getItemStressType(String stressType) {
        String sql = "SELECT DISTINCT(it.stress_type) AS stress_type, IF(it.stress_type=\"" + stressType + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.stress_type <> '' ORDER BY it.stress_type ASC";
        List<Item> itemList = new ArrayList<Item>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setStressType(rs.getString("stress_type"));
                item.setSelected(rs.getString("selected"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Stress Type ", e);
        }
        return itemList;
    }

    public List<Item> getItemStatus() {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_STATUS)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setStatus(rs.getString("status"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving item status for in getItemStatus ", e);
        }
        return itemList;
    }

    public List<Item> getItemSubType(String subType) {
        String sql = "SELECT DISTINCT(it.sub_type) AS sub_type, IF(it.sub_type=\"" + subType + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.sub_type <> '' ORDER BY it.sub_type ASC";
        List<Item> itemList = new ArrayList<Item>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setSubType(rs.getString("sub_type"));
                item.setSelected(rs.getString("selected"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Stress Type ", e);
        }
        return itemList;
    }

    public List<Item> getItemSubType(String subType, String itemType) {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_SUB_TYPE)) {
            ps.setString(1, subType);
            ps.setString(2, itemType);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setSubType(rs.getString("sub_type"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving item sub type for in getItemSubType ", e);
        }
        return itemList;
    }

    public List<Item> getItemSubType02(String subType, String itemType) {
        String sql = "SELECT DISTINCT(it.sub_type) AS sub_type, IF(it.sub_type=\"" + subType + "\",\"selected=''\",\"\") AS selected FROM item it WHERE it.sub_type <> '' ORDER BY it.sub_type ASC";
        List<Item> itemList = new ArrayList<Item>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item(
                        rs.getString("subType"),
                        rs.getString("selected")
                );
                itemList.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Item Sub Type 02 ", e);
        }
        return itemList;
    }

    public List<Item> getItemSummaryByAssemblyId() {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_SUMMARY_BY_ASSEMBL_ID)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setAssemblyId(rs.getString("assembly_id"));
                    item.setTotalQty(rs.getString("qty"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving item sub type for in getItemSubType ", e);
        }
        return itemList;
    }

    private static final String SQL_UPDATE_QUANTITY_BY_MIBITEMID = "UPDATE item SET on_hand_qty = ?, production_staging_qty = ? WHERE id = ? ";

    public QueryResult updateQuantityAfterReturnFromProduction(Item item) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_QUANTITY_BY_MIBITEMID)) {
            ps.setString(1, item.getOnHandQty());
            ps.setString(2, item.getProductionStagingQty());
            ps.setString(3, item.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }
    
    private static final String SQL_GET_HARDWARE_DETAIL_LIST = "SELECT * FROM item ORDER BY id ASC";
    private static final String SQL_GET_ACTIVE_BIB_LIST = "SELECT it.id, it.item_type, it.item_id, it.flag FROM item it WHERE it.item_type = 'BIB' AND it.flag NOT IN (0,99)";
    private static final String SQL_GET_HARDWARE_DETAIL_LIST_BY_ITEM_TYPE = "SELECT * FROM item WHERE item_type = ? AND STATUS <> 'Scrapped' AND flag = '1' ORDER BY item_id ASC";
    private static final String SQL_GET_ITEM_LIST_PENDING_VM_FUNCTIONAL_TEST = "SELECT it.id, it.item_type, it.sub_type, it.item_id, it.`status`, it.item_name, it.assembly_id, it.total_qty, it.flag, it.created_by, DATE_FORMAT(it.created_date,'%d %M %Y %h:%i %p') AS createdDate, con.id AS ActivityId FROM item it LEFT JOIN item_activity_config con ON it.id = con.mib_item_id WHERE it.flag = '0'";
    
    public List<Item> getHardwareDetailList() {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HARDWARE_DETAIL_LIST)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getString("id"));
                    item.setSptsPkid(rs.getString("spts_pkid"));
                    item.setItemType(rs.getString("item_type"));
                    item.setSubType(rs.getString("sub_type"));
                    item.setItemId(rs.getString("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setAssemblyId(rs.getString("assembly_id"));
                    item.setRack(rs.getString("rack"));
                    item.setShelf(rs.getString("shelf"));
                    item.setOnHandQty(rs.getString("on_hand_qty"));
                    item.setProductionStagingQty(rs.getString("production_staging_qty"));
                    item.setProductionQty(rs.getString("production_qty"));
                    item.setRepairQty(rs.getString("repair_qty"));
                    item.setOtherQty(rs.getString("other_qty"));
                    item.setQuarantineQty(rs.getString("quarantine_qty"));
                    item.setExternalCleanQty(rs.getString("external_clean_qty"));
                    item.setExternalRecleanQty(rs.getString("external_reclean_qty"));
                    item.setInternalCleanQty(rs.getString("internal_clean_qty"));
                    item.setInternalRecleanQty(rs.getString("internal_reclean_qty"));
                    item.setStorageFactoryQty(rs.getString("storage_factory_qty"));
                    item.setOtherOnsemiQty(rs.getString("other_onsemi_qty"));
                    item.setVendorQty(rs.getString("vendor_qty"));
                    item.setTotalQty(rs.getString("total_qty"));
                    item.setUnitCost(rs.getString("unit_cost"));
                    item.setTotalCost(rs.getString("total_cost"));
                    item.setStatus(rs.getString("status"));
                    item.setAluHrs(rs.getString("alu_hrs"));
                    item.setMovementAluHrs(rs.getString("movement_alu_hrs"));
                    item.setMinQty(rs.getString("min_qty"));
                    item.setMaxQty(rs.getString("max_qty"));
                    item.setPmWw1(rs.getString("pm_ww1"));
                    item.setPmWw2(rs.getString("pm_ww2"));
                    item.setExpirationDate(rs.getString("expiration_date"));
                    item.setIsCritical(rs.getString("is_critical"));
                    item.setIsConsumable(rs.getString("is_consumable"));
                    item.setDowntimeValue(rs.getString("downtime_value"));
                    item.setDowntimeUnit(rs.getString("downtime_unit"));
                    item.setImplementationCost(rs.getString("implementation_cost"));
                    item.setManpowerValue(rs.getString("manpower_value"));
                    item.setManpowerUnit(rs.getString("manpower_unit"));
                    item.setComplexity(rs.getString("complexity"));
                    item.setModel(rs.getString("model"));
                    item.setManufacturer(rs.getString("manufacturer"));
                    item.setEquipmentType(rs.getString("equipment_type"));
                    item.setEquipmentModel(rs.getString("equipment_model"));
                    item.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                    item.setStressType(rs.getString("stress_type"));
                    item.setRemarks(rs.getString("remarks"));
                    item.setFlag(rs.getString("flag"));
                    item.setCreatedBy(rs.getString("created_by"));
                    item.setCreatedDate(rs.getString("created_date"));
                    item.setModifedBy(rs.getString("modifed_by"));
                    item.setModifiedDate(rs.getString("modified_date"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving item in getHardwareDetailList ", e);
        }
        return itemList;
    }
    
    public List<Item> getActiveBibList() {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ACTIVE_BIB_LIST)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getString("id"));
                    item.setItemType(rs.getString("item_type"));
                    item.setItemId(rs.getString("item_id"));
                    item.setFlag(rs.getString("flag"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving item sub type for in getActiveBibList ", e);
        }
        return itemList;
    }
    
    public List<Item> getitemQuery(String SQL_QUERY) {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_QUERY)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getString("it.id"));
                    item.setSptsPkid(rs.getString("it.spts_pkid"));
                    item.setItemType(rs.getString("it.item_type"));
                    item.setSubType(rs.getString("it.sub_type"));
                    item.setItemId(rs.getString("it.item_id"));
                    item.setItemName(rs.getString("it.item_name"));
                    item.setAssemblyId(rs.getString("it.assembly_id"));
                    item.setRack(rs.getString("it.rack"));
                    item.setShelf(rs.getString("it.shelf"));
                    item.setOnHandQty(rs.getString("it.on_hand_qty"));
                    item.setProductionStagingQty(rs.getString("it.production_staging_qty"));
                    item.setProductionQty(rs.getString("it.production_qty"));
                    item.setRepairQty(rs.getString("it.repair_qty"));
                    item.setOtherQty(rs.getString("it.other_qty"));
                    item.setQuarantineQty(rs.getString("it.quarantine_qty"));
                    item.setExternalCleanQty(rs.getString("it.external_clean_qty"));
                    item.setExternalRecleanQty(rs.getString("it.external_reclean_qty"));
                    item.setInternalCleanQty(rs.getString("it.internal_clean_qty"));
                    item.setInternalRecleanQty(rs.getString("it.internal_reclean_qty"));
                    item.setStorageFactoryQty(rs.getString("it.storage_factory_qty"));
                    item.setOtherOnsemiQty(rs.getString("it.other_onsemi_qty"));
                    item.setVendorQty(rs.getString("it.vendor_qty"));
                    item.setTotalQty(rs.getString("it.total_qty"));
                    item.setUnitCost(rs.getString("it.unit_cost"));
                    item.setTotalCost(rs.getString("it.total_cost"));
                    item.setStatus(rs.getString("it.status"));
                    item.setAluHrs(rs.getString("it.alu_hrs"));
                    item.setMovementAluHrs(rs.getString("it.movement_alu_hrs"));
                    item.setMinQty(rs.getString("it.min_qty"));
                    item.setMaxQty(rs.getString("it.max_qty"));
                    item.setPmWw1(rs.getString("it.pm_ww1"));
                    item.setPmWw2(rs.getString("it.pm_ww2"));
                    item.setExpirationDate(rs.getString("it.expiration_date"));
                    item.setIsCritical(rs.getString("it.is_critical"));
                    item.setIsConsumable(rs.getString("it.is_consumable"));
                    item.setDowntimeValue(rs.getString("it.downtime_value"));
                    item.setDowntimeUnit(rs.getString("it.downtime_unit"));
                    item.setImplementationCost(rs.getString("it.implementation_cost"));
                    item.setManpowerValue(rs.getString("it.manpower_value"));
                    item.setManpowerUnit(rs.getString("it.manpower_unit"));
                    item.setComplexity(rs.getString("it.complexity"));
                    item.setModel(rs.getString("it.model"));
                    item.setManufacturer(rs.getString("it.manufacturer"));
                    item.setEquipmentType(rs.getString("it.equipment_type"));
                    item.setEquipmentModel(rs.getString("it.equipment_model"));
                    item.setEquipmentManufacturer(rs.getString("it.equipment_manufacturer"));
                    item.setStressType(rs.getString("it.stress_type"));
                    item.setRemarks(rs.getString("it.remarks"));
                    item.setFlag(rs.getString("it.flag"));
                    item.setCreatedBy(rs.getString("it.created_by"));
                    item.setCreatedDate(rs.getString("it.created_date"));
                    item.setModifedBy(rs.getString("it.modifed_by"));
                    item.setModifiedDate(rs.getString("it.modified_date"));
                    item.setVmId(rs.getString("vmId"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving item sub type for in getActiveBibList ", e);
        }
        return itemList;
    }

    public List<Item> getHardwareDetailListByItemType(String itemType) {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HARDWARE_DETAIL_LIST_BY_ITEM_TYPE)) {
            ps.setString(1, itemType);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getString("id"));
                    item.setSiteName(rs.getString("site_name"));
                    item.setSptsPkid(rs.getString("spts_pkid"));
                    item.setItemType(rs.getString("item_type"));
                    item.setSubType(rs.getString("sub_type"));
                    item.setItemId(rs.getString("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setAssemblyId(rs.getString("assembly_id"));
                    item.setRack(rs.getString("rack"));
                    item.setShelf(rs.getString("shelf"));
                    item.setOnHandQty(rs.getString("on_hand_qty"));
                    item.setProductionStagingQty(rs.getString("production_staging_qty"));
                    item.setProductionQty(rs.getString("production_qty"));
                    item.setRepairQty(rs.getString("repair_qty"));
                    item.setOtherQty(rs.getString("other_qty"));
                    item.setQuarantineQty(rs.getString("quarantine_qty"));
                    item.setExternalCleanQty(rs.getString("external_clean_qty"));
                    item.setExternalRecleanQty(rs.getString("external_reclean_qty"));
                    item.setInternalCleanQty(rs.getString("internal_clean_qty"));
                    item.setInternalRecleanQty(rs.getString("internal_reclean_qty"));
                    item.setStorageFactoryQty(rs.getString("storage_factory_qty"));
                    item.setOtherOnsemiQty(rs.getString("other_onsemi_qty"));
                    item.setVendorQty(rs.getString("vendor_qty"));
                    item.setTotalQty(rs.getString("total_qty"));
                    item.setUnitCost(rs.getString("unit_cost"));
                    item.setTotalCost(rs.getString("total_cost"));
                    item.setStatus(rs.getString("status"));
                    item.setAluHrs(rs.getString("alu_hrs"));
                    item.setMovementAluHrs(rs.getString("movement_alu_hrs"));
                    item.setMinQty(rs.getString("min_qty"));
                    item.setMaxQty(rs.getString("max_qty"));
                    item.setPmWw1(rs.getString("pm_ww1"));
                    item.setPmWw2(rs.getString("pm_ww2"));
                    item.setExpirationDate(rs.getString("expiration_date"));
                    item.setIsCritical(rs.getString("is_critical"));
                    item.setIsConsumable(rs.getString("is_consumable"));
                    item.setDowntimeValue(rs.getString("downtime_value"));
                    item.setDowntimeUnit(rs.getString("downtime_unit"));
                    item.setImplementationCost(rs.getString("implementation_cost"));
                    item.setManpowerValue(rs.getString("manpower_value"));
                    item.setManpowerUnit(rs.getString("manpower_unit"));
                    item.setComplexity(rs.getString("complexity"));
                    item.setModel(rs.getString("model"));
                    item.setManufacturer(rs.getString("manufacturer"));
                    item.setEquipmentType(rs.getString("equipment_type"));
                    item.setEquipmentModel(rs.getString("equipment_model"));
                    item.setEquipmentManufacturer(rs.getString("equipment_manufacturer"));
                    item.setStressType(rs.getString("stress_type"));
                    item.setRemarks(rs.getString("remarks"));
                    item.setFlag(rs.getString("flag"));
                    item.setCreatedBy(rs.getString("created_by"));
                    item.setCreatedDate(rs.getString("created_date"));
                    item.setModifedBy(rs.getString("modifed_by"));
                    item.setModifiedDate(rs.getString("modified_date"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving item sub type for in getItemSubType ", e);
        }
        return itemList;
    }

    public List<Item> getItemListPendingVMFunctionalTest() {
        List<Item> itemList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_LIST_PENDING_VM_FUNCTIONAL_TEST)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getString("id"));
                    item.setItemType(rs.getString("item_type"));
                    item.setSubType(rs.getString("sub_type"));
                    item.setItemId(rs.getString("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setAssemblyId(rs.getString("assembly_id"));
                    item.setTotalQty(rs.getString("total_qty"));
                    item.setStatus(rs.getString("status"));
                    item.setFlag(rs.getString("flag"));
                    item.setCreatedBy(rs.getString("created_by"));
                    item.setCreatedDate(rs.getString("createdDate"));
                    item.setActivityId(rs.getString("ActivityId"));
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving item sub type for in getItemSubType ", e);
        }
        return itemList;
    }

}