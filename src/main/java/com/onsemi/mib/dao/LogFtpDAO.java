<<<<<<< HEAD:src/main/java/com/onsemi/mib/dao/LogFtpDAO.java
package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
=======
package com.onsemi.ostorms.dao;

import com.onsemi.ostorms.db.DB;
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/dao/LogFtpDAO.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
<<<<<<< HEAD:src/main/java/com/onsemi/mib/dao/LogFtpDAO.java
import com.onsemi.mib.model.LogFtp;
import com.onsemi.mib.tools.QueryResult;
=======
import com.onsemi.ostorms.model.LogFtp;
import com.onsemi.ostorms.tools.QueryResult;
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/dao/LogFtpDAO.java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFtpDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogFtpDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public LogFtpDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertLogFtp(LogFtp logFtp) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_log_ftp (ftp_id, detail, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, logFtp.getFtpId());
            ps.setString(2, logFtp.getDetail());
            ps.setString(3, logFtp.getCreatedBy());
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

    public QueryResult updateLogFtp(LogFtp logFtp) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_log_ftp SET ftp_id = ?, detail = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, logFtp.getFtpId());
            ps.setString(2, logFtp.getDetail());
            ps.setString(3, logFtp.getCreatedBy());
            ps.setString(4, logFtp.getCreatedDate());
            ps.setString(5, logFtp.getId());
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

    public QueryResult deleteLogFtp(String logFtpId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_log_ftp WHERE id = '" + logFtpId + "'"
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

    public LogFtp getLogFtp(String logFtpId) {
        String sql = "SELECT * FROM sr_log_ftp WHERE id = '" + logFtpId + "'";
        LogFtp logFtp = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                logFtp = new LogFtp();
                logFtp.setId(rs.getString("id"));
                logFtp.setFtpId(rs.getString("ftp_id"));
                logFtp.setDetail(rs.getString("detail"));
                logFtp.setCreatedBy(rs.getString("created_by"));
                logFtp.setCreatedDate(rs.getString("created_date"));
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
        return logFtp;
    }

    public List<LogFtp> getLogFtpList() {
        String sql = "SELECT * FROM sr_log_ftp ORDER BY id ASC";
        List<LogFtp> logFtpList = new ArrayList<LogFtp>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            LogFtp logFtp;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                logFtp = new LogFtp();
                logFtp.setId(rs.getString("id"));
                logFtp.setFtpId(rs.getString("ftp_id"));
                logFtp.setDetail(rs.getString("detail"));
                logFtp.setCreatedBy(rs.getString("created_by"));
                logFtp.setCreatedDate(rs.getString("created_date"));
                logFtpList.add(logFtp);
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
        return logFtpList;
    }

    public List<LogFtp> getLogFtpListByFtpId(String ftpId) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view "
                + "FROM sr_log_ftp "
                + "WHERE ftp_id = '" + ftpId + "' ORDER BY id ASC";
        List<LogFtp> logFtpList = new ArrayList<LogFtp>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            LogFtp logFtp;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                logFtp = new LogFtp();
                logFtp.setId(rs.getString("id"));
                logFtp.setFtpId(rs.getString("ftp_id"));
                logFtp.setDetail(rs.getString("detail"));
                logFtp.setCreatedBy(rs.getString("created_by"));
                logFtp.setCreatedDate(rs.getString("created_date_view"));
                logFtpList.add(logFtp);
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
        return logFtpList;
    }
}
