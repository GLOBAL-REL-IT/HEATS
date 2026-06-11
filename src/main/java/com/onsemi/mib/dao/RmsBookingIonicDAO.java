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
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingIonicDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRmsBookingIonic(RmsBookingIonic rmsbookingIonic) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_ionic (group_id, module, bib_result, bib_status, bib_upload, bib_card_result, bib_card_status, bib_card_upload, status, flag, created_by, created_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rmsbookingIonic.getGroupId());
            ps.setString(2, rmsbookingIonic.getModule());
            ps.setString(3, rmsbookingIonic.getBibResult());
            ps.setString(4, rmsbookingIonic.getBibStatus());
            ps.setString(5, rmsbookingIonic.getBibUpload());
            ps.setString(6, rmsbookingIonic.getBibCardResult());
            ps.setString(7, rmsbookingIonic.getBibCardStatus());
            ps.setString(8, rmsbookingIonic.getBibCardUpload());
            ps.setString(9, rmsbookingIonic.getStatus());
            ps.setString(10, rmsbookingIonic.getFlag());
            ps.setString(11, rmsbookingIonic.getCreatedBy());
//            ps.setString(12, rmsbookingIonic.getCreatedDate());
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

    public QueryResult updateRmsBookingIonic(RmsBookingIonic rmsbookingIonic) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_ionic SET group_id = ?, module = ?, bib_result = ?, bib_status = ?, bib_upload = ?, bib_card_result = ?, bib_card_status = ?, bib_card_upload = ?, status = ?, flag = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingIonic.getGroupId());
            ps.setString(2, rmsbookingIonic.getModule());
            ps.setString(3, rmsbookingIonic.getBibResult());
            ps.setString(4, rmsbookingIonic.getBibStatus());
            ps.setString(5, rmsbookingIonic.getBibUpload());
            ps.setString(6, rmsbookingIonic.getBibCardResult());
            ps.setString(7, rmsbookingIonic.getBibCardStatus());
            ps.setString(8, rmsbookingIonic.getBibCardUpload());
            ps.setString(9, rmsbookingIonic.getStatus());
            ps.setString(10, rmsbookingIonic.getFlag());
            ps.setString(11, rmsbookingIonic.getCreatedBy());
            ps.setString(12, rmsbookingIonic.getCreatedDate());
            ps.setString(13, rmsbookingIonic.getId());
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

    public QueryResult updateRmsBookingIonicForUploadFile(RmsBookingIonic rmsbookingIonic) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_ionic SET bib_upload = ?, bib_card_upload = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingIonic.getBibUpload());
            ps.setString(2, rmsbookingIonic.getBibCardUpload());
            ps.setString(3, rmsbookingIonic.getId());
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

    public QueryResult deleteRmsBookingIonic(String rmsbookingIonicId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_ionic WHERE id = '" + rmsbookingIonicId + "'"
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

    public RmsBookingIonic getRmsBookingIonic(String rmsbookingIonicId) {
        String sql = "SELECT * FROM rms_booking_ionic WHERE id = '" + rmsbookingIonicId + "'";
        RmsBookingIonic rmsbookingIonic = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingIonic = new RmsBookingIonic();
                rmsbookingIonic.setId(rs.getString("id"));
                rmsbookingIonic.setGroupId(rs.getString("group_id"));
                rmsbookingIonic.setModule(rs.getString("module"));
                rmsbookingIonic.setBibResult(rs.getString("bib_result"));
                rmsbookingIonic.setBibStatus(rs.getString("bib_status"));
                rmsbookingIonic.setBibUpload(rs.getString("bib_upload"));
                rmsbookingIonic.setBibCardResult(rs.getString("bib_card_result"));
                rmsbookingIonic.setBibCardStatus(rs.getString("bib_card_status"));
                rmsbookingIonic.setBibCardUpload(rs.getString("bib_card_upload"));
                rmsbookingIonic.setStatus(rs.getString("status"));
                rmsbookingIonic.setFlag(rs.getString("flag"));
                rmsbookingIonic.setCreatedBy(rs.getString("created_by"));
                rmsbookingIonic.setCreatedDate(rs.getString("created_date"));
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
        return rmsbookingIonic;
    }

    public RmsBookingIonic getRmsBookingIonicbyGroupId(String groupId) {
        String sql = "SELECT * FROM rms_booking_ionic WHERE group_id = '" + groupId + "'";
        RmsBookingIonic rmsbookingIonic = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingIonic = new RmsBookingIonic();
                rmsbookingIonic.setId(rs.getString("id"));
                rmsbookingIonic.setGroupId(rs.getString("group_id"));
                rmsbookingIonic.setModule(rs.getString("module"));
                rmsbookingIonic.setBibResult(rs.getString("bib_result"));
                rmsbookingIonic.setBibStatus(rs.getString("bib_status"));
                rmsbookingIonic.setBibUpload(rs.getString("bib_upload"));
                rmsbookingIonic.setBibCardResult(rs.getString("bib_card_result"));
                rmsbookingIonic.setBibCardStatus(rs.getString("bib_card_status"));
                rmsbookingIonic.setBibCardUpload(rs.getString("bib_card_upload"));
                rmsbookingIonic.setStatus(rs.getString("status"));
                rmsbookingIonic.setFlag(rs.getString("flag"));
                rmsbookingIonic.setCreatedBy(rs.getString("created_by"));
                rmsbookingIonic.setCreatedDate(rs.getString("created_date"));
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
        return rmsbookingIonic;
    }

    public RmsBookingIonic getRmsBookingIonicbyGroupIdAndModule(String groupId, String status) {
        String sql = "SELECT * FROM rms_booking_ionic WHERE group_id = '" + groupId + "' AND module = '" + status + "'";
        RmsBookingIonic rmsbookingIonic = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingIonic = new RmsBookingIonic();
                rmsbookingIonic.setId(rs.getString("id"));
                rmsbookingIonic.setGroupId(rs.getString("group_id"));
                rmsbookingIonic.setModule(rs.getString("module"));
                rmsbookingIonic.setBibResult(rs.getString("bib_result"));
                rmsbookingIonic.setBibStatus(rs.getString("bib_status"));
                rmsbookingIonic.setBibUpload(rs.getString("bib_upload"));
                rmsbookingIonic.setBibCardResult(rs.getString("bib_card_result"));
                rmsbookingIonic.setBibCardStatus(rs.getString("bib_card_status"));
                rmsbookingIonic.setBibCardUpload(rs.getString("bib_card_upload"));
                rmsbookingIonic.setStatus(rs.getString("status"));
                rmsbookingIonic.setFlag(rs.getString("flag"));
                rmsbookingIonic.setCreatedBy(rs.getString("created_by"));
                rmsbookingIonic.setCreatedDate(rs.getString("created_date"));
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
        return rmsbookingIonic;
    }

    public List<RmsBookingIonic> getRmsBookingIonicList() {
        String sql = "SELECT * FROM rms_booking_ionic ORDER BY id ASC";
        List<RmsBookingIonic> rmsbookingIonicList = new ArrayList<RmsBookingIonic>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingIonic rmsbookingIonic;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingIonic = new RmsBookingIonic();
                rmsbookingIonic.setId(rs.getString("id"));
                rmsbookingIonic.setGroupId(rs.getString("group_id"));
                rmsbookingIonic.setModule(rs.getString("module"));
                rmsbookingIonic.setBibResult(rs.getString("bib_result"));
                rmsbookingIonic.setBibStatus(rs.getString("bib_status"));
                rmsbookingIonic.setBibUpload(rs.getString("bib_upload"));
                rmsbookingIonic.setBibCardResult(rs.getString("bib_card_result"));
                rmsbookingIonic.setBibCardStatus(rs.getString("bib_card_status"));
                rmsbookingIonic.setBibCardUpload(rs.getString("bib_card_upload"));
                rmsbookingIonic.setStatus(rs.getString("status"));
                rmsbookingIonic.setFlag(rs.getString("flag"));
                rmsbookingIonic.setCreatedBy(rs.getString("created_by"));
                rmsbookingIonic.setCreatedDate(rs.getString("created_date"));
                rmsbookingIonicList.add(rmsbookingIonic);
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
        return rmsbookingIonicList;
    }

    public Integer getCountByGroupIdAndModule(String groupId, String status) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_ionic inc WHERE inc.group_id = '" + groupId + "' AND ion.module = '" + status + "'"
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

    public Integer getCountBookingIdFromGroupIdAndModule(String bookingId, String status) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(ion.id) as count FROM rms_booking_ionic ion WHERE SUBSTRING_INDEX(ion.group_id,'/',1) = '" + bookingId + "' AND ion.module = '" + status + "'"
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
