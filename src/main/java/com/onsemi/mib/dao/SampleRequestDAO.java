package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.model.LogOuterBox;
import com.onsemi.mib.model.SampleRequest;
import com.onsemi.mib.tools.QueryResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleRequestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleRequestDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public SampleRequestDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }
    
    private static final String SQL_INSERT_SAMPLE_REQ = "INSERT INTO sr_request (register_id, req_type, req_details, others_reason, event, mth_to_scrap, pkg_family, created_date, created_by, modified_date, modified_by, flag, status) VALUES (?,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?)"; 
    private static final String SQL_DELETE_REQ = "DELETE FROM sr_request WHERE id = ?";
    private static final String SQL_UPDATE_REQ_BOX_ID = "UPDATE sr_request SET req_box_id = ?, flag = ?, status = ?, modified_date = NOW(), modified_by = ? WHERE id = ?";
    private static final String SQL_UPDATE_REQUEST_STATUS = "UPDATE sr_request SET flag = ?, status = ?, modified_date = NOW(), modified_by = ? WHERE id = ?";
    private static final String SQL_INSERT_RECALL_REQ = "INSERT INTO sr_request (register_id, old_req_id, req_box_id, req_type, req_details, others_reason, box_qty, event, mth_to_scrap, pkg_family, created_date, created_by, modified_date, modified_by, flag, status) VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?)";
    private static final String SQL_GET_ALL_REQUEST_LIST = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view FROM sr_request WHERE req_type = 'Ship to Sendayan' AND (flag = 0 OR flag = 1) ORDER BY created_date ASC ";
    private static final String SQL_GET_ALL_RETRIEVE_MERGE_INNER_LIST = "SELECT R.*, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, DATEDIFF(R.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(R.modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, COUNT(I.rmslot_event) AS count_lot, (SELECT UPPER(IFNULL(U.login_id, R.created_by)) FROM user_ldap U WHERE R.created_by = CONCAT(U.firstname,' ',U.lastname)) AS user_id FROM sr_request R, sr_req_inner I WHERE R.id = I.req_id AND R.flag = 9 AND R.status IN ('Shipped from Sendayan') AND DATEDIFF(R.mth_to_scrap, NOW()) > 7 GROUP BY R.id ORDER BY R.created_date DESC";
    private static final String SQL_GET_DISTINCT_EVENT_LIST = "SELECT DISTINCT event FROM sr_request";
    private static final String SQL_GET_DISTINCT_FAMILY_LIST = "SELECT DISTINCT pkg_family FROM sr_request";
    private static final String SQL_GET_PACKAGE_LIST = "SELECT DISTINCT ft.pkg_name FROM sr_ftp_data ft, sr_request re WHERE re.ftp_id = ft.id";
    private static final String SQL_GET_ALL_RMS = "SELECT DISTINCT ft.rms_id FROM sr_ftp_data ft, sr_request re WHERE re.ftp_id = ft.id";
    private static final String SQL_GET_REQUEST_STATUS = "SELECT DISTINCT status FROM sr_request";
    private static final String SQL_GET_DISTINCT_STATUS_LIST = "SELECT DISTINCT status FROM sr_request";
    private static final String SQL_GET_ALL_REQUEST_MERGE_INNER = "SELECT R.*, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, DATEDIFF(R.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(R.modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, COUNT(I.rmslot_event) AS count_lot, (SELECT UPPER(IFNULL(U.login_id,R.created_by)) FROM user_ldap U WHERE R.created_by = CONCAT(U.firstname,' ',U.lastname)) AS user_id FROM sr_request R, sr_req_inner I WHERE R.id = I.req_id AND R.req_type = 'Ship to Sendayan' AND R.flag NOT IN ('9','8','99') GROUP BY R.id ORDER BY R.created_date DESC";
    private static final String SQL_GET_ALL_DISTINCT_LIST = "SELECT DISTINCT(lot_type) FROM sr_ftp_data WHERE flag = 0 ORDER BY lot_type ASC";
    private static final String SQL_GET_ALL_DISTINCT_EVENT_SUB = "SELECT DISTINCT(rms_event) FROM sr_ftp_data WHERE flag = 0 AND pkg_family = ? AND (rms_event LIKE ? OR rms_event = ?) AND mth_to_scrap = ? ORDER BY rms_event ASC";
    private static final String SQL_GET_ALL_DISTINCT_EVENT_PER_RMS = "SELECT DISTINCT(rms_event) FROM sr_ftp_data WHERE flag = 0 AND rms_id = ? AND (rms_event LIKE ? OR rms_event = ?) ORDER BY rms_event ASC";
    private static final String SQL_GET_LOG_OUTER_LIST_NEW = "SELECT * FROM sr_log WHERE request_id = ?";
    private static final String SQL_GET_ALL_DISTINCT_RMS_NO = "SELECT DISTINCT(rms_id) FROM sr_ftp_data WHERE (rms_event LIKE ? OR rms_event = ?) AND pkg_family = ? AND mth_to_scrap = ? AND flag = 0 ORDER BY rms_id ASC";
    private static final String SQL_GET_ALL_DISTINCT_RMS_NO_ONLY = "SELECT DISTINCT(rms_id) FROM sr_ftp_data WHERE flag = 0 ORDER BY rms_id ASC";
    private static final String SQL_GET_ALL_EXP_REG_MERGE_INNER = "SELECT R.id, R.register_id, R.req_box_id, R.event, R.pkg_family, R.created_by, R.status, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, DATEDIFF(R.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, COUNT(I.rmslot_event) AS count_lot FROM sr_request R, sr_req_inner I WHERE R.id = I.req_id AND R.req_type = 'Ship to Sendayan' AND (R.flag IN (0,1,2) OR R.status = 'Pending DO' OR R.status = 'Pending Shipment') AND DATEDIFF(R.mth_to_scrap, NOW()) <= 0 GROUP BY R.id ORDER BY R.created_date DESC, COUNT(I.rmslot_event) ASC";
    private static final String SQL_GET_ALL_SUB_EVENT = "SELECT rms_id, GROUP_CONCAT(rms_event SEPARATOR ',') AS event_concat FROM sr_ftp_data WHERE flag = 0 AND (rms_event LIKE ? OR rms_event = ?) AND pkg_family = ? AND mth_to_scrap = ? GROUP BY rms_id, rms_event ORDER BY rms_id, rms_event"; 

    public QueryResult insertSampleReq(SampleRequest sampleRequest) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_SAMPLE_REQ, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, sampleRequest.getRegisterId());
            ps.setString(2, sampleRequest.getReqType());
            ps.setString(3, sampleRequest.getReqDetails());
            ps.setString(4, sampleRequest.getOthersReason());
            ps.setString(5, sampleRequest.getEvent());
            ps.setString(6, sampleRequest.getMthToScrap());
            ps.setString(7, sampleRequest.getPkgFamily());
            ps.setString(8, sampleRequest.getCreatedBy());
            ps.setString(9, sampleRequest.getModifiedBy());
            ps.setString(10, sampleRequest.getFlag());
            ps.setString(11, sampleRequest.getStatus());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting sample request", e);
        }
        return queryResult;
    }
    
    public QueryResult deleteReq(String id) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_REQ)) {
            ps.setString(1, id);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error in deleteReq()", e);
        }
        return queryResult;
    }
    
    public QueryResult updateReqBoxId(SampleRequest sampleReq) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_REQ_BOX_ID)) {
            ps.setString(1, sampleReq.getReqBoxId());
            ps.setString(2, sampleReq.getFlag());
            ps.setString(3, sampleReq.getStatus());
            ps.setString(4, sampleReq.getModifiedBy());
            ps.setString(5, sampleReq.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error in updateReqBoxId()", e);
        }
        return queryResult;
    }

    public QueryResult updateRequestStatus(SampleRequest sampleReq) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_REQUEST_STATUS)) {
            ps.setString(1, sampleReq.getFlag());
            ps.setString(2, sampleReq.getStatus());
            ps.setString(3, sampleReq.getModifiedBy());
            ps.setString(4, sampleReq.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error in updateRequestStatus()", e);
        }
        return queryResult;
    }
    
    public QueryResult insertRecallReq(SampleRequest sampleRequest) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RECALL_REQ, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, sampleRequest.getRegisterId());
            ps.setString(2, sampleRequest.getOldReqId());
            ps.setString(3, sampleRequest.getReqBoxId());
            ps.setString(4, sampleRequest.getReqType());
            ps.setString(5, sampleRequest.getReqDetails());
            ps.setString(6, sampleRequest.getOthersReason());
            ps.setString(7, sampleRequest.getBoxQty());
            ps.setString(8, sampleRequest.getEvent());
            ps.setString(9, sampleRequest.getMthToScrap());
            ps.setString(10, sampleRequest.getPkgFamily());
            ps.setString(11, sampleRequest.getCreatedBy());
            ps.setString(12, sampleRequest.getModifiedBy());
            ps.setString(13, sampleRequest.getFlag());
            ps.setString(14, sampleRequest.getStatus());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(
                            String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error in insertRecallReq()", e);
        }
        return queryResult;
    }

    public List<SampleRequest> getAllRequestList() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_REQUEST_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setId(rs.getString("id"));
                sampleReq.setRegisterId(rs.getString("register_id"));
                sampleReq.setReqBoxId(rs.getString("req_box_id"));
                sampleReq.setReqType(rs.getString("req_type"));
                sampleReq.setOthersReason(rs.getString("others_reason"));
                sampleReq.setReqDetails(rs.getString("req_details"));
                sampleReq.setBoxQty(rs.getString("box_qty"));
                sampleReq.setEvent(rs.getString("event"));
                sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleReq.setAging(rs.getString("aging"));
                sampleReq.setPkgFamily(rs.getString("pkg_family"));
                sampleReq.setCreatedDate(rs.getString("created_date_view"));
                sampleReq.setCreatedBy(rs.getString("created_by"));
                sampleReq.setModifiedDate(rs.getString("modified_date_view"));
                sampleReq.setModifiedBy(rs.getString("modified_by"));
                sampleReq.setFlag(rs.getString("flag"));
                sampleReq.setStatus(rs.getString("status"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllRequestList()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getAllQueryList(String query) {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setId(rs.getString("r.id"));
                sampleReq.setRegisterId(rs.getString("r.register_id"));
                sampleReq.setReqBoxId(rs.getString("r.req_box_id"));
                sampleReq.setReqType(rs.getString("r.req_type"));
                sampleReq.setReqDetails(rs.getString("r.req_details"));
                sampleReq.setEvent(rs.getString("r.event"));
                sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleReq.setPkgFamily(rs.getString("r.pkg_family"));
                sampleReq.setStatus(rs.getString("r.status"));
                sampleReq.setShipDateView(rs.getString("ship_date_view"));
                sampleReq.setRetrieveDateView(rs.getString("retrieve_date_view"));
                sampleReq.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampleReq.setCountRmsId(rs.getString("count_lot"));
                sampleReq.setGtsNo(rs.getString("s.gts_no"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllQueryList()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getAllQuery(String query) {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setId(rs.getString("reqId"));
                sampleReq.setMthToScrap(rs.getString("mthToScrapView"));
                sampleReq.setRms(rs.getString("rms_id"));
                sampleReq.setEvent(rs.getString("rms_event"));
                sampleReq.setPkgFamily(rs.getString("ft.pkg_family"));
                sampleReq.setPkgName(rs.getString("ft.pkg_name"));
                sampleReq.setStatus(rs.getString("rq.status"));
                sampleReq.setRmsLotEventConcat(rs.getString("rmslot_event"));
                sampleReq.setInventory(rs.getString("inventory_shelf"));
                sampleReq.setInventoryDate(rs.getString("inventoryDate"));
                sampleReq.setRetrieveDateView(rs.getString("retrieveDate"));
                sampleReq.setScrapDate(rs.getString("scrapDate"));
                sampleReq.setQty(rs.getString("ft.actual_qty"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllQuery()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getAllRetrievedMergeInnerList() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_RETRIEVE_MERGE_INNER_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setId(rs.getString("id"));
                sampleReq.setRegisterId(rs.getString("register_id"));
                sampleReq.setReqBoxId(rs.getString("req_box_id"));
                sampleReq.setReqType(rs.getString("req_type"));
                sampleReq.setOthersReason(rs.getString("others_reason"));
                sampleReq.setReqDetails(rs.getString("req_details"));
                sampleReq.setBoxQty(rs.getString("box_qty"));
                sampleReq.setEvent(rs.getString("event"));
                sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleReq.setAging(rs.getString("aging"));
                sampleReq.setPkgFamily(rs.getString("pkg_family"));
                sampleReq.setCreatedDate(rs.getString("created_date_view"));
                sampleReq.setCreatedBy(rs.getString("created_by"));
                sampleReq.setModifiedDate(rs.getString("modified_date_view"));
                sampleReq.setModifiedBy(rs.getString("modified_by"));
                sampleReq.setFlag(rs.getString("flag"));
                sampleReq.setStatus(rs.getString("status"));
                sampleReq.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampleReq.setLotQty(rs.getString("count_lot"));
                sampleReq.setUserId(rs.getString("user_id"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllRetrievedMergeInnerList()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getDistinctEventList() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_DISTINCT_EVENT_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setEvent(rs.getString("event"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getDistinctEventList()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getDistinctPkgFamilyList() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_DISTINCT_FAMILY_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setPkgFamily(rs.getString("pkg_family"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getDistinctPkgFamilyList()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getPackageList() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_PACKAGE_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setPkgName(rs.getString("pkg_name"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getPackageList()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getAllRms() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_RMS); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setRms(rs.getString("rms_id"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllRms()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getRequestStatus() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_REQUEST_STATUS); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setStatus(rs.getString("status"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getRequestStatus()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getDistinctStatusList() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_DISTINCT_STATUS_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setStatus(rs.getString("status"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getDistinctStatusList()", e);
        }
        return reqList;
    }

    public List<SampleRequest> getAllReqMergeInner() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_REQUEST_MERGE_INNER); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setId(rs.getString("id"));
                sampleReq.setRegisterId(rs.getString("register_id"));
                sampleReq.setReqBoxId(rs.getString("req_box_id"));
                sampleReq.setReqType(rs.getString("req_type"));
                sampleReq.setOthersReason(rs.getString("others_reason"));
                sampleReq.setReqDetails(rs.getString("req_details"));
                sampleReq.setBoxQty(rs.getString("box_qty"));
                sampleReq.setEvent(rs.getString("event"));
                sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleReq.setAging(rs.getString("aging"));
                sampleReq.setPkgFamily(rs.getString("pkg_family"));
                sampleReq.setCreatedDate(rs.getString("created_date_view"));
                sampleReq.setCreatedBy(rs.getString("created_by"));
                sampleReq.setModifiedDate(rs.getString("modified_date_view"));
                sampleReq.setModifiedBy(rs.getString("modified_by"));
                sampleReq.setFlag(rs.getString("flag"));
                sampleReq.setStatus(rs.getString("status"));
                sampleReq.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampleReq.setLotQty(rs.getString("count_lot"));
                sampleReq.setUserId(rs.getString("user_id"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllReqMergeInner()", e);
        }
        return reqList;
    }

    public List<FTPdata> getAllDistinctLot() {
        List<FTPdata> lotList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_DISTINCT_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FTPdata initData = new FTPdata();
                initData.setLotType(rs.getString("lot_type"));
                lotList.add(initData);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllDistinctLot()", e);
        }
        return lotList;
    }

    public List<FTPdata> getAllDistinctEventSub(String pkgFamily, String event, String mthToScrap) {
        List<FTPdata> eventList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_DISTINCT_EVENT_SUB)) {
            ps.setString(1, pkgFamily);
            ps.setString(2, event + "_");
            ps.setString(3, event);
            ps.setString(4, mthToScrap);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FTPdata initData = new FTPdata();
                    initData.setEvent(rs.getString("rms_event"));
                    eventList.add(initData);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllDistinctEventSub()", e);
        }
        return eventList;
    }

    public List<FTPdata> getAllDistinctEventPerRms(String rmsNo, String event) {
        List<FTPdata> eventList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_DISTINCT_EVENT_PER_RMS)) {
            ps.setString(1, rmsNo);
            ps.setString(2, event + "_");
            ps.setString(3, event);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FTPdata initData = new FTPdata();
                    initData.setEvent(rs.getString("rms_event"));
                    eventList.add(initData);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllDistinctEventPerRms()", e);
        }
        return eventList;
    }
    
    public List<LogOuterBox> getLogOuterListNew(String reqId) {
        List<LogOuterBox> logModuleList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_LOG_OUTER_LIST_NEW)) {
            ps.setString(1, reqId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LogOuterBox log = new LogOuterBox();
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
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getLogOuterListNew()", e);
        }
        return logModuleList;
    }
    
    public List<FTPdata> getAllDistinctRMSNo(String event, String pkgFamily, String mthToScrap) {
        List<FTPdata> rmsList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_DISTINCT_RMS_NO)) {
            ps.setString(1, event + "_");
            ps.setString(2, event);
            ps.setString(3, pkgFamily);
            ps.setString(4, mthToScrap);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FTPdata initData = new FTPdata();
                    initData.setRmsId(rs.getString("rms_id"));
                    rmsList.add(initData);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllDistinctRMSNo()", e);
        }
        return rmsList;
    }

    public List<FTPdata> getAllDistinctRMSNoOnly() {
        List<FTPdata> rmsList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_DISTINCT_RMS_NO_ONLY); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FTPdata initData = new FTPdata();
                initData.setRmsId(rs.getString("rms_id"));
                rmsList.add(initData);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllDistinctRMSNoOnly()", e);
        }
        return rmsList;
    }
    
    public List<SampleRequest> getAllExpReqMergeInner() {
        List<SampleRequest> reqList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_EXP_REG_MERGE_INNER); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SampleRequest sampleReq = new SampleRequest();
                sampleReq.setId(rs.getString("id"));
                sampleReq.setRegisterId(rs.getString("register_id"));
                sampleReq.setReqBoxId(rs.getString("req_box_id"));
                sampleReq.setEvent(rs.getString("event"));
                sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleReq.setAging(rs.getString("aging"));
                sampleReq.setPkgFamily(rs.getString("pkg_family"));
                sampleReq.setCreatedDate(rs.getString("created_date_view"));
                sampleReq.setCreatedBy(rs.getString("created_by"));
                sampleReq.setStatus(rs.getString("status"));
                sampleReq.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampleReq.setLotQty(rs.getString("count_lot"));
                reqList.add(sampleReq);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllExpReqMergeInner()", e);
        }
        return reqList;
    }
    
    public List<FTPdata> getAllSubEvent(String event, String pkgFamily, String mthToScrap) {
        List<FTPdata> subEventList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_SUB_EVENT)) {
            ps.setString(1, event + "_");
            ps.setString(2, event);
            ps.setString(3, pkgFamily);
            ps.setString(4, mthToScrap);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FTPdata ftpdata = new FTPdata();
                    ftpdata.setRmsId(rs.getString("rms_id"));
                    ftpdata.setConcatSubEvent(rs.getString("event_concat"));
                    subEventList.add(ftpdata);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllSubEvent()", e);
        }
        return subEventList;
    }
    
    private static final String SQL_GET_REQUEST_DETAILS = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view FROM sr_request WHERE id = ?"; 
    private static final String SQL_GET_RQ_DETAIL = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view FROM sr_request WHERE id = ?";
    private static final String SQL_GET_REQUEST_PER_REQ_ID = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view FROM sr_request WHERE register_id = ?";
    private static final String SQL_GET_REQUEST_ACTUAL = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging FROM sr_request WHERE id = ?";
    private static final String SQL_GET_REQUEST_PDF = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %Y')) AS mth_to_scrap_view FROM sr_request WHERE id = ?";
    
    public SampleRequest getRequestDetails(String id) {
        SampleRequest sampleReq = null;
        try ( Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_REQUEST_DETAILS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sampleReq = new SampleRequest();
                    sampleReq.setId(rs.getString("id"));
                    sampleReq.setOldReqId(rs.getString("old_req_id"));
                    sampleReq.setRegisterId(rs.getString("register_id"));
                    sampleReq.setReqBoxId(rs.getString("req_box_id"));
                    sampleReq.setReqType(rs.getString("req_type"));
                    sampleReq.setOthersReason(rs.getString("others_reason"));
                    sampleReq.setReqDetails(rs.getString("req_details"));
                    sampleReq.setBoxQty(rs.getString("box_qty"));
                    sampleReq.setEvent(rs.getString("event"));
                    sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                    sampleReq.setAging(rs.getString("aging"));
                    sampleReq.setPkgFamily(rs.getString("pkg_family"));
                    sampleReq.setCreatedDate(rs.getString("created_date_view"));
                    sampleReq.setCreatedBy(rs.getString("created_by"));
                    sampleReq.setModifiedDate(rs.getString("modified_date_view"));
                    sampleReq.setModifiedBy(rs.getString("modified_by"));
                    sampleReq.setFlag(rs.getString("flag"));
                    sampleReq.setStatus(rs.getString("status"));
                    sampleReq.setMthToScrapDB(rs.getString("mth_to_scrap"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getRequestDetails()", e);
        }
        return sampleReq;
    }
    
    public SampleRequest getRqDetail(String id) {
        SampleRequest sampleReq = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RQ_DETAIL)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sampleReq = new SampleRequest();
                    sampleReq.setId(rs.getString("id"));
                    sampleReq.setOldReqId(rs.getString("old_req_id"));
                    sampleReq.setRegisterId(rs.getString("register_id"));
                    sampleReq.setReqBoxId(rs.getString("req_box_id"));
                    sampleReq.setReqType(rs.getString("req_type"));
                    sampleReq.setOthersReason(rs.getString("others_reason"));
                    sampleReq.setReqDetails(rs.getString("req_details"));
                    sampleReq.setBoxQty(rs.getString("box_qty"));
                    sampleReq.setEvent(rs.getString("event"));
                    sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                    sampleReq.setAging(rs.getString("aging"));
                    sampleReq.setPkgFamily(rs.getString("pkg_family"));
                    sampleReq.setCreatedDate(rs.getString("created_date_view"));
                    sampleReq.setCreatedBy(rs.getString("created_by"));
                    sampleReq.setModifiedDate(rs.getString("modified_date_view"));
                    sampleReq.setModifiedBy(rs.getString("modified_by"));
                    sampleReq.setFlag(rs.getString("flag"));
                    sampleReq.setStatus(rs.getString("status"));
                    sampleReq.setMthToScrapDB(rs.getString("mth_to_scrap"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getRqDetail()", e);
        }
        return sampleReq;
    }

    public SampleRequest getRequestPerRegId(String regId) {
        SampleRequest sampleReq = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_REQUEST_PER_REQ_ID)) {
            ps.setString(1, regId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sampleReq = new SampleRequest();
                    sampleReq.setId(rs.getString("id"));
                    sampleReq.setRegisterId(rs.getString("register_id"));
                    sampleReq.setReqBoxId(rs.getString("req_box_id"));
                    sampleReq.setReqType(rs.getString("req_type"));
                    sampleReq.setOthersReason(rs.getString("others_reason"));
                    sampleReq.setReqDetails(rs.getString("req_details"));
                    sampleReq.setBoxQty(rs.getString("box_qty"));
                    sampleReq.setEvent(rs.getString("event"));
                    sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                    sampleReq.setAging(rs.getString("aging"));
                    sampleReq.setPkgFamily(rs.getString("pkg_family"));
                    sampleReq.setCreatedDate(rs.getString("created_date_view"));
                    sampleReq.setCreatedBy(rs.getString("created_by"));
                    sampleReq.setModifiedDate(rs.getString("modified_date_view"));
                    sampleReq.setModifiedBy(rs.getString("modified_by"));
                    sampleReq.setFlag(rs.getString("flag"));
                    sampleReq.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getRequestPerRegId()", e);
        }
        return sampleReq;
    }

    public SampleRequest getRequestActual(String id) {
        SampleRequest sampleReq = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_REQUEST_ACTUAL)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sampleReq = new SampleRequest();
                    sampleReq.setId(rs.getString("id"));
                    sampleReq.setRegisterId(rs.getString("register_id"));
                    sampleReq.setReqBoxId(rs.getString("req_box_id"));
                    sampleReq.setReqType(rs.getString("req_type"));
                    sampleReq.setOthersReason(rs.getString("others_reason"));
                    sampleReq.setReqDetails(rs.getString("req_details"));
                    sampleReq.setBoxQty(rs.getString("box_qty"));
                    sampleReq.setEvent(rs.getString("event"));
                    sampleReq.setMthToScrap(rs.getString("mth_to_scrap"));
                    sampleReq.setMthToScrapDB(rs.getString("mth_to_scrap"));
                    sampleReq.setAging(rs.getString("aging"));
                    sampleReq.setPkgFamily(rs.getString("pkg_family"));
                    sampleReq.setCreatedDate(rs.getString("created_date"));
                    sampleReq.setCreatedBy(rs.getString("created_by"));
                    sampleReq.setModifiedDate(rs.getString("modified_date"));
                    sampleReq.setModifiedBy(rs.getString("modified_by"));
                    sampleReq.setFlag(rs.getString("flag"));
                    sampleReq.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getRequestActual()", e);
        }
        return sampleReq;
    }

    public SampleRequest getRequestPdf(String id) {
        SampleRequest sampleReq = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_REQUEST_PDF)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sampleReq = new SampleRequest();
                    sampleReq.setId(rs.getString("id"));
                    sampleReq.setRegisterId(rs.getString("register_id"));
                    sampleReq.setReqBoxId(rs.getString("req_box_id"));
                    sampleReq.setReqType(rs.getString("req_type"));
                    sampleReq.setOthersReason(rs.getString("others_reason"));
                    sampleReq.setReqDetails(rs.getString("req_details"));
                    sampleReq.setBoxQty(rs.getString("box_qty"));
                    sampleReq.setEvent(rs.getString("event"));
                    sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                    sampleReq.setMthToScrapDB(rs.getString("mth_to_scrap"));
                    sampleReq.setAging(rs.getString("aging"));
                    sampleReq.setPkgFamily(rs.getString("pkg_family"));
                    sampleReq.setCreatedDate(rs.getString("created_date"));
                    sampleReq.setCreatedBy(rs.getString("created_by"));
                    sampleReq.setModifiedDate(rs.getString("modified_date"));
                    sampleReq.setModifiedBy(rs.getString("modified_by"));
                    sampleReq.setFlag(rs.getString("flag"));
                    sampleReq.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getRequestPdf()", e);
        }
        return sampleReq;
    }
    
    private static final String SQL_GET_MAX_REQ_ID = "SELECT MAX(id) AS max FROM sr_request"; 
    private static final String SQL_GET_SAME_REQ_ID = "SELECT COUNT(id) AS count FROM sr_request WHERE id = ?"; 
    private static final String SQL_GET_COUNT_MAX_BOX = "SELECT COUNT(id) AS count FROM sr_request WHERE req_box_id LIKE ?";
    private static final String SQL_GET_COUNT_EXISTING_DATA = "SELECT COUNT(id) AS count FROM sr_request WHERE id = ? AND (status LIKE 'Ship' OR status LIKE '%Inventory%' OR status LIKE '%Received%')";
    private static final String SQL_GET_COUNT_RECALL_DATA = "SELECT COUNT(id) AS count FROM sr_request WHERE id = ? AND status = 'Shipped from Sendayan'";

    public Integer getMaxReqID() {
        Integer max = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_MAX_REQ_ID); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                max = rs.getInt("max");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getMaxReqID()", e);
        }
        return max;
    }

    public Integer getSameReqID(String max) {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SAME_REQ_ID)) {
            ps.setString(1, max);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getSameReqID()", e);
        }
        return count;
    }

    public Integer getCountMaxBox(String date) {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_MAX_BOX)) {
            ps.setString(1, date + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getCountMaxBox()", e);
        }
        return count;
    }

    public Integer getCountExistingData(String id) {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_DATA)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getCountExistingData()", e);
        }
        return count;
    }

    public Integer getCountRecallData(String id) {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_RECALL_DATA)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getCountRecallData()", e);
        }
        return count;
    }

}