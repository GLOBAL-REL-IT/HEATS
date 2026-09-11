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
import com.onsemi.mib.model.RmsBookingMaverick;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingMaverickDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingMaverickDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingMaverickDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_INSERT_RMS_BOOKING_MAVERICK = "INSERT INTO rms_booking_maverick (group_id, module, submodule, disposition_1, disposition_remarks_1, disposition_1_by, disposition_1_date, disposition_2, disposition_2_remarks, disposition_2_by, disposition_2_date, status, flag, created_by, created_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())";
    private static final String SQL_UPDATE_RMS_BOOKING_MAVERICK = "UPDATE rms_booking_maverick SET group_id = ?, module = ?, submodule = ?, disposition_1 = ?, disposition_remarks_1 = ?, disposition_1_by = ?, disposition_1_date = ?, disposition_2 = ?, disposition_2_remarks = ?, disposition_2_by = ?, disposition_2_date = ?, status = ?, flag = ?, created_by = ?, created_date = ? WHERE id = ?";
    private static final String SQL_DELETE_RMS_BOOKING_MAVERICK = "DELETE FROM rms_booking_maverick WHERE id = ?";

    public QueryResult insertRmsBookingMaverick(RmsBookingMaverick rmsbookingMaverick) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RMS_BOOKING_MAVERICK, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rmsbookingMaverick.getGroupId());
            ps.setString(2, rmsbookingMaverick.getModule());
            ps.setString(3, rmsbookingMaverick.getSubmodule());
            ps.setString(4, rmsbookingMaverick.getDisposition1());
            ps.setString(5, rmsbookingMaverick.getDispositionRemarks1());
            ps.setString(6, rmsbookingMaverick.getDisposition1By());
            ps.setString(7, rmsbookingMaverick.getDisposition1Date());
            ps.setString(8, rmsbookingMaverick.getDisposition2());
            ps.setString(9, rmsbookingMaverick.getDisposition2Remarks());
            ps.setString(10, rmsbookingMaverick.getDisposition2By());
            ps.setString(11, rmsbookingMaverick.getDisposition2Date());
            ps.setString(12, rmsbookingMaverick.getStatus());
            ps.setString(13, rmsbookingMaverick.getFlag());
            ps.setString(14, rmsbookingMaverick.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting rms booking maverick", e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingMaverick(RmsBookingMaverick rmsbookingMaverick) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_MAVERICK)) {
            ps.setString(1, rmsbookingMaverick.getGroupId());
            ps.setString(2, rmsbookingMaverick.getModule());
            ps.setString(3, rmsbookingMaverick.getSubmodule());
            ps.setString(4, rmsbookingMaverick.getDisposition1());
            ps.setString(5, rmsbookingMaverick.getDispositionRemarks1());
            ps.setString(6, rmsbookingMaverick.getDisposition1By());
            ps.setString(7, rmsbookingMaverick.getDisposition1Date());
            ps.setString(8, rmsbookingMaverick.getDisposition2());
            ps.setString(9, rmsbookingMaverick.getDisposition2Remarks());
            ps.setString(10, rmsbookingMaverick.getDisposition2By());
            ps.setString(11, rmsbookingMaverick.getDisposition2Date());
            ps.setString(12, rmsbookingMaverick.getStatus());
            ps.setString(13, rmsbookingMaverick.getFlag());
            ps.setString(14, rmsbookingMaverick.getCreatedBy());
            ps.setString(15, rmsbookingMaverick.getCreatedDate());
            ps.setString(16, rmsbookingMaverick.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating rms_booking_maverick", e);
        }
        return queryResult;
    }

    public QueryResult deleteRmsBookingMaverick(String rmsbookingMaverickId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_RMS_BOOKING_MAVERICK)) {
            ps.setString(1, rmsbookingMaverickId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting rms booking maverick", e);
        }
        return queryResult;
    }

    private static final String SQL_GET_RMS_BOOKING_MAVERICK = "SELECT * FROM rms_booking_maverick WHERE id = ?"; 
    private static final String SQL_GET_RMS_BOOKING_MAVERICK_LIST = "SELECT * FROM rms_booking_maverick ORDER BY id ASC";
    private static final String SQL_GET_RMS_BOOKING_MAVERICK_LIST_UNION_WITH_ITEM_MAVERICK = "SELECT mav1.id, mav1.mib_item_id AS id2, mav1.module, mav1.submodule, mav1.disposition_1, mav1.disposition_remarks_1, mav1.disposition_1_by, mav1.disposition_1_date, mav1.disposition_2, mav1.disposition_2_remarks, mav1.disposition_2_by, mav1.disposition_2_date, mav1.status, mav1.flag, mav1.created_by, mav1.created_date FROM item_maverick mav1 "
                                                                                + " UNION ALL SELECT mav2.id, mav2.group_id AS id2, mav2.module, mav2.submodule, mav2.disposition_1, mav2.disposition_remarks_1, mav2.disposition_1_by, mav2.disposition_1_date, mav2.disposition_2, mav2.disposition_2_remarks, mav2.disposition_2_by, mav2.disposition_2_date, mav2.status, mav2.flag, mav2.created_by, mav2.created_date FROM rms_booking_maverick mav2 ORDER BY created_date";
    private static final String SQL_GET_ITEM_MAVERICK_UNION_RMS_BOOKING_MAVERICK = "SELECT mav1.id, mav1.mib_item_id AS id2, itm.item_type, itm.item_name AS item_identifier, mav1.module, mav1.submodule, mav1.disposition_1, mav1.disposition_remarks_1, mav1.disposition_1_by, mav1.disposition_1_date, mav1.disposition_2, mav1.disposition_2_remarks, mav1.disposition_2_by, mav1.disposition_2_date, mav1.status, mav1.flag, mav1.created_by, DATE_FORMAT(mav1.created_date, '%e %b %Y, %H:%i:%s') AS created_date FROM item_maverick mav1 LEFT JOIN item itm ON itm.id = mav1.mib_item_id " +
                                                                        "UNION ALL SELECT mav2.id, mav2.group_id AS id2, rbh.item_type, rbh.item_id AS item_identifier, mav2.module, mav2.submodule, mav2.disposition_1, mav2.disposition_remarks_1, mav2.disposition_1_by, mav2.disposition_1_date, mav2.disposition_2, mav2.disposition_2_remarks, mav2.disposition_2_by, mav2.disposition_2_date, mav2.status, mav2.flag, mav2.created_by, DATE_FORMAT(mav2.created_date, '%e %b %Y, %H:%i:%s') AS created_date FROM rms_booking_maverick mav2 LEFT JOIN rms_booking_hardware rbh ON rbh.booking_pkid = SUBSTRING_INDEX(mav2.group_id, '/', 1) AND rbh.pkid = SUBSTRING_INDEX(mav2.group_id, '/', -1) ORDER BY created_date DESC;";
    
    public RmsBookingMaverick getRmsBookingMaverick(String rmsbookingMaverickId) {
        RmsBookingMaverick rmsbookingMaverick = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_MAVERICK)) {
            ps.setString(1, rmsbookingMaverickId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingMaverick = new RmsBookingMaverick();
                    rmsbookingMaverick.setId(rs.getString("id"));
                    rmsbookingMaverick.setGroupId(rs.getString("group_id"));
                    rmsbookingMaverick.setModule(rs.getString("module"));
                    rmsbookingMaverick.setSubmodule(rs.getString("submodule"));
                    rmsbookingMaverick.setDisposition1(rs.getString("disposition_1"));
                    rmsbookingMaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                    rmsbookingMaverick.setDisposition1By(rs.getString("disposition_1_by"));
                    rmsbookingMaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                    rmsbookingMaverick.setDisposition2(rs.getString("disposition_2"));
                    rmsbookingMaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                    rmsbookingMaverick.setDisposition2By(rs.getString("disposition_2_by"));
                    rmsbookingMaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                    rmsbookingMaverick.setStatus(rs.getString("status"));
                    rmsbookingMaverick.setFlag(rs.getString("flag"));
                    rmsbookingMaverick.setCreatedBy(rs.getString("created_by"));
                    rmsbookingMaverick.setCreatedDate(rs.getString("created_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting rms booking maverick for id: {}", rmsbookingMaverickId, e);
        }
        return rmsbookingMaverick;
    }

    public List<RmsBookingMaverick> getRmsBookingMaverickList() {
        List<RmsBookingMaverick> rmsbookingMaverickList = new ArrayList<RmsBookingMaverick>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_MAVERICK_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingMaverick rmsbookingMaverick = new RmsBookingMaverick();
                rmsbookingMaverick.setId(rs.getString("id"));
                rmsbookingMaverick.setGroupId(rs.getString("group_id"));
                rmsbookingMaverick.setModule(rs.getString("module"));
                rmsbookingMaverick.setSubmodule(rs.getString("submodule"));
                rmsbookingMaverick.setDisposition1(rs.getString("disposition_1"));
                rmsbookingMaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                rmsbookingMaverick.setDisposition1By(rs.getString("disposition_1_by"));
                rmsbookingMaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                rmsbookingMaverick.setDisposition2(rs.getString("disposition_2"));
                rmsbookingMaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                rmsbookingMaverick.setDisposition2By(rs.getString("disposition_2_by"));
                rmsbookingMaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                rmsbookingMaverick.setStatus(rs.getString("status"));
                rmsbookingMaverick.setFlag(rs.getString("flag"));
                rmsbookingMaverick.setCreatedBy(rs.getString("created_by"));
                rmsbookingMaverick.setCreatedDate(rs.getString("created_date"));
                rmsbookingMaverickList.add(rmsbookingMaverick);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting rms booking maverick list", e);
        }
        return rmsbookingMaverickList;
    }

    public List<RmsBookingMaverick> getRmsBookingMaverickListUnionWithItemMaverick() {
        List<RmsBookingMaverick> rmsbookingMaverickList = new ArrayList<RmsBookingMaverick>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_MAVERICK_LIST_UNION_WITH_ITEM_MAVERICK); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingMaverick rmsbookingMaverick = new RmsBookingMaverick();
                rmsbookingMaverick.setId(rs.getString("id"));
                rmsbookingMaverick.setId2(rs.getString("id2"));
                rmsbookingMaverick.setModule(rs.getString("module"));
                rmsbookingMaverick.setSubmodule(rs.getString("submodule"));
                rmsbookingMaverick.setDisposition1(rs.getString("disposition_1"));
                rmsbookingMaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                rmsbookingMaverick.setDisposition1By(rs.getString("disposition_1_by"));
                rmsbookingMaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                rmsbookingMaverick.setDisposition2(rs.getString("disposition_2"));
                rmsbookingMaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                rmsbookingMaverick.setDisposition2By(rs.getString("disposition_2_by"));
                rmsbookingMaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                rmsbookingMaverick.setStatus(rs.getString("status"));
                rmsbookingMaverick.setFlag(rs.getString("flag"));
                rmsbookingMaverick.setCreatedBy(rs.getString("created_by"));
                rmsbookingMaverick.setCreatedDate(rs.getString("created_date"));
                rmsbookingMaverickList.add(rmsbookingMaverick);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting rms booking maverick union list", e);
        }
        return rmsbookingMaverickList;
    }
    
    public List<RmsBookingMaverick> getItemMaverickUnionRmsBookingMaverick() {
        List<RmsBookingMaverick> rmsbookingMaverickList = new ArrayList<RmsBookingMaverick>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_MAVERICK_UNION_RMS_BOOKING_MAVERICK); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingMaverick rmsbookingMaverick = new RmsBookingMaverick();
                rmsbookingMaverick.setId(rs.getString("id"));
                rmsbookingMaverick.setId2(rs.getString("id2"));
                rmsbookingMaverick.setItemType(rs.getString("item_type"));
                rmsbookingMaverick.setItemId(rs.getString("item_identifier"));
                rmsbookingMaverick.setModule(rs.getString("module"));
                rmsbookingMaverick.setSubmodule(rs.getString("submodule"));
                rmsbookingMaverick.setDisposition1(rs.getString("disposition_1"));
                rmsbookingMaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                rmsbookingMaverick.setDisposition1By(rs.getString("disposition_1_by"));
                rmsbookingMaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                rmsbookingMaverick.setDisposition2(rs.getString("disposition_2"));
                rmsbookingMaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                rmsbookingMaverick.setDisposition2By(rs.getString("disposition_2_by"));
                rmsbookingMaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                rmsbookingMaverick.setStatus(rs.getString("status"));
                rmsbookingMaverick.setFlag(rs.getString("flag"));
                rmsbookingMaverick.setCreatedBy(rs.getString("created_by"));
                rmsbookingMaverick.setCreatedDate(rs.getString("created_date"));
                rmsbookingMaverickList.add(rmsbookingMaverick);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting rms booking maverick union list", e);
        }
        return rmsbookingMaverickList;
    }

}