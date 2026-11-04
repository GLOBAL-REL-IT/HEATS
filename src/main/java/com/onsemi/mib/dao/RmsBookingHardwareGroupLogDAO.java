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
import com.onsemi.mib.model.RmsBookingHardwareGroupLog;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingHardwareGroupLogDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingHardwareGroupLogDAO.class);
    private final DataSource dataSource;

    public RmsBookingHardwareGroupLogDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    private static final String INSERT_RMS_BOOKING_HARDWARE_GROUP_LOG = "INSERT INTO rms_booking_hardware_group_log (group_id, detail, created_by, created_date) VALUES (?, ?, ?, NOW())";
    private static final String UPDATE_RMS_BOOKING_HARDWARE_GROUP_LOG = "UPDATE rms_booking_hardware_group_log SET group_id = ?, detail = ?, created_by = ?, created_date = ? WHERE id = ?";
    private static final String DELETE_RMS_BOOKING_HARDWARE_GROUP_LOG = "DELETE FROM rms_booking_hardware_group_log WHERE id = ?";

    public QueryResult insertRmsBookingHardwareGroupLog(RmsBookingHardwareGroupLog rmsBookingHardwareGroupLog) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_RMS_BOOKING_HARDWARE_GROUP_LOG, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rmsBookingHardwareGroupLog.getGroupId());
            ps.setString(2, rmsBookingHardwareGroupLog.getDetail());
            ps.setString(3, rmsBookingHardwareGroupLog.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(
                            String.valueOf(rs.getInt(1))
                    );
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting RMS booking hardware group log. GroupId: {}, CreatedBy: {}, Error: {}", rmsBookingHardwareGroupLog.getGroupId(), rmsBookingHardwareGroupLog.getCreatedBy(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareGroupLog(RmsBookingHardwareGroupLog rmsBookingHardwareGroupLog) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_RMS_BOOKING_HARDWARE_GROUP_LOG)) {
            ps.setString(1, rmsBookingHardwareGroupLog.getGroupId());
            ps.setString(2, rmsBookingHardwareGroupLog.getDetail());
            ps.setString(3, rmsBookingHardwareGroupLog.getCreatedBy());
            ps.setString(4, rmsBookingHardwareGroupLog.getCreatedDate());
            ps.setString(5, rmsBookingHardwareGroupLog.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating RMS booking hardware group log. Id: {}, GroupId: {}, Error: {}", rmsBookingHardwareGroupLog.getId(), rmsBookingHardwareGroupLog.getGroupId(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult deleteRmsBookingHardwareGroupLog(String rmsBookingHardwareGroupLogId) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_RMS_BOOKING_HARDWARE_GROUP_LOG)) {
            ps.setString(1, rmsBookingHardwareGroupLogId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting RMS booking hardware group log. Id: {}, Error: {}",  rmsBookingHardwareGroupLogId, e.getMessage(), e);
        }
        return queryResult;
    }

    private static final String GET_RMS_BOOKING_HARDWARE_GROUP_LOG = "SELECT id, group_id, detail, created_by, created_date FROM rms_booking_hardware_group_log WHERE id = ?";
    private static final String GET_RMS_BOOKING_HARDWARE_GROUP_LOG_LIST = "SELECT id, group_id, detail, created_by, created_date FROM rms_booking_hardware_group_log ORDER BY id ASC";

    public RmsBookingHardwareGroupLog getRmsBookingHardwareGroupLog(String rmsBookingHardwareGroupLogId) {
        RmsBookingHardwareGroupLog rmsBookingHardwareGroupLog = null;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_HARDWARE_GROUP_LOG)) {
            ps.setString(1, rmsBookingHardwareGroupLogId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingHardwareGroupLog = new RmsBookingHardwareGroupLog();
                    rmsBookingHardwareGroupLog.setId(rs.getString("id"));
                    rmsBookingHardwareGroupLog.setGroupId(rs.getString("group_id"));
                    rmsBookingHardwareGroupLog.setDetail(rs.getString("detail"));
                    rmsBookingHardwareGroupLog.setCreatedBy(rs.getString("created_by"));
                    rmsBookingHardwareGroupLog.setCreatedDate(rs.getString("created_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking hardware group log. Id: {}, Error: {}", rmsBookingHardwareGroupLogId, e.getMessage(), e);
        }
        return rmsBookingHardwareGroupLog;
    }

    public List<RmsBookingHardwareGroupLog> getRmsBookingHardwareGroupLogList() {
        List<RmsBookingHardwareGroupLog> rmsBookingHardwareGroupLogList = new ArrayList<>();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_HARDWARE_GROUP_LOG_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingHardwareGroupLog rmsBookingHardwareGroupLog = new RmsBookingHardwareGroupLog();
                rmsBookingHardwareGroupLog.setId(rs.getString("id"));
                rmsBookingHardwareGroupLog.setGroupId(rs.getString("group_id"));
                rmsBookingHardwareGroupLog.setDetail(rs.getString("detail"));
                rmsBookingHardwareGroupLog.setCreatedBy(rs.getString("created_by"));
                rmsBookingHardwareGroupLog.setCreatedDate(rs.getString("created_date"));
                rmsBookingHardwareGroupLogList.add(rmsBookingHardwareGroupLog);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking hardware group log list. Error: {}", e.getMessage(), e);
        }
        return rmsBookingHardwareGroupLogList;
    }

}