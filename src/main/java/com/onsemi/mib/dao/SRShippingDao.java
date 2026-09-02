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
import com.onsemi.mib.model.SRShipping;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SRShippingDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SRShippingDao.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public SRShippingDao() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_INSERT_INITIAL_SHIPPING = "INSERT INTO sr_shipping_list (outer_pkg_no, outer_id, status, flag, modified_date, modified_by, created_date, created_by) VALUES (?,?,?,?,NOW(),?,?,?)";
    private static final String SQL_INSERT_SHIPPING = "INSERT INTO sr_shipping_list (outer_pkg_no, outer_id, status, flag, modified_date, modified_by, created_date, created_by) VALUES (?,?,?,?,NOW(),?,NOW(),?)";
    private static final String SQL_UPDATE_STATUS_IN_SHIP_LIST = "UPDATE sr_shipping_list SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE outer_pkg_no = ?";
    private static final String SQL_UPDATE_STATUS_ADD_DO_LIST = "UPDATE sr_shipping_list SET do_added_date = NOW(), do_added_by = ?, status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE outer_pkg_no = ?";
    private static final String SQL_UPDATE_SHIPPING_STATUS = "UPDATE sr_shipping_list SET do_printed_date = NOW(), do_printed_by = ?, status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE outer_pkg_no = ?";
    private static final String SQL_UPDATE_CANCELLATION_STATUS = "UPDATE sr_shipping_list SET do_added_date = ?, do_added_by = ?, status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE outer_pkg_no = ?";
    private static final String SQL_UPDATE_SHIPPING_DETAILS = "UPDATE sr_shipping_list SET shipping_date = ?, gts_no = ?, status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE outer_pkg_no = ?";
    private static final String SQL_UPDATE_SHIP_STATUS = "UPDATE sr_shipping_list SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE outer_pkg_no = ?";

    public QueryResult insertInitialShipping(SRShipping shipping) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_INITIAL_SHIPPING, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, shipping.getOuterPkgNo());
            ps.setString(2, shipping.getOuterId());
            ps.setString(3, shipping.getStatus());
            ps.setString(4, shipping.getFlag());
            ps.setString(5, shipping.getModifiedBy());
            ps.setString(6, shipping.getCreatedDate());
            ps.setString(7, shipping.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting initial shipping record", e);
        }
        return queryResult;
    }

    public QueryResult insertShipping(SRShipping shipping) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_SHIPPING, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, shipping.getOuterPkgNo());
            ps.setString(2, shipping.getOuterId());
            ps.setString(3, shipping.getStatus());
            ps.setString(4, shipping.getFlag());
            ps.setString(5, shipping.getModifiedBy());
            ps.setString(6, shipping.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting shipping record", e);
        }
        return queryResult;
    }

    public QueryResult updateStatusInShipList(SRShipping shipping) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS_IN_SHIP_LIST)) {
            ps.setString(1, shipping.getStatus());
            ps.setString(2, shipping.getFlag());
            ps.setString(3, shipping.getModifiedBy());
            ps.setString(4, shipping.getOuterPkgNo());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating shipping list status", e);
        }
        return queryResult;
    }

    public QueryResult updateStatusAddDOList(SRShipping shipping) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS_ADD_DO_LIST)) {
            ps.setString(1, shipping.getDoAddedBy());
            ps.setString(2, shipping.getStatus());
            ps.setString(3, shipping.getFlag());
            ps.setString(4, shipping.getModifiedBy());
            ps.setString(5, shipping.getOuterPkgNo());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating DO status", e);
        }
        return queryResult;
    }

    public QueryResult updateShippingStatus(SRShipping shipping) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_SHIPPING_STATUS)) {
            ps.setString(1, shipping.getDoPrintedBy());
            ps.setString(2, shipping.getStatus());
            ps.setString(3, shipping.getFlag());
            ps.setString(4, shipping.getModifiedBy());
            ps.setString(5, shipping.getOuterPkgNo());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating shipping status", e);
        }
        return queryResult;
    }

    public QueryResult updateCancellationStatus(SRShipping shipping) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_CANCELLATION_STATUS)) {
            ps.setString(1, shipping.getDoAddedDate());
            ps.setString(2, shipping.getDoAddedBy());
            ps.setString(3, shipping.getStatus());
            ps.setString(4, shipping.getFlag());
            ps.setString(5, shipping.getModifiedBy());
            ps.setString(6, shipping.getOuterPkgNo());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating cancellation status", e);
        }
        return queryResult;
    }

    public QueryResult updateShippingDetails(SRShipping shipping) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_SHIPPING_DETAILS)) {
            ps.setString(1, shipping.getShippingDate());
            ps.setString(2, shipping.getGtsNo());
            ps.setString(3, shipping.getStatus());
            ps.setString(4, shipping.getFlag());
            ps.setString(5, shipping.getModifiedBy());
            ps.setString(6, shipping.getOuterPkgNo());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating shipping details", e);
        }
        return queryResult;
    }

    public QueryResult updateShipStatus(SRShipping shipping) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_SHIP_STATUS)) {
            ps.setString(1, shipping.getStatus());
            ps.setString(2, shipping.getFlag());
            ps.setString(3, shipping.getModifiedBy());
            ps.setString(4, shipping.getOuterPkgNo());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating ship status for outer package no: {}", shipping.getOuterPkgNo(), e);
        }
        return queryResult;
    }
    
    private static final String SQL_GET_SHIPPING = "SELECT *, DATE_FORMAT(shipping_date,'%d %M %Y %h:%i %p') AS shipping_date_view, DATE_FORMAT(do_printed_date,'%d %M %Y %h:%i %p') AS do_printed_date_view, DATE_FORMAT(modified_date,'%d %M %Y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS created_date_view FROM sr_shipping_list WHERE outer_pkg_no = ?";
    private static final String SQL_GET_SHIPPING_LIST_MERGE_PACKING_DISPLAY = "SELECT S.id, R.id, R.req_box_id, S.gts_no, DATE_FORMAT(S.shipping_date,'%d-%b-%y %h:%i %p') AS ship_date_view, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, R.event, R.pkg_family, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, S.status, DATEDIFF(R.mth_to_scrap, NOW()) AS aging, COUNT(I.rmslot_event) AS count_lot, S.flag FROM sr_request R, sr_req_inner I, sr_shipping_list S WHERE R.id = I.req_id AND R.req_type = 'Ship to Sendayan' AND S.outer_pkg_no = R.id AND S.flag NOT LIKE '9' AND S.flag NOT LIKE '99' AND S.status NOT LIKE 'Ship' GROUP BY R.id ORDER BY R.created_date DESC";
    private static final String SQL_GET_STAGING_LIST_MERGE_PACKING_DISPLAY = "SELECT S.id, R.id, R.req_box_id, S.gts_no, DATE_FORMAT(S.shipping_date,'%d-%b-%y %h:%i %p') AS ship_date_view, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, R.event, R.pkg_family, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, S.status, DATEDIFF(R.mth_to_scrap, NOW()) AS aging, COUNT(I.rmslot_event) AS count_lot, S.flag FROM sr_request R, sr_req_inner I, sr_shipping_list S WHERE R.id = I.req_id AND R.req_type = 'Ship to Sendayan' AND S.outer_pkg_no = R.id AND S.status = 'Ship' GROUP BY R.id ORDER BY R.created_date DESC";
    private static final String SQL_GET_SHIPPING_LIST_MERGE_PACKING_DO = "SELECT SL.id, DATE_FORMAT(mth_to_scrap,'%M %Y') AS mth_to_scrap_view, event, pkg_family, SL.outer_id, DATEDIFF(mth_to_scrap, NOW()) AS aging, GROUP_CONCAT(FD.rmslot_event SEPARATOR ',') AS rmslot_event_concat, SL.status, SL.outer_pkg_no, COUNT(FD.rmslot_event) AS count_lot FROM sr_shipping_list SL, sr_req_inner IL, sr_ftp_data FD WHERE SL.outer_pkg_no = IL.req_id AND SL.flag = '1' AND FD.id = IL.ftp_id GROUP BY SL.outer_pkg_no ORDER BY aging ASC";

    public SRShipping getShipping(String outerPkgNo) {
        SRShipping shipping = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SHIPPING)) {
            ps.setString(1, outerPkgNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    shipping = new SRShipping();
                    shipping.setId(rs.getString("id"));
                    shipping.setOuterPkgNo(rs.getString("outer_pkg_no"));
                    shipping.setOuterId(rs.getString("outer_id"));
                    shipping.setGtsNo(rs.getString("gts_no"));
                    shipping.setShippingDate(rs.getString("shipping_date_view"));
                    shipping.setDoPrintedDate(rs.getString("do_printed_date_view"));
                    shipping.setDoPrintedBy(rs.getString("do_printed_by"));
                    shipping.setStatus(rs.getString("status"));
                    shipping.setFlag(rs.getString("flag"));
                    shipping.setModifiedBy(rs.getString("modified_by"));
                    shipping.setModifiedDate(rs.getString("modified_date_view"));
                    shipping.setCreatedDate(rs.getString("created_date_view"));
                    shipping.setCreatedBy(rs.getString("created_by"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving shipping record for outerPkgNo: {}", outerPkgNo, e);
        }
        return shipping;
    }

    public List<SRShipping> getShippingListMergePackingDisplay() {
        List<SRShipping> shippingList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SHIPPING_LIST_MERGE_PACKING_DISPLAY); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SRShipping shipping = new SRShipping();
                shipping.setId(rs.getString("S.id"));
                shipping.setOuterId(rs.getString("R.req_box_id"));
                shipping.setGtsNo(rs.getString("S.gts_no"));
                shipping.setShippingDate(rs.getString("ship_date_view"));
                shipping.setOuterPkgNo(rs.getString("R.id"));
                shipping.setMthToScrap(rs.getString("mth_to_scrap_view"));
                shipping.setEvent(rs.getString("R.event"));
                shipping.setPkgFamily(rs.getString("R.pkg_family"));
                shipping.setRmsLotEvent(rs.getString("rmslot_event_concat"));
                shipping.setStatus(rs.getString("S.status"));
                shipping.setAging(rs.getString("aging"));
                shipping.setCountLot(rs.getString("count_lot"));
                shipping.setFlag(rs.getString("S.flag"));
                shippingList.add(shipping);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving shipping list merge packing display", e);
        }
        return shippingList;
    }

    public List<SRShipping> getStagingListMergePackingDisplay() {
        List<SRShipping> shippingList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_STAGING_LIST_MERGE_PACKING_DISPLAY); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SRShipping shipping = new SRShipping();
                shipping.setId(rs.getString("S.id"));
                shipping.setOuterId(rs.getString("R.req_box_id"));
                shipping.setGtsNo(rs.getString("S.gts_no"));
                shipping.setShippingDate(rs.getString("ship_date_view"));
                shipping.setOuterPkgNo(rs.getString("R.id"));
                shipping.setMthToScrap(rs.getString("mth_to_scrap_view"));
                shipping.setEvent(rs.getString("R.event"));
                shipping.setPkgFamily(rs.getString("R.pkg_family"));
                shipping.setRmsLotEvent(rs.getString("rmslot_event_concat"));
                shipping.setStatus(rs.getString("S.status"));
                shipping.setAging(rs.getString("aging"));
                shipping.setCountLot(rs.getString("count_lot"));
                shipping.setFlag(rs.getString("S.flag"));
                shippingList.add(shipping);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving staging list merge packing display", e);
        }
        return shippingList;
    }

    public List<SRShipping> getShippingListMergePackingDo() {
        List<SRShipping> shippingList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SHIPPING_LIST_MERGE_PACKING_DO); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SRShipping shipping = new SRShipping();
                shipping.setId(rs.getString("SL.id"));
                shipping.setMthToScrap(rs.getString("mth_to_scrap_view"));
                shipping.setEvent(rs.getString("event"));
                shipping.setPkgFamily(rs.getString("pkg_family"));
                shipping.setOuterId(rs.getString("SL.outer_id"));
                shipping.setAging(rs.getString("aging"));
                shipping.setRmsLotEvent(rs.getString("rmslot_event_concat"));
                shipping.setStatus(rs.getString("SL.status"));
                shipping.setOuterPkgNo(rs.getString("SL.outer_pkg_no"));
                shipping.setCountLot(rs.getString("count_lot"));
                shippingList.add(shipping);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving shipping list merge packing DO", e);
        }
        return shippingList;
    }
    
    private static final String SQL_GET_COUNT_EXISTING_DATA_RECEIVED = "SELECT COUNT(id) AS count FROM sr_shipping_list WHERE outer_pkg_no = ? AND flag = 2 AND status = 'Ship'"; 
    private static final String SQL_GET_COUNT_EXISTING_DATA_INVENTORY = "SELECT COUNT(id) AS count FROM sr_shipping_list WHERE outer_pkg_no = ? AND flag = 9 AND status = ?";
    private static final String SQL_GET_COUNT_EXISTING_DATA_PER_REQ_ID = "SELECT COUNT(outer_pkg_no) AS count FROM sr_shipping_list WHERE outer_pkg_no = ?";

    public Integer getCountExistingDataReceived(String reqId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_DATA_RECEIVED)) {
            ps.setString(1, reqId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting received data count for reqId: {}", reqId, e);
        }
        return count;
    }

    public Integer getCountExistingDataInventory(String reqId, String status) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_DATA_INVENTORY)) {
            ps.setString(1, reqId);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting inventory data count for reqId: {}", reqId, e);
        }
        return count;
    }

    public Integer getCountExistingDataPerReqId(String reqId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_EXISTING_DATA_PER_REQ_ID)) {
            ps.setString(1, reqId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting shipping data count for reqId: {}", reqId, e);
        }
        return count;
    }

}