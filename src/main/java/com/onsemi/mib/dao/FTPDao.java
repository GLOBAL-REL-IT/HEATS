package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.tools.QueryResult;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FTPDao.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public FTPDao() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_INSERT_FTP_DATA = "INSERT INTO sr_ftp_data (rms_id, lot_qty, p_status, lot_type, pkg_family, pkg_name, scrap_date, mth_to_scrap, completed_date, rms_event, modified_date, modified_by, created_date, created_by, status, rmslot_event, flag, actual_qty, creator) VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?,?,?,?)";
    private static final String SQL_UPDATE_STATUS = "UPDATE sr_ftp_data SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE id = ?";
    private static final String SQL_UPDATE_STATUS_PER_GROUP_ID = "UPDATE sr_ftp_data SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE group_id = ?";
    private static final String SQL_UPDATE_STATUS_PER_FTP_ID = "UPDATE sr_ftp_data SET status = ?, flag = ?, modified_date = NOW(), modified_by = ?, cancel_by = NULL, cancel_date = NULL WHERE id = ?";
    private static final String SQL_UPDATE_STATUS_AND_FLAG_BY_FTP_ID = "UPDATE sr_ftp_data SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE id = ?";
    private static final String SQL_UPDATE_QTY = "UPDATE sr_ftp_data SET lot_qty = ?, modified_date = NOW(), modified_by = ? WHERE id = ?";
    private static final String SQL_UPDATE_ACTUAL_QTY = "UPDATE sr_ftp_data SET actual_qty = ?, modified_date = NOW(), modified_by = ? WHERE id = ?";
    private static final String SQL_UPDATE_PACKAGE_FAMILY = "UPDATE sr_ftp_data SET pkg_family = ?, modified_date = NOW(), modified_by = ? WHERE id = ?";
    private static final String SQL_UPDATE_ACTUAL_QTY_FLAG_AND_STATUS = "UPDATE sr_ftp_data SET actual_qty = ?, flag = ?, status = ? WHERE id = ?";
    private static final String SQL_UPDATE_MONTH_TO_SCRAP = "UPDATE sr_ftp_data SET completed_date = ?, scrap_date = ?, mth_to_scrap = ?, modified_date = NOW(), modified_by = ? WHERE id = ?";
    private static final String SQL_UPDATE_CANCEL_RETENTION = "UPDATE sr_ftp_data SET cancel_by = ?, cancel_date = NOW(), status = ?, flag = ? WHERE id = ?";
    private static final String SQL_UPDATE_SELECTED_EXPIRED_LOT = "UPDATE sr_ftp_data SET status = ?, flag = ?, modified_date = NOW(), modified_by = ?, cancel_by = ?, cancel_date = NOW() WHERE id = ? AND flag = 0 AND ((YEAR(mth_to_scrap) < YEAR(NOW())) OR (MONTH(mth_to_scrap) < MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW())))";

    public QueryResult insertFTPdata(FTPdata ftpdata) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_FTP_DATA, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ftpdata.getRmsId());
            ps.setString(2, ftpdata.getUnitQty());
            ps.setString(3, ftpdata.getProcessStatus());
            ps.setString(4, ftpdata.getLotType());
            ps.setString(5, ftpdata.getPkgFamily());
            ps.setString(6, ftpdata.getPkgName());
            ps.setString(7, ftpdata.getScrapDate());
            ps.setString(8, ftpdata.getMthToScrap());
            ps.setString(9, ftpdata.getCompleteDate());
            ps.setString(10, ftpdata.getEvent());
            ps.setString(11, ftpdata.getModifiedBy());
            ps.setString(12, ftpdata.getCreatedBy());
            ps.setString(13, ftpdata.getStatus());
            ps.setString(14, ftpdata.getRmsLotEvent());
            ps.setString(15, ftpdata.getFlag());
            ps.setString(16, ftpdata.getActualQty());
            ps.setString(17, ftpdata.getCreator());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting FTP data", e);
        }
        return queryResult;
    }

    public QueryResult updateStatus(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS)) {
            ps.setString(1, ftpData.getStatus());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getModifiedBy());
            ps.setString(4, ftpData.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating status", e);
        }
        return queryResult;
    }

    public QueryResult updateStatusPerGroupId(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS_PER_GROUP_ID)) {
            ps.setString(1, ftpData.getStatus());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getModifiedBy());
            ps.setString(4, ftpData.getGroupId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating status by group id", e);
        }
        return queryResult;
    }

    public QueryResult updateStatusbyFtpId(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS_PER_FTP_ID)) {
            ps.setString(1, ftpData.getStatus());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getModifiedBy());
            ps.setString(4, ftpData.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating status by FTP id", e);
        }
        return queryResult;
    }

    public QueryResult updateStatusAndFlagbyFtpId(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS_AND_FLAG_BY_FTP_ID)) {
            ps.setString(1, ftpData.getStatus());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getModifiedBy());
            ps.setString(4, ftpData.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating status and flag by FTP id", e);
        }
        return queryResult;
    }

    public QueryResult updateQty(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_QTY)) {
            ps.setString(1, ftpData.getUnitQty());
            ps.setString(2, ftpData.getModifiedBy());
            ps.setString(3, ftpData.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating quantity", e);
        }
        return queryResult;
    }

    public QueryResult updateActualQty(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ACTUAL_QTY)) {
            ps.setString(1, ftpData.getActualQty());
            ps.setString(2, ftpData.getModifiedBy());
            ps.setString(3, ftpData.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating actual quantity", e);
        }
        return queryResult;
    }

    public QueryResult updatePkgFamily(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_PACKAGE_FAMILY)) {
            ps.setString(1, ftpData.getPkgFamily());
            ps.setString(2, ftpData.getModifiedBy());
            ps.setString(3, ftpData.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating package family", e);
        }
        return queryResult;
    }

    public QueryResult updateActualQtyFlagAndStatus(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ACTUAL_QTY_FLAG_AND_STATUS)) {
            ps.setString(1, ftpData.getActualQty());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getStatus());
            ps.setString(4, ftpData.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating actual quantity, flag and status", e);
        }
        return queryResult;
    }

    public QueryResult updateMthToScrap(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_MONTH_TO_SCRAP)) {
            ps.setString(1, ftpData.getCompleteDate());
            ps.setString(2, ftpData.getScrapDate());
            ps.setString(3, ftpData.getMthToScrap());
            ps.setString(4, ftpData.getModifiedBy());
            ps.setString(5, ftpData.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating month to scrap", e);
        }
        return queryResult;
    }

    public QueryResult updateCancelRetention(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_CANCEL_RETENTION)) {
            ps.setString(1, ftpData.getCancelBy());
            ps.setString(2, ftpData.getStatus());
            ps.setString(3, ftpData.getFlag());
            ps.setString(4, ftpData.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating cancel retention", e);
        }
        return queryResult;
    }

    public QueryResult updateSelectedExpiredLot(FTPdata ftpdata) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_SELECTED_EXPIRED_LOT)) {
            ps.setString(1, ftpdata.getStatus());
            ps.setString(2, ftpdata.getFlag());
            ps.setString(3, ftpdata.getModifiedBy());
            ps.setString(4, ftpdata.getCancelBy());
            ps.setString(5, ftpdata.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating selected expired lot", e);
        }
        return queryResult;
    }
    
    private static final String SQL_GET_FRP_DATA = "SELECT *, DATE_FORMAT(completed_date,'%d %M %Y') AS completed_date_view, DATE_FORMAT(scrap_date,'%d %M %Y') AS scrap_date_view, DATE_FORMAT(modified_date,'%d %M %Y') AS modified_date_view, DATE_FORMAT(created_date,'%d %M %Y') AS created_date_view, DATE_FORMAT(mth_to_scrap,'%M %Y') AS mth_to_scrap_view FROM sr_ftp_data WHERE rms_id = ? AND rms_event = ? AND lot_type = ?";
    private static final String SQL_GET_FTP_DATA_PER_RMS_LOT_EVENT = "SELECT *, DATE_FORMAT(completed_date,'%d %M %Y') AS completed_date_view, DATE_FORMAT(scrap_date,'%d %M %Y') AS scrap_date_view, DATE_FORMAT(mth_to_scrap,'%M %Y') AS mth_to_scrap_view FROM sr_ftp_data WHERE rmslot_event = ?";
    private static final String SQL_GET_FTP_DATA_PER_RMS_LOT_EVENT_FLAG_ZERO = "SELECT *, DATE_FORMAT(completed_date,'%d %M %Y') AS completed_date_view, DATE_FORMAT(scrap_date,'%d %M %Y') AS scrap_date_view, DATE_FORMAT(mth_to_scrap,'%M %Y') AS mth_to_scrap_view FROM sr_ftp_data WHERE rmslot_event = ? AND flag = '0'";
    private static final String SQL_GET_FTP_DATA_02 = "SELECT * FROM sr_ftp_data WHERE rms_id LIKE ? AND rms_event = ? AND lot_type = ?";
    private static final String SQL_GET_FTP_DATA_PER_COND = "SELECT * FROM sr_ftp_data WHERE rms_event = ? AND pkg_family = ? AND mth_to_scrap = ? AND rms_id = ? AND lot_type = ? AND flag = 0";
    private static final String SQL_GET_FTP_DATA_PER_RMS_LOT_EVENT_02 = "SELECT * FROM sr_ftp_data WHERE rms_event = ? AND pkg_family = ? AND mth_to_scrap = ? AND rmslot_event = ? AND flag = 0";

    public FTPdata getFtpData(String rmsId, String event, String lot) {
        FTPdata ftpdata = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FRP_DATA)) {
            ps.setString(1, rmsId);
            ps.setString(2, event);
            ps.setString(3, lot);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date_view"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                    ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                    ftpdata.setModifiedDate(rs.getString("modified_date_view"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    ftpdata.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting FTP data", e);
        }
        return ftpdata;
    }

    public FTPdata getFtpDataPerRmsLotEvent(String rmslotevent) {
        FTPdata ftpdata = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FTP_DATA_PER_RMS_LOT_EVENT)) {
            ps.setString(1, rmslotevent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setUnitQty(rs.getString("lot_qty"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date_view"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                    ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                    ftpdata.setModifiedDate(rs.getString("modified_date"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    ftpdata.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving FTP data for RMS Lot Event: {}", rmslotevent, e);
        }
        return ftpdata;
    }

    public FTPdata getFtpDataPerRmsLotEventFlagZero(String rmslotevent) {
        FTPdata ftpdata = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FTP_DATA_PER_RMS_LOT_EVENT_FLAG_ZERO)) {
            ps.setString(1, rmslotevent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setUnitQty(rs.getString("lot_qty"));
                    ftpdata.setActualQty(rs.getString("actual_qty"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date_view"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                    ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                    ftpdata.setModifiedDate(rs.getString("modified_date"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    ftpdata.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving FTP data by RMS lot event and flag 0: {}", rmslotevent, e);
        }
        return ftpdata;
    }

    public FTPdata getFtpData2(String rmsId, String event, String lot) {
        FTPdata ftpdata = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FTP_DATA_02)) {
            ps.setString(1, rmsId + "%");
            ps.setString(2, event);
            ps.setString(3, lot);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                    ftpdata.setCompleteDate(rs.getString("completed_date"));
                    ftpdata.setModifiedDate(rs.getString("modified_date"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    ftpdata.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving FTP data: rmsId={}, event={}, lot={}",rmsId, event, lot, e);
        }
        return ftpdata;
    }

    public FTPdata getFtpDataPerCond(String event, String mthToScrap, String pkgFamily, String rmsNo, String lotNo) {
        FTPdata ftpdata = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FTP_DATA_PER_COND)) {
            ps.setString(1, event);
            ps.setString(2, pkgFamily);
            ps.setString(3, mthToScrap);
            ps.setString(4, rmsNo);
            ps.setString(5, lotNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setUnitQty(rs.getString("lot_qty"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                    ftpdata.setCompleteDate(rs.getString("completed_date"));
                    ftpdata.setModifiedDate(rs.getString("modified_date"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    ftpdata.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving FTP data: event={}, mthToScrap={}, pkgFamily={}, rmsNo={}, lotNo={}", event, mthToScrap, pkgFamily, rmsNo, lotNo, e);
        }
        return ftpdata;
    }

    public FTPdata getFtpDataPerRMSLotEvent(String event, String mthToScrap, String pkgFamily, String rmsLotEvent) {
        FTPdata ftpdata = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FTP_DATA_PER_RMS_LOT_EVENT_02)) {
            ps.setString(1, event);
            ps.setString(2, pkgFamily);
            ps.setString(3, mthToScrap);
            ps.setString(4, rmsLotEvent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setUnitQty(rs.getString("lot_qty"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                    ftpdata.setCompleteDate(rs.getString("completed_date"));
                    ftpdata.setModifiedDate(rs.getString("modified_date"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    ftpdata.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving FTP data. Event: {}, MthToScrap: {}, PkgFamily: {}, RMSLotEvent: {}", event, mthToScrap, pkgFamily, rmsLotEvent, e);
        }
        return ftpdata;
    }
    
    private static final String SQL_GET_FTP_LOT_DATA_PER_COND = "SELECT * FROM sr_ftp_data WHERE rms_event = ? AND pkg_family = ? AND mth_to_scrap = ? AND rms_id = ? AND flag = 0";
    private static final String SQL_GET_FTP_DATA_BY_ID = "SELECT * FROM sr_ftp_data WHERE id = ?";
    private static final String SQL_GET_FTP_DATA_BY_REQUEST_ID = "SELECT ft.* FROM sr_ftp_data ft, sr_request re WHERE re.ftp_id = ft.id AND re.id = ?";
    private static final String SQL_GET_ALL_FTP_DATA = "SELECT id, group_id, rms_id, rms_event, pkg_family, pkg_name, p_status, status, DATEDIFF(mth_to_scrap, NOW()) AS aging, DATEDIFF(NOW(), completed_date) AS packingDay, GROUP_CONCAT(lot_type ORDER BY lot_type ASC SEPARATOR ', ') AS lot_concat, DATE_FORMAT(completed_date,'%d %b %Y') AS completed_date_view, DATE_FORMAT(mth_to_scrap,'%b %Y') AS mth_to_scrap_view FROM sr_ftp_data WHERE ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) AND flag = 0 GROUP BY group_id ORDER BY completed_date ASC";
    private static final String SQL_GET_ALL_FTP_DATA_LATEST = "SELECT id, rmslot_event, rms_id, rms_event, lot_type, pkg_family, pkg_name, p_status, status, DATEDIFF(mth_to_scrap, NOW()) AS aging, DATEDIFF(NOW(), completed_date) AS packingDay, DATE_FORMAT(completed_date,'%d %b %Y') AS completed_date_view, DATE_FORMAT(mth_to_scrap,'%b %Y') AS mth_to_scrap_view FROM sr_ftp_data WHERE ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) AND flag = 0 ORDER BY completed_date ASC";
    private static final String SQL_GET_ALL_FTP_DATA_FOR_MONTHLY_REPORT = "SELECT id, group_id, rms_id, rms_event, pkg_family, pkg_name, p_status, status, DATEDIFF(mth_to_scrap, NOW()) AS aging, DATEDIFF(NOW(), completed_date) AS packingDay, GROUP_CONCAT(lot_type ORDER BY lot_type ASC SEPARATOR ', ') AS lot_concat, DATE_FORMAT(completed_date,'%d %b %Y') AS completed_date_view, DATE_FORMAT(mth_to_scrap,'%b %Y') AS mth_to_scrap_view FROM sr_ftp_data WHERE ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) AND flag = 0 AND completed_date BETWEEN ? AND LAST_DAY(?) GROUP BY group_id ORDER BY completed_date ASC";
    private static final String SQL_GET_ALL_EXPIRED_FTP_DATA = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging FROM sr_ftp_data WHERE flag IN (0, 1, 9) AND ((YEAR(mth_to_scrap) < YEAR(NOW())) OR (MONTH(mth_to_scrap) < MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW())))";
    private static final String SQL_GET_EXPIRED_FTP_DATA_NEW = "SELECT id, group_id, rms_id, rms_event, lot_type, rmslot_event, p_status, pkg_family, completed_date, mth_to_scrap, pkg_name, status, DATEDIFF(mth_to_scrap, NOW()) AS aging FROM sr_ftp_data WHERE status LIKE '%New Record%' AND flag = 0 AND ((YEAR(mth_to_scrap) < YEAR(NOW())) OR (MONTH(mth_to_scrap) < MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW())))";
    private static final String SQL_GET_EXPIRED_FTP_DATA = "SELECT id, group_id, rms_id, rms_event, pkg_family, pkg_name, p_status, status, DATEDIFF(mth_to_scrap, NOW()) AS aging, GROUP_CONCAT(lot_type SEPARATOR ',') AS lot_concat, DATE_FORMAT(completed_date, '%d %M %Y') AS completed_date_view, DATE_FORMAT(mth_to_scrap, '%M %Y') AS mth_to_scrap_view FROM sr_ftp_data WHERE ((YEAR(mth_to_scrap) < YEAR(NOW())) OR (MONTH(mth_to_scrap) < MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) GROUP BY group_id ORDER BY completed_date DESC";
    private static final String SQL_GET_ALL_SUB_EVENT_PER_RMS = "SELECT rms_event FROM sr_ftp_data WHERE flag = 0 AND (rms_event LIKE ? OR rms_event = ?) AND rms_id = ? GROUP BY group_id ORDER BY rms_event";
    private static final String SQL_GET_ALL_EVENT_PER_RMS = "SELECT DISTINCT rms_event FROM sr_ftp_data WHERE flag = 0 AND rms_id = ? ORDER BY rms_event";

    public List<FTPdata> getFtpLotDataPerCond(String event, String mthToScrap, String pkgFamily, String rmsNo) {
        List<FTPdata> ftpDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FTP_LOT_DATA_PER_COND)) {
            ps.setString(1, event);
            ps.setString(2, pkgFamily);
            ps.setString(3, mthToScrap);
            ps.setString(4, rmsNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FTPdata ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setUnitQty(rs.getString("lot_qty"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                    ftpdata.setCompleteDate(rs.getString("completed_date"));
                    ftpdata.setModifiedDate(rs.getString("modified_date"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    ftpdata.setStatus(rs.getString("status"));
                    ftpDataList.add(ftpdata);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(
                    "Error retrieving FTP lot data. Event: {}, MthToScrap: {}, PkgFamily: {}, RMSNo: {}",
                    event, mthToScrap, pkgFamily, rmsNo, e);
        }
        return ftpDataList;
    }

    public FTPdata getFtpDataById(String id) {
        FTPdata ftpdata = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FTP_DATA_BY_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setUnitQty(rs.getString("lot_qty"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                    ftpdata.setCompleteDate(rs.getString("completed_date"));
                    ftpdata.setModifiedDate(rs.getString("modified_date"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    ftpdata.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving FTP data by ID: {}", id, e);
        }
        return ftpdata;
    }

    public FTPdata getFtpDataByRequestId(String requestId) {
        FTPdata ftpdata = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FTP_DATA_BY_REQUEST_ID)) {
            ps.setString(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setUnitQty(rs.getString("lot_qty"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                    ftpdata.setCompleteDate(rs.getString("completed_date"));
                    ftpdata.setModifiedDate(rs.getString("modified_date"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    ftpdata.setStatus(rs.getString("status"));
                    ftpdata.setCreator(rs.getString("creator"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving FTP data for Request ID: {}", requestId, e);
        }
        return ftpdata;
    }

    public List<FTPdata> getAllFtpData() {
        List<FTPdata> ftpDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_FTP_DATA); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FTPdata ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setConcatLot(rs.getString("lot_concat"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setAging(rs.getString("aging"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setStatus(rs.getString("status"));
                ftpdata.setPackingDay(rs.getString("packingDay"));
                ftpDataList.add(ftpdata);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving all FTP data", e);
        }
        return ftpDataList;
    }

    public List<FTPdata> getAllFtpDataLatest() {
        List<FTPdata> ftpDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_FTP_DATA_LATEST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FTPdata ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setAging(rs.getString("aging"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setStatus(rs.getString("status"));
                ftpdata.setPackingDay(rs.getString("packingDay"));
                ftpDataList.add(ftpdata);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving latest FTP data", e);
        }
        return ftpDataList;
    }

    public List<FTPdata> getAllFtpDataforMonhtlyReport(String fromDate, String toDate) {
        List<FTPdata> ftpDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_FTP_DATA_FOR_MONTHLY_REPORT)) {
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FTPdata ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setGroupId(rs.getString("group_id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setConcatLot(rs.getString("lot_concat"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                    ftpdata.setAging(rs.getString("aging"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setStatus(rs.getString("status"));
                    ftpdata.setPackingDay(rs.getString("packingDay"));
                    ftpDataList.add(ftpdata);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving monthly FTP report. From Date: {}, To Date: {}", fromDate, toDate, e);
        }
        return ftpDataList;
    }

    public List<FTPdata> getAllExpiredFtpData() {
        List<FTPdata> ftpDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_EXPIRED_FTP_DATA); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FTPdata ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setAging(rs.getString("aging"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setStatus(rs.getString("status"));
                ftpDataList.add(ftpdata);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving expired FTP data", e);
        }
        return ftpDataList;
    }

    public List<FTPdata> getAllExpiredFtpDataNew() {
        List<FTPdata> ftpDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_EXPIRED_FTP_DATA_NEW); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FTPdata ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setAging(rs.getString("aging"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setStatus(rs.getString("status"));
                ftpDataList.add(ftpdata);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving expired new FTP data", e);
        }
        return ftpDataList;
    }

    public List<FTPdata> getExpFtpData() {
        List<FTPdata> ftpDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_EXPIRED_FTP_DATA); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FTPdata ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setConcatLot(rs.getString("lot_concat"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setAging(rs.getString("aging"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setStatus(rs.getString("status"));
                ftpDataList.add(ftpdata);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving expired FTP summary data", e);
        }
        return ftpDataList;
    }

    public List<FTPdata> getAllSubEventPerRms(String event, String rmsNo) {
        List<FTPdata> subEventList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_SUB_EVENT_PER_RMS)) {
            ps.setString(1, event + "_");
            ps.setString(2, event);
            ps.setString(3, rmsNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FTPdata ftpData = new FTPdata();
                    ftpData.setEvent(rs.getString("rms_event"));
                    subEventList.add(ftpData);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving sub event list for RMS: {}", rmsNo, e);
        }
        return subEventList;
    }

    public List<FTPdata> getAllEventPerRms(String rmsNo) {
        List<FTPdata> subEventList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_EVENT_PER_RMS)) {
            ps.setString(1, rmsNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FTPdata ftpData = new FTPdata();
                    ftpData.setEvent(rs.getString("rms_event"));
                    subEventList.add(ftpData);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving event list for RMS: {}", rmsNo, e);
        }
        return subEventList;
    }

    public List<FTPdata> getAllActualDetailsPerRmsExtQuery(String rmsNo, String extQuery) {
        String sql = "SELECT * FROM sr_ftp_data WHERE flag = 0 AND rms_id = ? " + extQuery + " ORDER BY id";
        List<FTPdata> rmsDetailsList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rmsNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FTPdata ftpdata = new FTPdata();
                    ftpdata.setId(rs.getString("id"));
                    ftpdata.setGroupId(rs.getString("group_id"));
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setEvent(rs.getString("rms_event"));
                    ftpdata.setLotType(rs.getString("lot_type"));
                    ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                    ftpdata.setUnitQty(rs.getString("lot_qty"));
                    ftpdata.setStatus(rs.getString("rms_status"));
                    ftpdata.setProcessStatus(rs.getString("p_status"));
                    ftpdata.setPkgFamily(rs.getString("pkg_family"));
                    ftpdata.setPkgName(rs.getString("pkg_name"));
                    ftpdata.setScrapDate(rs.getString("scrap_date"));
                    ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                    ftpdata.setCompleteDate(rs.getString("completed_date"));
                    ftpdata.setStatus(rs.getString("status"));
                    ftpdata.setFlag(rs.getString("flag"));
                    ftpdata.setModifiedDate(rs.getString("modified_date"));
                    ftpdata.setModifiedBy(rs.getString("modified_by"));
                    ftpdata.setCreatedDate(rs.getString("created_date"));
                    ftpdata.setCreatedBy(rs.getString("created_by"));
                    rmsDetailsList.add(ftpdata);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving actual details for RMS: {}", rmsNo, e);
        }
        return rmsDetailsList;
    }
    
    private static final String SQL_GET_COUNT_EXISTING_DATA = "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rms_id = ? AND rms_event = ? AND lot_type = ?"; 
    private static final String SQL_GET_COUNT_EXISTING_DATA_NEW = "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rms_id = ? AND rms_event = ? AND lot_type = ? AND creator = 'FTP'";
    private static final String SQL_GET_COUNT_EXISTING_GROUP_ID = "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE group_id = ?";
    private static final String SQL_GET_COUNT_FTP_BY_ID = "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE id = ?";
    private static final String SQL_GET_COUNT_EXISTING_RMS_LOT_EVENT = "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rmslot_event = ?";
    private static final String SQL_GET_COUNT_RMS_LOT_EVENT_WITH_FLAG_ZERO = "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rmslot_event = ? AND flag = '0'";
    private static final String SQL_GET_COUNT_EXISTING_RMS_LOT_EVENT_FLAG_ZERO_OR_ONE = "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rmslot_event = ? AND (flag = 0 OR flag = 1)";
    private static final String SQL_GET_COUNT_EXISTING_RMS_EVENT = "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rms_id = ? AND rms_event = ?";
    private static final String SQL_GET_MAX_GROUP_ID = "SELECT MAX(group_id) AS max FROM sr_ftp_data";
    private static final String SQL_GET_GROUP_ID = "SELECT DISTINCT group_id FROM sr_ftp_data WHERE rms_id = ? AND rms_event = ?";
    private static final String SQL_GET_ACTUAL_EVENT = "SELECT DISTINCT rms_event AS act_event FROM sr_ftp_data WHERE (rms_event LIKE ? OR rms_event = ?) AND rms_id = ? AND mth_to_scrap = ? AND flag = 0";
    private static final String SQL_GET_LATEST_REV_DATE = "SELECT DATE_FORMAT(MAX(created_date), '%d-%b-%Y %h:%i %p') AS maxDate FROM sr_ftp_data";

    public Integer getCountExistingData(String rmsId, String event, String lot) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_DATA)) {
            ps.setString(1, rmsId);
            ps.setString(2, event);
            ps.setString(3, lot);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking existing FTP data. rmsId={}, event={}, lot={}", rmsId, event, lot, e);
        }
        return count;
    }

    public Integer getCountExistingDataNew(String rmsId, String event, String lot) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_DATA_NEW)) {
            ps.setString(1, rmsId);
            ps.setString(2, event);
            ps.setString(3, lot);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking existing FTP data. rmsId={}, event={}, lot={}", rmsId, event, lot, e);
        }
        return count;
    }

    public Integer getCountExistingGroupId(String groupId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking existing group ID. groupId={}", groupId, e);
        }
        return count;
    }

    public Integer getCountFtpById(String ftpId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_FTP_BY_ID)) {
            ps.setString(1, ftpId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking FTP ID: {}", ftpId, e);
        }
        return count;
    }

    public Integer getCountExistingRMSLotEvent(String rmslotevent) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_RMS_LOT_EVENT)) {
            ps.setString(1, rmslotevent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking existing RMS Lot Event: {}", rmslotevent, e);
        }
        return count;
    }

    public Integer getCountRMSLotEventWithFlagZero(String rmslotevent) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_RMS_LOT_EVENT_WITH_FLAG_ZERO)) {
            ps.setString(1, rmslotevent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking RMS Lot Event with flag 0: {}", rmslotevent, e);
        }
        return count;
    }

    public Integer getCountExistingRMSLotEventFlagZeroOrOne(String rmslotevent) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_RMS_LOT_EVENT_FLAG_ZERO_OR_ONE)) {
            ps.setString(1, rmslotevent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking RMS Lot Event with flag 0 or 1: {}", rmslotevent, e);
        }
        return count;
    }

    public Integer getCountExistingRmsEvent(String rmsId, String event) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_RMS_EVENT)) {
            ps.setString(1, rmsId);
            ps.setString(2, event);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking existing RMS Event. rmsId={}, event={}", rmsId, event, e);
        }
        return count;
    }

    public Integer getMaxGroupID() {
        Integer max = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_MAX_GROUP_ID); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                max = rs.getInt("max");
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving maximum group ID", e);
        }
        return max;
    }

    public Integer getGroupId(String rmsId, String event) {
        Integer groupId = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_GROUP_ID)) {
            ps.setString(1, rmsId);
            ps.setString(2, event);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    groupId = rs.getInt("group_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving group ID. rmsId={}, event={}",rmsId,event,e);
        }
        return groupId;
    }

    public String getActualEvent(String event, String rmsId, String mthToScrap) {
        String actEvent = event;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ACTUAL_EVENT)) {
            ps.setString(1, event + "_");
            ps.setString(2, event);
            ps.setString(3, rmsId);
            ps.setString(4, mthToScrap);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    actEvent = rs.getString("act_event");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving actual event. event={}, rmsId={}, mthToScrap={}", event, rmsId, mthToScrap, e);
        }
        return actEvent;
    }

    public String getLatestRevDate() {
        String revDate = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_LATEST_REV_DATE); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                revDate = rs.getString("maxDate");
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving latest revision date", e);
        }
        return revDate;
    }

}