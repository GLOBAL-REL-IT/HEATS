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
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingHardwareGroupDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRmsBookingHardwareGroup(RmsBookingHardwareGroup rmsbookingHardwareGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_hardware_group (group_id, item_pkid, item_id, hardware_pkid, hardware_id, rms_no, event, spts_status, status, created_by, created_date, flag, item_type) VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
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

    public QueryResult updateRmsBookingHardwareGroup(RmsBookingHardwareGroup rmsbookingHardwareGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware_group SET group_id = ?, item_pkid = ?, item_id = ?, hardware_pkid = ?, hardware_id = ?, rms_no = ?, event = ?, spts_status = ?, status = ?, created_by = ?, created_date = ?, flag = ?, item_type = ? WHERE id = ?"
            );
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

    public QueryResult updateRmsBookingHardwareGroupStatusAndFlag(RmsBookingHardwareGroup rmsbookingHardwareGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware_group SET status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardwareGroup.getStatus());
            ps.setString(2, rmsbookingHardwareGroup.getFlag());
            ps.setString(3, rmsbookingHardwareGroup.getId());
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

    public QueryResult updateRmsBookingHardwareGroupStatusAndSptsStatusAndFlag(RmsBookingHardwareGroup rmsbookingHardwareGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware_group SET spts_status = ?, status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardwareGroup.getSptsStatus());
            ps.setString(2, rmsbookingHardwareGroup.getStatus());
            ps.setString(3, rmsbookingHardwareGroup.getFlag());
            ps.setString(4, rmsbookingHardwareGroup.getId());
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

    public QueryResult updateRmsBookingHardwareGroupReturnByAndReturnDate(RmsBookingHardwareGroup rmsbookingHardwareGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware_group SET return_by = ?, return_date = NOW() WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardwareGroup.getReturnBy());
            ps.setString(2, rmsbookingHardwareGroup.getId());
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

    public QueryResult updateRmsBookingHardwareGroupReturnByAndReturnDateByGroupId(RmsBookingHardwareGroup rmsbookingHardwareGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware_group SET return_by = ?, return_date = NOW() WHERE group_id = ?"
            );
            ps.setString(1, rmsbookingHardwareGroup.getReturnBy());
            ps.setString(2, rmsbookingHardwareGroup.getGroupId());
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

    public QueryResult updateRmsBookingHardwareGroupFlagAndStatusByGroupId(RmsBookingHardwareGroup rmsbookingHardwareGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware_group SET flag = ?, status = ? WHERE group_id = ?"
            );
            ps.setString(1, rmsbookingHardwareGroup.getFlag());
            ps.setString(2, rmsbookingHardwareGroup.getStatus());
            ps.setString(3, rmsbookingHardwareGroup.getGroupId());
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

    public QueryResult deleteRmsBookingHardwareGroup(String rmsbookingHardwareGroupId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_hardware_group WHERE id = '" + rmsbookingHardwareGroupId + "'"
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

    public RmsBookingHardwareGroup getRmsBookingHardwareGroup(String id) {
        String sql = "SELECT * FROM rms_booking_hardware_group WHERE id = '" + id + "'";
        RmsBookingHardwareGroup rmsbookingHardwareGroup = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        return rmsbookingHardwareGroup;
    }

    public RmsBookingHardwareGroup getUnloadingDateByGroupId(String groupId) {
        String sql = "SELECT DATE_FORMAT(gr.unloading_date,'%d %M %Y %h:%i %p') AS viewUnloadingDate FROM rms_booking_hardware_group gr WHERE gr.group_id = ? AND gr.item_type = 'BIB' AND gr.flag = '2'";
        RmsBookingHardwareGroup rmsbookingHardwareGroup = null;
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, groupId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rmsbookingHardwareGroup = new RmsBookingHardwareGroup();
                    rmsbookingHardwareGroup.setUnloadingDate(rs.getString("viewUnloadingDate"));
                }
            }
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
        return rmsbookingHardwareGroup;
    }

    public RmsBookingHardwareGroup getLoadingDateByGroupId(String groupId) {
        String sql = "SELECT DATE_FORMAT(gr.loading_date,'%d %M %Y %h:%i %p') AS loadingDate FROM rms_booking_hardware_group gr WHERE gr.group_id = ? AND gr.item_type = 'BIB' AND gr.flag = '1'";
        RmsBookingHardwareGroup rmsbookingHardwareGroup = null;
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, groupId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rmsbookingHardwareGroup = new RmsBookingHardwareGroup();
                    rmsbookingHardwareGroup.setLoadingDate(rs.getString("loadingDate"));
                }
            }
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
        return rmsbookingHardwareGroup;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupList() {
        String sql = "SELECT * FROM rms_booking_hardware_group ORDER BY id ASC";
        List<RmsBookingHardwareGroup> rmsbookingHardwareGroupList = new ArrayList<RmsBookingHardwareGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardwareGroup rmsbookingHardwareGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareGroupList.add(rmsbookingHardwareGroup);
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
        return rmsbookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByGroupId(String groupId) {
        String sql = "SELECT g.*,DATE_FORMAT(g.created_date,'%d-%M-%Y') AS createdDate, i.item_type "
                + "FROM rms_booking_hardware_group g LEFT JOIN item i ON i.spts_pkid = g.item_pkid "
                + "WHERE g.group_id = '" + groupId + "' "
                + "ORDER BY g.hardware_id, g.item_id ASC";
        List<RmsBookingHardwareGroup> rmsbookingHardwareGroupList = new ArrayList<RmsBookingHardwareGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardwareGroup rmsbookingHardwareGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
                rmsbookingHardwareGroup.setItemType(rs.getString("i.item_type"));
                rmsbookingHardwareGroupList.add(rmsbookingHardwareGroup);
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
        return rmsbookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByBookingPkid(String bookingPkid) {
        String sql = "SELECT g.*, DATE_FORMAT(g.created_date,'%d-%M-%Y') AS createdDate "
                + "FROM rms_booking_hardware_group g  "
                + "WHERE g.group_id LIKE '" + bookingPkid + "/%' AND g.flag = '0' "
                + "ORDER BY g.hardware_id, g.item_id ASC";
        List<RmsBookingHardwareGroup> rmsbookingHardwareGroupList = new ArrayList<RmsBookingHardwareGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardwareGroup rmsbookingHardwareGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
                rmsbookingHardwareGroupList.add(rmsbookingHardwareGroup);
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
        return rmsbookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByGroupIdFlagZero(String groupId) {
        String sql = "SELECT g.*, DATE_FORMAT(g.created_date,'%d-%M-%Y') AS createdDate "
                + "FROM rms_booking_hardware_group g  "
                + "WHERE g.group_id = '" + groupId + "' AND g.flag = '0' "
                + "ORDER BY g.hardware_id, g.item_id ASC";
        List<RmsBookingHardwareGroup> rmsbookingHardwareGroupList = new ArrayList<RmsBookingHardwareGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardwareGroup rmsbookingHardwareGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
                rmsbookingHardwareGroupList.add(rmsbookingHardwareGroup);
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
        return rmsbookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByGroupIdFlagOne(String groupId) {
        String sql = "SELECT g.*, DATE_FORMAT(g.created_date,'%d-%M-%Y') AS createdDate "
                + "FROM rms_booking_hardware_group g  "
                + "WHERE g.group_id = '" + groupId + "' AND g.flag = '1' "
                + "ORDER BY g.hardware_id, g.item_id ASC";
        List<RmsBookingHardwareGroup> rmsbookingHardwareGroupList = new ArrayList<RmsBookingHardwareGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardwareGroup rmsbookingHardwareGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
                rmsbookingHardwareGroupList.add(rmsbookingHardwareGroup);
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
        return rmsbookingHardwareGroupList;
    }

    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupListByBookingPkidWithFlagOne(String bookingPkid) {
        String sql = "SELECT g.*, DATE_FORMAT(g.created_date,'%d-%M-%Y') AS createdDate "
                + "FROM rms_booking_hardware_group g  "
                + "WHERE g.group_id LIKE '" + bookingPkid + "/%' AND g.flag = '1' "
                + "ORDER BY g.hardware_id, g.item_id ASC";
        List<RmsBookingHardwareGroup> rmsbookingHardwareGroupList = new ArrayList<RmsBookingHardwareGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardwareGroup rmsbookingHardwareGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
                rmsbookingHardwareGroupList.add(rmsbookingHardwareGroup);
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
        return rmsbookingHardwareGroupList;
    }
    
    public List<RmsBookingHardwareGroup> getRmsBookingHardwareGroupForUnloading(String bookingPkid, String pkid) {
        String groupId = bookingPkid+"/"+pkid;
        String sql = "SELECT * FROM item_hardware WHERE hardware_id IN (SELECT hardware_id FROM rms_booking_hardware_group WHERE group_id = ?";
        List<RmsBookingHardwareGroup> rmsbookingHardwareGroupList = new ArrayList<RmsBookingHardwareGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, groupId);
            RmsBookingHardwareGroup rmsbookingHardwareGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareGroup.setCreatedDate(rs.getString("createdDate"));
                rmsbookingHardwareGroup.setFlag(rs.getString("flag"));
                rmsbookingHardwareGroupList.add(rmsbookingHardwareGroup);
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
        return rmsbookingHardwareGroupList;
    }

    public Integer getCountHwByGroupIdAndHwId(String groupId, String hwId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.group_id = '" + groupId + "' AND inc.hardware_id = '" + hwId + "'"
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

    public Integer getCountHwByBookingPkidAndHwId(String bookingPkid, String hwId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.group_id like '" + bookingPkid + "/%' AND inc.hardware_id = '" + hwId + "'"
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

    public Integer getCountHwWithFlagNE99(String hwId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.hardware_id = '" + hwId + "' AND inc.flag != '99'"
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

    public Integer getCountHwWithFlagNE99And2And3(String hwId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.hardware_id = '" + hwId + "' AND inc.flag NOT IN ('99', '2','3')"
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

    public Integer getCountHwWithinSameBookingPkidAndItemPkid(String bookingPkid, String itemPkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.group_id LIKE '" + bookingPkid + "/%' AND inc.item_pkid = '" + itemPkid + "'"
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

    public Integer getCountBibByGroupId(String groupId) {
        Integer count = null;
        try ( PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.group_id = ? AND inc.item_type = 'BIB'"
        )) {
            ps.setString(1, groupId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count = rs.getInt("count");
                }
            }
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

    public QueryResult updateGroupStatus(String status, String groupId, String hardwareId) {
        QueryResult queryResult = new QueryResult();
        try {
            String sql = "UPDATE rms_booking_hardware_group SET status = ? WHERE group_id = ? AND hardware_id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, groupId);
            ps.setString(3, hardwareId);
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

    public QueryResult updateGroupStatusToClosed(String status, String sptsStatus, String groupId) {
        QueryResult queryResult = new QueryResult();
        try {
            String sql = "UPDATE rms_booking_hardware_group SET status = ?, spts_status = ? WHERE group_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, sptsStatus);
            ps.setString(3, groupId);
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

}
