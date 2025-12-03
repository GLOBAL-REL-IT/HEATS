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
import com.onsemi.mib.model.ItemRecall;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemRecallDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemRecallDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemRecallDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemRecall(ItemRecall itemrecall) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_recall (hims_retrieve_id, item_type, item_id, box_no, qty, status, created_by, created_date, flag) VALUES (?,?,?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemrecall.getHimsRetrieveId());
            ps.setString(2, itemrecall.getItemType());
            ps.setString(3, itemrecall.getItemId());
            ps.setString(4, itemrecall.getBoxNo());
            ps.setString(5, itemrecall.getQty());
            ps.setString(6, itemrecall.getStatus());
            ps.setString(7, itemrecall.getCreatedBy());
//			ps.setString(8, itemrecall.getCreatedDate());
            ps.setString(8, itemrecall.getFlag());
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

    public QueryResult updateItemRecall(ItemRecall itemrecall) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_recall SET hims_retrieve_id = ?, item_type = ?, item_id = ?, box_no = ?, qty = ?, status = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, itemrecall.getHimsRetrieveId());
            ps.setString(2, itemrecall.getItemType());
            ps.setString(3, itemrecall.getItemId());
            ps.setString(4, itemrecall.getBoxNo());
            ps.setString(5, itemrecall.getQty());
            ps.setString(6, itemrecall.getStatus());
            ps.setString(7, itemrecall.getCreatedBy());
            ps.setString(8, itemrecall.getCreatedDate());
            ps.setString(9, itemrecall.getFlag());
            ps.setString(10, itemrecall.getId());
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

    public QueryResult updateItemRecallStatusAndFlag(ItemRecall itemrecall) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_recall SET status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, itemrecall.getStatus());
            ps.setString(2, itemrecall.getFlag());
            ps.setString(3, itemrecall.getId());
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

    public QueryResult deleteItemRecall(String itemrecallId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_recall WHERE id = '" + itemrecallId + "'"
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

    public ItemRecall getItemRecall(String itemrecallId) {
        String sql = "SELECT * FROM item_recall WHERE id = '" + itemrecallId + "'";
        ItemRecall itemrecall = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemrecall = new ItemRecall();
                itemrecall.setId(rs.getString("id"));
                itemrecall.setHimsRetrieveId(rs.getString("hims_retrieve_id"));
                itemrecall.setItemType(rs.getString("item_type"));
                itemrecall.setItemId(rs.getString("item_id"));
                itemrecall.setBoxNo(rs.getString("box_no"));
                itemrecall.setQty(rs.getString("qty"));
                itemrecall.setStatus(rs.getString("status"));
                itemrecall.setCreatedBy(rs.getString("created_by"));
                itemrecall.setCreatedDate(rs.getString("created_date"));
                itemrecall.setFlag(rs.getString("flag"));
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
        return itemrecall;
    }

    public List<ItemRecall> getItemRecallList() {
        String sql = "SELECT * FROM item_recall ORDER BY id ASC";
        List<ItemRecall> itemrecallList = new ArrayList<ItemRecall>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemRecall itemrecall;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemrecall = new ItemRecall();
                itemrecall.setId(rs.getString("id"));
                itemrecall.setHimsRetrieveId(rs.getString("hims_retrieve_id"));
                itemrecall.setItemType(rs.getString("item_type"));
                itemrecall.setItemId(rs.getString("item_id"));
                itemrecall.setBoxNo(rs.getString("box_no"));
                itemrecall.setQty(rs.getString("qty"));
                itemrecall.setStatus(rs.getString("status"));
                itemrecall.setCreatedBy(rs.getString("created_by"));
                itemrecall.setCreatedDate(rs.getString("created_date"));
                itemrecall.setFlag(rs.getString("flag"));
                itemrecallList.add(itemrecall);
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
        return itemrecallList;
    }

    public List<ItemRecall> getItemRecallListFlagZero() {
        String sql = "SELECT * FROM item_recall WHERE flag = '0' ORDER BY id ASC";
        List<ItemRecall> itemrecallList = new ArrayList<ItemRecall>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemRecall itemrecall;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemrecall = new ItemRecall();
                itemrecall.setId(rs.getString("id"));
                itemrecall.setHimsRetrieveId(rs.getString("hims_retrieve_id"));
                itemrecall.setItemType(rs.getString("item_type"));
                itemrecall.setItemId(rs.getString("item_id"));
                itemrecall.setBoxNo(rs.getString("box_no"));
                itemrecall.setQty(rs.getString("qty"));
                itemrecall.setStatus(rs.getString("status"));
                itemrecall.setCreatedBy(rs.getString("created_by"));
                itemrecall.setCreatedDate(rs.getString("created_date"));
                itemrecall.setFlag(rs.getString("flag"));
                itemrecallList.add(itemrecall);
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
        return itemrecallList;
    }
}
