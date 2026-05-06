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

    public QueryResult insertManual03(String itemId, String qtyId, String dutId, String ctype, String cpntName, String cpntValue, String lower, String upper, String percentage, String status, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        String sql = "";
        ManualTest test = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test_l3 (mib_item_id, l1_id, l2_id, component_type, component_name, component_value, lower_limit, upper_limit, percentage, status, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemId);
            ps.setString(2, qtyId);
            ps.setString(3, dutId);
            ps.setString(4, ctype);
            ps.setString(5, cpntName);
            ps.setString(6, cpntValue);
            ps.setString(7, lower);
            ps.setString(8, upper);
            ps.setString(9, percentage);
            ps.setString(10, status);
            ps.setString(11, user);
            ps.setString(12, flag);
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
    
    public QueryResult insertManualBeforeLoading(String itemId, String qtyId, String dutId, String ctype, String cpntName, String cpntValue, String lower, String upper, String percentage, String status, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        String sql = "";
        ManualTest test = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test_before_result (mib_item_id, l1_id, l2_id, component_type, component_name, component_value, lower_limit, upper_limit, percentage, status, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemId);
            ps.setString(2, qtyId);
            ps.setString(3, dutId);
            ps.setString(4, ctype);
            ps.setString(5, cpntName);
            ps.setString(6, cpntValue);
            ps.setString(7, lower);
            ps.setString(8, upper);
            ps.setString(9, percentage);
            ps.setString(10, status);
            ps.setString(11, user);
            ps.setString(12, flag);
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

    public ManualTest getComponentConfig(String configId) {
        String sql = "SELECT * FROM item_manual_test WHERE config_id = '" + configId + "'";
        ManualTest manual = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                manual = new ManualTest();
                manual.setId(rs.getString("id"));
                manual.setMibItemId(rs.getString("mib_item_id"));
                manual.setConfigId(rs.getString("config_id"));
                manual.setQty(rs.getString("qty"));
                manual.setDut(rs.getString("dut"));
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
        return manual;
    }

    public QueryResult updateConfigId(String configId, String manualId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_manual_test SET config_id = ? WHERE id = ?"
            );
//            ps.setString(1, itemactivityConfig.getMibItemId());
            ps.setString(1, configId);
            ps.setString(2, manualId);
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

    public QueryResult deleteConfigId(String itemId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_manual_test SET flag = 0 WHERE mib_item_id = ?"
            );
            ps.setString(1, itemId);
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

    public QueryResult deleteConfigLevel01(String itemId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_manual_test_l1 SET flag = 0 WHERE mib_item_id = ?"
            );
            ps.setString(1, itemId);
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

    public QueryResult deleteConfigLevel02(String itemId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_manual_test_l2 SET flag = 0 WHERE mib_item_id = ?"
            );
            ps.setString(1, itemId);
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

    public QueryResult deleteConfigLevel03(String itemId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_manual_test_l3 SET flag = 0 WHERE mib_item_id = ?"
            );
            ps.setString(1, itemId);
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

    public Integer getQuantity(String mibItemId) {
        Integer count = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item_activity_config con WHERE con.mib_item_id = '" + mibItemId + "'"
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

    public Integer getDut(String mibItemId) {
        Integer count = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item_activity_config con WHERE con.mib_item_id = '" + mibItemId + "'"
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

    public List<ManualTest> getAllComponentConfig(String mibItemId) {
        String sql = "SELECT * FROM item_manual_test_l3 WHERE mib_item_id = '" + mibItemId + "' AND flag = '1' GROUP BY component_name ";
        List<ManualTest> manualList = new ArrayList<ManualTest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ManualTest manualtest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                manualtest = new ManualTest();
                manualtest.setMibItemId(rs.getString("mib_item_id"));
                manualtest.setComponentType(rs.getString("component_type"));
                manualtest.setComponentName(rs.getString("component_name"));
                manualtest.setComponentValue(rs.getString("component_value"));
                manualtest.setPercentage(rs.getString("percentage"));
                manualtest.setLowerLimit(rs.getString("lower_limit"));
                manualtest.setUpperLimit(rs.getString("upper_limit"));
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

    public QueryResult insertManualTestBeforeLoading(String itemId, String configId, String qty, String dut, String cpnt, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test_before (mib_item_id, config_id, qty, dut, component, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemId);
            ps.setString(2, configId);
            ps.setString(3, qty);
            ps.setString(4, dut);
            ps.setString(5, cpnt);
            ps.setString(6, user);
            ps.setString(7, flag);
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

    public QueryResult insertManualTestBeforeLoadingSub(String itemId, String pkId, String dut, String compType, String compName, String compValue, String percentage, String lower, String upper, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test_before_sub (mib_item_id, pk_id, dut, comp_type, comp_name, comp_value, percentage, lower, upper, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemId);
            ps.setString(2, pkId);
            ps.setString(3, dut);
            ps.setString(4, compType);
            ps.setString(5, compName);
            ps.setString(6, compValue);
            ps.setString(7, percentage);
            ps.setString(8, lower);
            ps.setString(9, upper);
            ps.setString(10, user);
            ps.setString(11, flag);
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

    public QueryResult insertManualTestAfterLoading(String itemId, String qty, String dut, String cpnt, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test_after (mib_item_id, qty, dut, component, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
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

    public QueryResult insertManualTestAfterLoadingSub(String itemId, String pkId, String dut, String compName, String compValue, String percentage, String lower, String upper, String user, String flag) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_manual_test_after_sub (mib_item_id, pk_id, dut, comp_name, comp_value, percentage, lower, upper, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemId);
            ps.setString(2, pkId);
            ps.setString(3, dut);
            ps.setString(4, compName);
            ps.setString(5, compValue);
            ps.setString(6, percentage);
            ps.setString(7, lower);
            ps.setString(8, upper);
            ps.setString(9, user);
            ps.setString(10, flag);
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

    public ManualTest getComponentConfigBefore(String configId) {
        String sql = "SELECT * FROM item_manual_test_before WHERE config_id = '" + configId + "'";
        ManualTest manual = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                manual = new ManualTest();
                manual.setId(rs.getString("id"));
                manual.setMibItemId(rs.getString("mib_item_id"));
                manual.setConfigId(rs.getString("config_id"));
                manual.setQty(rs.getString("qty"));
                manual.setDut(rs.getString("dut"));
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
        return manual;
    }

    public ManualTest getComponentConfigBeforeByItemId(String configId) {
        String sql = "SELECT * FROM item_manual_test_before WHERE mib_item_id = '" + configId + "'";
        ManualTest manual = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                manual = new ManualTest();
                manual.setId(rs.getString("id"));
                manual.setMibItemId(rs.getString("mib_item_id"));
                manual.setConfigId(rs.getString("config_id"));
                manual.setQty(rs.getString("qty"));
                manual.setDut(rs.getString("dut"));
                manual.setComponent(rs.getString("component"));
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
        return manual;
    }

    public QueryResult updateConfigIdBeforeLoading(String configId, String manualId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_manual_test_before SET config_id = ? WHERE id = ?"
            );
            ps.setString(1, configId);
            ps.setString(2, manualId);
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

    public List<ManualTest> getAllComponentConfigBefore(String mibItemId) {
        String sql = "SELECT * FROM item_manual_test_before_sub WHERE mib_item_id = '" + mibItemId + "' AND flag = '1' ";
        List<ManualTest> manualList = new ArrayList<ManualTest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ManualTest manualtest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                manualtest = new ManualTest();
                manualtest.setMibItemId(rs.getString("mib_item_id"));
                manualtest.setComponentType(rs.getString("comp_type"));
                manualtest.setComponentName(rs.getString("comp_name"));
                manualtest.setComponentValue(rs.getString("comp_value"));
                manualtest.setPercentage(rs.getString("percentage"));
                manualtest.setLowerLimit(rs.getString("lower"));
                manualtest.setUpperLimit(rs.getString("upper"));
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

    public Integer getManualTestCurrentRecord(String mibItemId) {
        Integer count = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item_manual_test_before WHERE mib_item_id = '" + mibItemId + "'"
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

    public Integer getConfigIdByItemId(String mibItemId) {
        Integer count = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id FROM item_manual_test_before WHERE mib_item_id = '" + mibItemId + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("id");
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

    public Integer getManualTestCurrentRecordSub(String mibItemId) {
        Integer count = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item_manual_test_before_sub WHERE mib_item_id = '" + mibItemId + "'"
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
    
    public QueryResult updateItemActivityConfig(String qty, String dut, String cmp, String id) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_manual_test_before SET qty = ?, dut = ?, component = ? WHERE id = ?"
            );
            ps.setString(1, qty);
            ps.setString(2, dut);
            ps.setString(3, cmp);
            ps.setString(4, id);
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
    
    public QueryResult removeCurrentDataBefore(String configId, String itemId) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE item_manual_test_before_sub SET flag = '0' WHERE mib_item_id = '"+itemId+"' AND pk_id = '"+configId+"' ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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