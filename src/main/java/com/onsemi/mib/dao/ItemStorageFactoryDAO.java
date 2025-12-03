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
import com.onsemi.mib.model.ItemStorageFactory;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemStorageFactoryDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemStorageFactoryDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemStorageFactoryDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemStorageFactory(ItemStorageFactory itemstorageFactory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_storage_factory (sf_pkid, item_pkid, movement_type, qty, rack, shelf, movement_datetime, flag) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemstorageFactory.getSfPkid());
            ps.setString(2, itemstorageFactory.getItemPkid());
            ps.setString(3, itemstorageFactory.getMovementType());
            ps.setString(4, itemstorageFactory.getQty());
            ps.setString(5, itemstorageFactory.getRack());
            ps.setString(6, itemstorageFactory.getShelf());
            ps.setString(7, itemstorageFactory.getMovementDatetime());
            ps.setString(8, itemstorageFactory.getFlag());
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

    public QueryResult updateItemStorageFactory(ItemStorageFactory itemstorageFactory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_storage_factory SET sf_pkid = ?, item_pkid = ?, movement_type = ?, qty = ?, rack = ?, shelf = ?, movement_datetime = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, itemstorageFactory.getSfPkid());
            ps.setString(2, itemstorageFactory.getItemPkid());
            ps.setString(3, itemstorageFactory.getMovementType());
            ps.setString(4, itemstorageFactory.getQty());
            ps.setString(5, itemstorageFactory.getRack());
            ps.setString(6, itemstorageFactory.getShelf());
            ps.setString(7, itemstorageFactory.getMovementDatetime());
            ps.setString(8, itemstorageFactory.getFlag());
            ps.setString(9, itemstorageFactory.getId());
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

    public QueryResult deleteItemStorageFactory(String itemstorageFactoryId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_storage_factory WHERE id = '" + itemstorageFactoryId + "'"
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

    public ItemStorageFactory getItemStorageFactory(String itemstorageFactoryId) {
        String sql = "SELECT * FROM item_storage_factory WHERE id = '" + itemstorageFactoryId + "'";
        ItemStorageFactory itemstorageFactory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemstorageFactory = new ItemStorageFactory();
                itemstorageFactory.setId(rs.getString("id"));
                itemstorageFactory.setSfPkid(rs.getString("sf_pkid"));
                itemstorageFactory.setItemPkid(rs.getString("item_pkid"));
                itemstorageFactory.setMovementType(rs.getString("movement_type"));
                itemstorageFactory.setQty(rs.getString("qty"));
                itemstorageFactory.setRack(rs.getString("rack"));
                itemstorageFactory.setShelf(rs.getString("shelf"));
                itemstorageFactory.setMovementDatetime(rs.getString("movement_datetime"));
                itemstorageFactory.setFlag(rs.getString("flag"));
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
        return itemstorageFactory;
    }

    public List<ItemStorageFactory> getItemStorageFactoryList() {
        String sql = "SELECT * FROM item_storage_factory ORDER BY id ASC";
        List<ItemStorageFactory> itemstorageFactoryList = new ArrayList<ItemStorageFactory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemStorageFactory itemstorageFactory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemstorageFactory = new ItemStorageFactory();
                itemstorageFactory.setId(rs.getString("id"));
                itemstorageFactory.setSfPkid(rs.getString("sf_pkid"));
                itemstorageFactory.setItemPkid(rs.getString("item_pkid"));
                itemstorageFactory.setMovementType(rs.getString("movement_type"));
                itemstorageFactory.setQty(rs.getString("qty"));
                itemstorageFactory.setRack(rs.getString("rack"));
                itemstorageFactory.setShelf(rs.getString("shelf"));
                itemstorageFactory.setMovementDatetime(rs.getString("movement_datetime"));
                itemstorageFactory.setFlag(rs.getString("flag"));
                itemstorageFactoryList.add(itemstorageFactory);
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
        return itemstorageFactoryList;
    }

    public List<ItemStorageFactory> getItemStorageFactoryListByItemPkid(String itemPkid) {
        String sql = "SELECT sf.*, it.item_id FROM item_storage_factory sf, item it WHERE sf.item_pkid = '" + itemPkid + "' AND it.spts_pkid = sf.item_pkid ORDER BY id ASC";
        List<ItemStorageFactory> itemstorageFactoryList = new ArrayList<ItemStorageFactory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemStorageFactory itemstorageFactory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemstorageFactory = new ItemStorageFactory();
                itemstorageFactory.setId(rs.getString("id"));
                itemstorageFactory.setSfPkid(rs.getString("sf_pkid"));
                itemstorageFactory.setItemPkid(rs.getString("item_pkid"));
                itemstorageFactory.setMovementType(rs.getString("movement_type"));
                itemstorageFactory.setQty(rs.getString("qty"));
                itemstorageFactory.setRack(rs.getString("rack"));
                itemstorageFactory.setShelf(rs.getString("shelf"));
                itemstorageFactory.setMovementDatetime(rs.getString("movement_datetime"));
                itemstorageFactory.setFlag(rs.getString("flag"));
                itemstorageFactory.setItemId(rs.getString("item_id"));
                itemstorageFactoryList.add(itemstorageFactory);
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
        return itemstorageFactoryList;
    }

    public Integer getCountPkidAndItemPkid(String pkid, String itemPkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item_storage_factory inc WHERE inc.sf_pkid = '" + pkid + "' AND item_pkid = '" + itemPkid + "'"
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
