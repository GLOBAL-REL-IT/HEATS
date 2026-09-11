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
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingDetailDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingDetailDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingDetailDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_INSERT_RMS_BOOKING_DETAIL = "INSERT INTO rms_booking_detail (booking_pkid, rms_no, event, device, packages, event_start_date, rms_status, event_begin_status, event_end_status, no_current_ftp, equipment_location, est_start_date, act_start_date, fol_filename, total_booking, created_date, status, flag, priority, days_to_event_start) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?)";
    private static final String SQL_UPDATE_RMS_BOOKING_DETAIL = "UPDATE rms_booking_detail SET booking_pkid = ?, rms_no = ?, event = ?, device = ?, packages = ?, event_start_date = ?, rms_status = ?, event_begin_status = ?, event_end_status = ?, no_current_ftp = ?, equipment_location = ?, est_start_date = ?, act_start_date = ?, fol_filename = ?, total_booking = ?, created_date = ?, modified_date = ?, status = ?, priority = ?, priority_remarks = ?, priority_by = ?, priority_date = ?, flag = ? WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_DETAIL_FROM_CBMS_BY_PKID = "UPDATE rms_booking_detail SET booking_pkid = ?, rms_no = ?, event = ?, device = ?, packages = ?, event_start_date = ?, rms_status = ?, event_begin_status = ?, event_end_status = ?, no_current_ftp = ?, equipment_location = ?, est_start_date = ?, act_start_date = ?, fol_filename = ?, total_booking = ?, modified_date = NOW(), days_to_event_start = ? WHERE booking_pkid = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_DETAIL_FROM_CBMS_BY_RMS_NO_AND_EVENT = "UPDATE rms_booking_detail SET booking_pkid = ?, rms_no = ?, event = ?, device = ?, packages = ?, event_start_date = ?, rms_status = ?, event_begin_status = ?, event_end_status = ?, no_current_ftp = ?, equipment_location = ?, est_start_date = ?, act_start_date = ?, fol_filename = ?, total_booking = ?, modified_date = NOW(), days_to_event_start = ? WHERE rms_no = ? AND event = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_FLAG_AND_STATUS = "UPDATE rms_booking_detail SET flag = ?, modified_date = NOW(), status = ? WHERE booking_pkid = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_FLAG_AND_STATUS_BY_RMS_NO_AND_EVENT = "UPDATE rms_booking_detail SET flag = ?, modified_date = NOW(), status = ? WHERE rms_no = ? AND event = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_STATUS_AND_FLAG = "UPDATE rms_booking_detail SET modified_date = NOW(), status = ?, flag = ? WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_STATUS_AND_FLAG_AND_RELEASE_DATE_BY = "UPDATE rms_booking_detail SET modified_date = NOW(), status = ?, flag = ?, released_by = ?, released_date = NOW() WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_RETURN = "UPDATE rms_booking_detail SET return_date = NOW(), status = ?, flag = ?, return_by = ?, return_remarks = ? WHERE id = ?";
    private static final String SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_PRIORITY = "UPDATE rms_booking_detail SET priority = ?, priority_date = NOW(), priority_remarks = ?, priority_by = ? WHERE id = ?";
    private static final String SQL_DELETE_RMS_BOOKING_DETAIL = "DELETE FROM rms_booking_detail WHERE id = ?";

    public QueryResult insertRmsBookingDetail(RmsBookingDetail rmsBookingDetail) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RMS_BOOKING_DETAIL,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rmsBookingDetail.getBookingPkid());
            ps.setString(2, rmsBookingDetail.getRmsNo());
            ps.setString(3, rmsBookingDetail.getEvent());
            ps.setString(4, rmsBookingDetail.getDevice());
            ps.setString(5, rmsBookingDetail.getPackages());
            ps.setString(6, rmsBookingDetail.getEventStartDate());
            ps.setString(7, rmsBookingDetail.getRmsStatus());
            ps.setString(8, rmsBookingDetail.getEventBeginStatus());
            ps.setString(9, rmsBookingDetail.getEventEndStatus());
            ps.setString(10, rmsBookingDetail.getNoCurrentFtp());
            ps.setString(11, rmsBookingDetail.getEquipmentLocation());
            ps.setString(12, rmsBookingDetail.getEstStartDate());
            ps.setString(13, rmsBookingDetail.getActStartDate());
            ps.setString(14, rmsBookingDetail.getFolFilename());
            ps.setString(15, rmsBookingDetail.getTotalBooking());
            ps.setString(16, rmsBookingDetail.getStatus());
            ps.setString(17, rmsBookingDetail.getFlag());
            ps.setString(18, rmsBookingDetail.getPriority());
            ps.setString(19, rmsBookingDetail.getDaysToEventStart());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error inserting rms_booking_detail. bookingPkid={}, rmsNo={}", rmsBookingDetail.getBookingPkid(), rmsBookingDetail.getRmsNo(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetail(RmsBookingDetail rmsBookingDetail) {QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_DETAIL)) {
            ps.setString(1, rmsBookingDetail.getBookingPkid());
            ps.setString(2, rmsBookingDetail.getRmsNo());
            ps.setString(3, rmsBookingDetail.getEvent());
            ps.setString(4, rmsBookingDetail.getDevice());
            ps.setString(5, rmsBookingDetail.getPackages());
            ps.setString(6, rmsBookingDetail.getEventStartDate());
            ps.setString(7, rmsBookingDetail.getRmsStatus());
            ps.setString(8, rmsBookingDetail.getEventBeginStatus());
            ps.setString(9, rmsBookingDetail.getEventEndStatus());
            ps.setString(10, rmsBookingDetail.getNoCurrentFtp());
            ps.setString(11, rmsBookingDetail.getEquipmentLocation());
            ps.setString(12, rmsBookingDetail.getEstStartDate());
            ps.setString(13, rmsBookingDetail.getActStartDate());
            ps.setString(14, rmsBookingDetail.getFolFilename());
            ps.setString(15, rmsBookingDetail.getTotalBooking());
            ps.setString(16, rmsBookingDetail.getCreatedDate());
            ps.setString(17, rmsBookingDetail.getModifiedDate());
            ps.setString(18, rmsBookingDetail.getStatus());
            ps.setString(19, rmsBookingDetail.getPriority());
            ps.setString(20, rmsBookingDetail.getPriorityRemarks());
            ps.setString(21, rmsBookingDetail.getPriorityBy());
            ps.setString(22, rmsBookingDetail.getPriorityDate());
            ps.setString(23, rmsBookingDetail.getFlag());
            ps.setString(24, rmsBookingDetail.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_detail. id={}, bookingPkid={}, rmsNo={}", rmsBookingDetail.getId(), rmsBookingDetail.getBookingPkid(), rmsBookingDetail.getRmsNo(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailFromCBMSByPkid(RmsBookingDetail rmsBookingDetail) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_DETAIL_FROM_CBMS_BY_PKID)) {
            ps.setString(1, rmsBookingDetail.getBookingPkid());
            ps.setString(2, rmsBookingDetail.getRmsNo());
            ps.setString(3, rmsBookingDetail.getEvent());
            ps.setString(4, rmsBookingDetail.getDevice());
            ps.setString(5, rmsBookingDetail.getPackages());
            ps.setString(6, rmsBookingDetail.getEventStartDate());
            ps.setString(7, rmsBookingDetail.getRmsStatus());
            ps.setString(8, rmsBookingDetail.getEventBeginStatus());
            ps.setString(9, rmsBookingDetail.getEventEndStatus());
            ps.setString(10, rmsBookingDetail.getNoCurrentFtp());
            ps.setString(11, rmsBookingDetail.getEquipmentLocation());
            ps.setString(12, rmsBookingDetail.getEstStartDate());
            ps.setString(13, rmsBookingDetail.getActStartDate());
            ps.setString(14, rmsBookingDetail.getFolFilename());
            ps.setString(15, rmsBookingDetail.getTotalBooking());
            ps.setString(16, rmsBookingDetail.getDaysToEventStart());
            ps.setString(17, rmsBookingDetail.getBookingPkid());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_detail from CBMS. bookingPkid={}, rmsNo={}", rmsBookingDetail.getBookingPkid(), rmsBookingDetail.getRmsNo(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailFromCBMSByRmsNoAndEvent(RmsBookingDetail rmsBookingDetail) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_DETAIL_FROM_CBMS_BY_RMS_NO_AND_EVENT)) {
            ps.setString(1, rmsBookingDetail.getBookingPkid());
            ps.setString(2, rmsBookingDetail.getRmsNo());
            ps.setString(3, rmsBookingDetail.getEvent());
            ps.setString(4, rmsBookingDetail.getDevice());
            ps.setString(5, rmsBookingDetail.getPackages());
            ps.setString(6, rmsBookingDetail.getEventStartDate());
            ps.setString(7, rmsBookingDetail.getRmsStatus());
            ps.setString(8, rmsBookingDetail.getEventBeginStatus());
            ps.setString(9, rmsBookingDetail.getEventEndStatus());
            ps.setString(10, rmsBookingDetail.getNoCurrentFtp());
            ps.setString(11, rmsBookingDetail.getEquipmentLocation());
            ps.setString(12, rmsBookingDetail.getEstStartDate());
            ps.setString(13, rmsBookingDetail.getActStartDate());
            ps.setString(14, rmsBookingDetail.getFolFilename());
            ps.setString(15, rmsBookingDetail.getTotalBooking());
            ps.setString(16, rmsBookingDetail.getDaysToEventStart());
            ps.setString(17, rmsBookingDetail.getRmsNo());
            ps.setString(18, rmsBookingDetail.getEvent());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_detail from CBMS. rmsNo={}, event={}", rmsBookingDetail.getRmsNo(), rmsBookingDetail.getEvent(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailForFlagAndStatus(RmsBookingDetail rmsBookingDetail) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_FLAG_AND_STATUS)) {
            ps.setString(1, rmsBookingDetail.getFlag());
            ps.setString(2, rmsBookingDetail.getStatus());
            ps.setString(3, rmsBookingDetail.getBookingPkid());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_detail flag and status. bookingPkid={}, flag={}, status={}", rmsBookingDetail.getBookingPkid(), rmsBookingDetail.getFlag(), rmsBookingDetail.getStatus(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailForFlagAndStatusByRmsNoAndEvent(RmsBookingDetail rmsBookingDetail) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_FLAG_AND_STATUS_BY_RMS_NO_AND_EVENT)) {
            ps.setString(1, rmsBookingDetail.getFlag());
            ps.setString(2, rmsBookingDetail.getStatus());
            ps.setString(3, rmsBookingDetail.getRmsNo());
            ps.setString(4, rmsBookingDetail.getEvent());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_detail flag and status. rmsNo={}, event={}, flag={}, status={}", rmsBookingDetail.getRmsNo(), rmsBookingDetail.getEvent(), rmsBookingDetail.getFlag(), rmsBookingDetail.getStatus(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailForStatusAndFlag(RmsBookingDetail rmsBookingDetail) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_STATUS_AND_FLAG)) {
            ps.setString(1, rmsBookingDetail.getStatus());
            ps.setString(2, rmsBookingDetail.getFlag());
            ps.setString(3, rmsBookingDetail.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_detail status and flag. id={}, status={}, flag={}", rmsBookingDetail.getId(), rmsBookingDetail.getStatus(), rmsBookingDetail.getFlag(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailForStatusAndFlagAndReleaseDateBy(RmsBookingDetail rmsBookingDetail) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_STATUS_AND_FLAG_AND_RELEASE_DATE_BY)) {
            ps.setString(1, rmsBookingDetail.getStatus());
            ps.setString(2, rmsBookingDetail.getFlag());
            ps.setString(3, rmsBookingDetail.getReleasedBy());
            ps.setString(4, rmsBookingDetail.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_detail status, flag, and release information. id={}, status={}, flag={}, releasedBy={}", rmsBookingDetail.getId(), rmsBookingDetail.getStatus(), rmsBookingDetail.getFlag(), rmsBookingDetail.getReleasedBy(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailForReturn(RmsBookingDetail rmsBookingDetail) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_RETURN)) {
            ps.setString(1, rmsBookingDetail.getStatus());
            ps.setString(2, rmsBookingDetail.getFlag());
            ps.setString(3, rmsBookingDetail.getReturnBy());
            ps.setString(4, rmsBookingDetail.getReturnRemarks());
            ps.setString(5, rmsBookingDetail.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_detail for return. id={}, status={}, flag={}, returnBy={}", rmsBookingDetail.getId(), rmsBookingDetail.getStatus(), rmsBookingDetail.getFlag(), rmsBookingDetail.getReturnBy(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingDetailForPriority(RmsBookingDetail rmsBookingDetail) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RMS_BOOKING_DETAIL_FOR_PRIORITY)) {
            ps.setString(1, rmsBookingDetail.getPriority());
            ps.setString(2, rmsBookingDetail.getPriorityRemarks());
            ps.setString(3, rmsBookingDetail.getPriorityBy());
            ps.setString(4, rmsBookingDetail.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error updating rms_booking_detail priority. id={}, priority={}, priorityBy={}", rmsBookingDetail.getId(), rmsBookingDetail.getPriority(), rmsBookingDetail.getPriorityBy(), e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    public QueryResult deleteRmsBookingDetail(String rmsBookingDetailId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_RMS_BOOKING_DETAIL)) {
            ps.setString(1, rmsBookingDetailId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error deleting rms_booking_detail. id={}", rmsBookingDetailId, e);
            queryResult.setErrorMessage(e.getMessage());
        }
        return queryResult;
    }

    private static final String SQL_GET_RMS_BOOKING_DETAIL = "SELECT *, DATE_FORMAT(act_start_date,'%d-%M-%Y') AS actStartDate, DATE_FORMAT(event_start_date,'%d-%M-%Y') AS eventStartDate FROM rms_booking_detail WHERE id = ?";
    private static final String SQL_GET_RMS_BOOKING_DETAIL_BY_BOOKING_PKID = "SELECT *, DATE_FORMAT(act_start_date,'%d-%M-%Y') AS actStartDate, DATE_FORMAT(event_start_date,'%d-%M-%Y') AS eventStartDate FROM rms_booking_detail WHERE booking_pkid = ?";
    private static final String SQL_GET_BOOKING_ID_BY_RMS_NO_AND_EVENT = "SELECT id FROM rms_booking_detail WHERE rms_no = ? AND event = ?";
    private static final String SQL_GET_RMS_BOOKING_DETAIL_WITH_HW_GROUP_AFTER_LOADING_BY_GROUP_ID = "SELECT de.id, de.booking_pkid, de.rms_no, de.`event`, de.device, de.packages, gr.hardware_id, gr.`status`, DATE_FORMAT(gr.unloading_date,'%d-%M-%Y') AS unloadingDate, gr.group_id, gr.id AS bookingHwGroupId, hw.lc_qty, hw.pc_qty, gr.return_by, DATE_FORMAT(gr.return_date,'%d-%M-%Y') AS returnDate FROM rms_booking_detail de LEFT JOIN rms_booking_hardware_group gr ON SUBSTRING_INDEX(gr.group_id,'/',1) = de.booking_pkid LEFT JOIN rms_booking_hardware hw ON hw.pkid = SUBSTRING_INDEX(gr.group_id,'/',-1) WHERE gr.group_id = ? AND gr.item_type = 'BIB'";

    public RmsBookingDetail getRmsBookingDetail(String rmsBookingDetailId) {
        RmsBookingDetail rmsBookingDetail = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_DETAIL)) {
            ps.setString(1, rmsBookingDetailId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingDetail = new RmsBookingDetail();
                    rmsBookingDetail.setId(rs.getString("id"));
                    rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                    rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                    rmsBookingDetail.setEvent(rs.getString("event"));
                    rmsBookingDetail.setDevice(rs.getString("device"));
                    rmsBookingDetail.setPackages(rs.getString("packages"));
                    rmsBookingDetail.setEventStartDate(rs.getString("eventStartDate"));
                    rmsBookingDetail.setRmsStatus(rs.getString("rms_status"));
                    rmsBookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                    rmsBookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                    rmsBookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                    rmsBookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                    rmsBookingDetail.setEstStartDate(rs.getString("est_start_date"));
                    rmsBookingDetail.setActStartDate(rs.getString("actStartDate"));
                    rmsBookingDetail.setFolFilename(rs.getString("fol_filename"));
                    rmsBookingDetail.setTotalBooking(rs.getString("total_booking"));
                    rmsBookingDetail.setCreatedDate(rs.getString("created_date"));
                    rmsBookingDetail.setModifiedDate(rs.getString("modified_date"));
                    rmsBookingDetail.setStatus(rs.getString("status"));
                    rmsBookingDetail.setPriority(rs.getString("priority"));
                    rmsBookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                    rmsBookingDetail.setPriorityBy(rs.getString("priority_by"));
                    rmsBookingDetail.setPriorityDate(rs.getString("priority_date"));
                    rmsBookingDetail.setFlag(rs.getString("flag"));
                    rmsBookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail. id={}", rmsBookingDetailId, e);
        }
        return rmsBookingDetail;
    }

    public RmsBookingDetail getRmsBookingDetailByBookingPkid(String bookingPkid) {
        RmsBookingDetail rmsBookingDetail = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_DETAIL_BY_BOOKING_PKID)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingDetail = new RmsBookingDetail();
                    rmsBookingDetail.setId(rs.getString("id"));
                    rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                    rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                    rmsBookingDetail.setEvent(rs.getString("event"));
                    rmsBookingDetail.setDevice(rs.getString("device"));
                    rmsBookingDetail.setPackages(rs.getString("packages"));
                    rmsBookingDetail.setEventStartDate(rs.getString("eventStartDate"));
                    rmsBookingDetail.setRmsStatus(rs.getString("rms_status"));
                    rmsBookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                    rmsBookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                    rmsBookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                    rmsBookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                    rmsBookingDetail.setEstStartDate(rs.getString("est_start_date"));
                    rmsBookingDetail.setActStartDate(rs.getString("actStartDate"));
                    rmsBookingDetail.setFolFilename(rs.getString("fol_filename"));
                    rmsBookingDetail.setTotalBooking(rs.getString("total_booking"));
                    rmsBookingDetail.setCreatedDate(rs.getString("created_date"));
                    rmsBookingDetail.setModifiedDate(rs.getString("modified_date"));
                    rmsBookingDetail.setStatus(rs.getString("status"));
                    rmsBookingDetail.setPriority(rs.getString("priority"));
                    rmsBookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                    rmsBookingDetail.setPriorityBy(rs.getString("priority_by"));
                    rmsBookingDetail.setPriorityDate(rs.getString("priority_date"));
                    rmsBookingDetail.setFlag(rs.getString("flag"));
                    rmsBookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail. bookingPkid={}", bookingPkid, e);
        }
        return rmsBookingDetail;
    }

    public RmsBookingDetail getBookingIdByRmsNoAndEvent(String rmsNo, String event) {
        RmsBookingDetail rmsBookingDetail = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_BOOKING_ID_BY_RMS_NO_AND_EVENT)) {
            ps.setString(1, rmsNo);
            ps.setString(2, event);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingDetail = new RmsBookingDetail();
                    rmsBookingDetail.setId(rs.getString("id"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail id. rmsNo={}, event={}", rmsNo, event, e);
        }
        return rmsBookingDetail;
    }

    public RmsBookingDetail getRmsBookingDetailWithHwGroupAfterLoadingByGroupId(String groupId) {
        RmsBookingDetail rmsBookingDetail = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_DETAIL_WITH_HW_GROUP_AFTER_LOADING_BY_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingDetail = new RmsBookingDetail();
                    rmsBookingDetail.setId(rs.getString("id"));
                    rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                    rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                    rmsBookingDetail.setEvent(rs.getString("event"));
                    rmsBookingDetail.setDevice(rs.getString("device"));
                    rmsBookingDetail.setPackages(rs.getString("packages"));
                    rmsBookingDetail.setHardwareId(rs.getString("hardware_id"));
                    rmsBookingDetail.setHardwareGroupStatus(rs.getString("status"));
                    rmsBookingDetail.setUnloadingDate(rs.getString("unloadingDate"));
                    rmsBookingDetail.setGroupId(rs.getString("group_id"));
                    rmsBookingDetail.setBookingHwGroupId(rs.getString("bookingHwGroupId"));
                    rmsBookingDetail.setHardwareReturnBy(rs.getString("return_by"));
                    rmsBookingDetail.setHardwareReturnDate(rs.getString("returnDate"));
                    rmsBookingDetail.setLcQty(rs.getString("lc_qty"));
                    rmsBookingDetail.setPcQty(rs.getString("pc_qty"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail with hardware group after loading. groupId={}", groupId, e);
        }
        return rmsBookingDetail;
    }

    private static final String SQL_GET_RMS_BOOKING_DETAIL_LIST = "SELECT * FROM rms_booking_detail ORDER BY id ASC";
    private static final String SQL_GET_RMS_BOOKING_DETAIL_LIST_FLAG_ZERO = "SELECT *, DATE_FORMAT(act_start_date,'%d-%M-%Y') AS actStartDate, DATE_FORMAT(event_start_date,'%d-%M-%Y') AS eventStartDate FROM rms_booking_detail WHERE flag = '0' AND status = 'New' ORDER BY priority, act_start_date ASC";
    private static final String SQL_GET_RMS_BOOKING_DETAIL_LIST_RELEASED = "SELECT *, DATE_FORMAT(act_start_date,'%d-%M-%Y') AS actStartDate, DATE_FORMAT(event_start_date,'%d-%M-%Y') AS eventStartDate FROM rms_booking_detail WHERE flag = '1' AND status = 'Released to Production' ORDER BY priority, act_start_date ASC";
    private static final String SQL_GET_RMS_BOOKING_DETAIL_LIST_RELEASED_SINGLE_BIB = "SELECT de.id, de.booking_pkid, de.rms_no, de.`event`, DATE_FORMAT(ha.released_date,'%d %M %Y %h:%i %p') AS releasedDate, ha.released_by, DATE_FORMAT(gr.loading_date,'%d %M %Y %h:%i %p') AS loadingDate, ha.id AS bookingHwId, ha.item_id, ha.item_pkid, ha.lc_qty, ha.pc_qty, ha.sub_status, ha.pkid FROM rms_booking_detail de LEFT JOIN rms_booking_hardware_group gr ON SUBSTRING_INDEX(gr.group_id,'/',1) = de.booking_pkid LEFT JOIN rms_booking_hardware ha ON ha.pkid = SUBSTRING_INDEX(gr.group_id,'/',-1) WHERE ha.item_type = 'Motherboard' AND ha.sub_status = 'Released to Production' AND ha.flag = '1' AND gr.item_type = 'BIB'";
    private static final String SQL_GET_RMS_BOOKING_DETAIL_LIST_RECALL_SINGLE_BIB = "SELECT de.id, de.booking_pkid, de.rms_no, de.`event`, DATE_FORMAT(ha.return_date,'%d %M %Y %h:%i %p') AS returnDate, ha.return_by, ha.return_remarks, ha.id AS bookingHwId, ha.item_id, ha.item_pkid, ha.lc_qty, ha.pc_qty, ha.sub_status, ha.pkid FROM rms_booking_detail de LEFT JOIN rms_booking_hardware ha ON de.booking_pkid = ha.booking_pkid WHERE ha.item_type = 'Motherboard' AND ha.sub_status = 'Pending Release to Production' AND ha.flag = '0' AND de.status IN ('Released to Production','New') AND de.flag IN ('1','0')";
    private static final String SQL_GET_BOOKING_PKID_WITH_FLAG_ZERO = "SELECT booking_pkid, id FROM rms_booking_detail WHERE flag = '0'";
    private static final String SQL_GET_BOOKING_ID_RMS_AND_EVENT_WITH_FLAG_ZERO = "SELECT booking_pkid, id, rms_no, event FROM rms_booking_detail WHERE flag = '0'";
    private static final String SQL_GET_BOOKING_PKID_WITH_FLAG_99_AND_FOL_NULL = "SELECT booking_pkid, id FROM rms_booking_detail WHERE flag = '99' AND fol_filename IS NULL";
    private static final String SQL_GET_BOOKING_PKID_RMS_AND_EVENT_WITH_FLAG_99_AND_FOL_NULL = "SELECT booking_pkid, id, rms_no, event FROM rms_booking_detail WHERE flag = '99' AND fol_filename IS NULL";
    private static final String SQL_GET_RMS_BOOKING_DETAIL_LIST_WITH_HW_GROUP_AFTER_LOADING = "SELECT de.id, de.booking_pkid, hw.pkid, de.rms_no, de.`event`, de.device, de.packages, gr.hardware_id, hw.`status`, hw.sub_status, DATE_FORMAT(gr.unloading_date,'%d-%M-%Y') AS unloadingDate, gr.group_id, gr.id AS bookingHwGroupId, hw.lc_qty, hw.pc_qty, gr.return_by, DATE_FORMAT(gr.return_date,'%d-%M-%Y') AS returnDate FROM rms_booking_detail de LEFT JOIN rms_booking_hardware_group gr ON SUBSTRING_INDEX(gr.group_id,'/',1) = de.booking_pkid LEFT JOIN rms_booking_hardware hw ON hw.pkid = SUBSTRING_INDEX(gr.group_id,'/',-1) WHERE hw.item_type = 'Motherboard' AND hw.status LIKE 'Return from Production%' AND gr.flag = '2' AND gr.item_type = 'BIB'";

    public List<RmsBookingDetail> getRmsBookingDetailList() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_DETAIL_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsBookingDetail.setEvent(rs.getString("event"));
                rmsBookingDetail.setDevice(rs.getString("device"));
                rmsBookingDetail.setPackages(rs.getString("packages"));
                rmsBookingDetail.setEventStartDate(rs.getString("event_start_date"));
                rmsBookingDetail.setRmsStatus(rs.getString("rms_status"));
                rmsBookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                rmsBookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                rmsBookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                rmsBookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                rmsBookingDetail.setEstStartDate(rs.getString("est_start_date"));
                rmsBookingDetail.setActStartDate(rs.getString("act_start_date"));
                rmsBookingDetail.setFolFilename(rs.getString("fol_filename"));
                rmsBookingDetail.setTotalBooking(rs.getString("total_booking"));
                rmsBookingDetail.setCreatedDate(rs.getString("created_date"));
                rmsBookingDetail.setModifiedDate(rs.getString("modified_date"));
                rmsBookingDetail.setStatus(rs.getString("status"));
                rmsBookingDetail.setPriority(rs.getString("priority"));
                rmsBookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                rmsBookingDetail.setPriorityBy(rs.getString("priority_by"));
                rmsBookingDetail.setPriorityDate(rs.getString("priority_date"));
                rmsBookingDetail.setFlag(rs.getString("flag"));
                rmsBookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail list", e);
        }
        return rmsBookingDetailList;
    }

    public List<RmsBookingDetail> getRmsBookingDetailListFlagZero() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_DETAIL_LIST_FLAG_ZERO); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsBookingDetail.setEvent(rs.getString("event"));
                rmsBookingDetail.setDevice(rs.getString("device"));
                rmsBookingDetail.setPackages(rs.getString("packages"));
                rmsBookingDetail.setEventStartDate(rs.getString("eventStartDate"));
                rmsBookingDetail.setRmsStatus(rs.getString("rms_status"));
                rmsBookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                rmsBookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                rmsBookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                rmsBookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                rmsBookingDetail.setEstStartDate(rs.getString("est_start_date"));
                rmsBookingDetail.setActStartDate(rs.getString("actStartDate"));
                rmsBookingDetail.setFolFilename(rs.getString("fol_filename"));
                rmsBookingDetail.setTotalBooking(rs.getString("total_booking"));
                rmsBookingDetail.setCreatedDate(rs.getString("created_date"));
                rmsBookingDetail.setModifiedDate(rs.getString("modified_date"));
                rmsBookingDetail.setStatus(rs.getString("status"));
                rmsBookingDetail.setPriority(rs.getString("priority"));
                rmsBookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                rmsBookingDetail.setPriorityBy(rs.getString("priority_by"));
                rmsBookingDetail.setPriorityDate(rs.getString("priority_date"));
                rmsBookingDetail.setFlag(rs.getString("flag"));
                rmsBookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail list with flag=0 and status=New", e);
        }
        return rmsBookingDetailList;
    }

    public List<RmsBookingDetail> getRmsBookingDetailListReleased() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_DETAIL_LIST_RELEASED); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsBookingDetail.setEvent(rs.getString("event"));
                rmsBookingDetail.setDevice(rs.getString("device"));
                rmsBookingDetail.setPackages(rs.getString("packages"));
                rmsBookingDetail.setEventStartDate(rs.getString("eventStartDate"));
                rmsBookingDetail.setRmsStatus(rs.getString("rms_status"));
                rmsBookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                rmsBookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                rmsBookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                rmsBookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                rmsBookingDetail.setEstStartDate(rs.getString("est_start_date"));
                rmsBookingDetail.setActStartDate(rs.getString("actStartDate"));
                rmsBookingDetail.setFolFilename(rs.getString("fol_filename"));
                rmsBookingDetail.setTotalBooking(rs.getString("total_booking"));
                rmsBookingDetail.setCreatedDate(rs.getString("created_date"));
                rmsBookingDetail.setModifiedDate(rs.getString("modified_date"));
                rmsBookingDetail.setStatus(rs.getString("status"));
                rmsBookingDetail.setPriority(rs.getString("priority"));
                rmsBookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                rmsBookingDetail.setPriorityBy(rs.getString("priority_by"));
                rmsBookingDetail.setPriorityDate(rs.getString("priority_date"));
                rmsBookingDetail.setFlag(rs.getString("flag"));
                rmsBookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving released rms_booking_detail list", e);
        }
        return rmsBookingDetailList;
    }

    public List<RmsBookingDetail> getRmsBookingDetailListReleasedSingleBib() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_DETAIL_LIST_RELEASED_SINGLE_BIB); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsBookingDetail.setEvent(rs.getString("event"));
                rmsBookingDetail.setReleasedDate(rs.getString("releasedDate"));
                rmsBookingDetail.setReleasedBy(rs.getString("released_by"));
                rmsBookingDetail.setBookingHwId(rs.getString("bookingHwId"));
                rmsBookingDetail.setItemId(rs.getString("item_id"));
                rmsBookingDetail.setItemPkid(rs.getString("item_pkid"));
                rmsBookingDetail.setBookingHwPkid(rs.getString("pkid"));
                rmsBookingDetail.setLcQty(rs.getString("lc_qty"));
                rmsBookingDetail.setPcQty(rs.getString("pc_qty"));
                rmsBookingDetail.setStatus(rs.getString("sub_status"));
                rmsBookingDetail.setLoadingDate(rs.getString("loadingDate"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving released single BIB rms_booking_detail list", e);
        }
        return rmsBookingDetailList;
    }

    public List<RmsBookingDetail> getRmsBookingDetailListRecallSingleBib() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_DETAIL_LIST_RECALL_SINGLE_BIB); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsBookingDetail.setEvent(rs.getString("event"));
                rmsBookingDetail.setReturnDate(rs.getString("returnDate"));
                rmsBookingDetail.setReturnBy(rs.getString("return_by"));
                rmsBookingDetail.setReturnRemarks(rs.getString("return_remarks"));
                rmsBookingDetail.setBookingHwId(rs.getString("bookingHwId"));
                rmsBookingDetail.setItemId(rs.getString("item_id"));
                rmsBookingDetail.setItemPkid(rs.getString("item_pkid"));
                rmsBookingDetail.setBookingHwPkid(rs.getString("pkid"));
                rmsBookingDetail.setLcQty(rs.getString("lc_qty"));
                rmsBookingDetail.setPcQty(rs.getString("pc_qty"));
                rmsBookingDetail.setStatus(rs.getString("sub_status"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving recall single BIB rms_booking_detail list", e);
        }
        return rmsBookingDetailList;
    }

    public List<RmsBookingDetail> getBookingPkidwithFlagZero() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_BOOKING_PKID_WITH_FLAG_ZERO); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail records with flag=0", e);
        }
        return rmsBookingDetailList;
    }

    public List<RmsBookingDetail> getBookingIdRmsAndEventwithFlagZero() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_BOOKING_ID_RMS_AND_EVENT_WITH_FLAG_ZERO); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsBookingDetail.setEvent(rs.getString("event"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail bookingPkid, id, rmsNo and event records with flag=0", e);
        }
        return rmsBookingDetailList;
    }

    public List<RmsBookingDetail> getBookingPkidwithFlag99AndFolNull() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_BOOKING_PKID_WITH_FLAG_99_AND_FOL_NULL); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail records with flag=99 and null fol_filename", e);
        }
        return rmsBookingDetailList;
    }

    public List<RmsBookingDetail> getBookingPkidRmsAndEventwithFlag99AndFolNull() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_BOOKING_PKID_RMS_AND_EVENT_WITH_FLAG_99_AND_FOL_NULL); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsBookingDetail.setEvent(rs.getString("event"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail records with flag=99 and null fol_filename", e);
        }
        return rmsBookingDetailList;
    }

    public List<RmsBookingDetail> getRmsBookingDetailListWithHwGroupAfterLoading() {
        List<RmsBookingDetail> rmsBookingDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_DETAIL_LIST_WITH_HW_GROUP_AFTER_LOADING); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingDetail rmsBookingDetail = new RmsBookingDetail();
                rmsBookingDetail.setId(rs.getString("id"));
                rmsBookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsBookingDetail.setBookingHwPkid(rs.getString("pkid"));
                rmsBookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsBookingDetail.setEvent(rs.getString("event"));
                rmsBookingDetail.setDevice(rs.getString("device"));
                rmsBookingDetail.setPackages(rs.getString("packages"));
                rmsBookingDetail.setHardwareId(rs.getString("hardware_id"));
                rmsBookingDetail.setBookingHwStatus(rs.getString("status"));
                rmsBookingDetail.setBookingHwSubStatus(rs.getString("sub_status"));
                rmsBookingDetail.setUnloadingDate(rs.getString("unloadingDate"));
                rmsBookingDetail.setGroupId(rs.getString("group_id"));
                rmsBookingDetail.setBookingHwGroupId(rs.getString("bookingHwGroupId"));
                rmsBookingDetail.setHardwareReturnBy(rs.getString("return_by"));
                rmsBookingDetail.setHardwareReturnDate(rs.getString("returnDate"));
                rmsBookingDetail.setLcQty(rs.getString("lc_qty"));
                rmsBookingDetail.setPcQty(rs.getString("pc_qty"));
                rmsBookingDetailList.add(rmsBookingDetail);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving rms_booking_detail list with hardware group after loading", e);
        }
        return rmsBookingDetailList;
    }

    private static final String SQL_GET_COUNT_BOOKING_ID = "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.booking_pkid = ?";
    private static final String SQL_GET_COUNT_BOOKING_ID_FLAG_ZERO = "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.booking_pkid = ? AND inc.flag = '0'";
    private static final String SQL_GET_COUNT_BY_RMS_NO_AND_EVENT_WITH_FLAG_ZERO = "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.rms_no = ? AND inc.event = ? AND inc.flag = '0'";
    private static final String SQL_GET_COUNT_BOOKING_FLAG_ZERO = "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.flag = '0'";
    private static final String SQL_GET_COUNT_BY_RMS_AND_EVENT = "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.rms_no = ? AND inc.event = ?";
    private static final String SQL_GET_COUNT_BOOKING_RELEASED_PRODUCTION = "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.flag = '1' AND inc.status = 'Released to Production'";
    private static final String SQL_GET_COUNT_BOOKING_RECALL_BEFORE_LOADING = "SELECT COUNT(*) AS count FROM rms_booking_detail de LEFT JOIN rms_booking_hardware ha ON de.booking_pkid = ha.booking_pkid WHERE ha.item_type = 'Motherboard' AND ha.sub_status = 'Pending Release to Production' AND ha.flag = '0' AND de.status = 'Released to Production' AND de.flag = '1'";
    private static final String SQL_GET_BOOKING_ID = "SELECT booking_pkid FROM rms_booking_detail WHERE id = ?";
    private static final String SQL_GET_BOOKING_EVENT = "SELECT event FROM rms_booking_detail WHERE booking_pkid = ?";

    public Integer getCountBookingId(String bookingId) {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_ID)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting rms_booking_detail records. bookingId={}", bookingId, e);
        }
        return count;
    }

    public Integer getCountBookingIdFlagZero(String bookingId) {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_ID_FLAG_ZERO)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting rms_booking_detail records with flag=0. bookingId={}", bookingId, e);
        }
        return count;
    }

    public Integer getCountByRmsNoAndEventWithFlagZero(String rms, String event) {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BY_RMS_NO_AND_EVENT_WITH_FLAG_ZERO)) {
            ps.setString(1, rms);
            ps.setString(2, event);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting rms_booking_detail records with flag=0. rmsNo={}, event={}", rms, event, e);
        }
        return count;
    }

    public Integer getCountBookingFlagZero() {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_FLAG_ZERO); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting rms_booking_detail records with flag=0", e);
        }
        return count;
    }

    public Integer getCountByRmsAndEvent(String rms, String event) {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BY_RMS_AND_EVENT)) {
            ps.setString(1, rms);
            ps.setString(2, event);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting rms_booking_detail records. rmsNo={}, event={}", rms, event, e);
        }
        return count;
    }

    public Integer getCountBookingReleasedProduction() {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_RELEASED_PRODUCTION); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting rms_booking_detail records with flag=1 and status='Released to Production'", e);
        }
        return count;
    }

    public Integer getCountBookingRecallBeforeLoading() {
        Integer count = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BOOKING_RECALL_BEFORE_LOADING); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting recalled bookings before loading", e);
        }
        return count;
    }

    public String getBookingId(String id) {
        String bookingPkid = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_BOOKING_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bookingPkid = rs.getString("booking_pkid");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving booking_pkid from rms_booking_detail. id={}", id, e);
        }
        return bookingPkid;
    }

    public String getBookingEvent(String bookingPkid) {
        String event = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_BOOKING_EVENT)) {
            ps.setString(1, bookingPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    event = rs.getString("event");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving event from rms_booking_detail. bookingPkid={}", bookingPkid, e);
        }
        return event;
    }

}