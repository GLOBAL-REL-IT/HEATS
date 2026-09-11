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
import com.onsemi.mib.model.ItemMaverick;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemMaverickDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemMaverickDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public ItemMaverickDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_INSERT_ITEM_MAVERICK = "INSERT INTO item_maverick (mib_item_id, module, submodule, disposition_1, disposition_remarks_1, disposition_1_by, disposition_1_date, disposition_2, disposition_2_remarks, disposition_2_by, disposition_2_date, status, flag, created_by, created_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())";
    private static final String SQL_UPDATE_ITEM_MAVERICK = "UPDATE item_maverick SET mib_item_id = ?, module = ?, submodule = ?, disposition_1 = ?, disposition_remarks_1 = ?, disposition_1_by = ?, disposition_1_date = ?, disposition_2 = ?, disposition_2_remarks = ?, disposition_2_by = ?, disposition_2_date = ?, status = ?, flag = ?, created_by = ?, created_date = ? WHERE id = ?";
    private static final String SQL_DELETE_ITEM_MAVERICK = "DELETE FROM item_maverick WHERE id = ?";

    public QueryResult insertItemMaverick(ItemMaverick itemmaverick) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_ITEM_MAVERICK, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemmaverick.getMibItemId());
            ps.setString(2, itemmaverick.getModule());
            ps.setString(3, itemmaverick.getSubmodule());
            ps.setString(4, itemmaverick.getDisposition1());
            ps.setString(5, itemmaverick.getDispositionRemarks1());
            ps.setString(6, itemmaverick.getDisposition1By());
            ps.setString(7, itemmaverick.getDisposition1Date());
            ps.setString(8, itemmaverick.getDisposition2());
            ps.setString(9, itemmaverick.getDisposition2Remarks());
            ps.setString(10, itemmaverick.getDisposition2By());
            ps.setString(11, itemmaverick.getDisposition2Date());
            ps.setString(12, itemmaverick.getStatus());
            ps.setString(13, itemmaverick.getFlag());
            ps.setString(14, itemmaverick.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting Item Maverick", e);
        }
        return queryResult;
    }

    public QueryResult updateItemMaverick(ItemMaverick itemmaverick) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ITEM_MAVERICK)) {
            ps.setString(1, itemmaverick.getMibItemId());
            ps.setString(2, itemmaverick.getModule());
            ps.setString(3, itemmaverick.getSubmodule());
            ps.setString(4, itemmaverick.getDisposition1());
            ps.setString(5, itemmaverick.getDispositionRemarks1());
            ps.setString(6, itemmaverick.getDisposition1By());
            ps.setString(7, itemmaverick.getDisposition1Date());
            ps.setString(8, itemmaverick.getDisposition2());
            ps.setString(9, itemmaverick.getDisposition2Remarks());
            ps.setString(10, itemmaverick.getDisposition2By());
            ps.setString(11, itemmaverick.getDisposition2Date());
            ps.setString(12, itemmaverick.getStatus());
            ps.setString(13, itemmaverick.getFlag());
            ps.setString(14, itemmaverick.getCreatedBy());
            ps.setString(15, itemmaverick.getCreatedDate());
            ps.setString(16, itemmaverick.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating Item Maverick. ID: {}", itemmaverick.getId(), e);
        }
        return queryResult;
    }

    public QueryResult deleteItemMaverick(String itemmaverickId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_ITEM_MAVERICK)) {
            ps.setString(1, itemmaverickId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting Item Maverick. ID: {}", itemmaverickId, e);
        }
        return queryResult;
    }

    private static final String SQL_GET_ITEM_MAVERICK = "SELECT * FROM item_maverick WHERE id = ?";
    private static final String SQL_GET_ITEM_MAVERICK_LIST = "SELECT * FROM item_maverick ORDER BY id ASC";
    private static final String SQL_GET_ITEM_MAVERICK_LIST_FLAG_ZERO = "SELECT mav.*, DATE_FORMAT(mav.created_date,'%d %M %Y %h:%i %p') AS createdDate, it.item_id, it.item_type FROM item_maverick mav INNER JOIN item it ON mav.mib_item_id = it.id WHERE mav.flag = ? ORDER BY mav.id ASC";
    private static final String SQL_GET_ITEM_MAVERICK_UNION_RMS_BOOKING_MAVERICK = "SELECT mav1.id, mav1.mib_item_id AS id2, itm.item_type, itm.item_name AS item_identifier, mav1.module, mav1.submodule, mav1.disposition_1, mav1.disposition_remarks_1, mav1.disposition_1_by, mav1.disposition_1_date, mav1.disposition_2, mav1.disposition_2_remarks, mav1.disposition_2_by, mav1.disposition_2_date, mav1.status, mav1.flag, mav1.created_by, mav1.created_date FROM item_maverick mav1 LEFT JOIN item itm ON itm.id = mav1.mib_item_id " +
                                                                        "UNION ALL SELECT mav2.id, mav2.group_id AS id2, rbh.item_type, rbh.item_id AS item_identifier, mav2.module, mav2.submodule, mav2.disposition_1, mav2.disposition_remarks_1, mav2.disposition_1_by, mav2.disposition_1_date, mav2.disposition_2, mav2.disposition_2_remarks, mav2.disposition_2_by, mav2.disposition_2_date, mav2.status, mav2.flag, mav2.created_by, mav2.created_date FROM rms_booking_maverick mav2 LEFT JOIN rms_booking_hardware rbh ON rbh.booking_pkid = SUBSTRING_INDEX(mav2.group_id, '/', 1) AND rbh.pkid = SUBSTRING_INDEX(mav2.group_id, '/', -1) ORDER BY created_date DESC;";

    public ItemMaverick getItemMaverick(String itemmaverickId) {
        ItemMaverick itemmaverick = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_MAVERICK)) {
            ps.setString(1, itemmaverickId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    itemmaverick = new ItemMaverick();
                    itemmaverick.setId(rs.getString("id"));
                    itemmaverick.setItemId(rs.getString("mib_item_id"));
                    itemmaverick.setModule(rs.getString("module"));
                    itemmaverick.setSubmodule(rs.getString("submodule"));
                    itemmaverick.setDisposition1(rs.getString("disposition_1"));
                    itemmaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                    itemmaverick.setDisposition1By(rs.getString("disposition_1_by"));
                    itemmaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                    itemmaverick.setDisposition2(rs.getString("disposition_2"));
                    itemmaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                    itemmaverick.setDisposition2By(rs.getString("disposition_2_by"));
                    itemmaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                    itemmaverick.setStatus(rs.getString("status"));
                    itemmaverick.setFlag(rs.getString("flag"));
                    itemmaverick.setCreatedBy(rs.getString("created_by"));
                    itemmaverick.setCreatedDate(rs.getString("created_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Item Maverick. ID: {}", itemmaverickId, e);
        }
        return itemmaverick;
    }

    public List<ItemMaverick> getItemMaverickList() {
        List<ItemMaverick> itemmaverickList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_MAVERICK_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ItemMaverick itemmaverick = new ItemMaverick();
                itemmaverick.setId(rs.getString("id"));
                itemmaverick.setItemId(rs.getString("mib_item_id"));
                itemmaverick.setModule(rs.getString("module"));
                itemmaverick.setSubmodule(rs.getString("submodule"));
                itemmaverick.setDisposition1(rs.getString("disposition_1"));
                itemmaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                itemmaverick.setDisposition1By(rs.getString("disposition_1_by"));
                itemmaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                itemmaverick.setDisposition2(rs.getString("disposition_2"));
                itemmaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                itemmaverick.setDisposition2By(rs.getString("disposition_2_by"));
                itemmaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                itemmaverick.setStatus(rs.getString("status"));
                itemmaverick.setFlag(rs.getString("flag"));
                itemmaverick.setCreatedBy(rs.getString("created_by"));
                itemmaverick.setCreatedDate(rs.getString("created_date"));
                itemmaverickList.add(itemmaverick);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Item Maverick list", e);
        }
        return itemmaverickList;
    }

    public List<ItemMaverick> getItemMaverickListFlagZero() {
        List<ItemMaverick> itemmaverickList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_MAVERICK_LIST_FLAG_ZERO)) {
            ps.setString(1, "0");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemMaverick itemmaverick = new ItemMaverick();
                    itemmaverick.setId(rs.getString("id"));
                    itemmaverick.setMibItemId(rs.getString("mib_item_id"));
                    itemmaverick.setModule(rs.getString("module"));
                    itemmaverick.setSubmodule(rs.getString("submodule"));
                    itemmaverick.setDisposition1(rs.getString("disposition_1"));
                    itemmaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                    itemmaverick.setDisposition1By(rs.getString("disposition_1_by"));
                    itemmaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                    itemmaverick.setDisposition2(rs.getString("disposition_2"));
                    itemmaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                    itemmaverick.setDisposition2By(rs.getString("disposition_2_by"));
                    itemmaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                    itemmaverick.setStatus(rs.getString("status"));
                    itemmaverick.setFlag(rs.getString("flag"));
                    itemmaverick.setCreatedBy(rs.getString("created_by"));
                    itemmaverick.setCreatedDate(rs.getString("createdDate"));
                    itemmaverick.setItemId(rs.getString("item_id"));
                    itemmaverick.setItemType(rs.getString("item_type"));
                    itemmaverickList.add(itemmaverick);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Item Maverick list with flag 0", e);
        }
        return itemmaverickList;
    }
    
    private static final String SQL_GET_COUNT_FLAG_ZERO = "SELECT COUNT(*) AS count FROM item_maverick inc WHERE inc.flag = '0'";
    private static final String SQL_GET_COUNT_VM_WITH_MONTH_AND_YEAR = "SELECT COUNT(*) AS COUNT FROM item_maverick inc WHERE inc.submodule = 'Visual Inspection' AND MONTH(inc.created_date) = ? AND YEAR(inc.created_date) = ?";
    private static final String SQL_GET_COUNT_OPEN_VM = "SELECT COUNT(*) AS COUNT FROM item_maverick inc WHERE inc.submodule = 'Visual Inspection' AND flag = '0'";
    private static final String SQL_GET_COUNT_FT_WITH_MONTH_AND_YEAR = "SELECT COUNT(*) AS COUNT FROM item_maverick inc WHERE inc.submodule != 'Visual Inspection' AND MONTH(inc.created_date) = ? AND YEAR(inc.created_date) = ?";
    private static final String SQL_GET_COUNT_OPEN_FT = "SELECT COUNT(*) AS COUNT FROM item_maverick inc WHERE inc.submodule != 'Visual Inspection' AND flag = '0'";

    public Integer getCountFlagZero() {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_FLAG_ZERO)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving count flag zero", e);
        }
        return count;
    }

    public Integer getCountVmWithMonthAndYear(String month, String year) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_VM_WITH_MONTH_AND_YEAR)) {
            ps.setString(1, month);
            ps.setString(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving VM count by month and year", e);
        }
        return count;
    }

    public Integer getCountOpenVm() {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_OPEN_VM); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving open VM count", e);
        }
        return count;
    }

    public Integer getCountFtWithMonthAndYear(String month, String year) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_FT_WITH_MONTH_AND_YEAR)) {
            ps.setString(1, month);
            ps.setString(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving FT count by month and year", e);
        }
        return count;
    }

    public Integer getCountOpenFT() {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_OPEN_FT); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving open FT count", e);
        }
        return count;
    }

}