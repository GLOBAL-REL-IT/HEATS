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
import com.onsemi.mib.model.Scrap;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public ScrapDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }
    
    private static final String SQL_INSERT_SCRAP = "INSERT INTO sr_scrap (request_id, month_scrap, scrap_by, scrap_date, status, created_by, created_date, flag) VALUES (?,?,?,?,?,?,NOW(),?)"; 
    private static final String SQL_UPDATE_SCRAP = "UPDATE sr_scrap SET request_id = ?, box_id = ?, month_scrap = ?, scrap_by = ?, scrap_date = ?, status = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?";
    private static final String SQL_DELETE_SCRAP = "DELETE FROM sr_scrap WHERE id = ?";
    private static final String SQL_REVERT_SCRAP = "UPDATE sr_scrap SET status = 'Pending Scrap', flag = '0' WHERE id = ?";
    private static final String SQL_UPDATE_READY_SCRAP = "UPDATE sr_scrap SET status = 'Ready for Scrap', flag = '0' WHERE id = ?";
    private static final String SQL_SCRAP_ALL = "UPDATE sr_scrap SET status = ?, flag = ?, scrap_by = ?, scrap_date = NOW() WHERE status = 'Ready for Scrap' AND flag = '0'";

    public QueryResult insertScrap(Scrap scrap) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_SCRAP, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, scrap.getRequestId());
            ps.setString(2, scrap.getMonthScrap());
            ps.setString(3, scrap.getScrapBy());
            ps.setString(4, scrap.getScrapDate());
            ps.setString(5, scrap.getStatus());
            ps.setString(6, scrap.getCreatedBy());
            ps.setString(7, scrap.getFlag());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting scrap", e);
        }
        return queryResult;
    }

    public QueryResult updateScrap(Scrap scrap) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_SCRAP)) {
            ps.setString(1, scrap.getRequestId());
            ps.setString(2, scrap.getBoxId());
            ps.setString(3, scrap.getMonthScrap());
            ps.setString(4, scrap.getScrapBy());
            ps.setString(5, scrap.getScrapDate());
            ps.setString(6, scrap.getStatus());
            ps.setString(7, scrap.getCreatedBy());
            ps.setString(8, scrap.getCreatedDate());
            ps.setString(9, scrap.getFlag());
            ps.setString(10, scrap.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating scrap. Id: {}", scrap.getId(), e);
        }
        return queryResult;
    }

    public QueryResult deleteScrap(String scrapId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_SCRAP)) {
            ps.setString(1, scrapId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting scrap. Id: {}", scrapId, e);
        }
        return queryResult;
    }

    public QueryResult revertScrap(String scrapId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_REVERT_SCRAP)) {
            ps.setString(1, scrapId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error reverting scrap. Id: {}", scrapId, e);
        }
        return queryResult;
    }

    public QueryResult updateReadyScrap(String scrapId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_READY_SCRAP)) {
            ps.setString(1, scrapId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setResult(0);
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating ready scrap. Id: {}", scrapId, e);
        }
        return queryResult;
    }

    public QueryResult scrapAll(Scrap scrap) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_SCRAP_ALL)) {
            ps.setString(1, scrap.getStatus());
            ps.setString(2, scrap.getFlag());
            ps.setString(3, scrap.getScrapBy());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error performing scrapAll", e);
        }
        return queryResult;
    }

    public QueryResult readyScrap(String rmsEvent) {
        String scrapId = getScrapId(rmsEvent);
        QueryResult queryResult = updateReadyScrap(scrapId);
        return queryResult;
    }
    
    private static final String SQL_GET_SCRAP = "SELECT * FROM sr_scrap WHERE id = ?"; 
    private static final String SQL_GET_SCRAP_BY_ID = "SELECT sc.*, re.id AS reqID, re.ftp_id AS ftpId, re.inv_id AS invID FROM sr_scrap sc, sr_request re WHERE sc.request_id = re.id AND sc.id = ?";
    private static final String SQL_GET_SCRAP_LIST = "SELECT * FROM sr_scrap ORDER BY id ASC";
    private static final String SQL_GET_PENDING_SCRAP_LIST = "SELECT sc.id AS scrap_id, rq.id AS request_id, ftp.id AS ftp_id, DATE_FORMAT(sc.month_scrap,'%M %Y') AS month_scrap, final_qty, actual_qty, inventory_rack, inventory_shelf, inventory_date, sc.status scrap_status, rq.status AS request_status, rmslot_event, lot_qty, p_status, pkg_name, sc.flag AS flag, inv.`status` AS inventory_status FROM sr_scrap sc INNER JOIN sr_request rq ON request_id = rq.id INNER JOIN sr_ftp_data ftp ON ftp_id = ftp.id INNER JOIN sr_inventory inv ON inv_id = inv.id WHERE sc.flag IN ('0') ORDER BY sc.month_scrap ASC";
    private static final String SQL_GET_READY_SCRAP_LIST = "SELECT sc.id AS scrap_id, rq.id AS request_id, ftp.id AS ftp_id, inv.id AS invId, MONTHNAME(month_scrap) AS month_scrap, final_qty, actual_qty, inventory_rack, inventory_shelf, inventory_date, sc.status scrap_status, rq.status AS request_status, rmslot_event, lot_qty, p_status, pkg_name, sc.flag AS flag, inv.`status` AS inventory_status FROM sr_scrap sc INNER JOIN sr_request rq ON request_id = rq.id INNER JOIN sr_ftp_data ftp ON ftp_id = ftp.id INNER JOIN sr_inventory inv ON inv_id = inv.id WHERE sc.flag IN ('0') AND sc.status = 'Ready for Scrap' ORDER BY scrap_id ASC";

    public Scrap getScrap(String scrapId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SCRAP)) {
            ps.setString(1, scrapId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Scrap scrap = new Scrap();
                    scrap.setId(rs.getString("id"));
                    scrap.setRequestId(rs.getString("request_id"));
                    scrap.setBoxId(rs.getString("box_id"));
                    scrap.setMonthScrap(rs.getString("month_scrap"));
                    scrap.setScrapBy(rs.getString("scrap_by"));
                    scrap.setScrapDate(rs.getString("scrap_date"));
                    scrap.setStatus(rs.getString("status"));
                    scrap.setCreatedBy(rs.getString("created_by"));
                    scrap.setCreatedDate(rs.getString("created_date"));
                    scrap.setFlag(rs.getString("flag"));
                    return scrap;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting scrap. Id: {}", scrapId, e);
        }
        return null;
    }

    public Scrap getScrapById(String scrapId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SCRAP_BY_ID)) {
            ps.setString(1, scrapId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Scrap scrap = new Scrap();
                    scrap.setId(rs.getString("id"));
                    scrap.setRequestId(rs.getString("reqID"));
                    scrap.setFtpId(rs.getString("ftpId"));
                    scrap.setInvId(rs.getString("invID"));
                    scrap.setStatus(rs.getString("status"));
                    scrap.setFlag(rs.getString("flag"));
                    return scrap;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting scrap by id: {}", scrapId, e);
        }
        return null;
    }

    public List<Scrap> getScrapList() {
        List<Scrap> scrapList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SCRAP_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Scrap scrap = new Scrap();
                scrap.setId(rs.getString("id"));
                scrap.setRequestId(rs.getString("request_id"));
                scrap.setMonthScrap(rs.getString("month_scrap"));
                scrap.setScrapBy(rs.getString("scrap_by"));
                scrap.setScrapDate(rs.getString("scrap_date"));
                scrap.setStatus(rs.getString("status"));
                scrap.setCreatedBy(rs.getString("created_by"));
                scrap.setCreatedDate(rs.getString("created_date"));
                scrap.setFlag(rs.getString("flag"));
                scrapList.add(scrap);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting scrap list", e);
        }
        return scrapList;
    }

    public List<Scrap> getPendingScrapList() {
        List<Scrap> scrapList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_PENDING_SCRAP_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Scrap scrap = new Scrap();
                scrap.setId(rs.getString("scrap_id"));
                scrap.setRequestId(rs.getString("request_id"));
                scrap.setMonthScrap(rs.getString("month_scrap"));
                scrap.setStatus(rs.getString("scrap_status"));
                scrap.setActualQty(rs.getString("actual_qty"));
                scrap.setRmsLotEvent(rs.getString("rmslot_event"));
                scrap.setPackageName(rs.getString("pkg_name"));
                scrap.setInvId(rs.getString("inventory_rack"));
                scrap.setShelf(rs.getString("inventory_shelf"));
                scrap.setInvStatus(rs.getString("inventory_status"));
                scrap.setFlag(rs.getString("flag"));
                scrapList.add(scrap);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting pending scrap list", e);
        }
        return scrapList;
    }

    public List<Scrap> getReadyScrapList() {
        List<Scrap> scrapList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_READY_SCRAP_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Scrap scrap = new Scrap();
                scrap.setId(rs.getString("scrap_id"));
                scrap.setRequestId(rs.getString("request_id"));
                scrap.setFtpId(rs.getString("ftp_id"));
                scrap.setInvId(rs.getString("invId"));
                scrap.setMonthScrap(rs.getString("month_scrap"));
                scrap.setStatus(rs.getString("scrap_status"));
                scrap.setActualQty(rs.getString("actual_qty"));
                scrap.setRmsLotEvent(rs.getString("rmslot_event"));
                scrap.setPackageName(rs.getString("pkg_name"));
                scrap.setShelf(rs.getString("inventory_shelf"));
                scrap.setInvStatus(rs.getString("inventory_status"));
                scrap.setFlag(rs.getString("flag"));
                scrapList.add(scrap);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting ready scrap list", e);
        }
        return scrapList;
    }
    
    private static final String SQL_GET_SCRAP_ID = "SELECT s1.id FROM sr_scrap s1 INNER JOIN sr_request s2 ON s1.request_id = s2.id INNER JOIN sr_ftp_data s3 ON s2.ftp_id = s3.id WHERE rmslot_event = ?";
    private static final String SQL_GET_SCRAP_BY_RMS_LOT_EVENT = "SELECT s1.id, s1.request_id, s2.ftp_id, s2.inv_id FROM sr_scrap s1 INNER JOIN sr_request s2 ON s1.request_id = s2.id INNER JOIN sr_ftp_data s3 ON s2.ftp_id = s3.id WHERE rmslot_event = ?";
    private static final String SQL_GET_COUNT_PENDING_SCRAP = "SELECT COUNT(sc.id) AS count FROM sr_scrap sc, sr_request re, sr_ftp_data ft WHERE sc.status = 'Pending Scrap' AND sc.request_id = re.id AND re.ftp_id = ft.id AND ft.rmslot_event = ?";
    private static final String SQL_GET_READY_FOR_SCRAP = "SELECT COUNT(sc.id) AS count FROM sr_scrap sc WHERE sc.status = 'Ready for Scrap' AND sc.flag = '0'";

    public String getScrapId(String rmsEvent) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SCRAP_ID)) {
            ps.setString(1, rmsEvent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting scrap id for rmsEvent: {}", rmsEvent, e);
        }
        return "0";
    }

    public Scrap getScrapbyRmsLotEvent(String rmsEvent) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SCRAP_BY_RMS_LOT_EVENT)) {
            ps.setString(1, rmsEvent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Scrap scrap = new Scrap();
                    scrap.setId(rs.getString("id"));
                    scrap.setRequestId(rs.getString("request_id"));
                    scrap.setFtpId(rs.getString("ftp_id"));
                    scrap.setInvId(rs.getString("inv_id"));
                    return scrap;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting scrap by rmsEvent: {}", rmsEvent, e);
        }
        return null;
    }

    public Integer getCountPendingScrap(String rmsLotEvent) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_PENDING_SCRAP)) {
            ps.setString(1, rmsLotEvent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting pending scrap count for rmsLotEvent: {}", rmsLotEvent, e);
        }
        return 0;
    }

    public Integer getCountReadyForScrap() {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_READY_FOR_SCRAP); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting ready for scrap count", e);
        }
        return 0;
    }

}