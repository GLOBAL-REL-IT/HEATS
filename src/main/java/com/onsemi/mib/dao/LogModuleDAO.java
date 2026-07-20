package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.model.LogOuterBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.mib.model.LogRmsLot;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogModuleDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogModuleDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public LogModuleDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    //RMSLot Log Query
    public QueryResult insertRmsLotLog(LogRmsLot log) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_log_rmslot (ftp_id, inner_id, outer_id, module_id, module_name, status, created_date, created_by) "
                    + "VALUES (?,?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, log.getFtpId());
            ps.setString(2, log.getInnerId());
            ps.setString(3, log.getOuterId());
            ps.setString(4, log.getModuleId());
            ps.setString(5, log.getModuleName());
            ps.setString(6, log.getLogStatus());
            ps.setString(7, log.getCreatedBy());
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

    public List<LogRmsLot> getLogRmsLotList(String ftpId) {
        String sql = "SELECT *, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view"
                + "FROM sr_log_rmslot "
                + "WHERE ftp_id = ? ";
        List<LogRmsLot> logModuleList = new ArrayList<LogRmsLot>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpId);
            LogRmsLot log;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new LogRmsLot();
                log.setId(rs.getString("id"));
                log.setFtpId(rs.getString("ftp_id"));
                log.setInnerId(rs.getString("inner_id"));
                log.setOuterId(rs.getString("outer_id"));
                log.setModuleId(rs.getString("module_id"));
                log.setModuleName(rs.getString("module_name"));
                log.setLogStatus(rs.getString("status"));
                log.setCreatedDate(rs.getString("created_date_view"));
                log.setCreatedBy(rs.getString("created_by"));
                logModuleList.add(log);
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
        return logModuleList;
    }

    public List<LogRmsLot> getAllLotLogPerGroupId(String groupId) {
        String sql = "SELECT *, DATE_FORMAT(L.created_date,'%d %M %Y %h:%i %p') AS log_created_date_view, DATE_FORMAT(F.p_status_date,'%d %M %Y %h:%i %p') AS p_status_date_view, "
                + "DATE_FORMAT(F.scrap_date,'%d %M %Y %h:%i %p') AS scrap_date_view, UPPER(DATE_FORMAT(F.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATEDIFF(mth_to_scrap, NOW()) AS aging, "
                + "DATE_FORMAT(F.completed_date,'%d %M %Y') AS comp_date_view "
                + "FROM sr_log_rmslot L, sr_ftp_data F "
                + "WHERE L.ftp_id = F.id AND F.group_id = ? "
                + "ORDER BY F.rmslot_event, L.created_date ASC ";
        List<LogRmsLot> logModuleList = new ArrayList<LogRmsLot>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, groupId);
            ResultSet rs = ps.executeQuery();
            LogRmsLot logRmsLot;
            while (rs.next()) {
                logRmsLot = new LogRmsLot();
                logRmsLot.setId(rs.getString("L.id"));
                logRmsLot.setFtpId(rs.getString("L.ftp_id"));
                logRmsLot.setInnerId(rs.getString("L.inner_id"));
                logRmsLot.setOuterId(rs.getString("L.outer_id"));
                logRmsLot.setModuleId(rs.getString("L.module_id"));
                logRmsLot.setModuleName(rs.getString("L.module_name"));
                logRmsLot.setLogStatus(rs.getString("L.status"));
                logRmsLot.setCreatedDate(rs.getString("log_created_date_view"));
                logRmsLot.setCreatedBy(rs.getString("L.created_by"));
                //sr_ftp_data
                logRmsLot.setGroupId(rs.getString("F.group_id"));
                logRmsLot.setRmsId(rs.getString("F.rms_id"));
                logRmsLot.setRmsEvent(rs.getString("F.rms_event"));
                logRmsLot.setLotType(rs.getString("F.lot_type"));
                logRmsLot.setRmsLotEvent(rs.getString("F.rmslot_event"));
                logRmsLot.setLotQty(rs.getString("F.lot_qty"));
                logRmsLot.setRmsStatus(rs.getString("F.rms_status"));
                logRmsLot.setpStatus(rs.getString("F.p_status"));
                logRmsLot.setpStatusDate(rs.getString("p_status_date_view"));
                logRmsLot.setStressCompStatus(rs.getString("F.stress_comp_status"));
                logRmsLot.setPkgFamily(rs.getString("F.pkg_family"));
                logRmsLot.setPkgName(rs.getString("F.pkg_name"));
                logRmsLot.setScrapDate(rs.getString("scrap_date_view"));
                logRmsLot.setMthToScrap(rs.getString("mth_to_scrap_view"));
                logRmsLot.setCompDate(rs.getString("comp_date_view"));
                logRmsLot.setLotStatus(rs.getString("F.status"));
                logRmsLot.setFlag(rs.getString("F.flag"));
                logRmsLot.setAging(rs.getString("aging"));
                logModuleList.add(logRmsLot);
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
        return logModuleList;
    }

    public List<LogRmsLot> getAllLotLogPerFtpId(String ftpId) {
        String sql = "SELECT *, DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS log_created_date_view "
                + "FROM sr_log_rmslot "
                + "WHERE ftp_id = ? "
                + "ORDER BY created_date ASC ";
        List<LogRmsLot> logModuleList = new ArrayList<LogRmsLot>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpId);
            ResultSet rs = ps.executeQuery();
            LogRmsLot logRmsLot;
            while (rs.next()) {
                logRmsLot = new LogRmsLot();
                logRmsLot.setId(rs.getString("id"));
                logRmsLot.setFtpId(rs.getString("ftp_id"));
                logRmsLot.setInnerId(rs.getString("inner_id"));
                logRmsLot.setOuterId(rs.getString("outer_id"));
                logRmsLot.setModuleId(rs.getString("module_id"));
                logRmsLot.setModuleName(rs.getString("module_name"));
                logRmsLot.setLogStatus(rs.getString("status"));
                logRmsLot.setCreatedDate(rs.getString("log_created_date_view"));
                logRmsLot.setCreatedBy(rs.getString("created_by"));
                logModuleList.add(logRmsLot);
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
        return logModuleList;
    }

    public List<LogRmsLot> getAllLotLogPerFtpIdInnerId(String ftpId, String innerId) {
        String sql = "SELECT *, DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS log_created_date_view "
                + "FROM sr_log_rmslot "
                + "WHERE ftp_id = ? "
                + "ORDER BY created_date ASC ";
        List<LogRmsLot> logModuleList = new ArrayList<LogRmsLot>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpId);
            ResultSet rs = ps.executeQuery();
            LogRmsLot logRmsLot;
            while (rs.next()) {
                logRmsLot = new LogRmsLot();
                logRmsLot.setId(rs.getString("id"));
                logRmsLot.setFtpId(rs.getString("ftp_id"));
                logRmsLot.setInnerId(rs.getString("inner_id"));
                logRmsLot.setOuterId(rs.getString("outer_id"));
                logRmsLot.setModuleId(rs.getString("module_id"));
                logRmsLot.setModuleName(rs.getString("module_name"));
                logRmsLot.setLogStatus(rs.getString("status"));
                logRmsLot.setCreatedDate(rs.getString("log_created_date_view"));
                logRmsLot.setCreatedBy(rs.getString("created_by"));
                logModuleList.add(logRmsLot);
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
        return logModuleList;
    }

    public Integer getCountExistingRmsLotLog(String ftpId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count "
                    + "FROM sr_log_rmslot "
                    + "WHERE ftp_id = ? "
            );
            ps.setString(1, ftpId);
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

    //Outer Box Log Query
    public QueryResult insertOuterLog(LogOuterBox log) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_log_outer (outer_id, module_id, module_name, status, created_date, created_by) "
                    + "VALUES (?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, log.getOuterId());
            ps.setString(2, log.getModuleId());
            ps.setString(3, log.getModuleName());
            ps.setString(4, log.getStatus());
            ps.setString(5, log.getCreatedBy());
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

    public List<LogOuterBox> getLogOuterList(String ftpId) {
        String sql = "SELECT *, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view"
                + "FROM sr_log_outer "
                + "WHERE ftp_id = ? ";
        List<LogOuterBox> logModuleList = new ArrayList<LogOuterBox>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpId);
            LogOuterBox log;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new LogOuterBox();
                log.setId(rs.getString("id"));
                log.setOuterId(rs.getString("outer_id"));
                log.setModuleId(rs.getString("module_id"));
                log.setModuleName(rs.getString("module_name"));
                log.setStatus(rs.getString("status"));
                log.setCreatedDate(rs.getString("created_date_view"));
                log.setCreatedBy(rs.getString("created_by"));
                logModuleList.add(log);
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
        return logModuleList;
    }

    public List<LogOuterBox> getLogOuterListNew(String reqId) {
        String sql = "SELECT ou.*, DATE_FORMAT(ou.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, re.req_box_id "
                + "FROM sr_log_outer ou, sr_request re "
                + "WHERE ou.outer_id = re.id "
                + "AND ou.outer_id = ? ";
        List<LogOuterBox> logModuleList = new ArrayList<LogOuterBox>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, reqId);
            LogOuterBox log;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new LogOuterBox();
                log.setId(rs.getString("id"));
                log.setOuterId(rs.getString("outer_id"));
                log.setModuleId(rs.getString("module_id"));
                log.setModuleName(rs.getString("module_name"));
                log.setStatus(rs.getString("status"));
                log.setCreatedDate(rs.getString("created_date_view"));
                log.setCreatedBy(rs.getString("created_by"));
                log.setBoxId(rs.getString("re.req_box_id"));
                logModuleList.add(log);
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
        return logModuleList;
    }

    public Integer getCountExistingOuterLog(String outerId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count "
                    + "FROM sr_log_outer "
                    + "WHERE outer_id = ? "
            );
            ps.setString(1, outerId);
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