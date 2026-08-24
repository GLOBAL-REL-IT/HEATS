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
//    private final Connection conn;
    private final DataSource dataSource;

    public ManualTestDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemActivityConfig(ManualTest itemactivityConfig) {
        QueryResult queryResult = new QueryResult();
        return queryResult;
    }

    private static final String INSERT_MANUAL_TEST_SQL = "INSERT INTO item_manual_test (mib_item_id, qty, dut, component, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, NOW(), ?)";
    private static final String INSERT_MANUAL_L1_SQL = "INSERT INTO item_manual_test_l1 (mib_item_id, qty_no, created_by, created_date, flag) VALUES (?, ?, ?, NOW(), ?)";
    private static final String INSERT_MANUAL_L2_SQL = "INSERT INTO item_manual_test_l2 (mib_item_id, l1_id, dut_no, created_by, created_date, flag) VALUES (?, ?, ?, ?, NOW(), ?)";
    private static final String INSERT_MANUAL_L3_SQL = "INSERT INTO item_manual_test_l3 (mib_item_id, l1_id, l2_id, component_type, component_name, component_value, lower_limit, upper_limit, percentage, status, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
    private static final String INSERT_MANUAL_RESULT = "INSERT INTO item_manual_test_result (mib_item_id, l1_id, l2_id, component_type, component_name, component_value, lower_limit, upper_limit, percentage, status, created_by, created_date, flag, module) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?)";
    private static final String INSERT_MANUAL_TEST_PROD = "INSERT INTO item_manual_test_prod (mib_item_id, config_id, qty, dut, component, created_by, created_date, flag, module) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, ?)";

    private static final String INSERT_MANUAL_TEST_PROD_DATA = "INSERT INTO item_manual_test_prod (mib_item_id, qty, dut, component, created_by, created_date, flag, module) VALUES (?, ?, ?, ?, ?, NOW(), ?, ?)";
    private static final String INSERT_MANUAL_TEST_SUB = "INSERT INTO item_manual_test_sub (mib_item_id, pk_id, dut, comp_type, comp_name, comp_value, percentage, lower, upper, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
    private static final String INSERT_MANUAL_TEST_SUB_DATA = "INSERT INTO item_manual_test_sub (mib_item_id, pk_id, dut, comp_name, comp_value, percentage, lower, upper, created_by, created_date, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";

    public QueryResult insertManualTest(String itemId, String qty, String dut, String component, String user, String flag) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MANUAL_TEST_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemId);
            ps.setString(2, qty);
            ps.setString(3, dut);
            ps.setString(4, component);
            ps.setString(5, user);
            ps.setString(6, flag);
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    public QueryResult insertManual01(String itemId, String qtyNo, String user, String flag) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MANUAL_L1_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemId);
            ps.setString(2, qtyNo);
            ps.setString(3, user);
            ps.setString(4, flag);
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    public QueryResult insertManual02(String itemId, String qtyId, String dutNo, String user, String flag) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MANUAL_L2_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemId);
            ps.setString(2, qtyId);
            ps.setString(3, dutNo);
            ps.setString(4, user);
            ps.setString(5, flag);
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    public QueryResult insertManual03(String itemId, String qtyId, String dutId, String ctype, String cpntName, String cpntValue, String lower, String upper, String percentage, String status, String user, String flag) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MANUAL_L3_SQL, Statement.RETURN_GENERATED_KEYS)) {
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
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    public QueryResult insertManualResult(String itemId, String qtyId, String dutId, String ctype, String cpntName, String cpntValue, String lower, String upper, String percentage, String status, String user, String flag, String module) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MANUAL_RESULT, Statement.RETURN_GENERATED_KEYS)) {
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
            ps.setString(13, module);
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    public QueryResult insertManualTestProd(String itemId, String configId, String qty, String dut, String component, String user, String flag, String module) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MANUAL_TEST_PROD, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemId);
            ps.setString(2, configId);
            ps.setString(3, qty);
            ps.setString(4, dut);
            ps.setString(5, component);
            ps.setString(6, user);
            ps.setString(7, flag);
            ps.setString(8, module);
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    public QueryResult insertManualTestProdData(String itemId, String qty, String dut, String cpnt, String user, String flag, String module) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MANUAL_TEST_PROD_DATA, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemId);
            ps.setString(2, qty);
            ps.setString(3, dut);
            ps.setString(4, cpnt);
            ps.setString(5, user);
            ps.setString(6, flag);
            ps.setString(7, module);
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    public QueryResult insertManualTestSub(String itemId, String pkId, String dut, String compType, String compName, String compValue, String percentage, String lower, String upper, String user, String flag) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MANUAL_TEST_SUB, Statement.RETURN_GENERATED_KEYS)) {
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
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }

    public QueryResult insertManualTestSubData(String itemId, String pkId, String dut, String compName, String compValue, String percentage, String lower, String upper, String user, String flag, String module) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_MANUAL_TEST_SUB_DATA, Statement.RETURN_GENERATED_KEYS)) {
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
            ps.setString(11, module);
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

    private static final String GET_MANUAL_TEST_CONFIG_SQL = "SELECT id FROM item_activity_config ORDER BY id ASC";
    private static final String GET_COMPONENT_CONFIG_SQL = "SELECT id, mib_item_id, config_id, qty, dut FROM item_manual_test WHERE config_id = ? ";
    private static final String GET_COMPONENT_L3_BY_MODULE = "SELECT id, mib_item_id, config_id, qty, dut FROM item_manual_test WHERE config_id = ? AND module = ? ";
    private static final String GET_ALL_COMPONENT_CONFIG_SQL = "SELECT mib_item_id, component_type, component_name, component_value, percentage, lower_limit, upper_limit FROM item_manual_test_l3 WHERE mib_item_id = ? AND flag = '1' GROUP BY component_name ";
    private static final String GET_ALL_COMPONENT_RESULT_BY_MODULE = "SELECT DISTINCT mib_item_id, component_type, component_name, component_value, percentage, lower_limit, upper_limit FROM item_manual_test_result WHERE mib_item_id = ? AND flag = '1' AND module = ? ";
    private static final String GET_ALL_COMPONENT_CONFIG_SUB = "SELECT mib_item_id, comp_type, comp_name, comp_value, percentage, lower, upper FROM item_manual_test_sub WHERE mib_item_id = ? AND flag = '1' ";

    public List<ManualTest> getManualTestConfig() throws SQLException {
        List<ManualTest> manualList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_MANUAL_TEST_CONFIG_SQL); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ManualTest manualTest = new ManualTest();
                manualTest.setId(rs.getString("id"));
                manualList.add(manualTest);
            }
        }
        return manualList;
    }

    public List<ManualTest> getAllComponentConfig(String mibItemId) throws SQLException {
        List<ManualTest> manualList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_COMPONENT_CONFIG_SQL)) {
            ps.setString(1, mibItemId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    manualList.add(mapComponentResult(rs));
                }
            }
        }
        return manualList;
    }

    public List<ManualTest> getAllComponentConfigByModule(String mibItemId, String module) throws SQLException {
        List<ManualTest> manualList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_COMPONENT_RESULT_BY_MODULE)) {
            ps.setString(1, mibItemId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    manualList.add(mapComponentResult(rs));
                }
            }
        }
        return manualList;
    }

    public List<ManualTest> getAllComponentConfigSub(String mibItemId) throws SQLException {
        List<ManualTest> manualList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_COMPONENT_CONFIG_SUB)) {
            ps.setString(1, mibItemId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    manualList.add(mapComponentSub(rs));
                }
            }
        }
        return manualList;
    }

    public ManualTest getComponentConfig(String configId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_COMPONENT_CONFIG_SQL)) {
            ps.setString(1, configId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentConfig(rs);
                }
            }
        }
        return null;
    }

    public ManualTest getComponentConfigByModule(String configId, String module) {
        if (configId == null || configId.trim().isEmpty()) {
            throw new IllegalArgumentException("configId must not be null or empty");
        }
        if (module == null || module.trim().isEmpty()) {
            throw new IllegalArgumentException("module must not be null or empty");
        }

        final String sql = "SELECT id, mib_item_id, config_id, qty, dut FROM item_manual_test_prod WHERE config_id = ? AND module = ? ";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, configId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentConfig(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching ManualTest for configId={} and module={}", configId, module, e);
            throw new RuntimeException("Database error occurred while fetching component config", e);
        }
        return null;
    }

    public ManualTest getComponentConfigByItemIdAndModule(String mibItemId, String module) {
        if (mibItemId == null || mibItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("mibItemId must not be null or empty");
        }
        if (module == null || module.trim().isEmpty()) {
            throw new IllegalArgumentException("module must not be null or empty");
        }

        final String sql = "SELECT id, mib_item_id, config_id, qty, dut, component FROM item_manual_test_prod WHERE mib_item_id = ? AND module = ? ";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentConfig02(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching ManualTest for mibItemId={} and module={}", mibItemId, module, e);
            throw new RuntimeException("Database error occurred while fetching component config", e);
        }
        return null;
    }

    private ManualTest mapComponentConfig(ResultSet rs) throws SQLException {
        ManualTest manual = new ManualTest();
        manual.setId(rs.getString("id"));
        manual.setMibItemId(rs.getString("mib_item_id"));
        manual.setConfigId(rs.getString("config_id"));
        manual.setQty(rs.getString("qty"));
        manual.setDut(rs.getString("dut"));
        return manual;
    }

    private ManualTest mapComponentResult(ResultSet rs) throws SQLException {
        ManualTest manualTest = new ManualTest();
        manualTest.setMibItemId(rs.getString("mib_item_id"));
        manualTest.setComponentType(rs.getString("component_type"));
        manualTest.setComponentName(rs.getString("component_name"));
        manualTest.setComponentValue(rs.getString("component_value"));
        manualTest.setPercentage(rs.getString("percentage"));
        manualTest.setLowerLimit(rs.getString("lower_limit"));
        manualTest.setUpperLimit(rs.getString("upper_limit"));
        return manualTest;
    }

    private ManualTest mapComponentConfig02(ResultSet rs) throws SQLException {
        ManualTest manual = new ManualTest();
        manual.setId(rs.getString("id"));
        manual.setMibItemId(rs.getString("mib_item_id"));
        manual.setConfigId(rs.getString("config_id"));
        manual.setQty(rs.getString("qty"));
        manual.setDut(rs.getString("dut"));
        manual.setComponent(rs.getString("component"));
        return manual;
    }

    private ManualTest mapComponentSub(ResultSet rs) throws SQLException {
        ManualTest manual = new ManualTest();
        manual.setMibItemId(rs.getString("mib_item_id"));
        manual.setComponentType(rs.getString("comp_type"));
        manual.setComponentName(rs.getString("comp_name"));
        manual.setComponentValue(rs.getString("comp_value"));
        manual.setPercentage(rs.getString("percentage"));
        manual.setLowerLimit(rs.getString("lower"));
        manual.setUpperLimit(rs.getString("upper"));
        return manual;
    }

    private static final String UPDATE_CONFIG_ID_SQL = "UPDATE item_manual_test SET config_id = ? WHERE id = ? ";
    private static final String DELETE_CONFIG_ID_SQL = "UPDATE item_manual_test SET flag = 0 WHERE mib_item_id = ? ";
    private static final String DELETE_CONFIG_01_SQL = "UPDATE item_manual_test_l1 SET flag = 0 WHERE mib_item_id = ? ";
    private static final String DELETE_CONFIG_02_SQL = "UPDATE item_manual_test_l2 SET flag = 0 WHERE mib_item_id = ? ";
    private static final String DELETE_CONFIG_03_SQL = "UPDATE item_manual_test_l3 SET flag = 0 WHERE mib_item_id = ? ";
    private static final String UPDATE_PROD_BY_MODULE = "UPDATE item_manual_test_prod SET config_id = ? WHERE id = ? AND module = ? ";
    private static final String UPDATE_PROD_FOR_QTY_DUT_COMP = "UPDATE item_manual_test_prod SET qty = ?, dut = ?, component = ? WHERE id = ? ";
    private static final String UPDATE_PROD_FOR_COMP_BY_MODULE = "UPDATE item_manual_test_prod SET qty = ?, dut = ?, component = ? WHERE id = ? AND module = ? ";
    private static final String DELETE_SUB_BY_ITEMID = "UPDATE item_manual_test_sub SET flag = '0' WHERE mib_item_id = ? AND pk_id = ? ";

    public int updateConfigId(String configId, String manualId) {
        if (configId == null || configId.trim().isEmpty()) {
            throw new IllegalArgumentException("configId must not be null or empty");
        }
        if (manualId == null || manualId.trim().isEmpty()) {
            throw new IllegalArgumentException("manualId must not be null or empty");
        }

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_CONFIG_ID_SQL)) {
            ps.setString(1, configId);
            ps.setString(2, manualId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating configId={} for manualId={}", configId, manualId, e);
            throw new RuntimeException("Database update failed", e);
        }
    }

    public int updateConfigProdByModule(String configId, String manualId, String module) {
        if (configId == null || configId.trim().isEmpty()) {
            throw new IllegalArgumentException("configId must not be null or empty");
        }
        if (manualId == null || manualId.trim().isEmpty()) {
            throw new IllegalArgumentException("manualId must not be null or empty");
        }

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_PROD_BY_MODULE)) {
            ps.setString(1, configId);
            ps.setString(2, manualId);
            ps.setString(3, module);
            return ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating configId={} for manualId={}", configId, manualId, e);
            throw new RuntimeException("Database update failed", e);
        }
    }

    public QueryResult updateConfigIdResult(String configId, String manualId) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_CONFIG_ID_SQL)) {
            ps.setString(1, configId);
            ps.setString(2, manualId);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateConfigProd(String configId, String manualId, String module) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_CONFIG_ID_SQL)) {
            ps.setString(1, configId);
            ps.setString(2, manualId);
            ps.setString(3, module);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateConfigId(String configId, String manualId, String module) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_PROD_BY_MODULE)) {
            ps.setString(1, configId);
            ps.setString(2, manualId);
            ps.setString(3, module);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateItemActivityConfig(String qty, String dut, String cmp, String id) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_PROD_FOR_QTY_DUT_COMP)) {
            ps.setString(1, qty);
            ps.setString(2, dut);
            ps.setString(3, cmp);
            ps.setString(4, id);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateItemActivityConfigByModule(String qty, String dut, String cmp, String id, String module) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_PROD_FOR_COMP_BY_MODULE)) {
            ps.setString(1, qty);
            ps.setString(2, dut);
            ps.setString(3, cmp);
            ps.setString(4, id);
            ps.setString(5, module);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult removeCurrentData(String configId, String itemId) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_SUB_BY_ITEMID)) {
            ps.setString(1, configId);
            ps.setString(2, itemId);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public int deleteConfigId(String itemId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_CONFIG_ID_SQL)) {
            ps.setString(1, itemId);
            return ps.executeUpdate();
        }
    }

    public int deleteConfigLevel01(String itemId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_CONFIG_01_SQL)) {
            ps.setString(1, itemId);
            return ps.executeUpdate();
        }
    }

    public int deleteConfigLevel02(String itemId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_CONFIG_02_SQL)) {
            ps.setString(1, itemId);
            return ps.executeUpdate();
        }
    }

    public int deleteConfigLevel03(String itemId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_CONFIG_03_SQL)) {
            ps.setString(1, itemId);
            return ps.executeUpdate();
        }
    }

    public int getQuantity(String mibItemId) {
        if (mibItemId == null || mibItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("mibItemId must not be null or empty");
        }
        final String sql = "SELECT COUNT(*) FROM item_activity_config WHERE mib_item_id = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // safer and cleaner
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching quantity for mibItemId={}", mibItemId, e);
            throw new RuntimeException("Database error occurred while fetching quantity", e);
        }
        return 0;
    }

    public int getQuantityByModule(String mibItemId, String module) {
        if (mibItemId == null || mibItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("mibItemId must not be null or empty");
        }
        final String sql = "SELECT qty FROM item_manual_test_prod WHERE mib_item_id = ? AND module = ? ";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // safer and cleaner
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching quantity for mibItemId={}", mibItemId, e);
            throw new RuntimeException("Database error occurred while fetching quantity", e);
        }
        return 0;
    }

    public int getDut(String mibItemId) {
        if (mibItemId == null || mibItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("mibItemId must not be null or empty");
        }
        final String sql = "SELECT COUNT(*) FROM item_activity_config WHERE mib_item_id = ? ";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // safer and cleaner
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching quantity for mibItemId={}", mibItemId, e);
            throw new RuntimeException("Database error occurred while fetching quantity", e);
        }
        return 0;
    }

    public int getManualTestRecordByModule(String mibItemId, String module) throws SQLException {
        if (mibItemId == null || mibItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("mibItemId must not be null or empty");
        }
        final String sql = "SELECT COUNT(*) FROM item_manual_test_prod WHERE mib_item_id = ? AND module = ? ";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // safer and cleaner
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching quantity for mibItemId={}", mibItemId, e);
            throw new RuntimeException("Database error occurred while fetching quantity", e);
        }
        return 0;
    }

    public Integer getConfigIdByItemIdAndModule(String mibItemId, String module) {
        if (mibItemId == null || mibItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("mibItemId must not be null or empty");
        }
        final String sql = "SELECT id FROM item_manual_test_prod WHERE mib_item_id = ? AND module = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            ps.setString(2, module);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching configId for mibItemId={}", mibItemId, e);
            throw new RuntimeException("Database error while fetching configId", e);
        }
        return 0;
    }

    public int getManualTestCurrentRecordSub(String mibItemId) {
        if (mibItemId == null || mibItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("mibItemId must not be null or empty");
        }
        final String sql = "SELECT COUNT(*) FROM item_manual_test_sub WHERE mib_item_id = ? ";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mibItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // cleaner and safer
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error counting records for mibItemId={}", mibItemId, e);
            throw new RuntimeException("Database error occurred while counting records", e);
        }
        return 0;
    }

}