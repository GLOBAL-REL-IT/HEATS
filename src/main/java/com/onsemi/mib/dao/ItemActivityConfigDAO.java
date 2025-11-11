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
import com.onsemi.mib.model.ItemActivityConfig;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemActivityConfigDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemActivityConfigDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemActivityConfigDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemActivityConfig(ItemActivityConfig itemactivityConfig) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_activity_config (mib_item_id, vi, bib_test, manual_test, leakage_test, ps_leakage_test, created_by, created_date, status, flag, winchester_chamber_leakage_test) VALUES (?,?,?,?,?,?,?,NOW(),?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemactivityConfig.getMibItemId());
            ps.setString(2, itemactivityConfig.getVi());
            ps.setString(3, itemactivityConfig.getBibTest());
            ps.setString(4, itemactivityConfig.getManualTest());
            ps.setString(5, itemactivityConfig.getLeakageTest());
            ps.setString(6, itemactivityConfig.getPsLeakageTest());
            ps.setString(7, itemactivityConfig.getCreatedBy());
            ps.setString(8, itemactivityConfig.getStatus());
            ps.setString(9, itemactivityConfig.getFlag());
            ps.setString(10, itemactivityConfig.getWinchesterChamberLeakageTest());
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

    public QueryResult updateItemActivityConfig(ItemActivityConfig itemactivityConfig) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_activity_config SET vi = ?, bib_test = ?, manual_test = ?, leakage_test = ?, ps_leakage_test = ?, status = ?, flag = ?, winchester_chamber_leakage_test = ? WHERE id = ?"
            );
