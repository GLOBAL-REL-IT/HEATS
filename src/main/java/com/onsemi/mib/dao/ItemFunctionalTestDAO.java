/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.ItemFunctionalTest;
import com.onsemi.mib.tools.QueryResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemFunctionalTestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemFunctionalTestDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemFunctionalTestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }
    
    public QueryResult insertItemFunctionalTest(ItemFunctionalTest item) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_functional_test (mib_item_id, bib_qty, bib_status, bib_upload, man_status, leak_qty, leak_status, leak_upload, "
                            + "ps_qty, ps_status, ps_upload, win_qty, win_status, win_upload, "
                            + "remark, final_status, created_by, created_date, flag) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, item.getMibItemId());
            ps.setString(2, item.getBibQty());
            ps.setString(3, item.getBibStatus());
            ps.setString(4, item.getBibUpload());
            ps.setString(5, item.getManStatus());
            ps.setString(6, item.getLeakQty());
            ps.setString(7, item.getLeakStatus());
            ps.setString(8, item.getLeakUpload());
            ps.setString(9, item.getPsQty());
            ps.setString(10, item.getPsStatus());
            ps.setString(11, item.getPsUpload());
            ps.setString(12, item.getWinQty());
            ps.setString(13, item.getWinStatus());
            ps.setString(14, item.getWinUpload());
            ps.setString(15, item.getRemark());
            ps.setString(16, item.getFinalStatus());
            ps.setString(17, item.getCreatedBy());
            ps.setString(18, item.getFlag());

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
    
    public QueryResult updateItemFunctionalTest(ItemFunctionalTest item) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_functional_test SET mib_item_id=?, bib_qty=?, bib_status=?, bib_upload=?, man_status=?, leak_qty=?, leak_status=?, leak_upload=?, ps_qty=?, ps_status=?, ps_upload=?, win_qty=?, win_status=?, win_upload=?, remark=?, final_status=?, flag=? WHERE id=?"
            );
            ps.setString(1, item.getMibItemId());
            ps.setString(2, item.getBibQty());
            ps.setString(3, item.getBibStatus());
            ps.setString(4, item.getBibUpload());
            ps.setString(5, item.getManStatus());
            ps.setString(6, item.getLeakQty());
            ps.setString(7, item.getLeakStatus());
            ps.setString(8, item.getLeakUpload());
            ps.setString(9, item.getPsQty());
            ps.setString(10, item.getPsStatus());
            ps.setString(11, item.getPsUpload());
            ps.setString(12, item.getWinQty());
            ps.setString(13, item.getWinStatus());
            ps.setString(14, item.getWinUpload());
            ps.setString(15, item.getRemark());
            ps.setString(16, item.getFinalStatus());
            ps.setString(17, item.getFlag());
            ps.setString(18, item.getId());
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
    
    public QueryResult updateBibTest(ItemFunctionalTest item) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_functional_test SET bib_qty=?, bib_status=?, bib_upload=?, remark=?, final_status=?, flag=? WHERE mib_item_id=?"
            );
            ps.setString(1, item.getBibQty());
            ps.setString(2, item.getBibStatus());
            ps.setString(3, item.getBibUpload());
            ps.setString(4, item.getRemark());
            ps.setString(5, item.getFinalStatus());
            ps.setString(6, item.getFlag());
            ps.setString(7, item.getMibItemId());
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
    
    public QueryResult updateManualTest(ItemFunctionalTest item) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_functional_test SET man_status=?, remark=?, final_status=?, flag=? WHERE mib_item_id=?"
            );
            ps.setString(1, item.getManStatus());
            ps.setString(2, item.getRemark());
            ps.setString(3, item.getFinalStatus());
            ps.setString(4, item.getFlag());
            ps.setString(5, item.getMibItemId());
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
    
    public QueryResult updateLeakageTest(ItemFunctionalTest item) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_functional_test SET leak_qty=?, leak_status=?, leak_upload=?, remark=?, final_status=?, flag=? WHERE mib_item_id=?"
            );
            ps.setString(1, item.getLeakQty());
            ps.setString(2, item.getLeakStatus());
            ps.setString(3, item.getLeakUpload());
            ps.setString(4, item.getRemark());
            ps.setString(5, item.getFinalStatus());
            ps.setString(6, item.getFlag());
            ps.setString(7, item.getMibItemId());
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
    
    public QueryResult updatePowerTest(ItemFunctionalTest item) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_functional_test SET ps_qty=?, ps_status=?, ps_upload=?, remark=?, final_status=?, flag=? WHERE mib_item_id=?"
            );
            ps.setString(1, item.getPsQty());
            ps.setString(2, item.getPsStatus());
            ps.setString(3, item.getPsUpload());
            ps.setString(4, item.getRemark());
            ps.setString(5, item.getFinalStatus());
            ps.setString(6, item.getFlag());
            ps.setString(7, item.getMibItemId());
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
    
    public QueryResult updateWinchesterTest(ItemFunctionalTest item) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_functional_test SET win_qty=?, win_status=?, win_upload=?, remark=?, final_status=?, flag=? WHERE mib_item_id=?"
            );
            ps.setString(1, item.getWinQty());
            ps.setString(2, item.getWinStatus());
            ps.setString(3, item.getWinUpload());
            ps.setString(4, item.getRemark());
            ps.setString(5, item.getFinalStatus());
            ps.setString(6, item.getFlag());
            ps.setString(7, item.getMibItemId());
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
    
    public QueryResult deleteItemFunctionalTest(String id) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_functional_test WHERE id = '" + id + "'"
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
    
    public ItemFunctionalTest getItemActivityByItemId(String itemId) {
        String sql = "SELECT * FROM item_functional_test WHERE mib_item_id = '" + itemId + "'";
        ItemFunctionalTest item = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                item = new ItemFunctionalTest();
                item.setId(rs.getString("id"));
                item.setMibItemId(rs.getString("mib_item_id"));
                item.setBibQty(rs.getString("bib_qty"));
                item.setBibStatus(rs.getString("bib_status"));
                item.setBibUpload(rs.getString("bib_upload"));
                item.setManStatus(rs.getString("man_status"));
                item.setLeakQty(rs.getString("leak_qty"));
                item.setLeakStatus(rs.getString("leak_status"));
                item.setLeakUpload(rs.getString("leak_upload"));
                item.setPsQty(rs.getString("ps_qty"));
                item.setPsStatus(rs.getString("ps_status"));
                item.setPsUpload(rs.getString("ps_upload"));
                item.setWinQty(rs.getString("win_qty"));
                item.setWinStatus(rs.getString("win_status"));
                item.setWinUpload(rs.getString("win_upload"));
                item.setRemark(rs.getString("remark"));
                item.setFinalStatus(rs.getString("final_status"));
                item.setCreatedBy(rs.getString("created_by"));
                item.setCreatedDate(rs.getString("created_date"));
                item.setFlag(rs.getString("flag"));
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
        return item;
    }
    
}