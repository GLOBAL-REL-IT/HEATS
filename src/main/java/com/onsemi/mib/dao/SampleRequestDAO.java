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
    private final Connection conn;
    private final DataSource dataSource;

    public SampleRequestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertSampleReq(SampleRequest sampleRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_request (register_id, req_type, req_details, others_reason, event, mth_to_scrap, pkg_family, created_date, created_by, modified_date, modified_by, flag, status ) "
                    + "VALUES (?,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
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

    public List<SampleRequest> getAllRequestList() {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view "
                    + "FROM sr_request "
                    + "WHERE req_type = 'Ship to Sendayan' AND (flag = 0 OR flag = 1) "
                    + "ORDER BY created_date ASC ";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                reqList.add(sampleReq);
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
        return reqList;
    }

    public List<SampleRequest> getAllQueryList(String query) {
        String sql = query;
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleReq = new SampleRequest();
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
        return reqList;
    }

    public List<SampleRequest> getAllQuery(String query) {
        String sql = query;
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleReq = new SampleRequest();
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
        return reqList;
    }

    public List<SampleRequest> getAllRetrievedMergeInnerList() {
        String sql = "SELECT R.*, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, DATEDIFF(R.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                    + "DATE_FORMAT(R.modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, COUNT(I.rmslot_event) AS count_lot, "
                    + "(SELECT UPPER(IFNULL(U.login_id,R.created_by)) FROM user_ldap U WHERE R.created_by = CONCAT(U.firstname,' ',U.lastname)) AS user_id "
                    + "FROM sr_request R, sr_req_inner I "
                    + "WHERE R.id = I.req_id AND R.flag = 9 AND R.status IN ('Shipped from Sendayan') "
                    + "AND DATEDIFF(R.mth_to_scrap, NOW()) > 7 "
                    + "GROUP BY R.id  "
                    + "ORDER BY R.created_date DESC ";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                sampleReq.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampleReq.setLotQty(rs.getString("count_lot"));
                sampleReq.setFlag(rs.getString("R.flag"));
                sampleReq.setUserId(rs.getString("user_id"));
                reqList.add(sampleReq);
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
        return reqList;
    }

    public List<SampleRequest> getDistinctEventList() {
        String sql = "SELECT DISTINCT `event` FROM sr_request ";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleReq = new SampleRequest();
                sampleReq.setEvent(rs.getString("event"));
                reqList.add(sampleReq);
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
        return reqList;
    }

    public List<SampleRequest> getDistinctPkgFamilyList() {
        String sql = "SELECT DISTINCT pkg_family FROM sr_request ";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleReq = new SampleRequest();
                sampleReq.setPkgFamily(rs.getString("pkg_family"));
                reqList.add(sampleReq);
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
        return reqList;
    }

    public List<SampleRequest> getPackageList() {
        String sql = "SELECT DISTINCT ft.pkg_name FROM sr_ftp_data ft, sr_request re WHERE re.ftp_id = ft.id";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleReq = new SampleRequest();
                sampleReq.setPkgName(rs.getString("pkg_name"));
                reqList.add(sampleReq);
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
        return reqList;
    }

    public List<SampleRequest> getAllRms() {
        String sql = "SELECT DISTINCT ft.rms_id FROM sr_ftp_data ft, sr_request re WHERE re.ftp_id = ft.id";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleReq = new SampleRequest();
//                sampleReq.setEvent(rs.getString("rms_id"));
                sampleReq.setRms(rs.getString("rms_id"));
                reqList.add(sampleReq);
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
        return reqList;
    }

    public List<SampleRequest> getRequestStatus() {
        String sql = "SELECT DISTINCT sr_request.`status` FROM sr_request ";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleReq = new SampleRequest();
                sampleReq.setStatus(rs.getString("status"));
                reqList.add(sampleReq);
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
        return reqList;
    }

    public List<SampleRequest> getDistinctStatusList() {
        String sql = "SELECT DISTINCT status FROM sr_request ";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleReq = new SampleRequest();
                sampleReq.setStatus(rs.getString("status"));
                reqList.add(sampleReq);
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
        return reqList;
    }

    public List<SampleRequest> getAllReqMergeInner() {
        String sql = "SELECT R.*, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, DATEDIFF(R.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                    + "DATE_FORMAT(R.modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, COUNT(I.rmslot_event) AS count_lot, "
                    + "(SELECT UPPER(IFNULL(U.login_id,R.created_by)) FROM user_ldap U WHERE R.created_by = CONCAT(U.firstname,' ',U.lastname)) AS user_id "
                    + "FROM sr_request R, sr_req_inner I "
                    + "WHERE R.id = I.req_id AND R.req_type = 'Ship to Sendayan' AND R.flag NOT IN ('9','8','99') "
                    + "GROUP BY R.id  "
                    + "ORDER BY R.created_date DESC ";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                sampleReq.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampleReq.setLotQty(rs.getString("count_lot"));
                sampleReq.setFlag(rs.getString("R.flag"));
                sampleReq.setUserId(rs.getString("user_id"));
                reqList.add(sampleReq);
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
        return reqList;
    }

    public List<FTPdata> getAllDistinctLot() {
        String sql = "SELECT DISTINCT(lot_type) FROM sr_ftp_data WHERE flag = 0 ORDER BY lot_type ASC ";
        List<FTPdata> lotList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FTPdata initData;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                initData = new FTPdata();
                initData.setLotType(rs.getString("lot_type"));
                lotList.add(initData);
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
        return lotList;
    }

    public List<FTPdata> getAllDistinctEventSub(String pkgFamily, String event, String mthToScrap) {
        String sql = "SELECT DISTINCT(rms_event) FROM sr_ftp_data "
                    + "WHERE flag = 0 AND pkg_family = ? AND (rms_event LIKE ? OR rms_event = ?) AND mth_to_scrap = ? "
                    + "ORDER BY rms_event ASC ";
        List<FTPdata> eventList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pkgFamily);
            ps.setString(2, event+"_");
            ps.setString(3, event);
            ps.setString(4, mthToScrap);
            FTPdata initData;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                initData = new FTPdata();
                initData.setEvent(rs.getString("rms_event"));
                eventList.add(initData);
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
        return eventList;
    }

    public List<FTPdata> getAllDistinctEventPerRms(String rmsNo, String event) {
        String sql = "SELECT DISTINCT(rms_event) FROM sr_ftp_data "
                    + "WHERE flag = 0 AND rms_id = ? AND (rms_event LIKE ? OR rms_event = ?) "
                    + "ORDER BY rms_event ASC ";
        List<FTPdata> eventList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rmsNo);
            ps.setString(2, event+"_");
            ps.setString(3, event);
            FTPdata initData;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                initData = new FTPdata();
                initData.setEvent(rs.getString("rms_event"));
                eventList.add(initData);
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
        return eventList;
    }

    public Integer getMaxReqID() {
        Integer max = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT MAX(id) AS max FROM sr_request "
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                max = rs.getInt("max");
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
        return max;
    }

    public Integer getSameReqID(String max) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(id) AS count FROM sr_request WHERE id = ? "
            );
            ps.setString(1, max);
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

    public QueryResult deleteReq(String id) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_request WHERE id = ? "
            );
            ps.setString(1, id);
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

    public SampleRequest getRequestDetails(String id) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view FROM sr_request WHERE id = ? ";
        SampleRequest sampleReq = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        return sampleReq;
    }

    public List<LogOuterBox> getLogOuterListNew(String reqId) {
        String sql = "SELECT * FROM sr_log WHERE request_id = ? ";
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

    public SampleRequest getRqDetail(String id) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view FROM sr_request WHERE id = ? ";
        SampleRequest sampleReq = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        return sampleReq;
    }

    public SampleRequest getRequestPerRegId(String regId) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view FROM sr_request WHERE register_id = ? ";
        SampleRequest sampleReq = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, regId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        return sampleReq;
    }

    public SampleRequest getRequestActual(String id) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging FROM sr_request WHERE id = ? ";
        SampleRequest sampleReq = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        return sampleReq;
    }

    public SampleRequest getRequestPdf(String id) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %Y')) AS mth_to_scrap_view FROM sr_request WHERE id = ? ";
        SampleRequest sampleReq = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        return sampleReq;
    }

    public QueryResult updateReqBoxId(SampleRequest sampleReq) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_request SET req_box_id = ?, flag = ?, status = ?, modified_date = NOW(), modified_by = ? WHERE id  = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sampleReq.getReqBoxId());
            ps.setString(2, sampleReq.getFlag());
            ps.setString(3, sampleReq.getStatus());
            ps.setString(4, sampleReq.getModifiedBy());
            ps.setString(5, sampleReq.getId());
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

    public QueryResult updateRequestStatus(SampleRequest sampleReq) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_request SET flag = ?, status = ?, modified_date = NOW(), modified_by = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sampleReq.getFlag());
            ps.setString(2, sampleReq.getStatus());
            ps.setString(3, sampleReq.getModifiedBy());
            ps.setString(4, sampleReq.getId());
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

    public List<FTPdata> getAllDistinctRMSNo(String event, String pkgFamily, String mthToScrap) {
        String sql = "SELECT DISTINCT(rms_id) FROM sr_ftp_data WHERE (rms_event LIKE ? OR rms_event = ?) AND pkg_family = ? AND mth_to_scrap = ? AND flag = 0 ORDER BY rms_id ASC ";
        List<FTPdata> rmsList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, event+"_");
            ps.setString(2, event);
            ps.setString(3, pkgFamily);
            ps.setString(4, mthToScrap);
            FTPdata initData;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                initData = new FTPdata();
                initData.setRmsId(rs.getString("rms_id"));
                rmsList.add(initData);
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
        return rmsList;
    }

    public List<FTPdata> getAllDistinctRMSNoOnly() {
        String sql = "SELECT DISTINCT(rms_id) FROM sr_ftp_data WHERE flag = 0 ORDER BY rms_id ASC ";
        List<FTPdata> rmsList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FTPdata initData;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                initData = new FTPdata();
                initData.setRmsId(rs.getString("rms_id"));
                rmsList.add(initData);
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
        return rmsList;
    }

    public Integer getCountMaxBox(String date) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(id) AS count FROM sr_request WHERE req_box_id LIKE ? "
            );
            ps.setString(1, date+"%");
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

    public Integer getCountExistingData(String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(id) AS count FROM sr_request WHERE id = ? AND (status like 'Ship' OR status like '%Inventory%' OR status like '%Received%') "
            );
            ps.setString(1, id);
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

    public Integer getCountRecallData(String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(id) AS count FROM sr_request WHERE id = ? AND status = 'Shipped from Sendayan' "
            );
            ps.setString(1, id);
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

    public List<SampleRequest> getAllExpReqMergeInner() {
        String sql = "SELECT R.id, R.register_id, R.req_box_id, R.`event`, R.pkg_family, R.created_by, R.`status`, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, "
                    + "DATEDIFF(R.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                    + "DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, COUNT(I.rmslot_event) AS count_lot "
                    + "FROM sr_request R, sr_req_inner I "
                    + "WHERE R.id = I.req_id AND R.req_type = 'Ship to Sendayan' "
                    + "AND (R.flag IN(0,1,2) OR R.`status` = 'Pending DO' OR R.`status` = 'Pending Shipment') AND DATEDIFF(R.mth_to_scrap, NOW()) <= 0 "
                    + "GROUP BY R.id "
                    + "ORDER BY R.created_date DESC, COUNT(I.rmslot_event) ASC ";
        List<SampleRequest> reqList = new ArrayList<SampleRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SampleRequest sampleReq;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleReq = new SampleRequest();
                sampleReq.setId(rs.getString("R.id"));
                sampleReq.setRegisterId(rs.getString("R.register_id"));
                sampleReq.setReqBoxId(rs.getString("R.req_box_id"));
                sampleReq.setEvent(rs.getString("R.event"));
                sampleReq.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleReq.setAging(rs.getString("aging"));
                sampleReq.setPkgFamily(rs.getString("R.pkg_family"));
                sampleReq.setCreatedDate(rs.getString("created_date_view"));
                sampleReq.setCreatedBy(rs.getString("R.created_by"));
                sampleReq.setStatus(rs.getString("R.status"));
                sampleReq.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampleReq.setLotQty(rs.getString("count_lot"));
                reqList.add(sampleReq);
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
        return reqList;
    }

    public QueryResult insertRecallReq(SampleRequest sampleRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_request ("
                    + "register_id, old_req_id, req_box_id, req_type, req_details, others_reason, box_qty, event, mth_to_scrap, pkg_family, created_date, created_by, modified_date, modified_by, flag, status ) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
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

    public List<FTPdata> getAllSubEvent(String event, String pkgFamily, String mthToScrap) {
        String sql = "SELECT rms_id, GROUP_CONCAT(rms_event SEPARATOR ',') AS event_concat FROM sr_ftp_data "
                    + "WHERE flag = 0 and (rms_event LIKE ? OR rms_event = ?) AND pkg_family = ? AND mth_to_scrap = ? "
                    + "GROUP BY rms_id, rms_event "
                    + "ORDER BY rms_id, rms_event ";
        List<FTPdata> subEventList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, event+"_");
            ps.setString(2, event);
            ps.setString(3, pkgFamily);
            ps.setString(4, mthToScrap);
            FTPdata ftpdata;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setConcatSubEvent(rs.getString("event_concat"));
                subEventList.add(ftpdata);
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
        return subEventList;
    }

}