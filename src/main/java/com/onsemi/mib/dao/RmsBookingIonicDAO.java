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
import com.onsemi.mib.model.RmsBookingIonic;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingIonicDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingIonicDAO.class);
    private final DataSource dataSource;

    public RmsBookingIonicDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    private static final String INSERT_RMS_BOOKING_IONIC = "INSERT INTO rms_booking_ionic (group_id, module, bib_result, bib_status, bib_upload, bib_card_result, bib_card_status, bib_card_upload, status, flag, created_by, created_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
    private static final String UPDATE_RMS_BOOKING_IONIC = "UPDATE rms_booking_ionic SET group_id = ?, module = ?, bib_result = ?, bib_status = ?, bib_upload = ?, bib_card_result = ?, bib_card_status = ?, bib_card_upload = ?, status = ?, flag = ?, created_by = ?, created_date = ? WHERE id = ?";
    private static final String UPDATE_RMS_BOOKING_IONIC_FOR_UPLOAD_FILE = "UPDATE rms_booking_ionic SET bib_upload = ?, bib_card_upload = ? WHERE id = ?";
    private static final String DELETE_RMS_BOOKING_IONIC = "DELETE FROM rms_booking_ionic WHERE id = ?";

    public QueryResult insertRmsBookingIonic(RmsBookingIonic rmsBookingIonic) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_RMS_BOOKING_IONIC, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rmsBookingIonic.getGroupId());
            ps.setString(2, rmsBookingIonic.getModule());
            ps.setString(3, rmsBookingIonic.getBibResult());
            ps.setString(4, rmsBookingIonic.getBibStatus());
            ps.setString(5, rmsBookingIonic.getBibUpload());
            ps.setString(6, rmsBookingIonic.getBibCardResult());
            ps.setString(7, rmsBookingIonic.getBibCardStatus());
            ps.setString(8, rmsBookingIonic.getBibCardUpload());
            ps.setString(9, rmsBookingIonic.getStatus());
            ps.setString(10, rmsBookingIonic.getFlag());
            ps.setString(11, rmsBookingIonic.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(
                            String.valueOf(rs.getInt(1))
                    );
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting RMS booking ionic. GroupId: {}, Module: {}, Status: {}, Error: {}", rmsBookingIonic.getGroupId(), rmsBookingIonic.getModule(), rmsBookingIonic.getStatus(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingIonic(RmsBookingIonic rmsBookingIonic) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_RMS_BOOKING_IONIC)) {
            ps.setString(1, rmsBookingIonic.getGroupId());
            ps.setString(2, rmsBookingIonic.getModule());
            ps.setString(3, rmsBookingIonic.getBibResult());
            ps.setString(4, rmsBookingIonic.getBibStatus());
            ps.setString(5, rmsBookingIonic.getBibUpload());
            ps.setString(6, rmsBookingIonic.getBibCardResult());
            ps.setString(7, rmsBookingIonic.getBibCardStatus());
            ps.setString(8, rmsBookingIonic.getBibCardUpload());
            ps.setString(9, rmsBookingIonic.getStatus());
            ps.setString(10, rmsBookingIonic.getFlag());
            ps.setString(11, rmsBookingIonic.getCreatedBy());
            ps.setString(12, rmsBookingIonic.getCreatedDate());
            ps.setString(13, rmsBookingIonic.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating RMS booking ionic. Id: {}, GroupId: {}, Module: {}, Status: {}, Error: {}", rmsBookingIonic.getId(), rmsBookingIonic.getGroupId(), rmsBookingIonic.getModule(), rmsBookingIonic.getStatus(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult updateRmsBookingIonicForUploadFile(RmsBookingIonic rmsBookingIonic) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_RMS_BOOKING_IONIC_FOR_UPLOAD_FILE)) {
            ps.setString(1, rmsBookingIonic.getBibUpload());
            ps.setString(2, rmsBookingIonic.getBibCardUpload());
            ps.setString(3, rmsBookingIonic.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating RMS booking ionic upload files. Id: {}, BibUpload: {}, BibCardUpload: {}, Error: {}", rmsBookingIonic.getId(), rmsBookingIonic.getBibUpload(), rmsBookingIonic.getBibCardUpload(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult deleteRmsBookingIonic(String rmsBookingIonicId) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_RMS_BOOKING_IONIC)) {
            ps.setString(1, rmsBookingIonicId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting RMS booking ionic. Id: {}, Error: {}", rmsBookingIonicId, e.getMessage(), e);
        }
        return queryResult;
    }

    private static final String GET_RMS_BOOKING_IONIC = "SELECT id, group_id, module, bib_result, bib_status, bib_upload, bib_card_result, bib_card_status, bib_card_upload, status, flag, created_by, created_date FROM rms_booking_ionic WHERE id = ?";
    private static final String GET_RMS_BOOKING_IONIC_BY_GROUP_ID = "SELECT id, group_id, module, bib_result, bib_status, bib_upload, bib_card_result, bib_card_status, bib_card_upload, status, flag, created_by, created_date FROM rms_booking_ionic WHERE group_id = ?";
    private static final String GET_RMS_BOOKING_IONIC_BY_GROUP_ID_AND_MODULE = "SELECT id, group_id, module, bib_result, bib_status, bib_upload, bib_card_result, bib_card_status, bib_card_upload, status, flag, created_by, created_date FROM rms_booking_ionic WHERE group_id = ? AND module = ?";
    private static final String GET_RMS_BOOKING_IONIC_LIST = "SELECT id, group_id, module, bib_result, bib_status, bib_upload, bib_card_result, bib_card_status, bib_card_upload, status, flag, created_by, created_date FROM rms_booking_ionic ORDER BY id ASC";
    private static final String COUNT_BY_GROUP_ID_AND_MODULE = "SELECT COUNT(*) AS count FROM rms_booking_ionic inc WHERE inc.group_id = ? AND inc.module = ?";
    private static final String COUNT_BOOKING_ID_FROM_GROUP_ID_AND_MODULE = "SELECT COUNT(ion.id) AS count FROM rms_booking_ionic ion WHERE SUBSTRING_INDEX(ion.group_id, '/', 1) = ? AND ion.module = ?";

    public RmsBookingIonic getRmsBookingIonic(String rmsBookingIonicId) {
        RmsBookingIonic rmsBookingIonic = null;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_IONIC)) {
            ps.setString(1, rmsBookingIonicId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingIonic = new RmsBookingIonic();
                    rmsBookingIonic.setId(rs.getString("id"));
                    rmsBookingIonic.setGroupId(rs.getString("group_id"));
                    rmsBookingIonic.setModule(rs.getString("module"));
                    rmsBookingIonic.setBibResult(rs.getString("bib_result"));
                    rmsBookingIonic.setBibStatus(rs.getString("bib_status"));
                    rmsBookingIonic.setBibUpload(rs.getString("bib_upload"));
                    rmsBookingIonic.setBibCardResult(rs.getString("bib_card_result"));
                    rmsBookingIonic.setBibCardStatus(rs.getString("bib_card_status"));
                    rmsBookingIonic.setBibCardUpload(rs.getString("bib_card_upload"));
                    rmsBookingIonic.setStatus(rs.getString("status"));
                    rmsBookingIonic.setFlag(rs.getString("flag"));
                    rmsBookingIonic.setCreatedBy(rs.getString("created_by"));
                    rmsBookingIonic.setCreatedDate(rs.getString("created_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking ionic. Id: {}, Error: {}", rmsBookingIonicId, e.getMessage(), e);
        }
        return rmsBookingIonic;
    }

    public RmsBookingIonic getRmsBookingIonicbyGroupId(String groupId) {
        RmsBookingIonic rmsBookingIonic = null;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_IONIC_BY_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingIonic = new RmsBookingIonic();
                    rmsBookingIonic.setId(rs.getString("id"));
                    rmsBookingIonic.setGroupId(rs.getString("group_id"));
                    rmsBookingIonic.setModule(rs.getString("module"));
                    rmsBookingIonic.setBibResult(rs.getString("bib_result"));
                    rmsBookingIonic.setBibStatus(rs.getString("bib_status"));
                    rmsBookingIonic.setBibUpload(rs.getString("bib_upload"));
                    rmsBookingIonic.setBibCardResult(rs.getString("bib_card_result"));
                    rmsBookingIonic.setBibCardStatus(rs.getString("bib_card_status"));
                    rmsBookingIonic.setBibCardUpload(rs.getString("bib_card_upload"));
                    rmsBookingIonic.setStatus(rs.getString("status"));
                    rmsBookingIonic.setFlag(rs.getString("flag"));
                    rmsBookingIonic.setCreatedBy(rs.getString("created_by"));
                    rmsBookingIonic.setCreatedDate(rs.getString("created_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking ionic by group ID. GroupId: {}, Error: {}", groupId, e.getMessage(), e);
        }
        return rmsBookingIonic;
    }

    public RmsBookingIonic getRmsBookingIonicbyGroupIdAndModule(String groupId, String module) {
        RmsBookingIonic rmsBookingIonic = null;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_IONIC_BY_GROUP_ID_AND_MODULE)) {
            ps.setString(1, groupId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rmsBookingIonic = new RmsBookingIonic();
                    rmsBookingIonic.setId(rs.getString("id"));
                    rmsBookingIonic.setGroupId(rs.getString("group_id"));
                    rmsBookingIonic.setModule(rs.getString("module"));
                    rmsBookingIonic.setBibResult(rs.getString("bib_result"));
                    rmsBookingIonic.setBibStatus(rs.getString("bib_status"));
                    rmsBookingIonic.setBibUpload(rs.getString("bib_upload"));
                    rmsBookingIonic.setBibCardResult(rs.getString("bib_card_result"));
                    rmsBookingIonic.setBibCardStatus(rs.getString("bib_card_status"));
                    rmsBookingIonic.setBibCardUpload(rs.getString("bib_card_upload"));
                    rmsBookingIonic.setStatus(rs.getString("status"));
                    rmsBookingIonic.setFlag(rs.getString("flag"));
                    rmsBookingIonic.setCreatedBy(rs.getString("created_by"));
                    rmsBookingIonic.setCreatedDate(rs.getString("created_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking ionic by group ID and module. GroupId: {}, Module: {}, Error: {}", groupId, module, e.getMessage(), e );
        }
        return rmsBookingIonic;
    }

    public List<RmsBookingIonic> getRmsBookingIonicList() {
        List<RmsBookingIonic> rmsBookingIonicList = new ArrayList<>();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_RMS_BOOKING_IONIC_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingIonic rmsBookingIonic = new RmsBookingIonic();
                rmsBookingIonic.setId(rs.getString("id"));
                rmsBookingIonic.setGroupId(rs.getString("group_id"));
                rmsBookingIonic.setModule(rs.getString("module"));
                rmsBookingIonic.setBibResult(rs.getString("bib_result"));
                rmsBookingIonic.setBibStatus(rs.getString("bib_status"));
                rmsBookingIonic.setBibUpload(rs.getString("bib_upload"));
                rmsBookingIonic.setBibCardResult(rs.getString("bib_card_result"));
                rmsBookingIonic.setBibCardStatus(rs.getString("bib_card_status"));
                rmsBookingIonic.setBibCardUpload(rs.getString("bib_card_upload"));
                rmsBookingIonic.setStatus(rs.getString("status"));
                rmsBookingIonic.setFlag(rs.getString("flag"));
                rmsBookingIonic.setCreatedBy(rs.getString("created_by"));
                rmsBookingIonic.setCreatedDate(rs.getString("created_date"));
                rmsBookingIonicList.add(rmsBookingIonic);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving RMS booking ionic list. Error: {}", e.getMessage(), e);
        }
        return rmsBookingIonicList;
    }

    public Integer getCountByGroupIdAndModule(String groupId, String module) {
        Integer count = 0;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(COUNT_BY_GROUP_ID_AND_MODULE)) {
            ps.setString(1, groupId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting RMS booking ionic count. GroupId: {}, Module: {}, Error: {}", groupId, module, e.getMessage(), e);
        }
        return count;
    }

    public Integer getCountBookingIdFromGroupIdAndModule(String bookingId, String module) {
        Integer count = 0;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(COUNT_BOOKING_ID_FROM_GROUP_ID_AND_MODULE)) {
            ps.setString(1, bookingId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting RMS booking ionic count by booking ID and module. BookingId: {}, Module: {}, Error: {}", bookingId, module, e.getMessage(), e);
        }
        return count;
    }

}