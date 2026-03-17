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
import com.onsemi.mib.model.RmsBookingDetailHwReplacement;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingDetailHwReplacementDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingDetailHwReplacementDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingDetailHwReplacementDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRmsBookingDetailHwReplacement(RmsBookingDetailHwReplacement rmsbookingDetailHwReplacement) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_detail_hw_replacement (booking_pkid, booking_hw_pkid, item_pkid, item_id, remarks, created_by, created_date, flag) VALUES (?,?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rmsbookingDetailHwReplacement.getBookingPkid());
            ps.setString(2, rmsbookingDetailHwReplacement.getBookingHwPkid());
            ps.setString(3, rmsbookingDetailHwReplacement.getItemPkid());
            ps.setString(4, rmsbookingDetailHwReplacement.getItemId());
            ps.setString(5, rmsbookingDetailHwReplacement.getRemarks());
            ps.setString(6, rmsbookingDetailHwReplacement.getCreatedBy());
            ps.setString(7, rmsbookingDetailHwReplacement.getFlag());
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

    public QueryResult updateRmsBookingDetailHwReplacement(RmsBookingDetailHwReplacement rmsbookingDetailHwReplacement) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_detail_hw_replacement SET booking_pkid = ?, booking_hw_pkid = ?, item_pkid = ?, item_id = ?, remarks = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingDetailHwReplacement.getBookingPkid());
            ps.setString(2, rmsbookingDetailHwReplacement.getBookingHwPkid());
            ps.setString(3, rmsbookingDetailHwReplacement.getItemPkid());
            ps.setString(4, rmsbookingDetailHwReplacement.getItemId());
            ps.setString(5, rmsbookingDetailHwReplacement.getRemarks());
            ps.setString(6, rmsbookingDetailHwReplacement.getCreatedBy());
            ps.setString(7, rmsbookingDetailHwReplacement.getCreatedDate());
            ps.setString(8, rmsbookingDetailHwReplacement.getFlag());
            ps.setString(9, rmsbookingDetailHwReplacement.getId());
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

    public QueryResult updateRmsBookingDetailHwReplacementFlag(RmsBookingDetailHwReplacement rmsbookingDetailHwReplacement) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_detail_hw_replacement SET flag = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingDetailHwReplacement.getFlag());
            ps.setString(2, rmsbookingDetailHwReplacement.getId());
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

    public QueryResult deleteRmsBookingDetailHwReplacement(String rmsbookingDetailHwReplacementId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_detail_hw_replacement WHERE id = '" + rmsbookingDetailHwReplacementId + "'"
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

    public RmsBookingDetailHwReplacement getRmsBookingDetailHwReplacement(String rmsbookingDetailHwReplacementId) {
        String sql = "SELECT * FROM rms_booking_detail_hw_replacement WHERE id = '" + rmsbookingDetailHwReplacementId + "'";
        RmsBookingDetailHwReplacement rmsbookingDetailHwReplacement = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingDetailHwReplacement = new RmsBookingDetailHwReplacement();
                rmsbookingDetailHwReplacement.setId(rs.getString("id"));
                rmsbookingDetailHwReplacement.setBookingPkid(rs.getString("booking_pkid"));
                rmsbookingDetailHwReplacement.setBookingHwPkid(rs.getString("booking_hw_pkid"));
                rmsbookingDetailHwReplacement.setItemPkid(rs.getString("item_pkid"));
                rmsbookingDetailHwReplacement.setItemId(rs.getString("item_id"));
                rmsbookingDetailHwReplacement.setRemarks(rs.getString("remarks"));
                rmsbookingDetailHwReplacement.setCreatedBy(rs.getString("created_by"));
                rmsbookingDetailHwReplacement.setCreatedDate(rs.getString("created_date"));
                rmsbookingDetailHwReplacement.setFlag(rs.getString("flag"));
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
        return rmsbookingDetailHwReplacement;
    }

    public List<RmsBookingDetailHwReplacement> getRmsBookingDetailHwReplacementList() {
        String sql = "SELECT * FROM rms_booking_detail_hw_replacement ORDER BY id ASC";
        List<RmsBookingDetailHwReplacement> rmsbookingDetailHwReplacementList = new ArrayList<RmsBookingDetailHwReplacement>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingDetailHwReplacement rmsbookingDetailHwReplacement;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingDetailHwReplacement = new RmsBookingDetailHwReplacement();
                rmsbookingDetailHwReplacement.setId(rs.getString("id"));
                rmsbookingDetailHwReplacement.setBookingPkid(rs.getString("booking_pkid"));
                rmsbookingDetailHwReplacement.setBookingHwPkid(rs.getString("booking_hw_pkid"));
                rmsbookingDetailHwReplacement.setItemPkid(rs.getString("item_pkid"));
                rmsbookingDetailHwReplacement.setItemId(rs.getString("item_id"));
                rmsbookingDetailHwReplacement.setRemarks(rs.getString("remarks"));
                rmsbookingDetailHwReplacement.setCreatedBy(rs.getString("created_by"));
                rmsbookingDetailHwReplacement.setCreatedDate(rs.getString("created_date"));
                rmsbookingDetailHwReplacement.setFlag(rs.getString("flag"));
                rmsbookingDetailHwReplacementList.add(rmsbookingDetailHwReplacement);
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
        return rmsbookingDetailHwReplacementList;
    }

    public List<RmsBookingDetailHwReplacement> getRmsBookingDetailHwReplacementListByBookingPkid(String bookingPkid) {
        String sql = "SELECT re.*, ha.item_type, ha.qty, ha.status, ha.id AS bookingHwId FROM rms_booking_detail_hw_replacement re "
                + "LEFT JOIN rms_booking_hardware ha on ha.pkid = re.booking_hw_pkid "
                + "WHERE re.booking_pkid = '" + bookingPkid + "' AND re.flag = '0' "
                + "ORDER BY item_id ASC";
        List<RmsBookingDetailHwReplacement> rmsbookingDetailHwReplacementList = new ArrayList<RmsBookingDetailHwReplacement>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingDetailHwReplacement rmsbookingDetailHwReplacement;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingDetailHwReplacement = new RmsBookingDetailHwReplacement();
                rmsbookingDetailHwReplacement.setId(rs.getString("re.id"));
                rmsbookingDetailHwReplacement.setBookingPkid(rs.getString("re.booking_pkid"));
                rmsbookingDetailHwReplacement.setBookingHwPkid(rs.getString("booking_hw_pkid"));
                rmsbookingDetailHwReplacement.setItemPkid(rs.getString("re.item_pkid"));
                rmsbookingDetailHwReplacement.setItemId(rs.getString("re.item_id"));
                rmsbookingDetailHwReplacement.setRemarks(rs.getString("re.remarks"));
                rmsbookingDetailHwReplacement.setCreatedBy(rs.getString("re.created_by"));
                rmsbookingDetailHwReplacement.setCreatedDate(rs.getString("re.created_date"));
                rmsbookingDetailHwReplacement.setItemType(rs.getString("item_type"));
                rmsbookingDetailHwReplacement.setQty(rs.getString("qty"));
                rmsbookingDetailHwReplacement.setStatus(rs.getString("status"));
                rmsbookingDetailHwReplacement.setBookingHwId(rs.getString("bookingHwId"));
                rmsbookingDetailHwReplacement.setFlag(rs.getString("re.flag"));
                rmsbookingDetailHwReplacementList.add(rmsbookingDetailHwReplacement);
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
        return rmsbookingDetailHwReplacementList;
    }

    public Integer getCountBookingId(String bookingId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_detail_hw_replacement inc WHERE inc.booking_pkid = '" + bookingId + "'"
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

    public Integer getCountFlagZero() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_detail_hw_replacement inc WHERE inc.flag = '0'"
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
