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
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingLogDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRmsBookingLog(RmsBookingLog rmsbookingLog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_log (booking_id, detail, created_by, created_date) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rmsbookingLog.getBookingId());
            ps.setString(2, rmsbookingLog.getDetail());
            ps.setString(3, rmsbookingLog.getCreatedBy());
            ps.setString(4, rmsbookingLog.getCreatedDate());
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

    public QueryResult updateRmsBookingLog(RmsBookingLog rmsbookingLog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_log SET booking_id = ?, detail = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingLog.getBookingId());
            ps.setString(2, rmsbookingLog.getDetail());
            ps.setString(3, rmsbookingLog.getCreatedBy());
            ps.setString(4, rmsbookingLog.getCreatedDate());
            ps.setString(5, rmsbookingLog.getId());
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

    public QueryResult deleteRmsBookingLog(String rmsbookingLogId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_log WHERE id = '" + rmsbookingLogId + "'"
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

    public RmsBookingLog getRmsBookingLog(String rmsbookingLogId) {
        String sql = "SELECT * FROM rms_booking_log WHERE id = '" + rmsbookingLogId + "'";
        RmsBookingLog rmsbookingLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingLog = new RmsBookingLog();
                rmsbookingLog.setId(rs.getString("id"));
                rmsbookingLog.setBookingId(rs.getString("booking_id"));
                rmsbookingLog.setDetail(rs.getString("detail"));
                rmsbookingLog.setCreatedBy(rs.getString("created_by"));
                rmsbookingLog.setCreatedDate(rs.getString("created_date"));
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
        return rmsbookingLog;
    }

    public List<RmsBookingLog> getRmsBookingLogList() {
        String sql = "SELECT * FROM rms_booking_log ORDER BY id ASC";
        List<RmsBookingLog> rmsbookingLogList = new ArrayList<RmsBookingLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingLog rmsbookingLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingLog = new RmsBookingLog();
                rmsbookingLog.setId(rs.getString("id"));
                rmsbookingLog.setBookingId(rs.getString("booking_id"));
                rmsbookingLog.setDetail(rs.getString("detail"));
                rmsbookingLog.setCreatedBy(rs.getString("created_by"));
                rmsbookingLog.setCreatedDate(rs.getString("created_date"));
                rmsbookingLogList.add(rmsbookingLog);
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
        return rmsbookingLogList;
    }

}