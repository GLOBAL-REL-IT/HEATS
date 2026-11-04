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
import com.onsemi.mib.model.RmsBookingIonicConfig;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingIonicConfigDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingIonicConfigDAO.class);
    private final DataSource dataSource;

    public RmsBookingIonicConfigDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    private static final String INSERT_RMS_BOOKING_IONIC_CONFIG = "INSERT INTO rms_booking_ionic_config (event, pass_value, created_by, created_date) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_RMS_BOOKING_IONIC_CONFIG = "UPDATE rms_booking_ionic_config SET event = ?, pass_value = ?, created_by = ?, created_date = ? WHERE id = ?";
    private static final String DELETE_RMS_BOOKING_IONIC_CONFIG = "DELETE FROM rms_booking_ionic_config WHERE id = ?";

    public QueryResult insertRmsBookingIonicConfig(RmsBookingIonicConfig rmsBookingIonicConfig) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_RMS_BOOKING_IONIC_CONFIG, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rmsBookingIonicConfig.getEvent());
            ps.setString(2, rmsBookingIonicConfig.getPassValue());
            ps.setString(3, rmsBookingIonicConfig.getCreatedBy());
            ps.setString(4, rmsBookingIonicConfig.getCreatedDate());
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
            LOGGER.error("Error inserting RMS booking ionic config. Event: {}, CreatedBy: {}, Error: {}", rmsBookingIonicConfig.getEvent(), rmsBookingIonicConfig.getCreatedBy(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingIonicConfig(RmsBookingIonicConfig rmsBookingIonicConfig) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_RMS_BOOKING_IONIC_CONFIG)) {
            ps.setString(1, rmsBookingIonicConfig.getEvent());
            ps.setString(2, rmsBookingIonicConfig.getPassValue());
            ps.setString(3, rmsBookingIonicConfig.getCreatedBy());
            ps.setString(4, rmsBookingIonicConfig.getCreatedDate());
            ps.setString(5, rmsBookingIonicConfig.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating RMS booking ionic config. Id: {}, Event: {}, Error: {}", rmsBookingIonicConfig.getId(), rmsBookingIonicConfig.getEvent(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult deleteRmsBookingIonicConfig(String rmsBookingIonicConfigId) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_RMS_BOOKING_IONIC_CONFIG)) {
            ps.setString(1, rmsBookingIonicConfigId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting RMS booking ionic config. Id: {}, Error: {}", rmsBookingIonicConfigId, e.getMessage(), e);
        }
        return queryResult;
    }

    private static final String GET_RMS_BOOKING_IONIC_CONFIG = "SELECT id, event, pass_value, created_by, created_date FROM rms_booking_ionic_config WHERE id = ?";
    private static final String GET_RMS_BOOKING_IONIC_CONFIG_BY_EVENT = "SELECT id, event, pass_value, created_by, created_date FROM rms_booking_ionic_config WHERE event = ?";
    private static final String GET_RMS_BOOKING_IONIC_CONFIG_LIST = "SELECT id, event, pass_value, created_by, created_date FROM rms_booking_ionic_config ORDER BY id ASC";
    private static final String COUNT_BY_EVENT = "SELECT COUNT(*) AS count FROM rms_booking_ionic_config WHERE event = ?";

    public RmsBookingIonicConfig getRmsBookingIonicConfig(String rmsBookingIonicConfigId) {
        RmsBookingIonicConfig rmsBookingIonicConfig = null;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_IONIC_CONFIG)) {
            ps.setString(1, rmsBookingIonicConfigId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingIonicConfig = new RmsBookingIonicConfig();
                    rmsBookingIonicConfig.setId(rs.getString("id"));
                    rmsBookingIonicConfig.setEvent(rs.getString("event"));
                    rmsBookingIonicConfig.setPassValue(rs.getString("pass_value"));
                    rmsBookingIonicConfig.setCreatedBy(rs.getString("created_by"));
                    rmsBookingIonicConfig.setCreatedDate(rs.getString("created_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking ionic config. Id: {}, Error: {}", rmsBookingIonicConfigId, e.getMessage(), e);
        }
        return rmsBookingIonicConfig;
    }

    public RmsBookingIonicConfig getRmsBookingIonicConfigByEvent(String event) {
        RmsBookingIonicConfig rmsBookingIonicConfig = null;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_IONIC_CONFIG_BY_EVENT)) {
            ps.setString(1, event);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingIonicConfig = new RmsBookingIonicConfig();
                    rmsBookingIonicConfig.setId(rs.getString("id"));
                    rmsBookingIonicConfig.setEvent(rs.getString("event"));
                    rmsBookingIonicConfig.setPassValue(rs.getString("pass_value"));
                    rmsBookingIonicConfig.setCreatedBy(rs.getString("created_by"));
                    rmsBookingIonicConfig.setCreatedDate(rs.getString("created_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking ionic config by event. Event: {}, Error: {}", event, e.getMessage(), e);
        }
        return rmsBookingIonicConfig;
    }

    public List<RmsBookingIonicConfig> getRmsBookingIonicConfigList() {
        List<RmsBookingIonicConfig> rmsBookingIonicConfigList = new ArrayList<>();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_IONIC_CONFIG_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingIonicConfig rmsBookingIonicConfig = new RmsBookingIonicConfig();
                rmsBookingIonicConfig.setId(rs.getString("id"));
                rmsBookingIonicConfig.setEvent(rs.getString("event"));
                rmsBookingIonicConfig.setPassValue(rs.getString("pass_value"));
                rmsBookingIonicConfig.setCreatedBy(rs.getString("created_by"));
                rmsBookingIonicConfig.setCreatedDate(rs.getString("created_date"));
                rmsBookingIonicConfigList.add(rmsBookingIonicConfig);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking ionic config list. Error: {}", e.getMessage(), e);
        }
        return rmsBookingIonicConfigList;
    }

    public Integer getCountByEvent(String event) {
        Integer count = 0;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(COUNT_BY_EVENT)) {
            ps.setString(1, event);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting RMS booking ionic config count. Event: {}, Error: {}", event, e.getMessage(), e);
        }
        return count;
    }

}