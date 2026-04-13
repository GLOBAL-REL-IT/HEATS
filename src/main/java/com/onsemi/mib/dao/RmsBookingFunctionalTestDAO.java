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
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingFunctionalTestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }
    
    public QueryResult insertRmsBookingFunctionalTest(RmsBookingFunctionalTest book) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_functional_test (group_id, final_status, created_by, created_date, flag) "
                            + "VALUES (?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, book.getGroupId());
            ps.setString(2, book.getFinalStatus());
            ps.setString(3, book.getCreatedBy());
            ps.setString(4, book.getFlag());

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
    
    public QueryResult updateBibTest(RmsBookingFunctionalTest book) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_functional_test SET bib_qty = ?, bib_status = ?, bib_upload = ?, remark = ?, final_status = ?, flag = ? WHERE group_id = ? "
            );
            ps.setString(1, book.getBibQty());
            ps.setString(2, book.getBibStatus());
            ps.setString(3, book.getBibUpload());
            ps.setString(4, book.getRemark());
            ps.setString(5, book.getFinalStatus());
            ps.setString(6, book.getFlag());
            ps.setString(7, book.getGroupId());
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
    
    public QueryResult updateManualTest(RmsBookingFunctionalTest book) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_functional_test SET manual_status=?, manual_qty=?, remark=?, final_status=?, flag=? WHERE group_id = ? "
            );
            ps.setString(1, book.getManualStatus());
            ps.setString(2, book.getManualQty());
            ps.setString(3, book.getRemark());
            ps.setString(4, book.getFinalStatus());
            ps.setString(5, book.getFlag());
            ps.setString(6, book.getGroupId());
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
    
    public QueryResult updateLeakageTest(RmsBookingFunctionalTest book) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_functional_test SET leak_qty=?, leak_status=?, leak_upload=?, remark=?, final_status=?, flag=? WHERE group_id = ? "
            );
            ps.setString(1, book.getLeakQty());
            ps.setString(2, book.getLeakStatus());
            ps.setString(3, book.getLeakUpload());
            ps.setString(4, book.getRemark());
            ps.setString(5, book.getFinalStatus());
            ps.setString(6, book.getFlag());
            ps.setString(7, book.getGroupId());
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
    
    public QueryResult updatePowerTest(RmsBookingFunctionalTest book) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_functional_test SET ps_qty=?, ps_status=?, ps_upload=?, remark=?, final_status=?, flag=? WHERE group_id = ? "
            );
            ps.setString(1, book.getPsQty());
            ps.setString(2, book.getPsStatus());
            ps.setString(3, book.getPsUpload());
            ps.setString(4, book.getRemark());
            ps.setString(5, book.getFinalStatus());
            ps.setString(6, book.getFlag());
            ps.setString(7, book.getGroupId());
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
    
    public QueryResult updateWinchesterTest(RmsBookingFunctionalTest book) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_functional_test SET win_qty=?, win_status=?, win_upload=?, remark=?, final_status=?, flag=? WHERE group_id = ? "
            );
            ps.setString(1, book.getWinQty());
            ps.setString(2, book.getWinStatus());
            ps.setString(3, book.getWinUpload());
            ps.setString(4, book.getRemark());
            ps.setString(5, book.getFinalStatus());
            ps.setString(6, book.getFlag());
            ps.setString(7, book.getGroupId());
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
    
    public QueryResult deleteItemFunctionalTest(String id) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_functional_test WHERE id = '" + id + "'"
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
    
    public Integer getCountTestResultByGroupId(String groupId) {
        Integer count = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_functional_test WHERE group_id = '" + groupId + "' "
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