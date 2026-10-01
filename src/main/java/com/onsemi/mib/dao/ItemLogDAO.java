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
//    private final Connection conn;
    private final DataSource dataSource;

    public ItemLogDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String INSERT_ITEM_LOG = "INSERT INTO item_log (item_id, detail, created_by, created_date) VALUES (?, ?, ?, NOW())";
    private static final String UPDATE_ITEM_LOG = "UPDATE item_log SET item_id = ?, detail = ?, created_by = ?, created_date = ? WHERE id = ?";
    private static final String DELETE_ITEM_LOG = "DELETE FROM item_log WHERE id = ?";

    public QueryResult insertItemLog(ItemLog itemlog) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_ITEM_LOG, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemlog.getItemId());
            ps.setString(2, itemlog.getDetail());
            ps.setString(3, itemlog.getCreatedBy());
            int rowsAffected = ps.executeUpdate();
            queryResult.setResult(rowsAffected);
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
                    }
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Failed to insert item log. itemId={}, createdBy={}", itemlog.getItemId(), itemlog.getCreatedBy(), e);
        }
        return queryResult;
    }

    public QueryResult updateItemLog(ItemLog itemlog) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_ITEM_LOG)) {
            ps.setString(1, itemlog.getItemId());
            ps.setString(2, itemlog.getDetail());
            ps.setString(3, itemlog.getCreatedBy());
            ps.setString(4, itemlog.getCreatedDate());
            ps.setString(5, itemlog.getId());
            int rowsAffected = ps.executeUpdate();
            queryResult.setResult(rowsAffected);
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Failed to update item log. id={}, itemId={}, createdBy={}", itemlog.getId(), itemlog.getItemId(), itemlog.getCreatedBy(), e);
        }
        return queryResult;
    }

    public QueryResult deleteItemLog(String itemlogId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_ITEM_LOG)) {
            ps.setString(1, itemlogId);
            int rowsAffected = ps.executeUpdate();
            queryResult.setResult(rowsAffected);
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Failed to delete item log. itemlogId={}", itemlogId, e);
        }
        return queryResult;
    }

    private static final String GET_ITEM_LOG = "SELECT id, item_id, detail, created_by, created_date FROM item_log WHERE id = ?";

    public ItemLog getItemLog(String itemlogId) {
        ItemLog itemlog = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ITEM_LOG)) {
            ps.setString(1, itemlogId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    itemlog = new ItemLog();
                    itemlog.setId(rs.getString("id"));
                    itemlog.setItemId(rs.getString("item_id"));
                    itemlog.setDetail(rs.getString("detail"));
                    itemlog.setCreatedBy(rs.getString("created_by"));
                    itemlog.setCreatedDate(rs.getString("created_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to get item log. itemlogId={}", itemlogId, e);
        }
        return itemlog;
    }

    private static final String GET_ITEM_LOG_LIST = "SELECT id, item_id, detail, created_by, created_date FROM item_log ORDER BY id ASC";
    private static final String GET_ITEM_LOG_LIST_BY_ITEMID = "SELECT id, item_id, detail, created_by, created_date FROM item_log WHERE item_id = ? ORDER BY id ASC";

    public List<ItemLog> getItemLogList() {
        List<ItemLog> itemlogList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ITEM_LOG_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ItemLog itemlog = new ItemLog();
                itemlog.setId(rs.getString("id"));
                itemlog.setItemId(rs.getString("item_id"));
                itemlog.setDetail(rs.getString("detail"));
                itemlog.setCreatedBy(rs.getString("created_by"));
                itemlog.setCreatedDate(rs.getString("created_date"));
                itemlogList.add(itemlog);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve item log list", e);
        }
        return itemlogList;
    }

    public List<ItemLog> getItemLogListByItemId(String itemId) {
        List<ItemLog> itemlogList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ITEM_LOG_LIST_BY_ITEMID)) {
            ps.setString(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemLog itemlog = new ItemLog();
                    itemlog.setId(rs.getString("id"));
                    itemlog.setItemId(rs.getString("item_id"));
                    itemlog.setDetail(rs.getString("detail"));
                    itemlog.setCreatedBy(rs.getString("created_by"));
                    itemlog.setCreatedDate(rs.getString("created_date"));
                    itemlogList.add(itemlog);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve item log list. itemId={}", itemId, e);
        }
        return itemlogList;
    }

}