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
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingHardwareDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRmsBookingHardware(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_hardware (booking_pkid, pkid, item_type, item_id, item_pkid, qty, readiness, status, recall, flag, created_date, created_by) VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
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

    public QueryResult updateRmsBookingHardware(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET booking_pkid = ?, pkid = ?, item_type = ?, item_id = ?, item_pkid = ?, qty = ?, readiness = ?, status = ?, recall = ?, flag = ?, created_date = ?, created_by = ?, modified_date = ?, modified_by = ? WHERE id = ?"
            );
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

    public QueryResult updateRmsBookingHardwareForRequestReplacement(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET request_replacement_by = ?, request_replacement_remarks = ?, request_replacement_date = NOW(), status = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardware.getRequestReplacementBy());
            ps.setString(2, rmsbookingHardware.getRequestReplacementRemarks());
            ps.setString(3, rmsbookingHardware.getStatus());
            ps.setString(4, rmsbookingHardware.getId());
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

    public QueryResult updateRmsBookingHardwareForRecallSf(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET recall_sf_by = ?, recall_sf_date = NOW(), status = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardware.getRecallSfBy());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getId());
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

    public QueryResult updateRmsBookingHardwareForFlagAndStatusById(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET  flag = ?, status = ?, modified_date = NOW(), modified_by = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardware.getFlag());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getModifiedBy());
            ps.setString(4, rmsbookingHardware.getId());
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

    public QueryResult deleteRmsBookingHardware(String rmsbookingHardwareId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_hardware WHERE id = '" + rmsbookingHardwareId + "'"
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

    public RmsBookingHardware getRmsBookingHardware(String rmsbookingHardwareId) {
        String sql = "SELECT * FROM rms_booking_hardware WHERE id = '" + rmsbookingHardwareId + "'";
        RmsBookingHardware rmsbookingHardware = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareByPkid(String pkid) {
        String sql = "SELECT * FROM rms_booking_hardware WHERE pkid = '" + pkid + "'";
        RmsBookingHardware rmsbookingHardware = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        return rmsbookingHardware;
    }

    public RmsBookingHardware getRmsBookingHardwareRemarksByBookingPkid(String bookingPkid) {
        String sql = "SELECT ha.item_id FROM rms_booking_detail de, rms_booking_hardware ha WHERE ha.item_type = 'Remarks' AND ha.booking_pkid = de.booking_pkid AND de.booking_pkid = '" + bookingPkid + "'";
        RmsBookingHardware rmsbookingHardware = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingHardware = new RmsBookingHardware();
                rmsbookingHardware.setItemId(rs.getString("item_id"));
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
        return rmsbookingHardware;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareList() {
        String sql = "SELECT * FROM rms_booking_hardware ORDER BY id ASC";
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<RmsBookingHardware>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardware rmsbookingHardware;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareList.add(rmsbookingHardware);
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
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagZero(String bookingPkid) {
        String sql = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.flag = '0'";
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<RmsBookingHardware>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardware rmsbookingHardware;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareList.add(rmsbookingHardware);
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
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListForMotherboardByBookingPkid(String bookingPkid) {
        String sql = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.item_type = 'Motherboard' ORDER BY item_id, ha.flag";
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<RmsBookingHardware>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardware rmsbookingHardware;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareList.add(rmsbookingHardware);
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
        return rmsbookingHardwareList;
    }

    public List<RmsBookingHardware> getRmsBookingHardwareListForOtherHwByBookingPkid(String bookingPkid) {
        String sql = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.item_type NOT IN ('Motherboard','Remarks') ORDER BY ha.item_type, ha.flag";
        List<RmsBookingHardware> rmsbookingHardwareList = new ArrayList<RmsBookingHardware>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingHardware rmsbookingHardware;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                rmsbookingHardwareList.add(rmsbookingHardware);
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
        return rmsbookingHardwareList;
    }

    public QueryResult updateRmsBookingHardwareByPkidAndBookingPkid(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET item_type = ?, item_id = ?, item_pkid = ?, qty = ?, readiness = ?, status = ?, recall = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE booking_pkid = ? AND pkid = ?"
            );

            ps.setString(1, rmsbookingHardware.getItemType());
            ps.setString(2, rmsbookingHardware.getItemId());
            ps.setString(3, rmsbookingHardware.getItemPkid());
            ps.setString(4, rmsbookingHardware.getQty());
            ps.setString(5, rmsbookingHardware.getReadiness());
            ps.setString(6, rmsbookingHardware.getStatus());
            ps.setString(7, rmsbookingHardware.getRecall());
            ps.setString(8, rmsbookingHardware.getFlag());
            ps.setString(9, rmsbookingHardware.getModifiedBy());
            ps.setString(10, rmsbookingHardware.getBookingPkid());
            ps.setString(11, rmsbookingHardware.getPkid());
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

    public Integer getCountBookingId(String bookingId, String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = '" + bookingId + "' AND inc.pkid = '" + pkid + "'"
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

    public Integer getCountHwWithRemarksByBookingPkid(String bookingPkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.item_type = 'Remarks' AND inc.booking_pkid = '" + bookingPkid + "'"
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
