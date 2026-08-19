/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.RmsBookingFunctionalTest;
import com.onsemi.mib.tools.QueryResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zbqb9x
 */
public class RmsBookingFunctionalTestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingFunctionalTestDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingFunctionalTestDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_GET_FUNC_TEST_BY_MODULE = "SELECT * FROM rms_booking_functional_test WHERE group_id = ? AND module = ? ";
    private static final String SQL_GET_COUNT_TEST_RESULT_BY_GROUP_ID = "SELECT COUNT(*) AS count FROM rms_booking_functional_test WHERE group_id = ? AND module = ? ";
    private static final String SQL_GET_COUNT_TEST_RESULT_WITH_STATUS_RELEASE = "SELECT COUNT(*) AS count FROM rms_booking_functional_test WHERE group_id = ? AND module = ? AND final_status = 'Pending Release to Production' ";
    private static final String SQL_DELETE_ITEM_FUNCTIONAL_TEST = "DELETE FROM rms_booking_functional_test WHERE id = ? ";
    private static final String SQL_UPDATE_WINCHESTER_TEST = "UPDATE rms_booking_functional_test SET win_qty=?, win_status=?, win_upload=?, remark=?, final_status=?, flag=?, win_hwid=? WHERE group_id = ? AND module = ? ";
    private static final String SQL_UPDATE_POWER_TEST = "UPDATE rms_booking_functional_test SET ps_qty=?, ps_status=?, ps_upload=?, remark=?, final_status=?, flag=?, ps_hwid=? WHERE group_id= ? AND module = ? ";
    private static final String SQL_UPDATE_BIB_DAQ_TEST = "UPDATE rms_booking_functional_test SET bib_daq_qty = ?, bib_daq_status = ?, bib_daq_upload = ?, remark = ?, final_status = ?, flag = ?, bib_daq_hwid = ? WHERE group_id = ? AND module = ? ";
    private static final String SQL_UPDATE_LEAKAGE_TEST = "UPDATE rms_booking_functional_test SET leak_qty=?, leak_status=?, leak_upload=?, remark=?, final_status=?, flag=?, leak_hwid=? WHERE group_id = ? AND module = ? ";
    private static final String SQL_UPDATE_MANUAL_TEST = "UPDATE rms_booking_functional_test SET manual_status=?, manual_qty=?, remark=?, final_status=?, flag=? WHERE group_id = ? AND module = ? ";
    private static final String SQL_UPDATE_BIB_TEST = "UPDATE rms_booking_functional_test SET bib_qty = ?, bib_status = ?, bib_upload = ?, remark = ?, final_status = ?, flag = ?, bib_hwid = ? WHERE group_id = ? AND module = ? ";
    private static final String SQL_INSERT_BOOKING_FUNCTIONAL_TEST = "INSERT INTO rms_booking_functional_test (group_id, final_status, created_by, created_date, flag, module) VALUES (?, ?, ?, NOW(), ?, ?)";

    public QueryResult insertRmsBookingFunctionalTest(RmsBookingFunctionalTest book) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_BOOKING_FUNCTIONAL_TEST, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getGroupId());
            ps.setString(2, book.getFinalStatus());
            ps.setString(3, book.getCreatedBy());
            ps.setString(4, book.getFlag());
            ps.setString(5, book.getModule());
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    private String getGeneratedKey(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return String.valueOf(rs.getLong(1));
            }
            return null;
        }
    }

    public QueryResult updateBibTest(RmsBookingFunctionalTest book) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BIB_TEST)) {
            ps.setString(1, book.getBibQty());
            ps.setString(2, book.getBibStatus());
            ps.setString(3, book.getBibUpload());
            ps.setString(4, book.getRemark());
            ps.setString(5, book.getFinalStatus());
            ps.setString(6, book.getFlag());
            ps.setString(7, book.getBibHwid());
            ps.setString(8, book.getGroupId());
            ps.setString(9, book.getModule());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateManualTest(RmsBookingFunctionalTest book) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_MANUAL_TEST)) {
            ps.setString(1, book.getManualStatus());
            ps.setString(2, book.getManualQty());
            ps.setString(3, book.getRemark());
            ps.setString(4, book.getFinalStatus());
            ps.setString(5, book.getFlag());
            ps.setString(6, book.getGroupId());
            ps.setString(7, book.getModule());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateLeakageTest(RmsBookingFunctionalTest book) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_LEAKAGE_TEST)) {
            ps.setString(1, book.getLeakQty());
            ps.setString(2, book.getLeakStatus());
            ps.setString(3, book.getLeakUpload());
            ps.setString(4, book.getRemark());
            ps.setString(5, book.getFinalStatus());
            ps.setString(6, book.getFlag());
            ps.setString(7, book.getLeakHwid());
            ps.setString(8, book.getGroupId());
            ps.setString(9, book.getModule());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateBibDaqTest(RmsBookingFunctionalTest book) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_BIB_DAQ_TEST)) {
            ps.setString(1, book.getBibDaqQty());
            ps.setString(2, book.getBibDaqStatus());
            ps.setString(3, book.getBibDaqUpload());
            ps.setString(4, book.getRemark());
            ps.setString(5, book.getFinalStatus());
            ps.setString(6, book.getFlag());
            ps.setString(7, book.getBibDaqHwid());
            ps.setString(8, book.getGroupId());
            ps.setString(9, book.getModule());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updatePowerTest(RmsBookingFunctionalTest book) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_POWER_TEST)) {
            ps.setString(1, book.getPsQty());
            ps.setString(2, book.getPsStatus());
            ps.setString(3, book.getPsUpload());
            ps.setString(4, book.getRemark());
            ps.setString(5, book.getFinalStatus());
            ps.setString(6, book.getFlag());
            ps.setString(7, book.getPsHwid());
            ps.setString(8, book.getGroupId());
            ps.setString(9, book.getModule());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateWinchesterTest(RmsBookingFunctionalTest book) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_WINCHESTER_TEST)) {
            ps.setString(1, book.getWinQty());
            ps.setString(2, book.getWinStatus());
            ps.setString(3, book.getWinUpload());
            ps.setString(4, book.getRemark());
            ps.setString(5, book.getFinalStatus());
            ps.setString(6, book.getFlag());
            ps.setString(7, book.getWinHwid());
            ps.setString(8, book.getGroupId());
            ps.setString(9, book.getModule());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult deleteItemFunctionalTest(String id) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_ITEM_FUNCTIONAL_TEST)) {
            ps.setString(1, id);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public int getCountTestResultByGroupId(String mibItemId, String module) {
        final String sql = SQL_GET_COUNT_TEST_RESULT_WITH_STATUS_RELEASE;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for mibItemId={}, module={}", mibItemId, module, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return 0;
    }

    public int getCountTestResultByGroupIdWithFinalStatusPendingReleaseToProduction(String mibItemId, String module) {
        final String sql = SQL_GET_COUNT_TEST_RESULT_WITH_STATUS_RELEASE;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for mibItemId={}, module={}", mibItemId, module, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return 0;
    }

    public int getCountTestResultByGroupIdUnload(String mibItemId, String module) {
        final String sql = SQL_GET_COUNT_TEST_RESULT_BY_GROUP_ID;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching count for mibItemId={}, module={}", mibItemId, module, e);
            throw new RuntimeException("Database error occurred while fetching count", e);
        }
        return 0;
    }

    public RmsBookingFunctionalTest getFuncTestResultByModule(String groupId, String module) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_FUNC_TEST_BY_MODULE)) {
            ps.setString(1, groupId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentConfig(rs);
                }
            }
        }
        return null;
    }

    private RmsBookingFunctionalTest mapComponentConfig(ResultSet rs) throws SQLException {
        RmsBookingFunctionalTest testResult = new RmsBookingFunctionalTest();
        testResult.setGroupId(rs.getString("group_id"));
        testResult.setLeakHwid(rs.getString("leak_hwid"));
        testResult.setLeakQty(rs.getString("leak_qty"));
        testResult.setLeakStatus(rs.getString("leak_status"));
        testResult.setLeakUpload(rs.getString("leak_upload"));
        testResult.setManualQty(rs.getString("manual_qty"));
        testResult.setManualStatus(rs.getString("manual_status"));
        testResult.setBibHwid(rs.getString("bib_hwid"));
        testResult.setBibQty(rs.getString("bib_qty"));
        testResult.setBibStatus(rs.getString("bib_status"));
        testResult.setBibUpload(rs.getString("bib_upload"));
        testResult.setBibDaqHwid(rs.getString("bib_daq_hwid"));
        testResult.setBibDaqQty(rs.getString("bib_daq_qty"));
        testResult.setBibDaqStatus(rs.getString("bib_daq_status"));
        testResult.setBibDaqUpload(rs.getString("bib_daq_upload"));
        testResult.setPsHwid(rs.getString("ps_hwid"));
        testResult.setPsQty(rs.getString("ps_qty"));
        testResult.setPsStatus(rs.getString("ps_status"));
        testResult.setPsUpload(rs.getString("ps_upload"));
        testResult.setWinHwid(rs.getString("win_hwid"));
        testResult.setWinQty(rs.getString("win_qty"));
        testResult.setWinStatus(rs.getString("win_status"));
        testResult.setWinUpload(rs.getString("win_upload"));
        testResult.setCreatedBy(rs.getString("created_by"));
        testResult.setCreatedDate(rs.getString("created_date"));
        testResult.setFinalStatus(rs.getString("final_status"));
        testResult.setFlag(rs.getString("flag"));
        testResult.setRemark(rs.getString("remark"));
        return testResult;
    }

}