//            ps.setString(1, itemactivityConfig.getMibItemId());
            ps.setString(1, itemactivityConfig.getVi());
            ps.setString(2, itemactivityConfig.getBibTest());
            ps.setString(3, itemactivityConfig.getManualTest());
            ps.setString(4, itemactivityConfig.getLeakageTest());
            ps.setString(5, itemactivityConfig.getPsLeakageTest());
            ps.setString(6, itemactivityConfig.getStatus());
            ps.setString(7, itemactivityConfig.getFlag());
            ps.setString(8, itemactivityConfig.getWinchesterChamberLeakageTest());
            ps.setString(9, itemactivityConfig.getId());
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

    public QueryResult deleteItemActivityConfig(String itemactivityConfigId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_activity_config WHERE id = '" + itemactivityConfigId + "'"
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

    public ItemActivityConfig getItemActivityConfig(String itemactivityConfigId) {
        String sql = "SELECT * FROM item_activity_config WHERE id = '" + itemactivityConfigId + "'";
        ItemActivityConfig itemactivityConfig = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemactivityConfig = new ItemActivityConfig();
                itemactivityConfig.setId(rs.getString("id"));
                itemactivityConfig.setMibItemId(rs.getString("mib_item_id"));
                itemactivityConfig.setVi(rs.getString("vi"));
                itemactivityConfig.setBibTest(rs.getString("bib_test"));
                itemactivityConfig.setManualTest(rs.getString("manual_test"));
                itemactivityConfig.setLeakageTest(rs.getString("leakage_test"));
                itemactivityConfig.setPsLeakageTest(rs.getString("ps_leakage_test"));
                itemactivityConfig.setCreatedBy(rs.getString("created_by"));
                itemactivityConfig.setCreatedDate(rs.getString("created_date"));
                itemactivityConfig.setStatus(rs.getString("status"));
                itemactivityConfig.setFlag(rs.getString("flag"));
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
        return itemactivityConfig;
    }

    public List<ItemActivityConfig> getItemActivityConfigList() {
        String sql = "SELECT * FROM item_activity_config ORDER BY id ASC";
        List<ItemActivityConfig> itemactivityConfigList = new ArrayList<ItemActivityConfig>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemActivityConfig itemactivityConfig;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemactivityConfig = new ItemActivityConfig();
                itemactivityConfig.setId(rs.getString("id"));
                itemactivityConfig.setMibItemId(rs.getString("mib_item_id"));
                itemactivityConfig.setVi(rs.getString("vi"));
                itemactivityConfig.setBibTest(rs.getString("bib_test"));
                itemactivityConfig.setManualTest(rs.getString("manual_test"));
                itemactivityConfig.setLeakageTest(rs.getString("leakage_test"));
                itemactivityConfig.setPsLeakageTest(rs.getString("ps_leakage_test"));
                itemactivityConfig.setCreatedBy(rs.getString("created_by"));
                itemactivityConfig.setCreatedDate(rs.getString("created_date"));
                itemactivityConfig.setStatus(rs.getString("status"));
                itemactivityConfig.setFlag(rs.getString("flag"));
                itemactivityConfigList.add(itemactivityConfig);
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
        return itemactivityConfigList;
    }

    public List<ItemActivityConfig> getItemActivityConfigListWithItemDetail() {
        String sql = "SELECT it.item_type, it.sub_type, it.item_id, it.item_name, it.assembly_id, it.stress_type, con.* FROM item_activity_config con, item it WHERE it.id = con.mib_item_id ORDER BY it.item_id ASC";
        List<ItemActivityConfig> itemactivityConfigList = new ArrayList<ItemActivityConfig>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemActivityConfig itemactivityConfig;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemactivityConfig = new ItemActivityConfig();
                itemactivityConfig.setId(rs.getString("id"));
                itemactivityConfig.setMibItemId(rs.getString("mib_item_id"));
                itemactivityConfig.setVi(rs.getString("vi"));
                itemactivityConfig.setBibTest(rs.getString("bib_test"));
                itemactivityConfig.setManualTest(rs.getString("manual_test"));
                itemactivityConfig.setLeakageTest(rs.getString("leakage_test"));
                itemactivityConfig.setPsLeakageTest(rs.getString("ps_leakage_test"));
                itemactivityConfig.setCreatedBy(rs.getString("created_by"));
                itemactivityConfig.setCreatedDate(rs.getString("created_date"));
                itemactivityConfig.setStatus(rs.getString("status"));
                itemactivityConfig.setFlag(rs.getString("flag"));
                itemactivityConfig.setItemType(rs.getString("item_type"));
                itemactivityConfig.setSubType(rs.getString("sub_type"));
                itemactivityConfig.setItemId(rs.getString("item_id"));
                itemactivityConfig.setItemName(rs.getString("item_name"));
                itemactivityConfig.setAssemblyId(rs.getString("assembly_id"));
                itemactivityConfig.setStressType(rs.getString("stress_type"));
                itemactivityConfig.setWinchesterChamberLeakageTest(rs.getString("winchester_chamber_leakage_test"));
                itemactivityConfigList.add(itemactivityConfig);
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
        return itemactivityConfigList;
    }

    public ItemActivityConfig getItemActivityConfigWithItemDetail(String itemactivityConfigId) {
        String sql = "SELECT it.item_type, it.sub_type, it.item_id, it.item_name, it.assembly_id, it.stress_type, con.* "
                + "FROM item_activity_config con, item it "
                + "WHERE con.id = '" + itemactivityConfigId + "' AND it.id = con.mib_item_id "
                + "ORDER BY it.item_id ASC";
        ItemActivityConfig itemactivityConfig = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemactivityConfig = new ItemActivityConfig();
                itemactivityConfig.setId(rs.getString("id"));
                itemactivityConfig.setMibItemId(rs.getString("mib_item_id"));
                itemactivityConfig.setVi(rs.getString("vi"));
                itemactivityConfig.setBibTest(rs.getString("bib_test"));
                itemactivityConfig.setManualTest(rs.getString("manual_test"));
                itemactivityConfig.setLeakageTest(rs.getString("leakage_test"));
                itemactivityConfig.setPsLeakageTest(rs.getString("ps_leakage_test"));
                itemactivityConfig.setCreatedBy(rs.getString("created_by"));
                itemactivityConfig.setCreatedDate(rs.getString("created_date"));
                itemactivityConfig.setStatus(rs.getString("status"));
                itemactivityConfig.setFlag(rs.getString("flag"));
                itemactivityConfig.setItemType(rs.getString("item_type"));
                itemactivityConfig.setSubType(rs.getString("sub_type"));
                itemactivityConfig.setItemId(rs.getString("item_id"));
                itemactivityConfig.setItemName(rs.getString("item_name"));
                itemactivityConfig.setAssemblyId(rs.getString("assembly_id"));
                itemactivityConfig.setStressType(rs.getString("stress_type"));
                itemactivityConfig.setWinchesterChamberLeakageTest(rs.getString("winchester_chamber_leakage_test"));
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
        return itemactivityConfig;
    }
}
