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
import com.onsemi.mib.model.Log;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public LogDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertLog(Log log) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_log (request_id, detail, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, log.getRequestId());
            ps.setString(2, log.getDetail());
            ps.setString(3, log.getCreatedBy());
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

    public QueryResult updateLog(Log log) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_log SET request_id = ?, detail = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, log.getRequestId());
            ps.setString(2, log.getDetail());
            ps.setString(3, log.getCreatedBy());
            ps.setString(4, log.getCreatedDate());
            ps.setString(5, log.getId());
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

    public QueryResult deleteLog(String logId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_log WHERE id = '" + logId + "'"
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

    public Log getLog(String logId) {
        String sql = "SELECT * FROM sr_log WHERE id = '" + logId + "'";
        Log log = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new Log();
                log.setId(rs.getString("id"));
                log.setRequestId(rs.getString("request_id"));
                log.setDetail(rs.getString("detail"));
                log.setCreatedBy(rs.getString("created_by"));
                log.setCreatedDate(rs.getString("created_date"));
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
        return log;
    }
    
    public List<Log> getLogList(String reqId) {
        String sql = "SELECT * FROM sr_log WHERE request_id = '" + reqId + "'";
        LOGGER.info("LOGGER FOR LOGGER NK DAPATKAN SEMUA LOG LIST DEKAT SINI :: " +sql);
        List<Log> logList = new ArrayList<Log>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Log log;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new Log();
                log.setId(rs.getString("id"));
                log.setRequestId(rs.getString("request_id"));
                log.setDetail(rs.getString("detail"));
                log.setCreatedBy(rs.getString("created_by"));
                log.setCreatedDate(rs.getString("created_date"));
                logList.add(log);
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
        return logList;
    }

    public List<Log> getLogList() {
        String sql = "SELECT * FROM sr_log ORDER BY id ASC";
        List<Log> logList = new ArrayList<Log>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Log log;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new Log();
                log.setId(rs.getString("id"));
                log.setRequestId(rs.getString("request_id"));
                log.setDetail(rs.getString("detail"));
                log.setCreatedBy(rs.getString("created_by"));
                log.setCreatedDate(rs.getString("created_date"));
                logList.add(log);
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
        return logList;
    }
}
