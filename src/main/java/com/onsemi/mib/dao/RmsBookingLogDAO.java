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
import com.onsemi.mib.model.RmsBookingLog;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingLogDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingLogDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingLogDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String INSERT_RMS_BOOKING_LOG = "INSERT INTO rms_booking_log (booking_id, detail, created_by, created_date) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_RMS_BOOKING_LOG = "UPDATE rms_booking_log SET booking_id = ?, detail = ?, created_by = ?, created_date = ? WHERE id = ?";
    private static final String DELETE_RMS_BOOKING_LOG = "DELETE FROM rms_booking_log WHERE id = ?";

    public QueryResult insertRmsBookingLog(RmsBookingLog rmsbookingLog) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_RMS_BOOKING_LOG, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rmsbookingLog.getBookingId());
            ps.setString(2, rmsbookingLog.getDetail());
            ps.setString(3, rmsbookingLog.getCreatedBy());
            ps.setString(4, rmsbookingLog.getCreatedDate());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting rms booking log. bookingId={}",
                    rmsbookingLog.getBookingId(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingLog(RmsBookingLog rmsbookingLog) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_RMS_BOOKING_LOG)) {
            ps.setString(1, rmsbookingLog.getBookingId());
            ps.setString(2, rmsbookingLog.getDetail());
            ps.setString(3, rmsbookingLog.getCreatedBy());
            ps.setString(4, rmsbookingLog.getCreatedDate());
            ps.setString(5, rmsbookingLog.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating rms booking log. id={}", rmsbookingLog.getId(), e);
        }
        return queryResult;
    }

    public QueryResult deleteRmsBookingLog(String rmsbookingLogId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_RMS_BOOKING_LOG)) {
            ps.setString(1, rmsbookingLogId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting rms booking log. id={}", rmsbookingLogId, e);
        }
        return queryResult;
    }

    private static final String GET_RMS_BOOKING_LOG = "SELECT id, booking_id, detail, created_by, created_date FROM rms_booking_log WHERE id = ?";

    public RmsBookingLog getRmsBookingLog(String rmsbookingLogId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_LOG)) {
            ps.setString(1, rmsbookingLogId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RmsBookingLog rmsbookingLog = new RmsBookingLog();
                    rmsbookingLog.setId(rs.getString("id"));
                    rmsbookingLog.setBookingId(rs.getString("booking_id"));
                    rmsbookingLog.setDetail(rs.getString("detail"));
                    rmsbookingLog.setCreatedBy(rs.getString("created_by"));
                    rmsbookingLog.setCreatedDate(rs.getString("created_date"));
                    return rmsbookingLog;
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error getting rms booking log. id={}",
                    rmsbookingLogId, e);
        }

        return null;
    }

    private static final String GET_RMS_BOOKING_LOG_LIST = "SELECT id, booking_id, detail, created_by, created_date FROM rms_booking_log ORDER BY id ASC";
    private static final String GET_RMS_BOOKING_LOG_BY_BOOKINGID = "SELECT id, booking_id, detail, created_by, created_date FROM rms_booking_log WHERE booking_id = ? ORDER BY id ASC";

    public List<RmsBookingLog> getRmsBookingLogList() {
        List<RmsBookingLog> rmsbookingLogList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_LOG_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingLog rmsbookingLog = new RmsBookingLog();
                rmsbookingLog.setId(rs.getString("id"));
                rmsbookingLog.setBookingId(rs.getString("booking_id"));
                rmsbookingLog.setDetail(rs.getString("detail"));
                rmsbookingLog.setCreatedBy(rs.getString("created_by"));
                rmsbookingLog.setCreatedDate(rs.getString("created_date"));
                rmsbookingLogList.add(rmsbookingLog);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting rms booking log list.", e);
        }
        return rmsbookingLogList;
    }

    public List<RmsBookingLog> getRmsBookingLogByBookingId(String bookingId) {
        List<RmsBookingLog> rmsbookingLogList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_LOG_BY_BOOKINGID)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingLog rmsbookingLog = new RmsBookingLog();
                    rmsbookingLog.setId(rs.getString("id"));
                    rmsbookingLog.setBookingId(rs.getString("booking_id"));
                    rmsbookingLog.setDetail(rs.getString("detail"));
                    rmsbookingLog.setCreatedBy(rs.getString("created_by"));
                    rmsbookingLog.setCreatedDate(rs.getString("created_date"));
                    rmsbookingLogList.add(rmsbookingLog);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting rms booking log list. bookingId={}", bookingId,e);
        }
        return rmsbookingLogList;
    }

}