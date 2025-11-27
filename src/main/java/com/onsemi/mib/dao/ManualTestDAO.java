/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import com.onsemi.mib.model.ManualTest;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zbqb9x
 */
public class ManualTestDAO {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ManualTestDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ManualTestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }
    
    public QueryResult insertItemActivityConfig(ManualTest itemactivityConfig) {
        QueryResult queryResult = new QueryResult();
        return queryResult;
    }
    
    public QueryResult insertManualTest(String itemId, String qty, String dut, String cpnt, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        String sql = "";
        ManualTest test = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test (mib_item_id, qty, dut, component, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemId);
            ps.setString(2, qty);
            ps.setString(3, dut);
            ps.setString(4, cpnt);
            ps.setString(5, user);
            ps.setString(6, flag);
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
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
        return queryResult;
    }
    
    public QueryResult insertManual01(String itemId, String qtyNo, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        String sql = "";
        ManualTest test = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test_l1 (mib_item_id, qty_no, created_by, created_date, flag) VALUES (?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemId);
            ps.setString(2, qtyNo);
            ps.setString(3, user);
            ps.setString(4, flag);
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
                LOGGER.info("MANUAL 01 >>> " + rs.getInt(1));
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
        return queryResult;
    }
    
    public QueryResult insertManual02(String itemId, String qtyId, String dutNo, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        String sql = "";
        ManualTest test = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test_l2 (mib_item_id, l1_id, dut_no, created_by, created_date, flag) VALUES (?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemId);
            ps.setString(2, qtyId);
            ps.setString(3, dutNo);
            ps.setString(4, user);
            ps.setString(5, flag);
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
                LOGGER.info("MANUAL 02 >>> " + rs.getInt(1));
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
        return queryResult;
    }
    
    public QueryResult insertManual03(String itemId, String qtyId, String dutId, String cpntName, String cpntValue, String lower, String upper, String percentage, String status, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        String sql = "";
        ManualTest test = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test_l3 (mib_item_id, l1_id, l2_id, component_name, component_value, lower_limit, upper_limit, percentage, status, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemId);
            ps.setString(2, qtyId);
            ps.setString(3, dutId);
            ps.setString(4, cpntName);
            ps.setString(5, cpntValue);
            ps.setString(6, lower);
            ps.setString(7, upper);
            ps.setString(8, percentage);
            ps.setString(9, status);
            ps.setString(10, user);
            ps.setString(11, flag);
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
                LOGGER.info("MANUAL 03 >>> " + rs.getInt(1));
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
        return queryResult;
    }
    
    public List<ManualTest> getManualTestConfig() {
        String sql = "SELECT * FROM item_activity_config ORDER BY id ASC";
        List<ManualTest> manualList = new ArrayList<ManualTest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ManualTest manualtest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                manualtest = new ManualTest();
                manualtest.setId(rs.getString("id"));
//                itemactivityConfig.setMibItemId(rs.getString("mib_item_id"));
//                itemactivityConfig.setVi(rs.getString("vi"));
//                itemactivityConfig.setBibTest(rs.getString("bib_test"));
//                itemactivityConfig.setManualTest(rs.getString("manual_test"));
//                itemactivityConfig.setLeakageTest(rs.getString("leakage_test"));
//                itemactivityConfig.setPsLeakageTest(rs.getString("ps_leakage_test"));
//                itemactivityConfig.setCreatedBy(rs.getString("created_by"));
//                itemactivityConfig.setCreatedDate(rs.getString("created_date"));
//                itemactivityConfig.setStatus(rs.getString("status"));
//                itemactivityConfig.setFlag(rs.getString("flag"));
                manualList.add(manualtest);
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
        return manualList;
    }
    
}