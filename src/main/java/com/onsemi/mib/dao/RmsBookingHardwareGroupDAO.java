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
import com.onsemi.mib.model.RmsBookingHardwareGroup;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingHardwareGroupDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingHardwareGroupDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingHardwareGroupDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private String getGeneratedKey(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getString(1);
            }
            throw new SQLException("No generated key returned.");
        }
    }

    private static final String SQL_INSERT_RMS_BOOKING_HARDWARE_GROUP = "INSERT INTO rms_booking_hardware_group (group_id, item_pkid, item_id, hardware_pkid, hardware_id, rms_no, event, spts_status, status, created_by, created_date, flag, item_type) VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,?)";

    public QueryResult insertRmsBookingHardwareGroup(RmsBookingHardwareGroup rmsbookingHardwareGroup) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RMS_BOOKING_HARDWARE_GROUP, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rmsbookingHardwareGroup.getGroupId());
            ps.setString(2, rmsbookingHardwareGroup.getItemPkid());
            ps.setString(3, rmsbookingHardwareGroup.getItemId());
            ps.setString(4, rmsbookingHardwareGroup.getHardwarePkid());
            ps.setString(5, rmsbookingHardwareGroup.getHardwareId());
            ps.setString(6, rmsbookingHardwareGroup.getRmsNo());
            ps.setString(7, rmsbookingHardwareGroup.getEvent());
            ps.setString(8, rmsbookingHardwareGroup.getSptsStatus());
            ps.setString(9, rmsbookingHardwareGroup.getStatus());
            ps.setString(10, rmsbookingHardwareGroup.getCreatedBy());
            ps.setString(11, rmsbookingHardwareGroup.getFlag());
            ps.setString(12, rmsbookingHardwareGroup.getItemType());
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    private static final String SQL_UPDATE_BOOKING_HARDWARE_GROUP = "UPDATE rms_booking_hardware_group SET group_id = ?, item_pkid = ?, item_id = ?, hardware_pkid = ?, hardware_id = ?, rms_no = ?, event = ?, spts_status = ?, status = ?, created_by = ?, created_date = ?, flag = ?, item_type = ? WHERE id = ?";
    private static final String SQL_UPDATE_BOOKING_HARDWARE_GROUP_STATUS_AND_FLAG = "UPDATE rms_booking_hardware_group SET status = ?, flag = ? WHERE id = ?";
    private static final String SQL_UPDATE_BOOKING_HARDWARE_GROUP_SPTS_STATUS_AND_FLAG = "UPDATE rms_booking_hardware_group SET spts_status = ?, status = ?, flag = ? WHERE id = ?";
    private static final String SQL_UPDATE_BOOKING_HARDWARE_GROUP_RETURN = "UPDATE rms_booking_hardware_group SET return_by = ?, return_date = NOW() WHERE id = ?";
    private static final String SQL_UPDATE_BOOKING_HARDWARE_GROUP_RETURN_BY_GROUP = "UPDATE rms_booking_hardware_group SET return_by = ?, return_date = NOW() WHERE group_id = ?";
    private static final String SQL_UPDATE_BOOKING_HARDWARE_GROUP_FLAG_AND_STATUS = "UPDATE rms_booking_hardware_group SET flag = ?, status = ? WHERE group_id = ?";
    private static final String SQL_DELETE_BOOKING_HARDWARE_GROUP = "DELETE FROM rms_booking_hardware_group WHERE id = ?";

    public QueryResult updateRmsBookingHardwareGroup(RmsBookingHardwareGroup rmsbookingHardwareGroup) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BOOKING_HARDWARE_GROUP)) {
            ps.setString(1, rmsbookingHardwareGroup.getGroupId());
            ps.setString(2, rmsbookingHardwareGroup.getItemPkid());
            ps.setString(3, rmsbookingHardwareGroup.getItemId());
            ps.setString(4, rmsbookingHardwareGroup.getHardwarePkid());
            ps.setString(5, rmsbookingHardwareGroup.getHardwareId());
            ps.setString(6, rmsbookingHardwareGroup.getRmsNo());
            ps.setString(7, rmsbookingHardwareGroup.getEvent());
            ps.setString(8, rmsbookingHardwareGroup.getSptsStatus());
            ps.setString(9, rmsbookingHardwareGroup.getStatus());
            ps.setString(10, rmsbookingHardwareGroup.getCreatedBy());
            ps.setString(11, rmsbookingHardwareGroup.getCreatedDate());
            ps.setString(12, rmsbookingHardwareGroup.getFlag());
            ps.setString(13, rmsbookingHardwareGroup.getItemType());
            ps.setString(14, rmsbookingHardwareGroup.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateRmsBookingHardwareGroupStatusAndFlag(RmsBookingHardwareGroup rmsbookingHardwareGroup) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BOOKING_HARDWARE_GROUP_STATUS_AND_FLAG)) {
            ps.setString(1, rmsbookingHardwareGroup.getStatus());
            ps.setString(2, rmsbookingHardwareGroup.getFlag());
            ps.setString(3, rmsbookingHardwareGroup.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateRmsBookingHardwareGroupStatusAndSptsStatusAndFlag(RmsBookingHardwareGroup rmsbookingHardwareGroup) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BOOKING_HARDWARE_GROUP_SPTS_STATUS_AND_FLAG)) {
            ps.setString(1, rmsbookingHardwareGroup.getSptsStatus());
            ps.setString(2, rmsbookingHardwareGroup.getStatus());
            ps.setString(3, rmsbookingHardwareGroup.getFlag());
            ps.setString(4, rmsbookingHardwareGroup.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateRmsBookingHardwareGroupReturnByAndReturnDate(RmsBookingHardwareGroup rmsbookingHardwareGroup) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BOOKING_HARDWARE_GROUP_RETURN)) {
            ps.setString(1, rmsbookingHardwareGroup.getReturnBy());
            ps.setString(2, rmsbookingHardwareGroup.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateRmsBookingHardwareGroupReturnByAndReturnDateByGroupId(RmsBookingHardwareGroup rmsbookingHardwareGroup) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BOOKING_HARDWARE_GROUP_RETURN_BY_GROUP)) {
            ps.setString(1, rmsbookingHardwareGroup.getReturnBy());
            ps.setString(2, rmsbookingHardwareGroup.getGroupId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateRmsBookingHardwareGroupFlagAndStatusByGroupId(RmsBookingHardwareGroup rmsbookingHardwareGroup) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BOOKING_HARDWARE_GROUP_FLAG_AND_STATUS)) {
            ps.setString(1, rmsbookingHardwareGroup.getFlag());
            ps.setString(2, rmsbookingHardwareGroup.getStatus());
            ps.setString(3, rmsbookingHardwareGroup.getGroupId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult deleteRmsBookingHardwareGroup(String rmsbookingHardwareGroupId) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_BOOKING_HARDWARE_GROUP)) {
            ps.setString(1, rmsbookingHardwareGroupId);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    private static final String SQL_GET_RMS_BOOKING_HARDWARE_GROUP = "SELECT * FROM rms_booking_hardware_group WHERE id = ?";
    private static final String SQL_GET_UNLOADING_DATEBY_GROUP_ID = "SELECT DATE_FORMAT(gr.unloading_date,'%d %M %Y %h:%i %p') AS viewUnloadingDate FROM rms_booking_hardware_group gr WHERE gr.group_id = ? AND gr.item_type = 'BIB' AND gr.flag = '2'";
    private static final String SQL_GET_LOADING_DATE_BY_GROUP_ID = "SELECT DATE_FORMAT(gr.loading_date,'%d %M %Y %h:%i %p') AS loadingDate FROM rms_booking_hardware_group gr WHERE gr.group_id = ? AND gr.item_type = 'BIB' AND gr.flag = '1'";

    public RmsBookingHardwareGroup getRmsBookingHardwareGroup(String id) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_GROUP)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentHwGroup(rs);
                }
            }
        }
        return null;
    }

    private RmsBookingHardwareGroup mapComponentHwGroup(ResultSet rs) throws SQLException {
        RmsBookingHardwareGroup rmsbookingHardwareGroup = new RmsBookingHardwareGroup();
        rmsbookingHardwareGroup = new RmsBookingHardwareGroup();
        rmsbookingHardwareGroup.setId(rs.getString("id"));
        rmsbookingHardwareGroup.setGroupId(rs.getString("group_id"));
        rmsbookingHardwareGroup.setItemPkid(rs.getString("item_pkid"));
        rmsbookingHardwareGroup.setItemId(rs.getString("item_id"));
        rmsbookingHardwareGroup.setHardwarePkid(rs.getString("hardware_pkid"));
        rmsbookingHardwareGroup.setHardwareId(rs.getString("hardware_id"));
        rmsbookingHardwareGroup.setRmsNo(rs.getString("rms_no"));
        rmsbookingHardwareGroup.setEvent(rs.getString("event"));
        rmsbookingHardwareGroup.setSptsStatus(rs.getString("spts_status"));
        rmsbookingHardwareGroup.setStatus(rs.getString("status"));
        rmsbookingHardwareGroup.setCreatedBy(rs.getString("created_by"));
        rmsbookingHardwareGroup.setCreatedDate(rs.getString("created_date"));
        rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
        rmsbookingHardwareGroup.setItemType(rs.getString("item_type"));
        return rmsbookingHardwareGroup;
    }

    public RmsBookingHardwareGroup getUnloadingDateByGroupId(String groupId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_UNLOADING_DATEBY_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RmsBookingHardwareGroup group = new RmsBookingHardwareGroup();
                    group.setUnloadingDate(rs.getString("viewUnloadingDate"));
                    return group;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving unloading date for groupId={} in getUnloadingDateByGroupId ", groupId, e);
        }
        return null;
    }

    public RmsBookingHardwareGroup getLoadingDateByGroupId(String groupId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_LOADING_DATE_BY_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RmsBookingHardwareGroup group = new RmsBookingHardwareGroup();
                    group.setLoadingDate(rs.getString("loadingDate"));
                    return group;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving loading date for groupId={} in getLoadingDateByGroupId ", groupId, e);
        }
        return null;
    }

    private static final String SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST = "SELECT * FROM rms_booking_hardware_group ORDER BY id ASC";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_GROUP_ID = "SELECT g.*,DATE_FORMAT(g.created_date,'%d-%M-%Y') AS createdDate, i.item_type FROM rms_booking_hardware_group g LEFT JOIN item i ON i.spts_pkid = g.item_pkid WHERE g.group_id = ? ORDER BY g.hardware_id, g.item_id ASC";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_BOOKING_PKID = "SELECT g.*, DATE_FORMAT(g.created_date, '%d-%M-%Y') AS createdDate FROM rms_booking_hardware_group g WHERE g.group_id LIKE ? AND g.flag = '0' ORDER BY g.hardware_id, g.item_id ASC ";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_GROUP_ID_FLAG_ZERO = "SELECT g.*, DATE_FORMAT(g.created_date,'%d-%M-%Y') AS createdDate FROM rms_booking_hardware_group g WHERE g.group_id = ? AND g.flag = '0' ORDER BY g.hardware_id, g.item_id ASC";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_GROUP_ID_FLAG_ONE = "SELECT g.*, DATE_FORMAT(g.created_date,'%d-%M-%Y') AS createdDate FROM rms_booking_hardware_group g WHERE g.group_id = ? AND g.flag = '1' ORDER BY g.hardware_id, g.item_id ASC";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_GROUP_ID_FLAG_ONE_LIKE = "SELECT g.*, DATE_FORMAT(g.created_date,'%d-%M-%Y') AS createdDate FROM rms_booking_hardware_group g WHERE g.group_id LIKE ? AND g.flag = '1' ORDER BY g.hardware_id, g.item_id ASC";
    private static final String SQL_GET_RMS_BOOKING_HARDWARE_GROUP_FOR_UNLOADING = "SELECT * FROM rms_booking_hardware_group WHERE group_id = ? ";

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupList() {
        List<RmsBookingHardwareGroup> rmsBookingHardwareGroupList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RmsBookingHardwareGroup rmsBookingHardwareGroup = new RmsBookingHardwareGroup();
                rmsBookingHardwareGroup.setId(rs.getString("id"));
                rmsBookingHardwareGroup.setGroupId(rs.getString("group_id"));
                rmsBookingHardwareGroup.setItemPkid(rs.getString("item_pkid"));
                rmsBookingHardwareGroup.setItemId(rs.getString("item_id"));
                rmsBookingHardwareGroup.setHardwarePkid(rs.getString("hardware_pkid"));
                rmsBookingHardwareGroup.setHardwareId(rs.getString("hardware_id"));
                rmsBookingHardwareGroup.setRmsNo(rs.getString("rms_no"));
                rmsBookingHardwareGroup.setEvent(rs.getString("event"));
                rmsBookingHardwareGroup.setSptsStatus(rs.getString("spts_status"));
                rmsBookingHardwareGroup.setStatus(rs.getString("status"));
                rmsBookingHardwareGroup.setCreatedBy(rs.getString("created_by"));
                rmsBookingHardwareGroup.setCreatedDate(rs.getString("created_date"));
                rmsBookingHardwareGroup.setFlag(rs.getString("flag"));
                rmsBookingHardwareGroup.setItemType(rs.getString("item_type"));
                rmsBookingHardwareGroupList.add(rmsBookingHardwareGroup);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list getRmsBookingHardwareGroupList ", e);
        }
        return rmsBookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByGroupId(String groupId) {
        List<RmsBookingHardwareGroup> rmsBookingHardwareGroupList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardwareGroup rmsbookingHardwareGroup = new RmsBookingHardwareGroup();
                    rmsbookingHardwareGroup.setId(rs.getString("id"));
                    rmsbookingHardwareGroup.setGroupId(rs.getString("group_id"));
                    rmsbookingHardwareGroup.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardwareGroup.setItemId(rs.getString("item_id"));
                    rmsbookingHardwareGroup.setHardwarePkid(rs.getString("hardware_pkid"));
                    rmsbookingHardwareGroup.setHardwareId(rs.getString("hardware_id"));
                    rmsbookingHardwareGroup.setRmsNo(rs.getString("rms_no"));
                    rmsbookingHardwareGroup.setEvent(rs.getString("event"));
                    rmsbookingHardwareGroup.setSptsStatus(rs.getString("spts_status"));
                    rmsbookingHardwareGroup.setStatus(rs.getString("status"));
                    rmsbookingHardwareGroup.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                    rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
                    rmsbookingHardwareGroup.setItemType(rs.getString("item_type"));
                    rmsBookingHardwareGroupList.add(rmsbookingHardwareGroup);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list getRmsBookingHardwareGroupListByGroupId ", e);
        }
        return rmsBookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByBookingPkid(String bookingPkid) {
        List<RmsBookingHardwareGroup> rmsBookingHardwareGroupList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_BOOKING_PKID)) {
            ps.setString(1, bookingPkid + "/%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardwareGroup rmsBookingHardwareGroup = new RmsBookingHardwareGroup();
                    rmsBookingHardwareGroup.setId(rs.getString("id"));
                    rmsBookingHardwareGroup.setGroupId(rs.getString("group_id"));
                    rmsBookingHardwareGroup.setItemPkid(rs.getString("item_pkid"));
                    rmsBookingHardwareGroup.setItemId(rs.getString("item_id"));
                    rmsBookingHardwareGroup.setHardwarePkid(rs.getString("hardware_pkid"));
                    rmsBookingHardwareGroup.setHardwareId(rs.getString("hardware_id"));
                    rmsBookingHardwareGroup.setRmsNo(rs.getString("rms_no"));
                    rmsBookingHardwareGroup.setEvent(rs.getString("event"));
                    rmsBookingHardwareGroup.setSptsStatus(rs.getString("spts_status"));
                    rmsBookingHardwareGroup.setStatus(rs.getString("status"));
                    rmsBookingHardwareGroup.setCreatedBy(rs.getString("created_by"));
                    rmsBookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                    rmsBookingHardwareGroup.setFlag(rs.getString("flag"));
                    rmsBookingHardwareGroupList.add(rmsBookingHardwareGroup);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list for bookingPkid={} in getRmsBookingHardwareGroupListByBookingPkid ", bookingPkid, e);
        }
        return rmsBookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByGroupIdFlagZero(String groupId) {
        List<RmsBookingHardwareGroup> rmsBookingHardwareGroupList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_GROUP_ID_FLAG_ZERO)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardwareGroup rmsBookingHardwareGroup = new RmsBookingHardwareGroup();
                    rmsBookingHardwareGroup.setId(rs.getString("id"));
                    rmsBookingHardwareGroup.setGroupId(rs.getString("group_id"));
                    rmsBookingHardwareGroup.setItemPkid(rs.getString("item_pkid"));
                    rmsBookingHardwareGroup.setItemId(rs.getString("item_id"));
                    rmsBookingHardwareGroup.setHardwarePkid(rs.getString("hardware_pkid"));
                    rmsBookingHardwareGroup.setHardwareId(rs.getString("hardware_id"));
                    rmsBookingHardwareGroup.setRmsNo(rs.getString("rms_no"));
                    rmsBookingHardwareGroup.setEvent(rs.getString("event"));
                    rmsBookingHardwareGroup.setSptsStatus(rs.getString("spts_status"));
                    rmsBookingHardwareGroup.setStatus(rs.getString("status"));
                    rmsBookingHardwareGroup.setCreatedBy(rs.getString("created_by"));
                    rmsBookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                    rmsBookingHardwareGroup.setFlag(rs.getString("flag"));
                    rmsBookingHardwareGroupList.add(rmsBookingHardwareGroup);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list for groupId={} in getRmsBookingHardwareGroupListByGroupIdFlagZero", groupId, e);
        }
        return rmsBookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByGroupIdFlagOne(String groupId) {
        List<RmsBookingHardwareGroup> rmsBookingHardwareGroupList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_GROUP_ID_FLAG_ONE)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardwareGroup rmsBookingHardwareGroup = new RmsBookingHardwareGroup();
                    rmsBookingHardwareGroup.setId(rs.getString("id"));
                    rmsBookingHardwareGroup.setGroupId(rs.getString("group_id"));
                    rmsBookingHardwareGroup.setItemPkid(rs.getString("item_pkid"));
                    rmsBookingHardwareGroup.setItemId(rs.getString("item_id"));
                    rmsBookingHardwareGroup.setHardwarePkid(rs.getString("hardware_pkid"));
                    rmsBookingHardwareGroup.setHardwareId(rs.getString("hardware_id"));
                    rmsBookingHardwareGroup.setRmsNo(rs.getString("rms_no"));
                    rmsBookingHardwareGroup.setEvent(rs.getString("event"));
                    rmsBookingHardwareGroup.setSptsStatus(rs.getString("spts_status"));
                    rmsBookingHardwareGroup.setStatus(rs.getString("status"));
                    rmsBookingHardwareGroup.setCreatedBy(rs.getString("created_by"));
                    rmsBookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                    rmsBookingHardwareGroup.setFlag(rs.getString("flag"));
                    rmsBookingHardwareGroupList.add(rmsBookingHardwareGroup);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list for groupId={} in getRmsBookingHardwareGroupListByGroupIdFlagOne ", groupId, e);
        }
        return rmsBookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByBookingPkidWithFlagOne(String bookingPkid) {
        List<RmsBookingHardwareGroup> rmsBookingHardwareGroupList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_GROUP_LIST_BY_GROUP_ID_FLAG_ONE_LIKE)) {
            ps.setString(1, bookingPkid + "/%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardwareGroup rmsbookingHardwareGroup = new RmsBookingHardwareGroup();
                    rmsbookingHardwareGroup.setId(rs.getString("id"));
                    rmsbookingHardwareGroup.setGroupId(rs.getString("group_id"));
                    rmsbookingHardwareGroup.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardwareGroup.setItemId(rs.getString("item_id"));
                    rmsbookingHardwareGroup.setHardwarePkid(rs.getString("hardware_pkid"));
                    rmsbookingHardwareGroup.setHardwareId(rs.getString("hardware_id"));
                    rmsbookingHardwareGroup.setRmsNo(rs.getString("rms_no"));
                    rmsbookingHardwareGroup.setEvent(rs.getString("event"));
                    rmsbookingHardwareGroup.setSptsStatus(rs.getString("spts_status"));
                    rmsbookingHardwareGroup.setStatus(rs.getString("status"));
                    rmsbookingHardwareGroup.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardwareGroup.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
                    rmsBookingHardwareGroupList.add(rmsbookingHardwareGroup);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list for groupId={} in getRmsBookingHardwareGroupListByBookingPkidWithFlagOne ", bookingPkid, e);
        }
        return rmsBookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupForUnloading(String bookingPkid, String pkid) {
        String groupId = bookingPkid + "/" + pkid;
        List<RmsBookingHardwareGroup> rmsBookingHardwareGroupList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_RMS_BOOKING_HARDWARE_GROUP_FOR_UNLOADING)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RmsBookingHardwareGroup rmsbookingHardwareGroup = new RmsBookingHardwareGroup();
                    rmsbookingHardwareGroup.setId(rs.getString("id"));
                    rmsbookingHardwareGroup.setGroupId(rs.getString("group_id"));
                    rmsbookingHardwareGroup.setItemPkid(rs.getString("item_pkid"));
                    rmsbookingHardwareGroup.setItemId(rs.getString("item_id"));
                    rmsbookingHardwareGroup.setHardwarePkid(rs.getString("hardware_pkid"));
                    rmsbookingHardwareGroup.setHardwareId(rs.getString("hardware_id"));
                    rmsbookingHardwareGroup.setRmsNo(rs.getString("rms_no"));
                    rmsbookingHardwareGroup.setEvent(rs.getString("event"));
                    rmsbookingHardwareGroup.setSptsStatus(rs.getString("spts_status"));
                    rmsbookingHardwareGroup.setStatus(rs.getString("status"));
                    rmsbookingHardwareGroup.setCreatedBy(rs.getString("created_by"));
                    rmsbookingHardwareGroup.setCreatedDate(rs.getString("created_date"));
                    rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
                    rmsBookingHardwareGroupList.add(rmsbookingHardwareGroup);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list for groupId={} in getRmsBookingHardwareGroupForUnloading ", bookingPkid, e);
        }
        return rmsBookingHardwareGroupList;
    }

    private static final String SQL_COUNT_HW_BY_GROUP_ID_AND_HWID = "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.group_id = ? AND inc.hardware_id = ?";
    private static final String SQL_COUNT_HW_BOOKING_PKID_AND_HWID = "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.group_id LIKE ? AND inc.hardware_id = ?";
    private static final String SQL_COUNT_HW_WITH_FLAG_99 = "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.hardware_id = ? AND inc.flag != '99'";
    private static final String SQL_COUNT_HW_WITH_FLAG_99_2_3 = "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.hardware_id = ? AND inc.flag NOT IN ('99', '2','3')";
    private static final String SQL_COUNT_HW_WITHIN_SAME_BOOKING_PKID = "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.group_id LIKE ? AND inc.item_pkid = ? AND inc.flag IN ('0', '1')";
    private static final String SQL_COUNT_BIB_BY_GROUP_ID = "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.group_id = ? AND inc.item_type = 'BIB'";

    public Integer getCountHwByGroupIdAndHwId(String bookingPkid, String hwId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_COUNT_HW_BY_GROUP_ID_AND_HWID)) {
            ps.setString(1, bookingPkid);
            ps.setString(2, hwId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. bookingPkid={}, hwId={} in getCountHwByGroupIdAndHwId ", bookingPkid, hwId, e);
        }
        return 0;
    }

    public Integer getCountHwByBookingPkidAndHwId(String bookingPkid, String hwId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_COUNT_HW_BOOKING_PKID_AND_HWID)) {
            ps.setString(1, bookingPkid + "/%");
            ps.setString(2, hwId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. bookingPkid={}, hwId={} in getCountHwByBookingPkidAndHwId ", bookingPkid, hwId, e);
        }
        return 0;
    }

    public Integer getCountHwWithFlagNE99(String hwId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_COUNT_HW_WITH_FLAG_99)) {
            ps.setString(1, hwId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. hwId={} in getCountHwWithFlagNE99 ", hwId, e);
        }
        return 0;
    }

    public Integer getCountHwWithFlagNE99And2And3(String hwId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_COUNT_HW_WITH_FLAG_99_2_3)) {
            ps.setString(1, hwId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. hwId={} in getCountHwWithFlagNE99And2And3 ", hwId, e);
        }
        return 0;
    }

    public Integer getCountHwWithinSameBookingPkidAndItemPkid(String bookingPkid, String itemPkid) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_COUNT_HW_WITHIN_SAME_BOOKING_PKID)) {
            ps.setString(1, bookingPkid + "/%");
            ps.setString(2, itemPkid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. bookingPkid={}, itemPkid={} in getCountHwWithinSameBookingPkidAndItemPkid ", bookingPkid, itemPkid, e);
        }
        return 0;
    }

    public Integer getCountBibByGroupId(String groupId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_COUNT_BIB_BY_GROUP_ID)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. groupid={} in getCountBibByGroupId ", groupId, e);
        }
        return 0;
    }

    private static final String SQL_UPDATE_GROUP_STATUS = "UPDATE rms_booking_hardware_group SET status = ? WHERE group_id = ? AND hardware_id = ? ";
    private static final String SQL_UPDATE_GROUP_STATUS_CLOSED = "UPDATE rms_booking_hardware_group SET status = ?, spts_status = ? WHERE group_id = ?";

    public QueryResult updateGroupStatus(String status, String groupId, String hardwareId) {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_GROUP_STATUS)) {
            ps.setString(1, status);
            ps.setString(2, groupId);
            ps.setString(3, hardwareId);
            result.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error fetching mibItemId for status={}, groupId={}, hardwareId={} in updateGroupStatus ", status, groupId, hardwareId, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return result;
    }

    public QueryResult updateGroupStatusToClosed(String status, String sptsStatus, String groupId) {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_GROUP_STATUS_CLOSED)) {
            ps.setString(1, status);
            ps.setString(2, sptsStatus);
            ps.setString(3, groupId);
            result.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            LOGGER.error("Error fetching mibItemId for status={}, sptsStatus={}, groupId={} in updateGroupStatusToClosed ", status, sptsStatus, groupId, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return result;
    }

    private static final String SQL_GET_MIBITEMID = "SELECT id FROM item WHERE spts_pkid IN (SELECT item_pkid FROM rms_booking_hardware_group WHERE group_id = ? AND hardware_id = ?)";

    public String getMibItemIdByGroupIdAndHardwareId(String hardwareId, String groupId) {
        final String sql = SQL_GET_MIBITEMID;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, groupId);
            ps.setString(2, hardwareId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching mibItemId for groupId={}, hardwareId={} in getMibItemIdByGroupIdAndHardwareId ", groupId, hardwareId, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return null;
    }

}