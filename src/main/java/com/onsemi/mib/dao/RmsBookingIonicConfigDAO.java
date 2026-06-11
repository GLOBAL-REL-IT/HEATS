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
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingIonicConfigDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRmsBookingIonicConfig(RmsBookingIonicConfig rmsbookingIonicConfig) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_ionic_config (event, pass_value, created_by, created_date) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rmsbookingIonicConfig.getEvent());
            ps.setString(2, rmsbookingIonicConfig.getPassValue());
            ps.setString(3, rmsbookingIonicConfig.getCreatedBy());
            ps.setString(4, rmsbookingIonicConfig.getCreatedDate());
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

    public QueryResult updateRmsBookingIonicConfig(RmsBookingIonicConfig rmsbookingIonicConfig) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_ionic_config SET event = ?, pass_value = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingIonicConfig.getEvent());
            ps.setString(2, rmsbookingIonicConfig.getPassValue());
            ps.setString(3, rmsbookingIonicConfig.getCreatedBy());
            ps.setString(4, rmsbookingIonicConfig.getCreatedDate());
            ps.setString(5, rmsbookingIonicConfig.getId());
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

    public QueryResult deleteRmsBookingIonicConfig(String rmsbookingIonicConfigId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_ionic_config WHERE id = '" + rmsbookingIonicConfigId + "'"
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

    public RmsBookingIonicConfig getRmsBookingIonicConfig(String rmsbookingIonicConfigId) {
        String sql = "SELECT * FROM rms_booking_ionic_config WHERE id = '" + rmsbookingIonicConfigId + "'";
        RmsBookingIonicConfig rmsbookingIonicConfig = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingIonicConfig = new RmsBookingIonicConfig();
                rmsbookingIonicConfig.setId(rs.getString("id"));
                rmsbookingIonicConfig.setEvent(rs.getString("event"));
                rmsbookingIonicConfig.setPassValue(rs.getString("pass_value"));
                rmsbookingIonicConfig.setCreatedBy(rs.getString("created_by"));
                rmsbookingIonicConfig.setCreatedDate(rs.getString("created_date"));
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
        return rmsbookingIonicConfig;
    }

    public RmsBookingIonicConfig getRmsBookingIonicConfigByEvent(String event) {
        String sql = "SELECT * FROM rms_booking_ionic_config WHERE event = '" + event + "'";
        RmsBookingIonicConfig rmsbookingIonicConfig = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingIonicConfig = new RmsBookingIonicConfig();
                rmsbookingIonicConfig.setId(rs.getString("id"));
                rmsbookingIonicConfig.setEvent(rs.getString("event"));
                rmsbookingIonicConfig.setPassValue(rs.getString("pass_value"));
                rmsbookingIonicConfig.setCreatedBy(rs.getString("created_by"));
                rmsbookingIonicConfig.setCreatedDate(rs.getString("created_date"));
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
        return rmsbookingIonicConfig;
    }

    public List<RmsBookingIonicConfig> getRmsBookingIonicConfigList() {
        String sql = "SELECT * FROM rms_booking_ionic_config ORDER BY id ASC";
        List<RmsBookingIonicConfig> rmsbookingIonicConfigList = new ArrayList<RmsBookingIonicConfig>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingIonicConfig rmsbookingIonicConfig;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingIonicConfig = new RmsBookingIonicConfig();
                rmsbookingIonicConfig.setId(rs.getString("id"));
                rmsbookingIonicConfig.setEvent(rs.getString("event"));
                rmsbookingIonicConfig.setPassValue(rs.getString("pass_value"));
                rmsbookingIonicConfig.setCreatedBy(rs.getString("created_by"));
                rmsbookingIonicConfig.setCreatedDate(rs.getString("created_date"));
                rmsbookingIonicConfigList.add(rmsbookingIonicConfig);
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
        return rmsbookingIonicConfigList;
    }

    public Integer getCountByEvent(String event) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_ionic_config inc WHERE inc.event = '" + event + "'"
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
