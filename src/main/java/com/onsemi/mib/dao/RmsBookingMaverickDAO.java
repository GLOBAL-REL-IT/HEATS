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
import com.onsemi.mib.model.RmsBookingMaverick;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingMaverickDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingMaverickDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingMaverickDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRmsBookingMaverick(RmsBookingMaverick rmsbookingMaverick) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_maverick (group_id, module, submodule, disposition_1, disposition_remarks_1, disposition_1_by, disposition_1_date, disposition_2, disposition_2_remarks, disposition_2_by, disposition_2_date, status, flag, created_by, created_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rmsbookingMaverick.getGroupId());
            ps.setString(2, rmsbookingMaverick.getModule());
            ps.setString(3, rmsbookingMaverick.getSubmodule());
            ps.setString(4, rmsbookingMaverick.getDisposition1());
            ps.setString(5, rmsbookingMaverick.getDispositionRemarks1());
            ps.setString(6, rmsbookingMaverick.getDisposition1By());
            ps.setString(7, rmsbookingMaverick.getDisposition1Date());
            ps.setString(8, rmsbookingMaverick.getDisposition2());
            ps.setString(9, rmsbookingMaverick.getDisposition2Remarks());
            ps.setString(10, rmsbookingMaverick.getDisposition2By());
            ps.setString(11, rmsbookingMaverick.getDisposition2Date());
            ps.setString(12, rmsbookingMaverick.getStatus());
            ps.setString(13, rmsbookingMaverick.getFlag());
            ps.setString(14, rmsbookingMaverick.getCreatedBy());
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

    public QueryResult updateRmsBookingMaverick(RmsBookingMaverick rmsbookingMaverick) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_maverick SET group_id = ?, module = ?, submodule = ?, disposition_1 = ?, disposition_remarks_1 = ?, disposition_1_by = ?, disposition_1_date = ?, disposition_2 = ?, disposition_2_remarks = ?, disposition_2_by = ?, disposition_2_date = ?, status = ?, flag = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingMaverick.getGroupId());
            ps.setString(2, rmsbookingMaverick.getModule());
            ps.setString(3, rmsbookingMaverick.getSubmodule());
            ps.setString(4, rmsbookingMaverick.getDisposition1());
            ps.setString(5, rmsbookingMaverick.getDispositionRemarks1());
            ps.setString(6, rmsbookingMaverick.getDisposition1By());
            ps.setString(7, rmsbookingMaverick.getDisposition1Date());
            ps.setString(8, rmsbookingMaverick.getDisposition2());
            ps.setString(9, rmsbookingMaverick.getDisposition2Remarks());
            ps.setString(10, rmsbookingMaverick.getDisposition2By());
            ps.setString(11, rmsbookingMaverick.getDisposition2Date());
            ps.setString(12, rmsbookingMaverick.getStatus());
            ps.setString(13, rmsbookingMaverick.getFlag());
            ps.setString(14, rmsbookingMaverick.getCreatedBy());
            ps.setString(15, rmsbookingMaverick.getCreatedDate());
            ps.setString(16, rmsbookingMaverick.getId());
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

    public QueryResult deleteRmsBookingMaverick(String rmsbookingMaverickId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_maverick WHERE id = '" + rmsbookingMaverickId + "'"
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

    public RmsBookingMaverick getRmsBookingMaverick(String rmsbookingMaverickId) {
        String sql = "SELECT * FROM rms_booking_maverick WHERE id = '" + rmsbookingMaverickId + "'";
        RmsBookingMaverick rmsbookingMaverick = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingMaverick = new RmsBookingMaverick();
                rmsbookingMaverick.setId(rs.getString("id"));
                rmsbookingMaverick.setGroupId(rs.getString("group_id"));
                rmsbookingMaverick.setModule(rs.getString("module"));
                rmsbookingMaverick.setSubmodule(rs.getString("submodule"));
                rmsbookingMaverick.setDisposition1(rs.getString("disposition_1"));
                rmsbookingMaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                rmsbookingMaverick.setDisposition1By(rs.getString("disposition_1_by"));
                rmsbookingMaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                rmsbookingMaverick.setDisposition2(rs.getString("disposition_2"));
                rmsbookingMaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                rmsbookingMaverick.setDisposition2By(rs.getString("disposition_2_by"));
                rmsbookingMaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                rmsbookingMaverick.setStatus(rs.getString("status"));
                rmsbookingMaverick.setFlag(rs.getString("flag"));
                rmsbookingMaverick.setCreatedBy(rs.getString("created_by"));
                rmsbookingMaverick.setCreatedDate(rs.getString("created_date"));
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
        return rmsbookingMaverick;
    }

    public List<RmsBookingMaverick> getRmsBookingMaverickList() {
        String sql = "SELECT * FROM rms_booking_maverick ORDER BY id ASC";
        List<RmsBookingMaverick> rmsbookingMaverickList = new ArrayList<RmsBookingMaverick>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingMaverick rmsbookingMaverick;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingMaverick = new RmsBookingMaverick();
                rmsbookingMaverick.setId(rs.getString("id"));
                rmsbookingMaverick.setGroupId(rs.getString("group_id"));
                rmsbookingMaverick.setModule(rs.getString("module"));
                rmsbookingMaverick.setSubmodule(rs.getString("submodule"));
                rmsbookingMaverick.setDisposition1(rs.getString("disposition_1"));
                rmsbookingMaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                rmsbookingMaverick.setDisposition1By(rs.getString("disposition_1_by"));
                rmsbookingMaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                rmsbookingMaverick.setDisposition2(rs.getString("disposition_2"));
                rmsbookingMaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                rmsbookingMaverick.setDisposition2By(rs.getString("disposition_2_by"));
                rmsbookingMaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                rmsbookingMaverick.setStatus(rs.getString("status"));
                rmsbookingMaverick.setFlag(rs.getString("flag"));
                rmsbookingMaverick.setCreatedBy(rs.getString("created_by"));
                rmsbookingMaverick.setCreatedDate(rs.getString("created_date"));
                rmsbookingMaverickList.add(rmsbookingMaverick);
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
        return rmsbookingMaverickList;
    }

    public List<RmsBookingMaverick> getRmsBookingMaverickListUnionWithItemMaverick() {
        String sql = "SELECT mav1.id, mav1.mib_item_id AS id2, mav1.module, mav1.submodule,mav1.disposition_1, mav1.disposition_remarks_1, mav1.disposition_1_by, mav1.disposition_1_date, "
                + "mav1.disposition_2, mav1.disposition_2_remarks, mav1.disposition_2_by, mav1.disposition_2_date, mav1.`status`, mav1.flag, mav1.created_by, mav1.created_date "
                + "FROM item_maverick mav1 "
                + "UNION ALL "
                + "SELECT mav2.id, mav2.group_id AS id2, mav2.module, mav2.submodule,mav2.disposition_1, mav2.disposition_remarks_1, mav2.disposition_1_by, mav2.disposition_1_date, "
                + "mav2.disposition_2, mav2.disposition_2_remarks, mav2.disposition_2_by, mav2.disposition_2_date, mav2.`status`, mav2.flag, mav2.created_by, mav2.created_date "
                + "FROM rms_booking_maverick mav2 "
                + "ORDER BY created_date";
        List<RmsBookingMaverick> rmsbookingMaverickList = new ArrayList<RmsBookingMaverick>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingMaverick rmsbookingMaverick;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingMaverick = new RmsBookingMaverick();
                rmsbookingMaverick.setId(rs.getString("id"));
                rmsbookingMaverick.setId2(rs.getString("id2"));
                rmsbookingMaverick.setModule(rs.getString("module"));
                rmsbookingMaverick.setSubmodule(rs.getString("submodule"));
                rmsbookingMaverick.setDisposition1(rs.getString("disposition_1"));
                rmsbookingMaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                rmsbookingMaverick.setDisposition1By(rs.getString("disposition_1_by"));
                rmsbookingMaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                rmsbookingMaverick.setDisposition2(rs.getString("disposition_2"));
                rmsbookingMaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                rmsbookingMaverick.setDisposition2By(rs.getString("disposition_2_by"));
                rmsbookingMaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                rmsbookingMaverick.setStatus(rs.getString("status"));
                rmsbookingMaverick.setFlag(rs.getString("flag"));
                rmsbookingMaverick.setCreatedBy(rs.getString("created_by"));
                rmsbookingMaverick.setCreatedDate(rs.getString("created_date"));
                rmsbookingMaverickList.add(rmsbookingMaverick);
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
        return rmsbookingMaverickList;
    }
}
