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
                    "INSERT INTO rms_booking_hardware (booking_pkid, pkid, item_type, item_id, item_pkid, qty, readiness, status, recall, flag, created_date, created_by, sub_status) VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
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
            ps.setString(12, rmsbookingHardware.getSubStatus());
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
                    "UPDATE rms_booking_hardware SET  flag = ?, status = ?, modified_date = NOW(), modified_by = ?, sub_status = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardware.getFlag());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getModifiedBy());
            ps.setString(4, rmsbookingHardware.getSubStatus());
            ps.setString(5, rmsbookingHardware.getId());
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

    public QueryResult updateRmsBookingHardwareForFlagAndStatusAndReleaseDateById(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET  flag = ?, status = ?, modified_date = NOW(), modified_by = ?, sub_status = ?, released_date = NOW(), released_by = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardware.getFlag());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getModifiedBy());
            ps.setString(4, rmsbookingHardware.getSubStatus());
            ps.setString(5, rmsbookingHardware.getReleaseBy());
            ps.setString(6, rmsbookingHardware.getId());
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
    
    public QueryResult updateRmsBookingHardwareForFlagAndStatusAndReturnDateById(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET flag = ?, status = ?, modified_date = NOW(), modified_by = ?, sub_status = ?, return_date = NOW(), return_by = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardware.getFlag());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getModifiedBy());
            ps.setString(4, rmsbookingHardware.getSubStatus());
            ps.setString(5, rmsbookingHardware.getReturnBy());
            ps.setString(6, rmsbookingHardware.getId());
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

    public QueryResult updateRmsBookingHardwareForFlagAndStatusByBookingId(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET  flag = ?, status = ?, modified_date = NOW(), modified_by = ?, sub_status = ? WHERE booking_pkid = ?"
            );
            ps.setString(1, rmsbookingHardware.getFlag());
            ps.setString(2, rmsbookingHardware.getStatus());
            ps.setString(3, rmsbookingHardware.getModifiedBy());
            ps.setString(4, rmsbookingHardware.getSubStatus());
            ps.setString(5, rmsbookingHardware.getBookingPkid());
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

    public RmsBookingHardware getRmsBookingHardwareByBookingPkidForLoadCardFlagZero(String bookingPkid) {
        String sql = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.flag = '0' AND status != 'NA' AND ha.item_type = 'Load Card'";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

    public RmsBookingHardware getRmsBookingHardwareByBookingPkidForProgramCardFlagZero(String bookingPkid) {
        String sql = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.flag = '0' AND status != 'NA' AND ha.item_type = 'Program Card'";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

    public RmsBookingHardware getRmsBookingHardwareByBookingPkidAndItemPKid(String bookingPkid, String itemPkid) {
        String sql = "SELECT * FROM rms_booking_hardware WHERE booking_pkid = '" + bookingPkid + "' AND item_pkid = '" + itemPkid + "' AND status = 'Available'";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

    public RmsBookingHardware getRmsBookingHardwareByBookingPkidAndPkid(String bookingPkid, String pkid) {
        String sql = "SELECT * FROM rms_booking_hardware WHERE booking_pkid = '" + bookingPkid + "' AND pkid = '" + pkid + "' AND item_type = 'Motherboard'";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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
                rmsbookingHardware.setSubStatus(rs.getString("sub_status"));
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

    public RmsBookingHardware getRmsBookingHardwareBybookingPkidAndItemTypeAndItemIdAndFlagNE99(String bookingId, String itemType, String itemId) {
        String sql = "SELECT * FROM rms_booking_hardware WHERE booking_pkid = '" + bookingId + "' AND item_type = '" + itemType + "' AND item_id = '" + itemId + "' AND flag != '99'";
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
                rmsbookingHardware.setSubStatus(rs.getString("sub_status"));
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagZeroAndStatusNotNA(String bookingPkid) {
        String sql = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.flag = '0' AND status != 'NA'";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagOneAndStatusNotNA(String bookingPkid) {
        String sql = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.flag = '1' AND status != 'NA'";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagZeroForHwReplacement(String bookingPkid) {
        String sql = "SELECT ha.* "
                + "FROM rms_booking_hardware ha "
                + "LEFT JOIN rms_booking_detail_hw_replacement re ON ha.pkid = re.booking_hw_pkid "
                + "WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.flag = '0' AND ha.item_type IN ('Motherboard', 'Load Card', 'Program Card') "
                + "AND ha.status LIKE 'Not Available%' AND re.booking_hw_pkid IS NULL";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

    public List<RmsBookingHardware> getRmsBookingHardwareListByBookingPkidWithFlagZeroForHwReplacement2(String bookingPkid) {
        String sql = "SELECT ha.* "
                + "FROM rms_booking_hardware ha "
                + "LEFT JOIN rms_booking_detail_hw_replacement re ON ha.pkid = re.booking_hw_pkid "
                + "WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.flag = '0' AND ha.item_type IN ('Motherboard', 'Load Card', 'Program Card') "
                + "AND re.booking_hw_pkid IS NULL";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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
        String sql = "SELECT ha.*,IFNULL(lc_qty,'0') AS lcQty, IFNULL(pc_qty,'0') AS pcQty FROM rms_booking_hardware ha WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.item_type = 'Motherboard' ORDER BY ha.flag, ha.item_id ";
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
                rmsbookingHardware.setSubStatus(rs.getString("sub_status"));
                rmsbookingHardware.setLcQty(rs.getString("lcQty"));
                rmsbookingHardware.setPcQty(rs.getString("pcQty"));
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
        String sql = "SELECT ha.* FROM rms_booking_hardware ha WHERE ha.booking_pkid = '" + bookingPkid + "' AND ha.item_type NOT IN ('Motherboard','Remarks') ORDER BY ha.flag, ha.item_type";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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
                    "UPDATE rms_booking_hardware SET item_type = ?, item_id = ?, item_pkid = ?, qty = ?, readiness = ?, status = ?, recall = ?, flag = ?, modified_date = NOW(), modified_by = ?, sub_status = ? WHERE booking_pkid = ? AND pkid = ?"
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
            ps.setString(10, rmsbookingHardware.getSubStatus());
            ps.setString(11, rmsbookingHardware.getBookingPkid());
            ps.setString(12, rmsbookingHardware.getPkid());
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

    public QueryResult updateRmsBookingHardwareByBookingPkidItemTypeItemIdFlagZero(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET item_type = ?, item_id = ?, item_pkid = ?, qty = ?, readiness = ?, status = ?, recall = ?, flag = ?, modified_date = NOW(), modified_by = ?, sub_status = ? "
                    + "WHERE booking_pkid = ? AND item_type = ? AND item_id = ? AND flag = '0'"
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
            ps.setString(10, rmsbookingHardware.getSubStatus());
            ps.setString(11, rmsbookingHardware.getBookingPkid());
            ps.setString(12, rmsbookingHardware.getItemType());
            ps.setString(13, rmsbookingHardware.getItemId());
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

    public QueryResult updateRmsBookingHardwareSubStatusByPkidAndBookingPkid(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET sub_status = ? WHERE booking_pkid = ? AND pkid = ?"
            );
            ps.setString(1, rmsbookingHardware.getSubStatus());
            ps.setString(2, rmsbookingHardware.getBookingPkid());
            ps.setString(3, rmsbookingHardware.getPkid());
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

    public QueryResult updateRmsBookingHardwareStatus(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET sub_status = ?, flag = ? WHERE booking_pkid = ? AND pkid = ? "
            );
            ps.setString(1, rmsbookingHardware.getSubStatus());
            ps.setString(2, rmsbookingHardware.getFlag());
            ps.setString(3, rmsbookingHardware.getBookingPkid());
            ps.setString(4, rmsbookingHardware.getPkid());
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
    
    public QueryResult updateRmsBookingHardwareSubStatusById(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET sub_status = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingHardware.getSubStatus());
            ps.setString(2, rmsbookingHardware.getId());
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

    public QueryResult updateRmsBookingHardwareLcQtyAndPcQtyByBookingPkidAndPkid(RmsBookingHardware rmsbookingHardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET lc_qty = ?, pc_qty = ? WHERE booking_pkid = ? AND pkid = ?"
            );
            ps.setString(1, rmsbookingHardware.getLcQty());
            ps.setString(2, rmsbookingHardware.getPcQty());
            ps.setString(3, rmsbookingHardware.getBookingPkid());
            ps.setString(4, rmsbookingHardware.getPkid());
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

    public Integer getCountBookingIdFlagZero(String bookingId, String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = '" + bookingId + "' AND inc.pkid = '" + pkid + "' AND inc.flag = '0'"
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

    public Integer getCountBookingIdWithItemTypeAndItemIdAndFlagNE99(String bookingId, String itemType, String itemId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = '" + bookingId + "' AND inc.item_type = '" + itemType + "' AND inc.item_id = '" + itemId + "' AND inc.flag != '99'"
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

    public Integer getCountBookingIdWithItemTypeAndItemIdAndFlagZero(String bookingId, String itemType, String itemId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = '" + bookingId + "' AND inc.item_type = '" + itemType + "' AND inc.item_id = '" + itemId + "' AND inc.flag = '0'"
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

    public Integer getCountBookingIdWithItemTypeAndItemIdFlag99(String bookingId, String itemType, String itemId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = '" + bookingId + "' AND inc.item_type = '" + itemType + "' AND inc.item_id = '" + itemId + "' AND inc.flag = '99'"
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

    public Integer getCountBookingPkidAndItemPkid(String bookingPkid, String itemPkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = '" + bookingPkid + "' AND inc.item_pkid = '" + itemPkid + "' AND inc.status = 'Available'"
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

    public Integer getCountBookingPkidAndPkidForMotherboard(String bookingPkid, String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = '" + bookingPkid + "' AND inc.pkid = '" + pkid + "' AND inc.item_type = 'Motherboard'"
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

    public Integer getCountMotherboardByBookingPkidAndFlagNot99(String bookingPkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = '" + bookingPkid + "' AND inc.item_type = 'Motherboard' AND inc.flag != '99'"
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

    public Integer getCountMotherboardByBookingPkidAndPendingRelease(String bookingPkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.booking_pkid = '" + bookingPkid + "' AND inc.item_type = 'Motherboard' AND inc.sub_status = 'Pending Release to Production'"
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

    public Integer getCountMotherboardReturnFromProduction() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_hardware inc WHERE inc.item_type = 'Motherboard' AND inc.sub_status LIKE 'Return from Production%' AND inc.flag = '2'"
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

    public Integer checkMotherboardData(String bookingId) {
        Integer count = 0;
        String sql = "SELECT COUNT(*) as count FROM rms_booking_hardware WHERE booking_pkid = '" + bookingId + "' AND item_type = 'Motherboard'";
//        LOGGER.info("QUERY KITA CHECK DEKAT SINI >> checkMotherboardData ::::: " + sql);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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

    public Integer checkCardData(String bookingId) {
        Integer count = 0;
        String sql = "SELECT COUNT(*) as count FROM rms_booking_hardware WHERE booking_pkid = '" + bookingId + "' AND item_type != 'Motherboard' AND qty != 0";
//        LOGGER.info("QUERY KITA CHECK DEKAT SINI >> checkCardData ::::: " + sql);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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

    public String getSptsPkidForItemIdLC(String bookingId) {
        String data = "";
        String sql = "SELECT * FROM item WHERE spts_pkid = (SELECT item_pkid FROM rms_booking_hardware WHERE booking_pkid = '" + bookingId + "' AND item_type = 'Load Card' AND qty != 0 LIMIT 1)";
//        LOGGER.info("QUERY KITA CHECK DEKAT SINI >> getSptsPkidForItemIdLC Load Card ::::: " + sql);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data = rs.getString("id");
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
        return data;
    }

    public String getSptsPkidForItemIdMb(String bookingId, String pkId) {
        String data = "";
        String sql = "SELECT item_pkid FROM rms_booking_hardware WHERE booking_pkid = '" + bookingId + "' AND item_type = 'Motherboard' AND pkid = '" + pkId + "'";
//        LOGGER.info("QUERY KITA CHECK DEKAT SINI >> getSptsPkidForItemIdMb MOTHERBOARD ::::: " + sql);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data = rs.getString("item_pkid");
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
        return data;
    }
    
    public String getLatestStatus(String bookingId, String pkId) {
        String data = "";
        String sql = "SELECT status FROM rms_booking_hardware WHERE booking_pkid = '" + bookingId + "' AND item_type = 'Motherboard' AND pkid = '" + pkId + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data = rs.getString("status");
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
        return data;
    }

    public QueryResult updateRmsBookingHardwareFunctionalTestStatus(String status, String bookId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_hardware SET status = ? WHERE id = ?"
            );
            ps.setString(1, status);
            ps.setString(2, bookId);
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

    public String getMbMibItemIdFromGroupId(String groupId) {
        String data = "";
        String sql = "SELECT it.id AS item_id FROM rms_booking_hardware_group rms "
                + "INNER JOIN item it ON it.spts_pkid = rms.item_pkid "
                + "INNER JOIN item_activity_config ac ON ac.mib_item_id = it.id "
                + "WHERE rms.item_type = 'BIB' AND rms.group_id = '" + groupId + "' ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data = rs.getString("item_id");
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
        return data;
    }

    public String getLcMibItemIdFromGroupId(String groupId) {
        String data = "";
        String sql = "SELECT it.id AS item_id FROM rms_booking_hardware_group rms "
                + "INNER JOIN item it ON it.spts_pkid = rms.item_pkid "
                + "INNER JOIN item_activity_config ac ON ac.mib_item_id = it.id "
                + "WHERE rms.item_type = 'BIB Card' AND rms.group_id = '" + groupId + "' GROUP BY item_id ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data = rs.getString("item_id");
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
        return data;
    }

    public List<RmsBookingHardware> getRmsHardwareList(String id) {
        String sql = "SELECT * FROM rms_booking_hardware WHERE booking_pkid = '" + id + "' AND status = 'Available' ";
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
                rmsbookingHardware.setLcQty(rs.getString("lc_qty"));
                rmsbookingHardware.setPcQty(rs.getString("pc_qty"));
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

}
