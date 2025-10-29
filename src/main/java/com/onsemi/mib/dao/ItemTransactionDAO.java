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
import com.onsemi.mib.model.ItemTransaction;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemTransactionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemTransactionDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemTransactionDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemTransaction(ItemTransaction itemtransaction) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_transaction (spts_pkid, site_name, date_time, item_pkid, trans_type, trans_type_name, trans_qty, trans_in_qty, trans_out_qty, alu, remarks) VALUES (?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemtransaction.getSptsPkid());
            ps.setString(2, itemtransaction.getSiteName());
            ps.setString(3, itemtransaction.getDateTime());
            ps.setString(4, itemtransaction.getItemPkid());
            ps.setString(5, itemtransaction.getTransType());
            ps.setString(6, itemtransaction.getTransTypeName());
            ps.setString(7, itemtransaction.getTransQty());
            ps.setString(8, itemtransaction.getTransInQty());
            ps.setString(9, itemtransaction.getTransOutQty());
            ps.setString(10, itemtransaction.getAlu());
            ps.setString(11, itemtransaction.getRemarks());
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

    public QueryResult updateItemTransaction(ItemTransaction itemtransaction) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_transaction SET spts_pkid = ?, site_name = ?, date_time = ?, item_pkid = ?, trans_type = ?, trans_type_name = ?, trans_qty = ?, trans_in_qty = ?, trans_out_qty = ?, alu = ?, remarks = ? WHERE id = ?"
            );
            ps.setString(1, itemtransaction.getSptsPkid());
            ps.setString(2, itemtransaction.getSiteName());
            ps.setString(3, itemtransaction.getDateTime());
            ps.setString(4, itemtransaction.getItemPkid());
            ps.setString(5, itemtransaction.getTransType());
            ps.setString(6, itemtransaction.getTransTypeName());
            ps.setString(7, itemtransaction.getTransQty());
            ps.setString(8, itemtransaction.getTransInQty());
            ps.setString(9, itemtransaction.getTransOutQty());
            ps.setString(10, itemtransaction.getAlu());
            ps.setString(11, itemtransaction.getRemarks());
            ps.setString(12, itemtransaction.getId());
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

    public QueryResult deleteItemTransaction(String itemtransactionId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_transaction WHERE id = '" + itemtransactionId + "'"
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

    public ItemTransaction getItemTransaction(String itemtransactionId) {
        String sql = "SELECT * FROM item_transaction WHERE id = '" + itemtransactionId + "'";
        ItemTransaction itemtransaction = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemtransaction = new ItemTransaction();
                itemtransaction.setId(rs.getString("id"));
                itemtransaction.setSptsPkid(rs.getString("spts_pkid"));
                itemtransaction.setSiteName(rs.getString("site_name"));
                itemtransaction.setDateTime(rs.getString("date_time"));
                itemtransaction.setItemPkid(rs.getString("item_pkid"));
                itemtransaction.setTransType(rs.getString("trans_type"));
                itemtransaction.setTransTypeName(rs.getString("trans_type_name"));
                itemtransaction.setTransQty(rs.getString("trans_qty"));
                itemtransaction.setTransInQty(rs.getString("trans_in_qty"));
                itemtransaction.setTransOutQty(rs.getString("trans_out_qty"));
                itemtransaction.setAlu(rs.getString("alu"));
                itemtransaction.setRemarks(rs.getString("remarks"));
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
        return itemtransaction;
    }

    public List<ItemTransaction> getItemTransactionList() {
        String sql = "SELECT * FROM item_transaction ORDER BY id ASC";
        List<ItemTransaction> itemtransactionList = new ArrayList<ItemTransaction>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemTransaction itemtransaction;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemtransaction = new ItemTransaction();
                itemtransaction.setId(rs.getString("id"));
                itemtransaction.setSptsPkid(rs.getString("spts_pkid"));
                itemtransaction.setSiteName(rs.getString("site_name"));
                itemtransaction.setDateTime(rs.getString("date_time"));
                itemtransaction.setItemPkid(rs.getString("item_pkid"));
                itemtransaction.setTransType(rs.getString("trans_type"));
                itemtransaction.setTransTypeName(rs.getString("trans_type_name"));
                itemtransaction.setTransQty(rs.getString("trans_qty"));
                itemtransaction.setTransInQty(rs.getString("trans_in_qty"));
                itemtransaction.setTransOutQty(rs.getString("trans_out_qty"));
                itemtransaction.setAlu(rs.getString("alu"));
                itemtransaction.setRemarks(rs.getString("remarks"));
                itemtransactionList.add(itemtransaction);
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
        return itemtransactionList;
    }

    public List<ItemTransaction> getItemTransactionListByItemPkid(String itemPkid) {
        String sql = "SELECT it.item_id, "
//                + "tran.item_pkid, "
                + "DATE_FORMAT(tran.date_time,'%d %M %Y %h:%i %p') AS view_date_time, tran.trans_type_name, "
                + "IFNULL(tran.trans_in_qty,'-') AS transInQty, IFNULL(tran.trans_out_qty,'-') AS transOutQty, "
                + "IFNULL(tran.alu,'-') AS ALU, tran.remarks "
                + "FROM item_transaction tran, item it "
                + "WHERE item_pkid = '" + itemPkid + "' AND it.spts_pkid = tran.item_pkid "
                + "ORDER BY tran.id ASC";
        List<ItemTransaction> itemtransactionList = new ArrayList<ItemTransaction>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemTransaction itemtransaction;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemtransaction = new ItemTransaction();
                itemtransaction.setItemId(rs.getString("item_id"));
                itemtransaction.setDateTime(rs.getString("view_date_time"));
//                itemtransaction.setItemPkid(rs.getString("item_pkid"));
                itemtransaction.setTransTypeName(rs.getString("trans_type_name"));
                itemtransaction.setTransInQty(rs.getString("transInQty"));
                itemtransaction.setTransOutQty(rs.getString("transOutQty"));
                itemtransaction.setAlu(rs.getString("ALU"));
                itemtransaction.setRemarks(rs.getString("remarks"));
                itemtransactionList.add(itemtransaction);
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
        return itemtransactionList;
    }

    public Integer getCountPkidAndItemPkid(String pkid, String itemPkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item_transaction inc WHERE inc.spts_pkid = '" + pkid + "' AND item_pkid = '" + itemPkid + "'"
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
    
    public List<ItemTransaction> getDataTest(String sptsId) {
        String sql = "SELECT * FROM item_transaction WHERE spts_pkid = '" + sptsId + "' ORDER BY id ASC";
        List<ItemTransaction> itemtransactionList = new ArrayList<ItemTransaction>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemTransaction itemtransaction;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemtransaction = new ItemTransaction();
                itemtransaction.setId(rs.getString("id"));
                itemtransaction.setSptsPkid(rs.getString("spts_pkid"));
                itemtransaction.setSiteName(rs.getString("site_name"));
                itemtransaction.setDateTime(rs.getString("date_time"));
                itemtransaction.setItemPkid(rs.getString("item_pkid"));
                itemtransaction.setTransType(rs.getString("trans_type"));
                itemtransaction.setTransTypeName(rs.getString("trans_type_name"));
                itemtransaction.setTransQty(rs.getString("trans_qty"));
                itemtransaction.setTransInQty(rs.getString("trans_in_qty"));
                itemtransaction.setTransOutQty(rs.getString("trans_out_qty"));
                itemtransaction.setAlu(rs.getString("alu"));
                itemtransaction.setRemarks(rs.getString("remarks"));
                itemtransactionList.add(itemtransaction);
            }
            LOGGER.info("tengok data macam mana rs     >>> " + rs);
            LOGGER.info("tengok data macam mana sptsId >>> " + itemtransactionList);
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
        return itemtransactionList;
    }
    
}