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
import com.onsemi.mib.model.ItemAluConfig;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemAluConfigDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemAluConfigDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemAluConfigDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemAluConfig(ItemAluConfig itemaluConfig) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_alu_config (item_type, created_by, created_date) VALUES (?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemaluConfig.getItemType());
            ps.setString(2, itemaluConfig.getCreatedBy());
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

    public QueryResult updateItemAluConfig(ItemAluConfig itemaluConfig) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_alu_config SET item_type = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, itemaluConfig.getItemType());
            ps.setString(2, itemaluConfig.getCreatedBy());
            ps.setString(3, itemaluConfig.getCreatedDate());
            ps.setString(4, itemaluConfig.getId());
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

    public QueryResult deleteItemAluConfig(String itemaluConfigId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_alu_config WHERE id = '" + itemaluConfigId + "'"
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

    public ItemAluConfig getItemAluConfig(String itemaluConfigId) {
        String sql = "SELECT * FROM item_alu_config WHERE id = '" + itemaluConfigId + "'";
        ItemAluConfig itemaluConfig = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemaluConfig = new ItemAluConfig();
                itemaluConfig.setId(rs.getString("id"));
                itemaluConfig.setItemType(rs.getString("item_type"));
                itemaluConfig.setCreatedBy(rs.getString("created_by"));
                itemaluConfig.setCreatedDate(rs.getString("created_date"));
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
        return itemaluConfig;
    }

    public List<ItemAluConfig> getItemAluConfigList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM item_alu_config ORDER BY id ASC";
        List<ItemAluConfig> itemaluConfigList = new ArrayList<ItemAluConfig>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemAluConfig itemaluConfig;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemaluConfig = new ItemAluConfig();
                itemaluConfig.setId(rs.getString("id"));
                itemaluConfig.setItemType(rs.getString("item_type"));
                itemaluConfig.setCreatedBy(rs.getString("created_by"));
                itemaluConfig.setCreatedDate(rs.getString("createdDate"));
                itemaluConfigList.add(itemaluConfig);
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
        return itemaluConfigList;
    }

    public Integer getCountItemType(String itemType) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item_alu_config inc WHERE inc.item_type = '" + itemType + "'"
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
