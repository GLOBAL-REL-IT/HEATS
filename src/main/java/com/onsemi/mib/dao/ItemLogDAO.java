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
import com.onsemi.mib.model.ItemLog;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemLogDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemLogDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemLogDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemLog(ItemLog itemlog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_log (item_id, detail, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemlog.getItemId());
            ps.setString(2, itemlog.getDetail());
            ps.setString(3, itemlog.getCreatedBy());
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

    public QueryResult updateItemLog(ItemLog itemlog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_log SET item_id = ?, detail = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, itemlog.getItemId());
            ps.setString(2, itemlog.getDetail());
            ps.setString(3, itemlog.getCreatedBy());
            ps.setString(4, itemlog.getCreatedDate());
            ps.setString(5, itemlog.getId());
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

    public QueryResult deleteItemLog(String itemlogId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_log WHERE id = '" + itemlogId + "'"
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

    public ItemLog getItemLog(String itemlogId) {
        String sql = "SELECT * FROM item_log WHERE id = '" + itemlogId + "'";
        ItemLog itemlog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemlog = new ItemLog();
                itemlog.setId(rs.getString("id"));
                itemlog.setItemId(rs.getString("item_id"));
                itemlog.setDetail(rs.getString("detail"));
                itemlog.setCreatedBy(rs.getString("created_by"));
                itemlog.setCreatedDate(rs.getString("created_date"));
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
        return itemlog;
    }

    public List<ItemLog> getItemLogList() {
        String sql = "SELECT * FROM item_log ORDER BY id ASC";
        List<ItemLog> itemlogList = new ArrayList<ItemLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemLog itemlog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemlog = new ItemLog();
                itemlog.setId(rs.getString("id"));
                itemlog.setItemId(rs.getString("item_id"));
                itemlog.setDetail(rs.getString("detail"));
                itemlog.setCreatedBy(rs.getString("created_by"));
                itemlog.setCreatedDate(rs.getString("created_date"));
                itemlogList.add(itemlog);
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
        return itemlogList;
    }
}
