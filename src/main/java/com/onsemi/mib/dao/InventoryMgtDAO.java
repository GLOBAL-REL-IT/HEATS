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
import com.onsemi.mib.model.InventoryMgt;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryMgtDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryMgtDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public InventoryMgtDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertInventoryMgt(InventoryMgt inventoryMgt) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_inventory_mgt (rack, shelf, status, flag, date_created) VALUES (?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
//            ps.setString(1, inventoryMgt.getReqId());
            ps.setString(1, inventoryMgt.getRack());
            ps.setString(2, inventoryMgt.getShelf());
            ps.setString(3, inventoryMgt.getStatus());
            ps.setString(4, inventoryMgt.getFlag());
//            ps.setString(5, inventoryMgt.getModifiedDate());
//            ps.setString(6, inventoryMgt.getDateCreated());
//            ps.setString(7, inventoryMgt.getRmsLotEvent());
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

    public QueryResult updateInventoryMgt(InventoryMgt inventoryMgt) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_inventory_mgt SET rack = ?, shelf = ?, status = ?, flag = ?, modified_date = ?, date_created = ? WHERE id = ?"
            );
//            ps.setString(1, inventoryMgt.getReqId());
            ps.setString(1, inventoryMgt.getRack());
            ps.setString(2, inventoryMgt.getShelf());
            ps.setString(3, inventoryMgt.getStatus());
            ps.setString(4, inventoryMgt.getFlag());
            ps.setString(5, inventoryMgt.getModifiedDate());
