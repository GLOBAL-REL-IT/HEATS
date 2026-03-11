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
                    "INSERT INTO rms_booking_hardware_group (group_id, item_pkid, item_id, hardware_pkid, hardware_id, rms_no, event, spts_status, status, created_by, created_date, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
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
                    "UPDATE rms_booking_hardware_group SET group_id = ?, item_pkid = ?, item_id = ?, hardware_pkid = ?, hardware_id = ?, rms_no = ?, event = ?, spts_status = ?, status = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?"
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
            ps.setString(13, rmsbookingHardwareGroup.getId());
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

    public RmsBookingHardwareGroup getRmsBookingHardwareGroup(String rmsbookingHardwareGroupId) {
        String sql = "SELECT * FROM rms_booking_hardware_group WHERE id = '" + rmsbookingHardwareGroupId + "'";
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
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware_group inc WHERE inc.group_id like '" + bookingPkid + "%' AND inc.hardware_id = '" + hwId + "'"
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
}
