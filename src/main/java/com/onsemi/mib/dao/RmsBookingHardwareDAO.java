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
import com.onsemi.mib.model.RmsBookingHardware;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingHardwareDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingHardwareDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingHardwareDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_INSERT_RMS_BOOKING_HARDWARE = "INSERT INTO rms_booking_hardware (booking_pkid, pkid, item_type, item_id, item_pkid, qty, readiness, status, recall, flag, created_date, created_by, sub_status) VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,?)";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE = "UPDATE rms_booking_hardware SET booking_pkid = ?, pkid = ?, item_type = ?, item_id = ?, item_pkid = ?, qty = ?, readiness = ?, status = ?, recall = ?, flag = ?, created_date = ?, created_by = ?, modified_date = ?, modified_by = ? WHERE id = ?";
    private static final String SQL_UPDATERMS_BOOKING_HARDWARE_FOR_REQUEST_REPLACEMENT = "UPDATE rms_booking_hardware SET request_replacement_by = ?, request_replacement_remarks = ?, request_replacement_date = NOW(), status = ? WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_RECALL_SF = "UPDATE rms_booking_hardware SET recall_sf_by = ?, recall_sf_date = NOW(), status = ? WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_FLAG_AND_STATUS_BY_ID = "UPDATE rms_booking_hardware SET flag = ?, status = ?, modified_date = NOW(), modified_by = ?, sub_status = ? WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_FLAG_AND_STATUS_AND_RELEASE_DATE_BY_ID = "UPDATE rms_booking_hardware SET flag = ?, status = ?, modified_date = NOW(), modified_by = ?, sub_status = ?, released_date = NOW(), released_by = ? WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_FLAG_AND_STATUS_AND_RETURN_DATE_BY_ID = "UPDATE rms_booking_hardware SET flag = ?, status = ?, modified_date = NOW(), modified_by = ?, sub_status = ?, return_date = NOW(), return_by = ?, return_remarks = ? WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_FLAG_AND_STATUS_BY_BOOKING_ID = "UPDATE rms_booking_hardware SET flag = ?, status = ?, modified_date = NOW(), modified_by = ?, sub_status = ? WHERE booking_pkid = ?";
    private static final String SQL_DELETE_RMS_BOOKING_HARDWARE = "DELETE FROM rms_booking_hardware WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_BY_PKID_AND_BOOKING_PKID = "UPDATE rms_booking_hardware SET item_type = ?, item_id = ?, item_pkid = ?, qty = ?, readiness = ?, status = ?, recall = ?, flag = ?, modified_date = NOW(), modified_by = ?, sub_status = ? WHERE booking_pkid = ? AND pkid = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_ITEM_TYPE_ITEM_ID_FLAG_ZERO = "UPDATE rms_booking_hardware SET item_type = ?, item_id = ?, item_pkid = ?, qty = ?, readiness = ?, status = ?, recall = ?, flag = ?, modified_date = NOW(), modified_by = ?, sub_status = ? WHERE booking_pkid = ? AND item_type = ? AND item_id = ? AND flag = '0'";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_SUB_STATUS_BY_PKID_AND_BOOKING_PKID = "UPDATE rms_booking_hardware SET sub_status = ? WHERE booking_pkid = ? AND pkid = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_STATUS = "UPDATE rms_booking_hardware SET sub_status = ?, flag = ? WHERE booking_pkid = ? AND pkid = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_SUB_STATUS_BY_ID = "UPDATE rms_booking_hardware SET sub_status = ? WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_LC_QTY_AND_PC_QTY_BY_BOOKING_PKID_AND_PKID = "UPDATE rms_booking_hardware SET lc_qty = ?, pc_qty = ? WHERE booking_pkid = ? AND pkid = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_HARDWARE_FUNCTIONAL_TEST_STATUS = "UPDATE rms_booking_hardware SET status = ? WHERE id = ?";

    public QueryResult insertRmsBookingHardware(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RMS_BOOKING_HARDWARE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rmsbookingHardware.getBookingPkid());
            ps.setString(2, rmsbookingHardware.getPkid());
            ps.setString(3, rmsbookingHardware.getItemType());
            ps.setString(4, rmsbookingHardware.getItemId());
            ps.setString(5, rmsbookingHardware.getItemPkid());
            ps.setString(6, rmsbookingHardware.getQty());
            ps.setString(7, rmsbookingHardware.getReadiness());
            ps.setString(8, rmsbookingHardware.getStatus());
            ps.setString(9, rmsbookingHardware.getRecall());
            ps.setString(10, rmsbookingHardware.getFlag());
            ps.setString(11, rmsbookingHardware.getCreatedBy());
            ps.setString(12, rmsbookingHardware.getSubStatus());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getLong(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting rms booking hardware", e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardware(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE)) {
            ps.setString(1, rmsbookingHardware.getBookingPkid());
            ps.setString(2, rmsbookingHardware.getPkid());
            ps.setString(3, rmsbookingHardware.getItemType());
            ps.setString(4, rmsbookingHardware.getItemId());
            ps.setString(5, rmsbookingHardware.getItemPkid());
            ps.setString(6, rmsbookingHardware.getQty());
            ps.setString(7, rmsbookingHardware.getReadiness());
            ps.setString(8, rmsbookingHardware.getStatus());
            ps.setString(9, rmsbookingHardware.getRecall());
            ps.setString(10, rmsbookingHardware.getFlag());
            ps.setString(11, rmsbookingHardware.getCreatedDate());
            ps.setString(12, rmsbookingHardware.getCreatedBy());
            ps.setString(13, rmsbookingHardware.getModifiedDate());
            ps.setString(14, rmsbookingHardware.getModifiedBy());
            ps.setString(15, rmsbookingHardware.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating rms_booking_hardware for ID: {}", rmsbookingHardware.getId(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareForRequestReplacement(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATERMS_BOOKING_HARDWARE_FOR_REQUEST_REPLACEMENT)) {
            ps.setString(1, rmsbookingHardware.getRequestReplacementBy());
            ps.setString(2, rmsbookingHardware.getRequestReplacementRemarks());
            ps.setString(3, rmsbookingHardware.getStatus());
            ps.setString(4, rmsbookingHardware.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating replacement request for rms_booking_hardware ID: {}", rmsbookingHardware.getId(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareForRecallSf(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_RECALL_SF)) {
            ps.setString(1, rmsbookingHardware.getRecallSfBy());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating recall SF for rms_booking_hardware ID: {}", rmsbookingHardware.getId(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareForFlagAndStatusById(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_FLAG_AND_STATUS_BY_ID)) {
            ps.setString(1, rmsbookingHardware.getFlag());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getModifiedBy());
            ps.setString(4, rmsbookingHardware.getSubStatus());
            ps.setString(5, rmsbookingHardware.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating flag and status for rms_booking_hardware ID: {}", rmsbookingHardware.getId(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareForFlagAndStatusAndReleaseDateById(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_FLAG_AND_STATUS_AND_RELEASE_DATE_BY_ID)) {
            ps.setString(1, rmsbookingHardware.getFlag());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getModifiedBy());
            ps.setString(4, rmsbookingHardware.getSubStatus());
            ps.setString(5, rmsbookingHardware.getReleaseBy());
            ps.setString(6, rmsbookingHardware.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating flag, status and release information for rms_booking_hardware ID: {}", rmsbookingHardware.getId(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareForFlagAndStatusAndReturnDateById(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_FLAG_AND_STATUS_AND_RETURN_DATE_BY_ID)) {
            ps.setString(1, rmsbookingHardware.getFlag());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getModifiedBy());
            ps.setString(4, rmsbookingHardware.getSubStatus());
            ps.setString(5, rmsbookingHardware.getReturnBy());
            ps.setString(6, rmsbookingHardware.getReturnRemarks());
            ps.setString(7, rmsbookingHardware.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating flag, status and return information for rms_booking_hardware ID: {}", rmsbookingHardware.getId(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareForFlagAndStatusByBookingId(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_FOR_FLAG_AND_STATUS_BY_BOOKING_ID)) {
            ps.setString(1, rmsbookingHardware.getFlag());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getModifiedBy());
            ps.setString(4, rmsbookingHardware.getSubStatus());
            ps.setString(5, rmsbookingHardware.getBookingPkid());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating flag and status for rms_booking_hardware Booking PKID: {}", rmsbookingHardware.getBookingPkid(), e);
        }
        return queryResult;
    }

    public QueryResult deleteRmsBookingHardware(String rmsbookingHardwareId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_RMS_BOOKING_HARDWARE)) {
            ps.setString(1, rmsbookingHardwareId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting rms_booking_hardware ID: {}", rmsbookingHardwareId, e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareByPkidAndBookingPkid(RmsBookingHardware rmsBookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_BY_PKID_AND_BOOKING_PKID)) {
            ps.setString(1, rmsBookingHardware.getItemType());
            ps.setString(2, rmsBookingHardware.getItemId());
            ps.setString(3, rmsBookingHardware.getItemPkid());
            ps.setString(4, rmsBookingHardware.getQty());
            ps.setString(5, rmsBookingHardware.getReadiness());
            ps.setString(6, rmsBookingHardware.getStatus());
            ps.setString(7, rmsBookingHardware.getRecall());
            ps.setString(8, rmsBookingHardware.getFlag());
            ps.setString(9, rmsBookingHardware.getModifiedBy());
            ps.setString(10, rmsBookingHardware.getSubStatus());
            ps.setString(11, rmsBookingHardware.getBookingPkid());
            ps.setString(12, rmsBookingHardware.getPkid());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_hardware. bookingPkid={}, pkid={}", rmsBookingHardware.getBookingPkid(), rmsBookingHardware.getPkid(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareByBookingPkidItemTypeItemIdFlagZero(RmsBookingHardware rmsBookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_ITEM_TYPE_ITEM_ID_FLAG_ZERO)) {
            ps.setString(1, rmsBookingHardware.getItemType());
            ps.setString(2, rmsBookingHardware.getItemId());
            ps.setString(3, rmsBookingHardware.getItemPkid());
            ps.setString(4, rmsBookingHardware.getQty());
            ps.setString(5, rmsBookingHardware.getReadiness());
            ps.setString(6, rmsBookingHardware.getStatus());
            ps.setString(7, rmsBookingHardware.getRecall());
            ps.setString(8, rmsBookingHardware.getFlag());
            ps.setString(9, rmsBookingHardware.getModifiedBy());
            ps.setString(10, rmsBookingHardware.getSubStatus());
            ps.setString(11, rmsBookingHardware.getBookingPkid());
            ps.setString(12, rmsBookingHardware.getItemType());
            ps.setString(13, rmsBookingHardware.getItemId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_hardware. bookingPkid={}, itemType={}, itemId={}", rmsBookingHardware.getBookingPkid(), rmsBookingHardware.getItemType(), rmsBookingHardware.getItemId(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(RmsBookingHardware rmsBookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_SUB_STATUS_BY_PKID_AND_BOOKING_PKID)) {
            ps.setString(1, rmsBookingHardware.getSubStatus());
            ps.setString(2, rmsBookingHardware.getBookingPkid());
            ps.setString(3, rmsBookingHardware.getPkid());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_hardware sub_status. bookingPkid={}, pkid={}", rmsBookingHardware.getBookingPkid(), rmsBookingHardware.getPkid(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareStatus(RmsBookingHardware rmsBookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_STATUS)) {
            ps.setString(1, rmsBookingHardware.getSubStatus());
            ps.setString(2, rmsBookingHardware.getFlag());
            ps.setString(3, rmsBookingHardware.getBookingPkid());
            ps.setString(4, rmsBookingHardware.getPkid());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_hardware status. bookingPkid={}, pkid={}", rmsBookingHardware.getBookingPkid(), rmsBookingHardware.getPkid(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareSubStatusById(RmsBookingHardware rmsBookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_SUB_STATUS_BY_ID)) {
            ps.setString(1, rmsBookingHardware.getSubStatus());
            ps.setString(2, rmsBookingHardware.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_hardware sub_status. id={}", rmsBookingHardware.getId(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareLcQtyAndPcQtyByBookingPkidAndPkid(RmsBookingHardware rmsBookingHardware) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_LC_QTY_AND_PC_QTY_BY_BOOKING_PKID_AND_PKID)) {
            ps.setString(1, rmsBookingHardware.getLcQty());
            ps.setString(2, rmsBookingHardware.getPcQty());
            ps.setString(3, rmsBookingHardware.getBookingPkid());
            ps.setString(4, rmsBookingHardware.getPkid());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_hardware lc_qty and pc_qty. bookingPkid={}, pkid={}", rmsBookingHardware.getBookingPkid(), rmsBookingHardware.getPkid(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingHardwareFunctionalTestStatus(String status, String bookId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_HARDWARE_FUNCTIONAL_TEST_STATUS)) {
            ps.setString(1, status);
            ps.setString(2, bookId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_hardware functional test status. id={}, status={}", bookId, status, e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    private static final String SQL_GET_RMS_BOOKING_HARDWARE = "SELECT * FROM rms_booking_hardware WHERE id = ?";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_FOR_LOADCARD_FLAG_ZERO = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = ? AND ha.flag = '0' AND ha.status != 'NA' AND ha.item_type = 'Load Card'";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_FOR_PROGRAMCARD_FLAG_ZERO = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = ? AND ha.flag = '0' AND ha.status != 'NA' AND ha.item_type = 'Program Card'";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_AND_ITEM_PKID = "SELECT * FROM rms_booking_hardware WHERE booking_pkid = ? AND item_pkid = ? AND status = 'Available'";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_AND_ITEM_PKID_BIB_CARD = "SELECT * FROM rms_booking_hardware WHERE booking_pkid = ? AND item_pkid = ? AND status IN ('Available', 'Released to Production')";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_AND_PKID = "SELECT * FROM rms_booking_hardware WHERE booking_pkid = ? AND pkid = ? AND item_type = 'Motherboard'";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_BY_PKID = "SELECT *, DATE_FORMAT(return_date, '%d %M %Y %h:%i %p') AS returnDate FROM rms_booking_hardware WHERE pkid = ?";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_AND_ITEM_TYPE_AND_ITEMID_AND_FLAG_NE99 = "SELECT * FROM rms_booking_hardware WHERE booking_pkid = ? AND item_type = ? AND item_id = ? AND flag != '99'";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_REMARKS_BY_BOOKING_PKID = "SELECT ha.item_id FROM rms_booking_detail de INNER JOIN rms_booking_hardware ha ON ha.booking_pkid = de.booking_pkid WHERE ha.item_type = 'Remarks' AND de.booking_pkid = ?";

    public RmsBookingHardware getRmsBookingHardware(String rmsbookingHardwareId) {
        RmsBookingHardware rmsbookingHardware = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE)) {
            ps.setString(1, rmsbookingHardwareId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware ID: {}", rmsbookingHardwareId, e);
        }
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareByBookingPkidForLoadCardFlagZero(String bookingPkid) {
        RmsBookingHardware rmsbookingHardware = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_FOR_LOADCARD_FLAG_ZERO)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Load Card with flag 0 for booking PKID: {}", bookingPkid, e);
        }
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareByBookingPkidForProgramCardFlagZero(String bookingPkid) {
        RmsBookingHardware rmsbookingHardware = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_FOR_PROGRAMCARD_FLAG_ZERO)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Program Card with flag 0 for booking PKID: {}", bookingPkid, e);
        }
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareByBookingPkidAndItemPKid(String bookingPkid, String itemPkid) {
        RmsBookingHardware rmsbookingHardware = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_AND_ITEM_PKID)) {
            ps.setString(1, bookingPkid);
            ps.setString(2, itemPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware for bookingPkid: {} and itemPkid: {}", bookingPkid, itemPkid, e);
        }
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareByBookingPkidAndItemPKidBibCard(String bookingPkid, String itemPkid) {
        RmsBookingHardware rmsbookingHardware = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_AND_ITEM_PKID_BIB_CARD)) {
            ps.setString(1, bookingPkid);
            ps.setString(2, itemPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Bib Card rms_booking_hardware for bookingPkid: {} and itemPkid: {}", bookingPkid, itemPkid, e);
        }
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareByBookingPkidAndPkid(String bookingPkid, String pkid) {
        RmsBookingHardware rmsbookingHardware = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_AND_PKID)) {
            ps.setString(1, bookingPkid);
            ps.setString(2, pkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Motherboard rms_booking_hardware for bookingPkid: {} and pkid: {}", bookingPkid, pkid, e);
        }
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareByPkid(String pkid) {
        RmsBookingHardware rmsbookingHardware = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_BY_PKID)) {
            ps.setString(1, pkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setSubStatus(rs.getString("sub_status"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                    rmsbookingHardware.setReturnBy(rs.getString("return_by"));
                    rmsbookingHardware.setReturnDate(rs.getString("returnDate"));
                    rmsbookingHardware.setReturnRemarks(rs.getString("return_remarks"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware for pkid: {}", pkid, e);
        }
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareBybookingPkidAndItemTypeAndItemIdAndFlagNE99(String bookingId, String itemType, String itemId) {
        RmsBookingHardware rmsbookingHardware = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_BY_BOOKING_PKID_AND_ITEM_TYPE_AND_ITEMID_AND_FLAG_NE99)) {
            ps.setString(1, bookingId);
            ps.setString(2, itemType);
            ps.setString(3, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setSubStatus(rs.getString("sub_status"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware for bookingId: {}, itemType: {}, itemId: {}", bookingId, itemType, itemId, e);
        }
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareRemarksByBookingPkid(String bookingPkid) {
        RmsBookingHardware rmsbookingHardware = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_REMARKS_BY_BOOKING_PKID)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Remarks hardware for bookingPkid: {}", bookingPkid, e);
        }
        return rmsbookingHardware;
    }

    private static final String SQL_GET_RMS_BOOKING_HARDWARE_LIST = "SELECT * FROM rms_booking_hardware ORDER BY id ASC";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ZERO = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = ? AND ha.flag = '0'";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ZERO_AND_STATUS_NOT_NA = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = ? AND ha.flag = '0' ";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ONE_AND_STATUS_NOT_NA = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = ? AND ha.flag = '1' AND ha.status != 'NA'";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ZERO_FOR_HW_REPLACEMENT = "SELECT ha.* FROM rms_booking_hardware ha LEFT JOIN rms_booking_detail_hw_replacement re ON ha.pkid = re.booking_hw_pkid WHERE ha.booking_pkid = ? AND ha.flag = '0' AND ha.item_type IN ('Motherboard', 'Load Card', 'Program Card') AND ha.status LIKE 'Not Available%' AND re.booking_hw_pkid IS NULL";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ZERO_FOR_HW_REPLACEMENT_2 = "SELECT ha.* FROM rms_booking_hardware ha LEFT JOIN rms_booking_detail_hw_replacement re ON ha.pkid = re.booking_hw_pkid WHERE ha.booking_pkid = ? AND ha.flag = '0' AND ha.item_type IN ('Motherboard', 'Load Card', 'Program Card') AND re.booking_hw_pkid IS NULL";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_LIST_FOR_MOTHERBOARD_BY_BOOKING_PKID = "SELECT ha.*, IFNULL(lc_qty, '0') AS lcQty, IFNULL(pc_qty, '0') AS pcQty FROM rms_booking_hardware ha WHERE ha.booking_pkid = ? AND ha.item_type = 'Motherboard' ORDER BY ha.flag, ha.item_id";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_LIST_FOR_OTHER_HW_BY_BOOKING_PKID = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = ? AND ha.item_type NOT IN ('Motherboard', 'Remarks') ORDER BY ha.flag, ha.item_type";
    private static final String SQL_GET_RMS_BOOKING_FOR_UNLOADING = "SELECT booking_pkid, pkid, item_id, item_pkid, status, sub_status, qty, flag FROM rms_booking_hardware WHERE booking_pkid = ? AND pkid = ?";
    private static final String SQL_GET_RMS_HARDWARE_LIST = "SELECT * FROM rms_booking_hardware WHERE booking_pkid = ? AND status = 'Available'";

    public List<RmsBookingHardware> getRmsBookingHardwareList() {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                rmsbookingHardware.setId(rs.getString("id"));
                rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                rmsbookingHardware.setPkid(rs.getString("pkid"));
                rmsbookingHardware.setItemType(rs.getString("item_type"));
                rmsbookingHardware.setItemId(rs.getString("item_id"));
                rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                rmsbookingHardware.setQty(rs.getString("qty"));
                rmsbookingHardware.setReadiness(rs.getString("readiness"));
                rmsbookingHardware.setStatus(rs.getString("status"));
                rmsbookingHardware.setRecall(rs.getString("recall"));
                rmsbookingHardware.setFlag(rs.getString("flag"));
                rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                rmsbookingHardwareList.add(rmsbookingHardware);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware list", e);
        }
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagZero(String bookingPkid) {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ZERO)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                    rmsbookingHardwareList.add(rmsbookingHardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware list for bookingPkid: {}", bookingPkid, e);
        }
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagZeroAndStatusNotNA(String bookingPkid) {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ZERO_AND_STATUS_NOT_NA)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                    rmsbookingHardwareList.add(rmsbookingHardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware list with flag 0 and status not NA for bookingPkid: {}", bookingPkid, e);
        }
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagOneAndStatusNotNA(String bookingPkid) {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ONE_AND_STATUS_NOT_NA)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                    rmsbookingHardwareList.add(rmsbookingHardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware list with flag 1 and status not NA for bookingPkid: {}", bookingPkid, e);
        }
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagZeroForHwReplacement(String bookingPkid) {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ZERO_FOR_HW_REPLACEMENT)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                    rmsbookingHardwareList.add(rmsbookingHardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware list for hardware replacement, bookingPkid: {}", bookingPkid, e);
        }
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagZeroForHwReplacement2(String bookingPkid) {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_LIST_BY_BOOKING_PKID_WITH_FLAG_ZERO_FOR_HW_REPLACEMENT_2)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                    rmsbookingHardwareList.add(rmsbookingHardware);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_hardware list for hardware replacement 2, bookingPkid: {}", bookingPkid, e);
        }
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListForMotherboardByBookingPkid(String bookingPkid) {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_LIST_FOR_MOTHERBOARD_BY_BOOKING_PKID)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setSubStatus(rs.getString("sub_status"));
                    rmsbookingHardware.setLcQty(rs.getString("lcQty"));
                    rmsbookingHardware.setPcQty(rs.getString("pcQty"));
                    rmsbookingHardwareList.add(rmsbookingHardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving motherboard rms_booking_hardware list for bookingPkid: {}", bookingPkid, e);
        }
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListForOtherHwByBookingPkid(String bookingPkid) {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_LIST_FOR_OTHER_HW_BY_BOOKING_PKID)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                    rmsbookingHardwareList.add(rmsbookingHardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving other hardware rms_booking_hardware list for bookingPkid: {}", bookingPkid, e);
        }
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingForUnloading(String bookingPkid, String pkid) {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_FOR_UNLOADING)) {
            ps.setString(1, bookingPkid);
            ps.setString(2, pkid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setSubStatus(rs.getString("sub_status"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardwareList.add(rmsbookingHardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms booking hardware for unloading. bookingPkid: {}, pkid: {}", bookingPkid, pkid, e);
        }
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsHardwareList(String id) {
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_HARDWARE_LIST)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardware rmsbookingHardware = new RmsBookingHardware();
                    rmsbookingHardware.setId(rs.getString("id"));
                    rmsbookingHardware.setBookingPkid(rs.getString("booking_pkid"));
                    rmsbookingHardware.setPkid(rs.getString("pkid"));
                    rmsbookingHardware.setItemType(rs.getString("item_type"));
                    rmsbookingHardware.setItemId(rs.getString("item_id"));
                    rmsbookingHardware.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardware.setQty(rs.getString("qty"));
                    rmsbookingHardware.setReadiness(rs.getString("readiness"));
                    rmsbookingHardware.setStatus(rs.getString("status"));
                    rmsbookingHardware.setRecall(rs.getString("recall"));
                    rmsbookingHardware.setFlag(rs.getString("flag"));
                    rmsbookingHardware.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardware.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardware.setModifiedDate(rs.getString("modified_date"));
                    rmsbookingHardware.setModifiedBy(rs.getString("modified_by"));
                    rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                    rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
                    rmsbookingHardwareList.add(rmsbookingHardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving available rms booking hardware list for bookingPkid: {}", id, e);
        }
        return rmsbookingHardwareList;
    }

    private static final String SQL_GET_COUNT_BOOKING_ID = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.pkid = ?";
    private static final String SQL_GET_COUNT_BOOKING_ID_FLAG_ZERO = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.pkid = ? AND inc.flag = '0'";
    private static final String SQL_GET_COUNT_BOOKING_ID_WITH_ITEM_TYPE_AND_ITEM_ID_AND_FLAG_NE99 = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.item_type = ? AND inc.item_id = ? AND inc.flag != '99'";
    private static final String SQL_GET_COUNT_BOOKING_ID_WITH_ITEM_TYPE_AND_ITEM_ID_AND_FLAG_ZERO = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.item_type = ? AND inc.item_id = ? AND inc.flag = '0'";
    private static final String SQL_GET_COUNT_BOOKING_ID_WITH_ITEM_TYPE_AND_ITEM_ID_FLAG_99 = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.item_type = ? AND inc.item_id = ? AND inc.flag = '99'";
    private static final String SQL_GET_COUNT_HW_WITH_REMARKS_BY_BOOKING_PKID = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.item_type = 'Remarks' AND inc.booking_pkid = ?";
    private static final String SQL_GET_COUNT_BOOKING_PKID_AND_ITEM_PKID = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.item_pkid = ? AND inc.status = 'Available'";
    private static final String SQL_GET_COUNT_BOOKING_PKID_AND_ITEM_PKID_FOR_BIB_CARD = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.item_pkid = ? AND inc.status IN ('Available', 'Released to Production')";
    private static final String SQL_GET_COUNT_BOOKING_PKID_AND_PKID_FOR_MOTHERBOARD = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.pkid = ? AND inc.item_type = 'Motherboard'";
    private static final String SQL_GET_COUNT_MOTHERBOARD_BY_BOOKING_PKID_AND_FLAG_NOT_99 = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.item_type = 'Motherboard' AND inc.flag != '99'";
    private static final String SQL_GET_COUNT_MOTHERBOARD_BY_BOOKING_PKID_AND_FLAG_ZERO = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.item_type = 'Motherboard' AND inc.flag = '0'";
    private static final String SQL_GET_COUNT_MOTHERBOARD_BY_BOOKING_PKID_AND_PENDING_RELEASE = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = ? AND inc.item_type = 'Motherboard' AND inc.sub_status = 'Pending Release to Production'";
    private static final String SQL_GET_COUNT_MOTHERBOARD_RETURN_FROM_PRODUCTION = "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.item_type = 'Motherboard' AND inc.sub_status LIKE 'Return from Production%' AND inc.flag = '2'";
    private static final String SQL_CHECK_MOTHERBOARD_DATA = "SELECT COUNT(*) AS count FROM rms_booking_hardware WHERE booking_pkid = ? AND item_type = 'Motherboard'";
    private static final String SQL_CHECK_CARD_DATA = "SELECT COUNT(*) AS count FROM rms_booking_hardware WHERE booking_pkid = ? AND item_type != 'Motherboard' AND qty != 0";
    private static final String SQL_COUNT_DATA_HARDWARE_SEMUA = "SELECT COUNT(*) AS count FROM rms_booking_hardware WHERE booking_pkid = ? AND item_type = 'Motherboard' AND status != 'Removed'";
    private static final String SQL_COUNT_DATA_HARDWARE_CLOSED = "SELECT COUNT(*) AS count FROM rms_booking_hardware WHERE booking_pkid = ? AND item_type = 'Motherboard' AND status != 'Removed' AND status = 'Closed'";
    private static final String SQL_GET_SPTS_PKID_FOR_ITEM_ID_LC = "SELECT * FROM item WHERE spts_pkid = (SELECT item_pkid FROM rms_booking_hardware WHERE booking_pkid = ? AND item_type = 'Load Card' AND qty != 0 LIMIT 1 )";
    private static final String SQL_GET_SPTS_PKID_FOR_ITEM_ID_MB = "SELECT item_pkid FROM rms_booking_hardware WHERE booking_pkid = ? AND item_type = 'Motherboard' AND pkid = ?";
    private static final String SQL_GET_LATEST_STATUS = "SELECT status FROM rms_booking_hardware WHERE booking_pkid = ? AND item_type = 'Motherboard' AND pkid = ?";
    private static final String SQL_GET_MB_MIB_ITEM_ID_FROM_GROUP_ID = "SELECT it.id AS item_id FROM rms_booking_hardware_group rms INNER JOIN item it ON it.spts_pkid = rms.item_pkid INNER JOIN item_activity_config ac ON ac.mib_item_id = it.id WHERE rms.item_type = 'BIB' AND rms.group_id = ?";
    private static final String SQL_GET_LC_MIB_ITEM_ID_FROM_GROUP_ID = "SELECT it.id AS item_id FROM rms_booking_hardware_group rms INNER JOIN item it ON it.spts_pkid = rms.item_pkid INNER JOIN item_activity_config ac ON ac.mib_item_id = it.id WHERE rms.item_type = 'BIB Card' AND rms.group_id = ? GROUP BY item_id";
    private static final String SQL_GET_COUNT_BIB_IN_BY_MONTH_AND_YEAR = "SELECT COUNT(*) AS count FROM rms_booking_hardware ha WHERE ha.item_type = 'Motherboard' AND MONTH(ha.created_date) = ? AND YEAR(ha.created_date) = ? AND ha.flag != '99'";
    private static final String SQL_GET_COUNT_BIB_RELEASE_BY_MONTH_AND_YEAR = "SELECT COUNT(*) AS count FROM rms_booking_hardware ha WHERE ha.item_type = 'Motherboard' AND MONTH(ha.released_date) = ? AND YEAR(ha.released_date) = ? AND ha.flag != '99'";
    private static final String SQL_GET_COUNT_AVG_CIRCLE_TYPE_BY_MONTH_AND_YEAR = "SELECT COALESCE(ROUND(AVG(DATEDIFF(ha.released_date, ha.created_date)), 2), 0) AS avg_day_difference FROM rms_booking_hardware ha WHERE ha.item_type = 'Motherboard' AND MONTH(ha.created_date) = ? AND YEAR(ha.created_date) = ? AND ha.flag != '99' AND ha.released_date IS NOT NULL";

    public Integer getCountBookingId(String bookingId, String pkid) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_ID)) {
            ps.setString(1, bookingId);
            ps.setString(2, pkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving booking count for bookingId: {} and pkid: {}", bookingId, pkid, e);
        }
        return count;
    }

    public Integer getCountBookingIdFlagZero(String bookingId, String pkid) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_ID_FLAG_ZERO)) {
            ps.setString(1, bookingId);
            ps.setString(2, pkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving booking count with flag 0 for bookingId: {} and pkid: {}", bookingId, pkid, e);
        }
        return count;
    }

    public Integer getCountBookingIdWithItemTypeAndItemIdAndFlagNE99(String bookingId, String itemType, String itemId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_ID_WITH_ITEM_TYPE_AND_ITEM_ID_AND_FLAG_NE99)) {
            ps.setString(1, bookingId);
            ps.setString(2, itemType);
            ps.setString(3, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving booking count for bookingId: {}, itemType: {}, itemId: {}", bookingId, itemType, itemId, e);
        }
        return count;
    }

    public Integer getCountBookingIdWithItemTypeAndItemIdAndFlagZero(String bookingId, String itemType, String itemId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_ID_WITH_ITEM_TYPE_AND_ITEM_ID_AND_FLAG_ZERO)) {
            ps.setString(1, bookingId);
            ps.setString(2, itemType);
            ps.setString(3, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving booking count with flag 0 for bookingId: {}, itemType: {}, itemId: {}", bookingId, itemType, itemId, e);
        }
        return count;
    }

    public Integer getCountBookingIdWithItemTypeAndItemIdFlag99(String bookingId, String itemType, String itemId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_ID_WITH_ITEM_TYPE_AND_ITEM_ID_FLAG_99)) {
            ps.setString(1, bookingId);
            ps.setString(2, itemType);
            ps.setString(3, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving booking count with flag 99 for bookingId: {}, itemType: {}, itemId: {}", bookingId, itemType, itemId, e);
        }
        return count;
    }

    public Integer getCountHwWithRemarksByBookingPkid(String bookingPkid) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_HW_WITH_REMARKS_BY_BOOKING_PKID)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving remarks hardware count for bookingPkid: {}", bookingPkid, e);
        }
        return count;
    }

    public Integer getCountBookingPkidAndItemPkid(String bookingPkid, String itemPkid) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_PKID_AND_ITEM_PKID)) {
            ps.setString(1, bookingPkid);
            ps.setString(2, itemPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware count for bookingPkid: {} and itemPkid: {}", bookingPkid, itemPkid, e);
        }
        return count;
    }

    public Integer getCountBookingPkidAndItemPkidForBibCard(String bookingPkid, String itemPkid) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_PKID_AND_ITEM_PKID_FOR_BIB_CARD)) {
            ps.setString(1, bookingPkid);
            ps.setString(2, itemPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Bib Card hardware count for bookingPkid: {} and itemPkid: {}", bookingPkid, itemPkid, e);
        }
        return count;
    }

    public Integer getCountBookingPkidAndPkidForMotherboard(String bookingPkid, String pkid) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_PKID_AND_PKID_FOR_MOTHERBOARD)) {
            ps.setString(1, bookingPkid);
            ps.setString(2, pkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving motherboard count for bookingPkid: {} and pkid: {}", bookingPkid, pkid, e);
        }
        return count;
    }

    public Integer getCountMotherboardByBookingPkidAndFlagNot99(String bookingPkid) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_MOTHERBOARD_BY_BOOKING_PKID_AND_FLAG_NOT_99)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving motherboard count with flag not 99 for bookingPkid: {}", bookingPkid, e);
        }
        return count;
    }

    public Integer getCountMotherboardByBookingPkidAndFlagZero(String bookingPkid) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_MOTHERBOARD_BY_BOOKING_PKID_AND_FLAG_ZERO)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving motherboard count with flag 0 for bookingPkid: {}", bookingPkid, e);
        }
        return count;
    }

    public Integer getCountMotherboardByBookingPkidAndPendingRelease(String bookingPkid) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_MOTHERBOARD_BY_BOOKING_PKID_AND_PENDING_RELEASE)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving motherboard count pending release for bookingPkid: {}", bookingPkid, e);
        }
        return count;
    }

    public Integer getCountMotherboardReturnFromProduction() {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_MOTHERBOARD_RETURN_FROM_PRODUCTION); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving motherboard count returned from production", e);
        }
        return count;
    }

    public Integer checkMotherboardData(String bookingId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_CHECK_MOTHERBOARD_DATA)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking motherboard data for bookingId: {}", bookingId, e);
        }
        return count;
    }

    public Integer checkCardData(String bookingId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_CHECK_CARD_DATA)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking card data for bookingId: {}", bookingId, e);
        }
        return count;
    }

    public Integer countDataHardwareSemua(String bookingId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_COUNT_DATA_HARDWARE_SEMUA)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting motherboard hardware records for bookingId: {}", bookingId, e);
        }
        return count;
    }

    public Integer countDataHardwareClosed(String bookingId) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_COUNT_DATA_HARDWARE_CLOSED)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting closed motherboard hardware records for bookingId: {}", bookingId, e);
        }
        return count;
    }

    public String getSptsPkidForItemIdLC(String bookingId) {
        String data = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SPTS_PKID_FOR_ITEM_ID_LC)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving SPTS PKID item ID for Load Card, bookingId: {}", bookingId, e);
        }
        return data;
    }

    public String getSptsPkidForItemIdMb(String bookingId, String pkId) {
        String data = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SPTS_PKID_FOR_ITEM_ID_MB)) {
            ps.setString(1, bookingId);
            ps.setString(2, pkId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("item_pkid");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving item_pkid for Motherboard, bookingId: {} and pkId: {}", bookingId, pkId, e);
        }
        return data;
    }

    public String getLatestStatus(String bookingId, String pkId) {
        String data = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_LATEST_STATUS)) {
            ps.setString(1, bookingId);
            ps.setString(2, pkId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("status");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving latest status for bookingId: {} and pkId: {}", bookingId, pkId, e);
        }
        return data;
    }

    public String getMbMibItemIdFromGroupId(String groupId) {
        String data = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_MB_MIB_ITEM_ID_FROM_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("item_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving motherboard MIB item ID for groupId: {}", groupId, e);
        }
        return data;
    }

    public String getLcMibItemIdFromGroupId(String groupId) {
        String data = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_LC_MIB_ITEM_ID_FROM_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("item_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Load Card MIB item ID for groupId: {}", groupId, e);
        }
        return data;
    }

    public Integer getCountBibInByMonthAndYear(String month, String year) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BIB_IN_BY_MONTH_AND_YEAR)) {
            ps.setString(1, month);
            ps.setString(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving BIB In count for month: {} and year: {}", month, year, e);
        }
        return count;
    }

    public Integer getCountBibReleaseByMonthAndYear(String month, String year) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BIB_RELEASE_BY_MONTH_AND_YEAR)) {
            ps.setString(1, month);
            ps.setString(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving BIB Release count for month: {} and year: {}", month, year, e);
        }
        return count;
    }

    public String getCountAvgCircleTypeByMonthAndYear(String month, String year) {
        String count = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_AVG_CIRCLE_TYPE_BY_MONTH_AND_YEAR)) {
            ps.setString(1, month);
            ps.setString(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getString("avg_day_difference");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving average cycle time for month: {} and year: {}", month, year, e);
        }
        return count;
    }

}