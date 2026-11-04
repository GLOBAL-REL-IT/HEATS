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
import com.onsemi.mib.model.RmsBookingDetailHwReplacement;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingDetailHwReplacementDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingDetailHwReplacementDAO.class);
    private final DataSource dataSource;

    public RmsBookingDetailHwReplacementDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    private static final String INSERT_RMS_BOOKING_DETAIL_HW_REPLACEMENT = "INSERT INTO rms_booking_detail_hw_replacement (booking_pkid, booking_hw_pkid, item_pkid, item_id, remarks, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";
    private static final String UPDATE_RMS_BOOKING_DETAIL_HW_REPLACEMENT = "UPDATE rms_booking_detail_hw_replacement SET booking_pkid = ?, booking_hw_pkid = ?, item_pkid = ?, item_id = ?, remarks = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?";
    private static final String UPDATE_RMS_BOOKING_DETAIL_HW_REPLACEMENT_FLAG = "UPDATE rms_booking_detail_hw_replacement SET flag = ? WHERE id = ?";
    private static final String DELETE_RMS_BOOKING_DETAIL_HW_REPLACEMENT = "DELETE FROM rms_booking_detail_hw_replacement WHERE id = ?";

    public QueryResult insertRmsBookingDetailHwReplacement(RmsBookingDetailHwReplacement rmsBookingDetailHwReplacement) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_RMS_BOOKING_DETAIL_HW_REPLACEMENT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rmsBookingDetailHwReplacement.getBookingPkid());
            ps.setString(2, rmsBookingDetailHwReplacement.getBookingHwPkid());
            ps.setString(3, rmsBookingDetailHwReplacement.getItemPkid());
            ps.setString(4, rmsBookingDetailHwReplacement.getItemId());
            ps.setString(5, rmsBookingDetailHwReplacement.getRemarks());
            ps.setString(6, rmsBookingDetailHwReplacement.getCreatedBy());
            ps.setString(7, rmsBookingDetailHwReplacement.getFlag());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting RMS booking detail HW replacement. BookingPkid: {}, BookingHwPkid: {}, ItemPkid: {}, Error: {}", rmsBookingDetailHwReplacement.getBookingPkid(), rmsBookingDetailHwReplacement.getBookingHwPkid(), rmsBookingDetailHwReplacement.getItemPkid(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailHwReplacement(RmsBookingDetailHwReplacement rmsBookingDetailHwReplacement) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_RMS_BOOKING_DETAIL_HW_REPLACEMENT)) {
            ps.setString(1, rmsBookingDetailHwReplacement.getBookingPkid());
            ps.setString(2, rmsBookingDetailHwReplacement.getBookingHwPkid());
            ps.setString(3, rmsBookingDetailHwReplacement.getItemPkid());
            ps.setString(4, rmsBookingDetailHwReplacement.getItemId());
            ps.setString(5, rmsBookingDetailHwReplacement.getRemarks());
            ps.setString(6, rmsBookingDetailHwReplacement.getCreatedBy());
            ps.setString(7, rmsBookingDetailHwReplacement.getCreatedDate());
            ps.setString(8, rmsBookingDetailHwReplacement.getFlag());
            ps.setString(9, rmsBookingDetailHwReplacement.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating RMS booking detail HW replacement. Id: {}, BookingPkid: {}, BookingHwPkid: {}, ItemPkid: {}, Error: {}", rmsBookingDetailHwReplacement.getId(), rmsBookingDetailHwReplacement.getBookingPkid(), rmsBookingDetailHwReplacement.getBookingHwPkid(), rmsBookingDetailHwReplacement.getItemPkid(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailHwReplacementFlag(RmsBookingDetailHwReplacement rmsBookingDetailHwReplacement) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_RMS_BOOKING_DETAIL_HW_REPLACEMENT_FLAG)) {
            ps.setString(1, rmsBookingDetailHwReplacement.getFlag());
            ps.setString(2, rmsBookingDetailHwReplacement.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating RMS booking detail HW replacement flag. Id: {}, Flag: {}, Error: {}", rmsBookingDetailHwReplacement.getId(), rmsBookingDetailHwReplacement.getFlag(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult deleteRmsBookingDetailHwReplacement(String rmsBookingDetailHwReplacementId) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_RMS_BOOKING_DETAIL_HW_REPLACEMENT)) {
            ps.setString(1, rmsBookingDetailHwReplacementId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting RMS booking detail HW replacement. Id: {}, Error: {}", rmsBookingDetailHwReplacementId, e.getMessage(), e);
        }
        return queryResult;
    }

    private static final String GET_RMS_BOOKING_DETAIL_HW_REPLACEMENT = "SELECT id, booking_pkid, booking_hw_pkid, item_pkid, item_id, remarks, created_by, created_date, flag FROM rms_booking_detail_hw_replacement WHERE id = ?";
    private static final String GET_RMS_BOOKING_DETAIL_HW_REPLACEMENT_LIST = "SELECT id, booking_pkid, booking_hw_pkid, item_pkid, item_id, remarks, created_by, created_date, flag FROM rms_booking_detail_hw_rePLACEMENT ORDER BY id ASC";
    private static final String GET_RMS_BOOKING_DETAIL_HW_REPLACEMENT_LIST_BY_BOOKING_PKID = "SELECT re.*, ha.item_type, ha.qty, ha.status, ha.id AS bookingHwId FROM rms_booking_detail_hw_replacement re LEFT JOIN rms_booking_hardware ha ON ha.pkid = re.booking_hw_pkid WHERE re.booking_pkid = ? AND re.flag = '0' ORDER BY re.item_id ASC";
    private static final String COUNT_BY_BOOKING_ID = "SELECT COUNT(*) AS count FROM rms_booking_detail_hw_replacement WHERE booking_pkid = ?";
    private static final String COUNT_FLAG_ZERO = "SELECT COUNT(*) AS count FROM rms_booking_detail_hw_replacement WHERE flag = '0'";

    public RmsBookingDetailHwReplacement getRmsBookingDetailHwReplacement(String rmsBookingDetailHwReplacementId) {
        RmsBookingDetailHwReplacement rmsBookingDetailHwReplacement = null;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_DETAIL_HW_REPLACEMENT)) {
            ps.setString(1, rmsBookingDetailHwReplacementId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingDetailHwReplacement = new RmsBookingDetailHwReplacement();
                    rmsBookingDetailHwReplacement.setId(rs.getString("id"));
                    rmsBookingDetailHwReplacement.setBookingPkid(rs.getString("booking_pkid"));
                    rmsBookingDetailHwReplacement.setBookingHwPkid(rs.getString("booking_hw_pkid"));
                    rmsBookingDetailHwReplacement.setItemPkid(rs.getString("item_pkid"));
                    rmsBookingDetailHwReplacement.setItemId(rs.getString("item_id"));
                    rmsBookingDetailHwReplacement.setRemarks(rs.getString("remarks"));
                    rmsBookingDetailHwReplacement.setCreatedBy(rs.getString("created_by"));
                    rmsBookingDetailHwReplacement.setCreatedDate(rs.getString("created_date"));
                    rmsBookingDetailHwReplacement.setFlag(rs.getString("flag"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking detail HW replacement. Id: {}, Error: {}", rmsBookingDetailHwReplacementId, e.getMessage(), e);
        }
        return rmsBookingDetailHwReplacement;
    }

    public List<RmsBookingDetailHwReplacement> getRmsBookingDetailHwReplacementList() {
        List<RmsBookingDetailHwReplacement> rmsBookingDetailHwReplacementList = new ArrayList<>();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_DETAIL_HW_REPLACEMENT_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetailHwReplacement rmsBookingDetailHwReplacement = new RmsBookingDetailHwReplacement();
                rmsBookingDetailHwReplacement.setId(rs.getString("id"));
                rmsBookingDetailHwReplacement.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetailHwReplacement.setBookingHwPkid(rs.getString("booking_hw_pkid"));
                rmsBookingDetailHwReplacement.setItemPkid(rs.getString("item_pkid"));
                rmsBookingDetailHwReplacement.setItemId(rs.getString("item_id"));
                rmsBookingDetailHwReplacement.setRemarks(rs.getString("remarks"));
                rmsBookingDetailHwReplacement.setCreatedBy(rs.getString("created_by"));
                rmsBookingDetailHwReplacement.setCreatedDate(rs.getString("created_date"));
                rmsBookingDetailHwReplacement.setFlag(rs.getString("flag"));
                rmsBookingDetailHwReplacementList.add(rmsBookingDetailHwReplacement);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking detail HW replacement list. Error: {}", e.getMessage(), e);
        }
        return rmsBookingDetailHwReplacementList;
    }

    public List<RmsBookingDetailHwReplacement> getRmsBookingDetailHwReplacementListByBookingPkid(String bookingPkid) {
        List<RmsBookingDetailHwReplacement> rmsBookingDetailHwReplacementList = new ArrayList<>();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_DETAIL_HW_REPLACEMENT_LIST_BY_BOOKING_PKID)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingDetailHwReplacement rmsBookingDetailHwReplacement = new RmsBookingDetailHwReplacement();
                    rmsBookingDetailHwReplacement.setId(rs.getString("id"));
                    rmsBookingDetailHwReplacement.setBookingPkid(rs.getString("booking_pkid"));
                    rmsBookingDetailHwReplacement.setBookingHwPkid(rs.getString("booking_hw_pkid"));
                    rmsBookingDetailHwReplacement.setItemPkid(rs.getString("item_pkid"));
                    rmsBookingDetailHwReplacement.setItemId(rs.getString("item_id"));
                    rmsBookingDetailHwReplacement.setRemarks(rs.getString("remarks"));
                    rmsBookingDetailHwReplacement.setCreatedBy(rs.getString("created_by"));
                    rmsBookingDetailHwReplacement.setCreatedDate(rs.getString("created_date"));
                    rmsBookingDetailHwReplacement.setItemType(rs.getString("item_type"));
                    rmsBookingDetailHwReplacement.setQty(rs.getString("qty"));
                    rmsBookingDetailHwReplacement.setStatus(rs.getString("status"));
                    rmsBookingDetailHwReplacement.setBookingHwId(rs.getString("bookingHwId"));
                    rmsBookingDetailHwReplacement.setFlag(rs.getString("flag"));
                    rmsBookingDetailHwReplacementList.add(rmsBookingDetailHwReplacement);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking detail HW replacement list. BookingPkid: {}, Error: {}", bookingPkid, e.getMessage(), e);
        }
        return rmsBookingDetailHwReplacementList;
    }

    public Integer getCountBookingId(String bookingId) {
        Integer count = 0;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(COUNT_BY_BOOKING_ID)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting RMS booking detail HW replacement count. BookingId: {}, Error: {}", bookingId, e.getMessage(), e);
        }
        return count;
    }

    public Integer getCountFlagZero() {
        Integer count = 0;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(COUNT_FLAG_ZERO); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting RMS booking detail HW replacement count with flag = 0. Error: {}", e.getMessage(), e);
        }
        return count;
    }

}