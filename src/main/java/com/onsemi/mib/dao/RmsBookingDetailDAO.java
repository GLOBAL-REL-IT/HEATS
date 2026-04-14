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
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingDetailDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRmsBookingDetail(RmsBookingDetail rmsbookingDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_detail (booking_pkid, rms_no, event, device, packages, event_start_date, rms_status, event_begin_status, event_end_status, no_current_ftp, equipment_location, est_start_date, act_start_date, fol_filename, total_booking, created_date, status, flag, priority, days_to_event_start) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rmsbookingDetail.getBookingPkid());
            ps.setString(2, rmsbookingDetail.getRmsNo());
            ps.setString(3, rmsbookingDetail.getEvent());
            ps.setString(4, rmsbookingDetail.getDevice());
            ps.setString(5, rmsbookingDetail.getPackages());
            ps.setString(6, rmsbookingDetail.getEventStartDate());
            ps.setString(7, rmsbookingDetail.getRmsStatus());
            ps.setString(8, rmsbookingDetail.getEventBeginStatus());
            ps.setString(9, rmsbookingDetail.getEventEndStatus());
            ps.setString(10, rmsbookingDetail.getNoCurrentFtp());
            ps.setString(11, rmsbookingDetail.getEquipmentLocation());
            ps.setString(12, rmsbookingDetail.getEstStartDate());
            ps.setString(13, rmsbookingDetail.getActStartDate());
            ps.setString(14, rmsbookingDetail.getFolFilename());
            ps.setString(15, rmsbookingDetail.getTotalBooking());
            ps.setString(16, rmsbookingDetail.getStatus());
            ps.setString(17, rmsbookingDetail.getFlag());
            ps.setString(18, rmsbookingDetail.getPriority());
            ps.setString(19, rmsbookingDetail.getDaysToEventStart());
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

    public QueryResult updateRmsBookingDetail(RmsBookingDetail rmsbookingDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_detail SET booking_pkid = ?, rms_no = ?, event = ?, device = ?, packages = ?, event_start_date = ?, rms_status = ?, event_begin_status = ?, event_end_status = ?, no_current_ftp = ?, equipment_location = ?, est_start_date = ?, act_start_date = ?, fol_filename = ?, total_booking = ?, created_date = ?, modified_date = ?, status = ?, priority = ?, priority_remarks = ?, priority_by = ?, priority_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingDetail.getBookingPkid());
            ps.setString(2, rmsbookingDetail.getRmsNo());
            ps.setString(3, rmsbookingDetail.getEvent());
            ps.setString(4, rmsbookingDetail.getDevice());
            ps.setString(5, rmsbookingDetail.getPackages());
            ps.setString(6, rmsbookingDetail.getEventStartDate());
            ps.setString(7, rmsbookingDetail.getRmsStatus());
            ps.setString(8, rmsbookingDetail.getEventBeginStatus());
            ps.setString(9, rmsbookingDetail.getEventEndStatus());
            ps.setString(10, rmsbookingDetail.getNoCurrentFtp());
            ps.setString(11, rmsbookingDetail.getEquipmentLocation());
            ps.setString(12, rmsbookingDetail.getEstStartDate());
            ps.setString(13, rmsbookingDetail.getActStartDate());
            ps.setString(14, rmsbookingDetail.getFolFilename());
            ps.setString(15, rmsbookingDetail.getTotalBooking());
            ps.setString(16, rmsbookingDetail.getCreatedDate());
            ps.setString(17, rmsbookingDetail.getModifiedDate());
            ps.setString(18, rmsbookingDetail.getStatus());
            ps.setString(19, rmsbookingDetail.getPriority());
            ps.setString(20, rmsbookingDetail.getPriorityRemarks());
            ps.setString(21, rmsbookingDetail.getPriorityBy());
            ps.setString(22, rmsbookingDetail.getPriorityDate());
            ps.setString(23, rmsbookingDetail.getFlag());
            ps.setString(24, rmsbookingDetail.getId());
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

    public QueryResult updateRmsBookingDetailFromCBMSByPkid(RmsBookingDetail rmsbookingDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_detail SET booking_pkid = ?, rms_no = ?, event = ?, device = ?, packages = ?, event_start_date = ?, rms_status = ?, event_begin_status = ?, event_end_status = ?, no_current_ftp = ?, equipment_location = ?, est_start_date = ?, act_start_date = ?, fol_filename = ?, total_booking = ?, modified_date = NOW(), days_to_event_start = ? WHERE booking_pkid = ?"
            );
            ps.setString(1, rmsbookingDetail.getBookingPkid());
            ps.setString(2, rmsbookingDetail.getRmsNo());
            ps.setString(3, rmsbookingDetail.getEvent());
            ps.setString(4, rmsbookingDetail.getDevice());
            ps.setString(5, rmsbookingDetail.getPackages());
            ps.setString(6, rmsbookingDetail.getEventStartDate());
            ps.setString(7, rmsbookingDetail.getRmsStatus());
            ps.setString(8, rmsbookingDetail.getEventBeginStatus());
            ps.setString(9, rmsbookingDetail.getEventEndStatus());
            ps.setString(10, rmsbookingDetail.getNoCurrentFtp());
            ps.setString(11, rmsbookingDetail.getEquipmentLocation());
            ps.setString(12, rmsbookingDetail.getEstStartDate());
            ps.setString(13, rmsbookingDetail.getActStartDate());
            ps.setString(14, rmsbookingDetail.getFolFilename());
            ps.setString(15, rmsbookingDetail.getTotalBooking());
            ps.setString(16, rmsbookingDetail.getDaysToEventStart());
            ps.setString(17, rmsbookingDetail.getBookingPkid());
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

    public QueryResult updateRmsBookingDetailForFlagAndStatus(RmsBookingDetail rmsbookingDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_detail SET flag = ?, modified_date = NOW(), status = ? WHERE booking_pkid = ?"
            );
            ps.setString(1, rmsbookingDetail.getFlag());
            ps.setString(2, rmsbookingDetail.getStatus());
            ps.setString(3, rmsbookingDetail.getBookingPkid());
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

    public QueryResult updateRmsBookingDetailForStatusAndFlag(RmsBookingDetail rmsbookingDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_detail SET modified_date = NOW(), status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingDetail.getStatus());
            ps.setString(2, rmsbookingDetail.getFlag());
            ps.setString(3, rmsbookingDetail.getId());
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

    public QueryResult updateRmsBookingDetailForReturn(RmsBookingDetail rmsbookingDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_detail SET return_date = NOW(), status = ?, flag = ?, return_by = ?, return_remarks = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingDetail.getStatus());
            ps.setString(2, rmsbookingDetail.getFlag());
            ps.setString(3, rmsbookingDetail.getReturnBy());
            ps.setString(4, rmsbookingDetail.getReturnRemarks());
            ps.setString(5, rmsbookingDetail.getId());
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

    public QueryResult updateRmsBookingDetailForPriority(RmsBookingDetail rmsbookingDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_detail SET priority = ?, priority_date = NOW(), priority_remarks = ?, priority_by = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingDetail.getPriority());
            ps.setString(2, rmsbookingDetail.getPriorityRemarks());
            ps.setString(3, rmsbookingDetail.getPriorityBy());
            ps.setString(4, rmsbookingDetail.getId());
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

    public QueryResult deleteRmsBookingDetail(String rmsbookingDetailId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_detail WHERE id = '" + rmsbookingDetailId + "'"
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

    public RmsBookingDetail getRmsBookingDetail(String rmsbookingDetailId) {
        String sql = "SELECT *,DATE_FORMAT(act_start_date,'%d-%M-%Y') AS actStartDate, DATE_FORMAT(event_start_date,'%d-%M-%Y') AS eventStartDate FROM rms_booking_detail WHERE id = '" + rmsbookingDetailId + "'";
        RmsBookingDetail rmsbookingDetail = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingDetail = new RmsBookingDetail();
                rmsbookingDetail.setId(rs.getString("id"));
                rmsbookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsbookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsbookingDetail.setEvent(rs.getString("event"));
                rmsbookingDetail.setDevice(rs.getString("device"));
                rmsbookingDetail.setPackages(rs.getString("packages"));
                rmsbookingDetail.setEventStartDate(rs.getString("eventStartDate"));
                rmsbookingDetail.setRmsStatus(rs.getString("rms_status"));
                rmsbookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                rmsbookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                rmsbookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                rmsbookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                rmsbookingDetail.setEstStartDate(rs.getString("est_start_date"));
                rmsbookingDetail.setActStartDate(rs.getString("actStartDate"));
                rmsbookingDetail.setFolFilename(rs.getString("fol_filename"));
                rmsbookingDetail.setTotalBooking(rs.getString("total_booking"));
                rmsbookingDetail.setCreatedDate(rs.getString("created_date"));
                rmsbookingDetail.setModifiedDate(rs.getString("modified_date"));
                rmsbookingDetail.setStatus(rs.getString("status"));
                rmsbookingDetail.setPriority(rs.getString("priority"));
                rmsbookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                rmsbookingDetail.setPriorityBy(rs.getString("priority_by"));
                rmsbookingDetail.setPriorityDate(rs.getString("priority_date"));
                rmsbookingDetail.setFlag(rs.getString("flag"));
                rmsbookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
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
        return rmsbookingDetail;
    }

    public RmsBookingDetail getRmsBookingDetailByBookingPkid(String bookingPkid) {
        String sql = "SELECT *,DATE_FORMAT(act_start_date,'%d-%M-%Y') AS actStartDate, DATE_FORMAT(event_start_date,'%d-%M-%Y') AS eventStartDate FROM rms_booking_detail WHERE booking_pkid = '" + bookingPkid + "'";
        RmsBookingDetail rmsbookingDetail = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingDetail = new RmsBookingDetail();
                rmsbookingDetail.setId(rs.getString("id"));
                rmsbookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsbookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsbookingDetail.setEvent(rs.getString("event"));
                rmsbookingDetail.setDevice(rs.getString("device"));
                rmsbookingDetail.setPackages(rs.getString("packages"));
                rmsbookingDetail.setEventStartDate(rs.getString("eventStartDate"));
                rmsbookingDetail.setRmsStatus(rs.getString("rms_status"));
                rmsbookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                rmsbookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                rmsbookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                rmsbookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                rmsbookingDetail.setEstStartDate(rs.getString("est_start_date"));
                rmsbookingDetail.setActStartDate(rs.getString("actStartDate"));
                rmsbookingDetail.setFolFilename(rs.getString("fol_filename"));
                rmsbookingDetail.setTotalBooking(rs.getString("total_booking"));
                rmsbookingDetail.setCreatedDate(rs.getString("created_date"));
                rmsbookingDetail.setModifiedDate(rs.getString("modified_date"));
                rmsbookingDetail.setStatus(rs.getString("status"));
                rmsbookingDetail.setPriority(rs.getString("priority"));
                rmsbookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                rmsbookingDetail.setPriorityBy(rs.getString("priority_by"));
                rmsbookingDetail.setPriorityDate(rs.getString("priority_date"));
                rmsbookingDetail.setFlag(rs.getString("flag"));
                rmsbookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
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
        return rmsbookingDetail;
    }

    public List<RmsBookingDetail> getRmsBookingDetailList() {
        String sql = "SELECT * FROM rms_booking_detail ORDER BY id ASC";
        List<RmsBookingDetail> rmsbookingDetailList = new ArrayList<RmsBookingDetail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingDetail rmsbookingDetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingDetail = new RmsBookingDetail();
                rmsbookingDetail.setId(rs.getString("id"));
                rmsbookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsbookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsbookingDetail.setEvent(rs.getString("event"));
                rmsbookingDetail.setDevice(rs.getString("device"));
                rmsbookingDetail.setPackages(rs.getString("packages"));
                rmsbookingDetail.setEventStartDate(rs.getString("event_start_date"));
                rmsbookingDetail.setRmsStatus(rs.getString("rms_status"));
                rmsbookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                rmsbookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                rmsbookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                rmsbookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                rmsbookingDetail.setEstStartDate(rs.getString("est_start_date"));
                rmsbookingDetail.setActStartDate(rs.getString("act_start_date"));
                rmsbookingDetail.setFolFilename(rs.getString("fol_filename"));
                rmsbookingDetail.setTotalBooking(rs.getString("total_booking"));
                rmsbookingDetail.setCreatedDate(rs.getString("created_date"));
                rmsbookingDetail.setModifiedDate(rs.getString("modified_date"));
                rmsbookingDetail.setStatus(rs.getString("status"));
                rmsbookingDetail.setPriority(rs.getString("priority"));
                rmsbookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                rmsbookingDetail.setPriorityBy(rs.getString("priority_by"));
                rmsbookingDetail.setPriorityDate(rs.getString("priority_date"));
                rmsbookingDetail.setFlag(rs.getString("flag"));
                rmsbookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
                rmsbookingDetailList.add(rmsbookingDetail);
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
        return rmsbookingDetailList;
    }

    public List<RmsBookingDetail> getRmsBookingDetailListFlagZero() {
        String sql = "SELECT *, DATE_FORMAT(act_start_date,'%d-%M-%Y') AS actStartDate, DATE_FORMAT(event_start_date,'%d-%M-%Y') AS eventStartDate FROM rms_booking_detail WHERE flag = '0' AND status = 'New' ORDER BY priority, act_start_date ASC";
        List<RmsBookingDetail> rmsbookingDetailList = new ArrayList<RmsBookingDetail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingDetail rmsbookingDetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingDetail = new RmsBookingDetail();
                rmsbookingDetail.setId(rs.getString("id"));
                rmsbookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsbookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsbookingDetail.setEvent(rs.getString("event"));
                rmsbookingDetail.setDevice(rs.getString("device"));
                rmsbookingDetail.setPackages(rs.getString("packages"));
                rmsbookingDetail.setEventStartDate(rs.getString("eventStartDate"));
                rmsbookingDetail.setRmsStatus(rs.getString("rms_status"));
                rmsbookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                rmsbookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                rmsbookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                rmsbookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                rmsbookingDetail.setEstStartDate(rs.getString("est_start_date"));
                rmsbookingDetail.setActStartDate(rs.getString("actStartDate"));
                rmsbookingDetail.setFolFilename(rs.getString("fol_filename"));
                rmsbookingDetail.setTotalBooking(rs.getString("total_booking"));
                rmsbookingDetail.setCreatedDate(rs.getString("created_date"));
                rmsbookingDetail.setModifiedDate(rs.getString("modified_date"));
                rmsbookingDetail.setStatus(rs.getString("status"));
                rmsbookingDetail.setPriority(rs.getString("priority"));
                rmsbookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                rmsbookingDetail.setPriorityBy(rs.getString("priority_by"));
                rmsbookingDetail.setPriorityDate(rs.getString("priority_date"));
                rmsbookingDetail.setFlag(rs.getString("flag"));
                rmsbookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
                rmsbookingDetailList.add(rmsbookingDetail);
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
        return rmsbookingDetailList;
    }

    public List<RmsBookingDetail> getRmsBookingDetailListReleased() {
        String sql = "SELECT *, DATE_FORMAT(act_start_date,'%d-%M-%Y') AS actStartDate, DATE_FORMAT(event_start_date,'%d-%M-%Y') AS eventStartDate FROM rms_booking_detail WHERE flag = '1' AND status = 'Released to Production' ORDER BY priority, act_start_date ASC";
        List<RmsBookingDetail> rmsbookingDetailList = new ArrayList<RmsBookingDetail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingDetail rmsbookingDetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingDetail = new RmsBookingDetail();
                rmsbookingDetail.setId(rs.getString("id"));
                rmsbookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsbookingDetail.setRmsNo(rs.getString("rms_no"));
                rmsbookingDetail.setEvent(rs.getString("event"));
                rmsbookingDetail.setDevice(rs.getString("device"));
                rmsbookingDetail.setPackages(rs.getString("packages"));
                rmsbookingDetail.setEventStartDate(rs.getString("eventStartDate"));
                rmsbookingDetail.setRmsStatus(rs.getString("rms_status"));
                rmsbookingDetail.setEventBeginStatus(rs.getString("event_begin_status"));
                rmsbookingDetail.setEventEndStatus(rs.getString("event_end_status"));
                rmsbookingDetail.setNoCurrentFtp(rs.getString("no_current_ftp"));
                rmsbookingDetail.setEquipmentLocation(rs.getString("equipment_location"));
                rmsbookingDetail.setEstStartDate(rs.getString("est_start_date"));
                rmsbookingDetail.setActStartDate(rs.getString("actStartDate"));
                rmsbookingDetail.setFolFilename(rs.getString("fol_filename"));
                rmsbookingDetail.setTotalBooking(rs.getString("total_booking"));
                rmsbookingDetail.setCreatedDate(rs.getString("created_date"));
                rmsbookingDetail.setModifiedDate(rs.getString("modified_date"));
                rmsbookingDetail.setStatus(rs.getString("status"));
                rmsbookingDetail.setPriority(rs.getString("priority"));
                rmsbookingDetail.setPriorityRemarks(rs.getString("priority_remarks"));
                rmsbookingDetail.setPriorityBy(rs.getString("priority_by"));
                rmsbookingDetail.setPriorityDate(rs.getString("priority_date"));
                rmsbookingDetail.setFlag(rs.getString("flag"));
                rmsbookingDetail.setDaysToEventStart(rs.getString("days_to_event_start"));
                rmsbookingDetailList.add(rmsbookingDetail);
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
        return rmsbookingDetailList;
    }

    public Integer getCountBookingId(String bookingId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.booking_pkid = '" + bookingId + "'"
            );
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

    public Integer getCountBookingIdFlagZero(String bookingId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.booking_pkid = '" + bookingId + "' AND inc.flag = '0'"
            );
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

    public Integer getCountBookingFlagZero() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.flag = '0'"
            );
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

    public Integer getCountBookingReleasedProduction() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_detail inc WHERE inc.flag = '0' And status = 'Released to Production' "
            );
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

    public List<RmsBookingDetail> getBookingPkidwithFlagZero() {
        String sql = "SELECT booking_pkid, id FROM rms_booking_detail WHERE flag = '0'";
        List<RmsBookingDetail> rmsbookingDetailList = new ArrayList<RmsBookingDetail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingDetail rmsbookingDetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingDetail = new RmsBookingDetail();
                rmsbookingDetail.setBookingPkid(rs.getString("booking_pkid"));
                rmsbookingDetail.setId(rs.getString("id"));
                rmsbookingDetailList.add(rmsbookingDetail);
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
        return rmsbookingDetailList;
    }
}
