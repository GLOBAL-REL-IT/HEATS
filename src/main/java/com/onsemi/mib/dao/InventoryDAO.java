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
import com.onsemi.mib.model.Inventory;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public InventoryDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertInventory(Inventory inventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_inventory (req_id, shelf_id, mth_to_scrap, inventory_rack, inventory_shelf, inventory_by, inventory_date, inventory_remarks, status, flag, modified_date, modified_by, created_date, created_by) VALUES (?,?,?,?,?,?,NOW(),?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, inventory.getReqId());
            ps.setString(2, inventory.getShelfId());
            ps.setString(3, inventory.getMthToScrap());
            ps.setString(4, inventory.getInventoryRack());
            ps.setString(5, inventory.getInventoryShelf());
            ps.setString(6, inventory.getInventoryBy());
            ps.setString(7, inventory.getInventoryRemarks());
            ps.setString(8, inventory.getStatus());
            ps.setString(9, inventory.getFlag());
            ps.setString(10, inventory.getModifiedDate());
            ps.setString(11, inventory.getModifiedBy());
            ps.setString(12, inventory.getCreatedBy());
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

    public QueryResult updateInventory(Inventory inventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_inventory SET req_id = ?, shelf_id = ?, mth_to_scrap = ?, inventory_rack = ?, inventory_shelf = ?, inventory_by = ?, inventory_date = ?, inventory_remarks = ?, status = ?, flag = ?, modified_date = ?, modified_by = ?, created_date = ?, created_by = ? WHERE id = ?"
            );
            ps.setString(1, inventory.getReqId());
            ps.setString(2, inventory.getShelfId());
            ps.setString(3, inventory.getMthToScrap());
            ps.setString(4, inventory.getInventoryRack());
            ps.setString(5, inventory.getInventoryShelf());
            ps.setString(6, inventory.getInventoryBy());
            ps.setString(7, inventory.getInventoryDate());
            ps.setString(8, inventory.getInventoryRemarks());
            ps.setString(9, inventory.getStatus());
            ps.setString(10, inventory.getFlag());
            ps.setString(11, inventory.getModifiedDate());
            ps.setString(12, inventory.getModifiedBy());
            ps.setString(13, inventory.getCreatedDate());
            ps.setString(14, inventory.getCreatedBy());
            ps.setString(15, inventory.getId());
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

    public QueryResult updateInventoryStatusAndFlag(Inventory inventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_inventory SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE id = ?"
            );
            ps.setString(1, inventory.getStatus());
            ps.setString(2, inventory.getFlag());
            ps.setString(3, inventory.getModifiedBy());
            ps.setString(4, inventory.getId());
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

    public QueryResult updateInventoryLocation(Inventory inventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_inventory SET req_id = ?, inventory_rack = ?, inventory_shelf = ?, inventory_by = ?, "
                    + "inventory_date = NOW(), status = ?, flag = ?, modified_date = NOW(), modified_by = ?, shelf_id = ? WHERE id = ?"
            );
            ps.setString(1, inventory.getReqId());
            ps.setString(2, inventory.getInventoryRack());
            ps.setString(3, inventory.getInventoryShelf());
            ps.setString(4, inventory.getInventoryBy());
            ps.setString(5, inventory.getStatus());
            ps.setString(6, inventory.getFlag());
            ps.setString(7, inventory.getModifiedBy());
            ps.setString(8, inventory.getShelfId());
            ps.setString(9, inventory.getId());
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

    public QueryResult deleteInventory(String inventoryId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_inventory WHERE id = '" + inventoryId + "'"
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

    public Inventory getInventory(String inventoryId) {
        String sql = "SELECT *,DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS inventory_date_view, "
                + "DATE_FORMAT(mth_to_scrap,'%M %Y') AS mth_to_scrap_view "
                + "FROM sr_inventory WHERE id = '" + inventoryId + "'";
        Inventory inventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventory = new Inventory();
                inventory.setId(rs.getString("id"));
                inventory.setReqId(rs.getString("req_id"));
                inventory.setShelfId(rs.getString("shelf_id"));
                inventory.setMthToScrap(rs.getString("mth_to_scrap_view"));
                inventory.setInventoryRack(rs.getString("inventory_rack"));
                inventory.setInventoryShelf(rs.getString("inventory_shelf"));
                inventory.setInventoryBy(rs.getString("inventory_by"));
                inventory.setInventoryDate(rs.getString("inventory_date_view"));
                inventory.setInventoryRemarks(rs.getString("inventory_remarks"));
                inventory.setStatus(rs.getString("status"));
                inventory.setFlag(rs.getString("flag"));
                inventory.setModifiedDate(rs.getString("modified_date"));
                inventory.setModifiedBy(rs.getString("modified_by"));
                inventory.setCreatedDate(rs.getString("created_date"));
                inventory.setCreatedBy(rs.getString("created_by"));
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
        return inventory;
    }

    public Inventory getInventoryByShelfId(String shelf) {
        String sql = "SELECT * FROM sr_inventory_mgt WHERE shelf = '" + shelf + "'";
        Inventory inventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventory = new Inventory();
                inventory.setId(rs.getString("id"));
                inventory.setReqId(rs.getString("req_id"));
                inventory.setInventoryRack(rs.getString("rack"));
                inventory.setInventoryShelf(rs.getString("shelf"));
                inventory.setStatus(rs.getString("status"));
                inventory.setFlag(rs.getString("flag"));
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
        return inventory;
    }

    public List<Inventory> getInventoryList() {
        String sql = "SELECT *, DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS inventory_date_view, "
                + "DATE_FORMAT(inv.mth_to_scrap,'%M %Y') AS mth_to_scrap_view, "
                + "DATEDIFF(inv.mth_to_scrap, NOW()) AS aging "
                + "FROM sr_inventory inv, sr_request re, sr_ftp_data ftp "
                + "WHERE inv.flag = '0' AND inv.req_id = re.id AND re.ftp_id = ftp.id "
                + "ORDER BY re.id DESC";
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Inventory inventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventory = new Inventory();
                inventory.setId(rs.getString("inv.id"));
                inventory.setReqId(rs.getString("inv.req_id"));
//                inventory.setBoxId(rs.getString("inv.box_id"));
                inventory.setMthToScrap(rs.getString("mth_to_scrap_view"));
                inventory.setInventoryRack(rs.getString("inv.inventory_rack"));
                inventory.setInventoryShelf(rs.getString("inv.inventory_shelf"));
                inventory.setInventoryBy(rs.getString("inv.inventory_by"));
                inventory.setInventoryDate(rs.getString("inventory_date_view"));
//                inventory.setInventoryRemarks(rs.getString("inv.inventory_remarks"));
                inventory.setStatus(rs.getString("inv.status"));
                inventory.setFlag(rs.getString("inv.flag"));
//                inventory.setModifiedDate(rs.getString("modified_date"));
//                inventory.setModifiedBy(rs.getString("modified_by"));
//                inventory.setCreatedDate(rs.getString("created_date"));
//                inventory.setCreatedBy(rs.getString("created_by"));
                inventory.setRmsLotEvent(rs.getString("ftp.rmslot_event"));
                inventory.setQty(rs.getString("ftp.actual_qty"));
                inventory.setPackageFamily(rs.getString("ftp.pkg_family"));
                inventory.setPackageName(rs.getString("ftp.pkg_name"));
                inventory.setAging(rs.getString("aging"));
                inventoryList.add(inventory);
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
        return inventoryList;
    }

    public List<Inventory> getInventoryListActive() {
        String sql = "SELECT inv.*, DATE_FORMAT(inv.inventory_date,'%d %M %Y') AS inventory_date_view, "
                + "DATE_FORMAT(inv.mth_to_scrap,'%M %Y') AS mth_to_scrap_view, "
                + "ftp.rms_id, ftp.rms_event, ftp.lot_type, "
                + "DATE_FORMAT(ftp.completed_date,'%d %M %Y') AS complete_date_view , "
                + "ftp.rmslot_event, ftp.actual_qty, ftp.pkg_family, ftp.pkg_name, re.ftp_id "
                + "FROM sr_inventory inv, sr_request re, sr_ftp_data ftp "
                + "WHERE inv.flag = '0' AND inv.status = 'In Inventory' AND inv.req_id = re.id AND re.ftp_id = ftp.id "
                + "ORDER BY id DESC";
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Inventory inventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventory = new Inventory();
                inventory.setId(rs.getString("inv.id"));
                inventory.setReqId(rs.getString("inv.req_id"));
                inventory.setFtpId(rs.getString("re.ftp_id"));
                inventory.setMthToScrap(rs.getString("mth_to_scrap_view"));
                inventory.setInventoryShelf(rs.getString("inventory_shelf"));
                inventory.setInventoryBy(rs.getString("inventory_by"));
                inventory.setInventoryDate(rs.getString("inventory_date_view"));
                inventory.setInventoryRemarks(rs.getString("inventory_remarks"));
                inventory.setStatus(rs.getString("inv.status"));
                inventory.setFlag(rs.getString("inv.flag"));
                inventory.setRmsLotEvent(rs.getString("ftp.rmslot_event"));
                inventory.setQty(rs.getString("ftp.actual_qty"));
                inventory.setPackageFamily(rs.getString("ftp.pkg_family"));
                inventory.setPackageName(rs.getString("ftp.pkg_name"));
                inventory.setRmsId(rs.getString("ftp.rms_id"));
                inventory.setLot(rs.getString("ftp.lot_type"));
                inventory.setRmsEvent(rs.getString("ftp.rms_event"));
                inventory.setCompleteDate(rs.getString("complete_date_view"));
//                inventory.setModifiedDate(rs.getString("modified_date"));
//                inventory.setModifiedBy(rs.getString("modified_by"));
//                inventory.setCreatedDate(rs.getString("created_date"));
//                inventory.setCreatedBy(rs.getString("created_by"));

                inventoryList.add(inventory);
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
        return inventoryList;
    }

    public Inventory getInventoryListActiveByInvId(String invId) {
        String sql = "SELECT inv.*, DATE_FORMAT(inv.inventory_date,'%d %M %Y') AS inventory_date_view, "
                + "DATE_FORMAT(inv.mth_to_scrap,'%M %Y') AS mth_to_scrap_view, "
                + "ftp.rms_id, ftp.rms_event, ftp.lot_type, "
                + "DATE_FORMAT(ftp.completed_date,'%d %M %Y') AS complete_date_view , "
                + "ftp.rmslot_event, ftp.actual_qty, ftp.pkg_family, ftp.pkg_name, re.ftp_id "
                + "FROM sr_inventory inv, sr_request re, sr_ftp_data ftp "
                + "WHERE inv.id = '" + invId + "' AND inv.flag = '0' AND inv.status = 'In Inventory' AND inv.req_id = re.id AND re.ftp_id = ftp.id ";
//                + "ORDER BY id DESC";
        Inventory inventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventory = new Inventory();
                inventory.setId(rs.getString("inv.id"));
                inventory.setReqId(rs.getString("inv.req_id"));
                inventory.setFtpId(rs.getString("re.ftp_id"));
                inventory.setMthToScrap(rs.getString("mth_to_scrap_view"));
                inventory.setInventoryShelf(rs.getString("inventory_shelf"));
                inventory.setInventoryBy(rs.getString("inventory_by"));
                inventory.setInventoryDate(rs.getString("inventory_date_view"));
                inventory.setInventoryRemarks(rs.getString("inventory_remarks"));
                inventory.setStatus(rs.getString("inv.status"));
                inventory.setFlag(rs.getString("inv.flag"));
                inventory.setRmsLotEvent(rs.getString("ftp.rmslot_event"));
                inventory.setQty(rs.getString("ftp.actual_qty"));
                inventory.setPackageFamily(rs.getString("ftp.pkg_family"));
                inventory.setPackageName(rs.getString("ftp.pkg_name"));
                inventory.setRmsId(rs.getString("ftp.rms_id"));
                inventory.setLot(rs.getString("ftp.lot_type"));
                inventory.setRmsEvent(rs.getString("ftp.rms_event"));
                inventory.setCompleteDate(rs.getString("complete_date_view"));

//                inventoryList.add(inventory);
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
        return inventory;
    }

    public Inventory getInventoryPendingforScrapByInvId(String invId) {
        String sql = "SELECT inv.*, DATE_FORMAT(inv.inventory_date,'%d %M %Y') AS inventory_date_view, "
                + "DATE_FORMAT(inv.mth_to_scrap,'%M %Y') AS mth_to_scrap_view, "
                + "ftp.rms_id, ftp.rms_event, ftp.lot_type, "
                + "DATE_FORMAT(ftp.completed_date,'%d %M %Y') AS complete_date_view , "
                + "ftp.rmslot_event, ftp.actual_qty, ftp.pkg_family, ftp.pkg_name, re.ftp_id "
                + "FROM sr_inventory inv, sr_request re, sr_ftp_data ftp "
                + "WHERE inv.id = '" + invId + "' AND inv.flag = '0' AND inv.status = 'Pending Scrap' AND inv.req_id = re.id AND re.ftp_id = ftp.id ";
//                + "ORDER BY id DESC";
        Inventory inventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventory = new Inventory();
                inventory.setId(rs.getString("inv.id"));
                inventory.setReqId(rs.getString("inv.req_id"));
                inventory.setFtpId(rs.getString("re.ftp_id"));
                inventory.setMthToScrap(rs.getString("mth_to_scrap_view"));
                inventory.setInventoryShelf(rs.getString("inventory_shelf"));
                inventory.setInventoryBy(rs.getString("inventory_by"));
                inventory.setInventoryDate(rs.getString("inventory_date_view"));
                inventory.setInventoryRemarks(rs.getString("inventory_remarks"));
                inventory.setStatus(rs.getString("inv.status"));
                inventory.setFlag(rs.getString("inv.flag"));
                inventory.setRmsLotEvent(rs.getString("ftp.rmslot_event"));
                inventory.setQty(rs.getString("ftp.actual_qty"));
                inventory.setPackageFamily(rs.getString("ftp.pkg_family"));
                inventory.setPackageName(rs.getString("ftp.pkg_name"));
                inventory.setRmsId(rs.getString("ftp.rms_id"));
                inventory.setLot(rs.getString("ftp.lot_type"));
                inventory.setRmsEvent(rs.getString("ftp.rms_event"));
                inventory.setCompleteDate(rs.getString("complete_date_view"));

//                inventoryList.add(inventory);
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
        return inventory;
    }

    public List<Inventory> getInventoryListForScrap() {
        String sql = "SELECT req.id AS reqId, req.inv_id AS invId, ft.id AS ftpId, ft.rmslot_event, ft.actual_qty, ft.pkg_family, "
                + "ft.pkg_name, inv.mth_to_scrap, DATE_FORMAT(inv.mth_to_scrap,'%M %Y') AS mthToScrapView, inv.inventory_shelf, inv.`status` "
                + "FROM sr_inventory inv, sr_request req, sr_ftp_data ft "
                + "WHERE inv.req_id = req.id AND req.ftp_id = ft.id AND DATEDIFF(inv.mth_to_scrap, NOW()) <= 0 "
                + "AND inv.flag = '0' AND inv.`status` = 'In Inventory'";
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Inventory inventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventory = new Inventory();
                inventory.setId(rs.getString("invId"));
                inventory.setReqId(rs.getString("reqId"));
                inventory.setFtpId(rs.getString("ftpId"));
                inventory.setMthToScrap(rs.getString("mth_to_scrap"));
                inventory.setMthToScrapView(rs.getString("mthToScrapView"));
                inventory.setInventoryShelf(rs.getString("inventory_shelf"));
                inventory.setRmsLotEvent(rs.getString("rmslot_event"));
                inventory.setQty(rs.getString("actual_qty"));
                inventory.setPackageFamily(rs.getString("pkg_family"));
                inventory.setPackageName(rs.getString("pkg_name"));
                inventoryList.add(inventory);
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
        return inventoryList;
    }

    public List<Inventory> getInventoryListPendingForScrap() {
        String sql = "SELECT req.id AS reqId, req.inv_id AS invId, ft.id AS ftpId, ft.rmslot_event, ft.actual_qty, ft.pkg_family, "
                + "ft.pkg_name, inv.mth_to_scrap, DATE_FORMAT(inv.mth_to_scrap,'%M %Y') AS mthToScrapView, inv.inventory_shelf, inv.`status` "
                + "FROM sr_inventory inv, sr_request req, sr_ftp_data ft "
                + "WHERE inv.req_id = req.id AND req.ftp_id = ft.id AND DATEDIFF(inv.mth_to_scrap, NOW()) <= 0 "
                + "AND inv.flag = '0' AND inv.`status` = 'Pending Scrap'";
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Inventory inventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inventory = new Inventory();
                inventory.setId(rs.getString("invId"));
                inventory.setReqId(rs.getString("reqId"));
                inventory.setFtpId(rs.getString("ftpId"));
                inventory.setMthToScrap(rs.getString("mth_to_scrap"));
                inventory.setMthToScrapView(rs.getString("mthToScrapView"));
                inventory.setInventoryShelf(rs.getString("inventory_shelf"));
                inventory.setRmsLotEvent(rs.getString("rmslot_event"));
                inventory.setQty(rs.getString("actual_qty"));
                inventory.setPackageFamily(rs.getString("pkg_family"));
                inventory.setPackageName(rs.getString("pkg_name"));
                inventoryList.add(inventory);
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
        return inventoryList;
    }

    public Integer getCountAvailableShelf(String shelf) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    //                    "SELECT COUNT(*) AS count FROM sr_inventory_mgt WHERE shelf = '" + shelf + "' AND flag = '0'" 
                    "SELECT COUNT(*) AS count FROM sr_inventory_mgt WHERE shelf = '" + shelf + "'" // 1 shelf can have multiple items
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