//            ps.setString(7, inventoryMgt.getRmsLotEvent());
            ps.setString(6, inventoryMgt.getDateCreated());
            ps.setString(7, inventoryMgt.getId());
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

    public QueryResult updateInventoryMgtAfterRequest(InventoryMgt inventoryMgt) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_inventory_mgt SET status = ?, flag = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, inventoryMgt.getStatus());
            ps.setString(2, inventoryMgt.getFlag());
            ps.setString(3, inventoryMgt.getId());
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

    public QueryResult deleteInventoryMgt(String inventoryMgtId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_inventory_mgt WHERE id = '" + inventoryMgtId + "'"
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

    public InventoryMgt getInventoryMgt(String inventoryMgtId) {
        String sql = "SELECT * FROM sr_inventory_mgt WHERE id = '" + inventoryMgtId + "'";
        InventoryMgt inventoryMgt = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventoryMgt = new InventoryMgt();
                inventoryMgt.setId(rs.getString("id"));
//                inventoryMgt.setReqId(rs.getString("req_id"));
                inventoryMgt.setRack(rs.getString("rack"));
                inventoryMgt.setShelf(rs.getString("shelf"));
                inventoryMgt.setStatus(rs.getString("status"));
                inventoryMgt.setFlag(rs.getString("flag"));
                inventoryMgt.setModifiedDate(rs.getString("modified_date"));
                inventoryMgt.setDateCreated(rs.getString("date_created"));
//                inventoryMgt.setRmsLotEvent(rs.getString("rms_lot_event"));
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
        return inventoryMgt;
    }

    public InventoryMgt getInventoryMgtByShelf(String shelf) {
        String sql = "SELECT * FROM sr_inventory_mgt WHERE shelf = '" + shelf + "'";
        InventoryMgt inventoryMgt = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventoryMgt = new InventoryMgt();
                inventoryMgt.setId(rs.getString("id"));
//                inventoryMgt.setReqId(rs.getString("req_id"));
                inventoryMgt.setRack(rs.getString("rack"));
                inventoryMgt.setShelf(rs.getString("shelf"));
                inventoryMgt.setStatus(rs.getString("status"));
                inventoryMgt.setFlag(rs.getString("flag"));
                inventoryMgt.setModifiedDate(rs.getString("modified_date"));
                inventoryMgt.setDateCreated(rs.getString("date_created"));
//                inventoryMgt.setRmsLotEvent(rs.getString("rms_lot_event"));
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
        return inventoryMgt;
    }

//    public InventoryMgt getInventoryMgtByReqId(String reqId) {
//        String sql = "SELECT * FROM sr_inventory_mgt WHERE req_id = '" + reqId + "'";
//        InventoryMgt inventoryMgt = null;
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                inventoryMgt = new InventoryMgt();
//                inventoryMgt.setId(rs.getString("id"));
//                inventoryMgt.setReqId(rs.getString("req_id"));
//                inventoryMgt.setRack(rs.getString("rack"));
//                inventoryMgt.setShelf(rs.getString("shelf"));
//                inventoryMgt.setStatus(rs.getString("status"));
//                inventoryMgt.setFlag(rs.getString("flag"));
//                inventoryMgt.setModifiedDate(rs.getString("modified_date"));
//                inventoryMgt.setDateCreated(rs.getString("date_created"));
//                inventoryMgt.setRmsLotEvent(rs.getString("rms_lot_event"));
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        return inventoryMgt;
//    }
    public List<InventoryMgt> getInventoryMgtList() {
        String sql = "SELECT * FROM sr_inventory_mgt ORDER BY shelf";
        List<InventoryMgt> inventoryMgtList = new ArrayList<InventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            InventoryMgt inventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventoryMgt = new InventoryMgt();
                inventoryMgt.setId(rs.getString("id"));
//                inventoryMgt.setReqId(rs.getString("req_id"));
                inventoryMgt.setRack(rs.getString("rack"));
                inventoryMgt.setShelf(rs.getString("shelf"));
                inventoryMgt.setStatus(rs.getString("status"));
                inventoryMgt.setFlag(rs.getString("flag"));
                inventoryMgt.setModifiedDate(rs.getString("modified_date"));
                inventoryMgt.setDateCreated(rs.getString("date_created"));
                inventoryMgt.setRmsLotEvent(rs.getString("rms_lot_event"));
                inventoryMgtList.add(inventoryMgt);
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
        return inventoryMgtList;
    }

    public List<InventoryMgt> getInventoryMgtListwithSampleDetail() {
        String sql = "SELECT mg.id, mg.req_id, mg.shelf, mg.`status`, mg.flag, ft.rmslot_event "
                + "FROM sr_inventory_mgt mg "
                + "LEFT JOIN sr_request re ON mg.req_id = re.id "
                + "LEFT JOIN sr_ftp_data ft ON re.ftp_id = ft.id "
                + "ORDER BY shelf";
        List<InventoryMgt> inventoryMgtList = new ArrayList<InventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            InventoryMgt inventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventoryMgt = new InventoryMgt();
                inventoryMgt.setId(rs.getString("id"));
                inventoryMgt.setReqId(rs.getString("req_id"));
                inventoryMgt.setShelf(rs.getString("shelf"));
                inventoryMgt.setStatus(rs.getString("status"));
                inventoryMgt.setFlag(rs.getString("flag"));
                inventoryMgt.setRmsLotEvent(rs.getString("rmslot_event"));
                inventoryMgtList.add(inventoryMgt);
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
        return inventoryMgtList;
    }

    public List<InventoryMgt> getInventoryMgtListwithSampleDetailNew() {
//        String sql = "SELECT mg.id, mg.req_id, mg.shelf, mg.`status`, mg.flag, mg.rms_lot_event "
        String sql = "SELECT mg.id, mg.shelf, mg.`status`, mg.flag, mg.rms_lot_event "
                + "FROM sr_inventory_mgt mg "
                + "ORDER BY status";
        List<InventoryMgt> inventoryMgtList = new ArrayList<InventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            InventoryMgt inventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventoryMgt = new InventoryMgt();
                inventoryMgt.setId(rs.getString("id"));
//                inventoryMgt.setReqId(rs.getString("req_id"));
                inventoryMgt.setShelf(rs.getString("shelf"));
                inventoryMgt.setStatus(rs.getString("status"));
                inventoryMgt.setFlag(rs.getString("flag"));
                inventoryMgt.setRmsLotEvent(rs.getString("rms_lot_event"));
                inventoryMgtList.add(inventoryMgt);
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
        return inventoryMgtList;
    }

    public List<InventoryMgt> getInventoryMgtWithInventoryAndRequestAndFtpTable() {
        String sql = "SELECT mg.id, mg.shelf, mg.`status`, mg.flag, GROUP_CONCAT(ft.rmslot_event SEPARATOR ', ') AS rmslot_event_concat "
                + "FROM sr_inventory_mgt mg "
                + "LEFT JOIN sr_inventory inv ON mg.id = inv.shelf_id "
                + "LEFT JOIN sr_request re ON inv.req_id = re.id "
                + "LEFT JOIN sr_ftp_data ft ON re.ftp_id = ft.id "
                + "GROUP BY mg.id "
                + "ORDER BY mg.`status`";
        List<InventoryMgt> inventoryMgtList = new ArrayList<InventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            InventoryMgt inventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventoryMgt = new InventoryMgt();
                inventoryMgt.setId(rs.getString("id"));
//                inventoryMgt.setReqId(rs.getString("req_id"));
                inventoryMgt.setShelf(rs.getString("shelf"));
                inventoryMgt.setStatus(rs.getString("status"));
                inventoryMgt.setFlag(rs.getString("flag"));
                inventoryMgt.setRmsLotEvent(rs.getString("rmslot_event_concat"));
                inventoryMgtList.add(inventoryMgt);
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
        return inventoryMgtList;
    }

    public List<InventoryMgt> getInventoryMgtWithInventoryAndRequestAndFtpTableByRack(String rack) {
        String sql = "SELECT mg.id, mg.shelf, mg.`status`, mg.flag, GROUP_CONCAT(ft.rmslot_event SEPARATOR ', ') AS rmslot_event_concat "
                + "FROM sr_inventory_mgt mg "
                + "LEFT JOIN sr_inventory inv ON mg.id = inv.shelf_id "
                + "LEFT JOIN sr_request re ON inv.req_id = re.id "
                + "LEFT JOIN sr_ftp_data ft ON re.ftp_id = ft.id "
                + "WHERE mg.rack = '" + rack + "' "
                + "GROUP BY mg.id "
                + "ORDER BY mg.`status`";
        List<InventoryMgt> inventoryMgtList = new ArrayList<InventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            InventoryMgt inventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventoryMgt = new InventoryMgt();
                inventoryMgt.setId(rs.getString("id"));
//                inventoryMgt.setReqId(rs.getString("req_id"));
                inventoryMgt.setShelf(rs.getString("shelf"));
                inventoryMgt.setStatus(rs.getString("status"));
                inventoryMgt.setFlag(rs.getString("flag"));
                inventoryMgt.setRmsLotEvent(rs.getString("rmslot_event_concat"));
                inventoryMgtList.add(inventoryMgt);
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
        return inventoryMgtList;
    }

    public Integer getCountShelfIdInInventoryTable(String invMId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_inventory inv, sr_inventory_mgt invM WHERE inv.shelf_id = invM.id AND invM.id = '" + invMId + "' AND inv.flag = '0'"
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

    public Integer getCountShelfId(String shelf) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS COUNT FROM sr_inventory_mgt invM WHERE invM.shelf = '" + shelf + "'"
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

    public String getMaxShelfNumber(String rack) {
        String shelfNumberSeq = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT IFNULL(MAX(mg.shelf),0) AS do_number_seq FROM sr_inventory_mgt mg WHERE mg.shelf LIKE '" + rack + "%'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                shelfNumberSeq = rs.getString("do_number_seq");
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
        return shelfNumberSeq;
    }

}
