package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.SRInventory;
import com.onsemi.mib.tools.QueryResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SRInventoryDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(SRInventoryDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public SRInventoryDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertReceiving(SRInventory sampleInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO sr_inventory (req_id, box_id, gts_no, received_date, custom_no, custom_date, event, mth_to_scrap, pkg_family, modified_date, modified_by, created_date, created_by, status, flag ) "
                + "VALUES (?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, sampleInventory.getReqId());
            ps.setString(2, sampleInventory.getBoxId());
            ps.setString(3, sampleInventory.getGtsNo());
            ps.setString(4, sampleInventory.getReceivedDate());
            ps.setString(5, sampleInventory.getCustomNo());
            ps.setString(6, sampleInventory.getCustomDate());
            ps.setString(7, sampleInventory.getEvent());
            ps.setString(8, sampleInventory.getMthToScrap());
            ps.setString(9, sampleInventory.getPkgFamily());
            ps.setString(10, sampleInventory.getModifiedBy());
            ps.setString(11, sampleInventory.getCreatedBy());
            ps.setString(12, sampleInventory.getStatus());
            ps.setString(13, sampleInventory.getFlag());
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
    
    public QueryResult updateInventory(SRInventory sampleInventory) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_inventory SET "
                   + "inventory_rack = ?, inventory_shelf = ?, inventory_date = ?, inventory_by = ?, flag = ?, status = ?, modified_date = NOW(), modified_by = ? "
                   + "WHERE req_id  = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sampleInventory.getInventoryRack());
            ps.setString(2, sampleInventory.getInventoryShelf());
            ps.setString(3, sampleInventory.getInventoryDate());
            ps.setString(4, sampleInventory.getInventoryBy());
            ps.setString(5, sampleInventory.getFlag());
            ps.setString(6, sampleInventory.getStatus());
            ps.setString(7, sampleInventory.getModifiedBy());
            ps.setString(8, sampleInventory.getReqId());
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
    
    public List<SRInventory> getAllInventoryList() {
        String sql = "SELECT *, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, COUNT(I.rmslot_event) AS count_lot, "
                   + "DATEDIFF(V.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(V.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                   + "DATE_FORMAT(V.modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(V.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, "
                   + "IFNULL(DATE_FORMAT(V.inventory_date,'%d/%m/%y %h:%i %p'),V.status) AS inventory_date_view , IFNULL(V.inventory_shelf,'-') AS shelf_id_view "
                   + "FROM sr_inventory V, sr_req_inner I "
                   + "WHERE V.flag NOT LIKE '99' AND V.req_id = I.req_id "
                   + "GROUP BY V.id "
                   + "ORDER BY V.created_date DESC ";
        List<SRInventory> retrieveList = new ArrayList<SRInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRInventory sampleInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleInventory = new SRInventory();
                sampleInventory.setId(rs.getString("V.id"));
                sampleInventory.setReqId(rs.getString("V.req_id"));
                sampleInventory.setBoxId(rs.getString("V.box_id"));
                sampleInventory.setGtsNo(rs.getString("V.gts_no"));
                sampleInventory.setCustomNo(rs.getString("custom_no"));
                sampleInventory.setInventoryBy(rs.getString("V.inventory_by"));
                sampleInventory.setInventoryDate(rs.getString("inventory_date_view"));
                sampleInventory.setInventoryRack(rs.getString("V.inventory_rack"));
                sampleInventory.setInventoryShelf(rs.getString("shelf_id_view"));
                sampleInventory.setEvent(rs.getString("V.event"));
                sampleInventory.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleInventory.setPkgFamily(rs.getString("V.pkg_family"));
                sampleInventory.setModifiedBy(rs.getString("V.modified_by"));
                sampleInventory.setModifiedDate(rs.getString("modified_date_view"));
                sampleInventory.setCreatedBy(rs.getString("V.created_by"));
                sampleInventory.setCreatedDate(rs.getString("created_date_view"));
                sampleInventory.setStatus(rs.getString("V.status"));
                sampleInventory.setFlag(rs.getString("V.flag"));
                sampleInventory.setAging(rs.getString("aging"));
                sampleInventory.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampleInventory.setLotQty(rs.getString("count_lot"));
                retrieveList.add(sampleInventory);
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
        return retrieveList;
    }
    
    public SRInventory getInventoryDetails(String reqId) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                   + "DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view, "
                   + "DATE_FORMAT(received_date,'%d/%m/%y %h:%i %p') AS received_date_view, DATE_FORMAT(custom_date,'%d/%m/%y %h:%i %p') AS custom_date_view, "
                   + "DATE_FORMAT(inventory_date,'%d/%m/%y %h:%i %p') AS inventory_date_view, DATE_FORMAT(inventory_date_new,'%d/%m/%y %h:%i %p') AS inventory_date_new_view "
                   + "FROM sr_inventory "
                   + "WHERE req_id LIKE '" + reqId + "' "
                   + "ORDER BY created_date DESC ";
        SRInventory sampleInventory = new SRInventory();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleInventory.setId(rs.getString("id"));
                sampleInventory.setReqId(rs.getString("req_id"));
                sampleInventory.setBoxId(rs.getString("box_id"));
                sampleInventory.setEvent(rs.getString("event"));
                sampleInventory.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleInventory.setPkgFamily(rs.getString("pkg_family"));
                sampleInventory.setGtsNo(rs.getString("gts_no"));
                sampleInventory.setReceivedDate(rs.getString("received_date_view"));
                sampleInventory.setCustomNo(rs.getString("custom_no"));
                sampleInventory.setCustomDate(rs.getString("custom_date_view"));
                sampleInventory.setInventoryRack(rs.getString("inventory_rack"));
                sampleInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                sampleInventory.setInventoryBy(rs.getString("inventory_by"));
                sampleInventory.setInventoryDate(rs.getString("inventory_date_view"));
                sampleInventory.setInventoryDateNew(rs.getString("inventory_date_new_view"));
                sampleInventory.setInventoryRemarks(rs.getString("inventory_remarks"));
                sampleInventory.setStatus(rs.getString("status"));
                sampleInventory.setFlag(rs.getString("flag"));
                sampleInventory.setModifiedBy(rs.getString("modified_by"));
                sampleInventory.setModifiedDate(rs.getString("modified_date_view"));
                sampleInventory.setCreatedBy(rs.getString("created_by"));
                sampleInventory.setCreatedDate(rs.getString("created_date_view"));
                sampleInventory.setAging(rs.getString("aging"));
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
        return sampleInventory;
    }
    
    public SRInventory getInventoryDetailsActual(String reqId) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging "
                   + "FROM sr_inventory "
                   + "WHERE req_id LIKE '" + reqId + "' ";
        SRInventory sampleInventory = new SRInventory();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleInventory.setId(rs.getString("id"));
                sampleInventory.setReqId(rs.getString("req_id"));
                sampleInventory.setBoxId(rs.getString("box_id"));
                sampleInventory.setEvent(rs.getString("event"));
                sampleInventory.setMthToScrap(rs.getString("mth_to_scrap"));
                sampleInventory.setPkgFamily(rs.getString("pkg_family"));
                sampleInventory.setGtsNo(rs.getString("gts_no"));
                sampleInventory.setReceivedDate(rs.getString("received_date"));
                sampleInventory.setCustomNo(rs.getString("custom_no"));
                sampleInventory.setCustomDate(rs.getString("custom_date"));
                sampleInventory.setInventoryRack(rs.getString("inventory_rack"));
                sampleInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                sampleInventory.setInventoryBy(rs.getString("inventory_by"));
                sampleInventory.setInventoryDate(rs.getString("inventory_date"));
                sampleInventory.setInventoryDate(rs.getString("inventory_date_new"));
                sampleInventory.setInventoryDate(rs.getString("inventory_remarks"));
                sampleInventory.setStatus(rs.getString("status"));
                sampleInventory.setFlag(rs.getString("flag"));
                sampleInventory.setModifiedBy(rs.getString("modified_by"));
                sampleInventory.setModifiedDate(rs.getString("modified_date"));
                sampleInventory.setCreatedBy(rs.getString("created_by"));
                sampleInventory.setCreatedDate(rs.getString("created_date"));
                sampleInventory.setAging(rs.getString("aging"));
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
        return sampleInventory;
    }
    
    public QueryResult updateInventoryStatus(SRInventory sampleInventory) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_inventory SET "
                   + "flag = ?, status = ?, modified_date = NOW(), modified_by = ? "
                   + "WHERE id  = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sampleInventory.getFlag());
            ps.setString(2, sampleInventory.getStatus());
            ps.setString(3, sampleInventory.getModifiedBy());
            ps.setString(4, sampleInventory.getId());
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
    
    public QueryResult updateNewInventory(SRInventory sampleInventory) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_inventory SET "
                   + "inventory_rack = ?, inventory_shelf = ?, inventory_date_new = ?, inventory_remarks = ?, modified_date = NOW(), modified_by = ? "
                   + "WHERE id  = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sampleInventory.getInventoryRack());
            ps.setString(2, sampleInventory.getInventoryShelf());
            ps.setString(3, sampleInventory.getInventoryDateNew());
            ps.setString(4, sampleInventory.getInventoryRemarks());
            ps.setString(5, sampleInventory.getModifiedBy());
            ps.setString(6, sampleInventory.getId());
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
    
    public Integer getCountExistData(String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(req_id) AS count FROM sr_inventory " +
                "WHERE req_id = '" + id + "' "
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
    
    public List<SRInventory> getInventoryDueScrap() {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging "
                   + "FROM sr_inventory "
                   + "WHERE DATEDIFF(mth_to_scrap, NOW()) <= 0 AND flag = 0 "
                   + "ORDER BY inventory_shelf ASC";
        List<SRInventory> dueScrapList = new ArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRInventory sampleInventory = new SRInventory();
                sampleInventory.setId(rs.getString("id"));
                sampleInventory.setReqId(rs.getString("req_id"));
                sampleInventory.setBoxId(rs.getString("box_id"));
                sampleInventory.setEvent(rs.getString("event"));
                sampleInventory.setMthToScrap(rs.getString("mth_to_scrap"));
                sampleInventory.setPkgFamily(rs.getString("pkg_family"));
                sampleInventory.setGtsNo(rs.getString("gts_no"));
                sampleInventory.setReceivedDate(rs.getString("received_date"));
                sampleInventory.setCustomNo(rs.getString("custom_no"));
                sampleInventory.setCustomDate(rs.getString("custom_date"));
                sampleInventory.setInventoryRack(rs.getString("inventory_rack"));
                sampleInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                sampleInventory.setInventoryBy(rs.getString("inventory_by"));
                sampleInventory.setInventoryDate(rs.getString("inventory_date"));
                sampleInventory.setInventoryDate(rs.getString("inventory_date_new"));
                sampleInventory.setInventoryDate(rs.getString("inventory_remarks"));
                sampleInventory.setStatus(rs.getString("status"));
                sampleInventory.setFlag(rs.getString("flag"));
                sampleInventory.setModifiedBy(rs.getString("modified_by"));
                sampleInventory.setModifiedDate(rs.getString("modified_date"));
                sampleInventory.setCreatedBy(rs.getString("created_by"));
                sampleInventory.setCreatedDate(rs.getString("created_date"));
                sampleInventory.setAging(rs.getString("aging"));
                dueScrapList.add(sampleInventory);
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
        return dueScrapList;
    }
    
    public List<SRInventory> getAllBoxIdInventoryList() {
        String sql = "SELECT * "
                   + "FROM sr_inventory V, sr_req_inner I "
                   + "WHERE V.req_id = I.req_id AND V.flag = 0 AND I.req_id NOT IN (SELECT req_id FROM sr_retrieve) "
                   + "GROUP BY V.req_id "
                   + "ORDER BY V.box_id ASC ";
        List<SRInventory> retrieveList = new ArrayList<SRInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRInventory sampleInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleInventory = new SRInventory();
                sampleInventory.setId(rs.getString("V.id"));
                sampleInventory.setReqId(rs.getString("V.req_id"));
                sampleInventory.setBoxId(rs.getString("V.box_id"));
                sampleInventory.setGtsNo(rs.getString("V.gts_no"));
                sampleInventory.setInventoryBy(rs.getString("V.inventory_by"));
                sampleInventory.setInventoryDate(rs.getString("V.inventory_date"));
                sampleInventory.setInventoryRack(rs.getString("V.inventory_rack"));
                sampleInventory.setInventoryShelf(rs.getString("V.inventory_shelf"));
                sampleInventory.setEvent(rs.getString("V.event"));
                sampleInventory.setMthToScrap(rs.getString("V.mth_to_scrap"));
                sampleInventory.setPkgFamily(rs.getString("V.pkg_family"));
                sampleInventory.setModifiedBy(rs.getString("V.modified_by"));
                sampleInventory.setModifiedDate(rs.getString("modified_date"));
                sampleInventory.setCreatedBy(rs.getString("V.created_by"));
                sampleInventory.setCreatedDate(rs.getString("V.created_date"));
                sampleInventory.setStatus(rs.getString("V.status"));
                sampleInventory.setFlag(rs.getString("V.flag"));
                retrieveList.add(sampleInventory);
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
        return retrieveList;
    }
    
    public List<SRInventory> getAllRmsInInventoryList() {
        String sql = "SELECT *, CONCAT(I.rms_no, ' (', I.event, ') ') AS concat_rms "
                   + "FROM sr_inventory V, sr_req_inner I "
                   + "WHERE V.req_id = I.req_id AND V.flag = 0 AND I.flag = 4 AND I.req_id NOT IN (SELECT req_id FROM sr_retrieve) "
                   + "GROUP BY CONCAT(I.rms_no, '_', I.`event`) "
                   + "ORDER BY concat_rms ASC ";
        List<SRInventory> retrieveList = new ArrayList<SRInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRInventory sampleInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleInventory = new SRInventory();
                sampleInventory.setId(rs.getString("V.id"));
                sampleInventory.setReqId(rs.getString("V.req_id"));
                sampleInventory.setBoxId(rs.getString("V.box_id"));
                sampleInventory.setGtsNo(rs.getString("V.gts_no"));
                sampleInventory.setInventoryBy(rs.getString("V.inventory_by"));
                sampleInventory.setInventoryDate(rs.getString("V.inventory_date"));
                sampleInventory.setInventoryRack(rs.getString("V.inventory_rack"));
                sampleInventory.setInventoryShelf(rs.getString("V.inventory_shelf"));
                sampleInventory.setEvent(rs.getString("V.event"));
                sampleInventory.setMthToScrap(rs.getString("V.mth_to_scrap"));
                sampleInventory.setPkgFamily(rs.getString("V.pkg_family"));
                sampleInventory.setModifiedBy(rs.getString("V.modified_by"));
                sampleInventory.setModifiedDate(rs.getString("modified_date"));
                sampleInventory.setCreatedBy(rs.getString("V.created_by"));
                sampleInventory.setCreatedDate(rs.getString("V.created_date"));
                sampleInventory.setStatus(rs.getString("V.status"));
                sampleInventory.setFlag(rs.getString("V.flag"));
                sampleInventory.setConcatRmsEvent(rs.getString("concat_rms"));
                retrieveList.add(sampleInventory);
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
        return retrieveList;
    }
    
    public List<SRInventory> getMthToScrapList() {
        String sql = "SELECT DISTINCT UPPER((DATE_FORMAT(mth_to_scrap,'%M %Y'))) AS mthToScrapView, (DATE_FORMAT(mth_to_scrap,'%Y-%m-01')) AS mthToScrapDb "
                   + "FROM sr_inventory "
                   + "WHERE req_id NOT IN (SELECT req_id FROM sr_retrieve) "
                   + "AND ( "
                    + "(MONTH(mth_to_scrap) = MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW())) "
                    + "OR "
                    + "(mth_to_scrap < NOW()) "
                    + "OR "
                    + "(MONTH(mth_to_scrap) = (MONTH(NOW())+1) AND YEAR(mth_to_scrap) = YEAR(NOW())) "
                    + "OR "
                    + "(MONTH(mth_to_scrap) = IF(MONTH(NOW())+1>12,1,MONTH(NOW())) AND YEAR(mth_to_scrap) = YEAR(NOW())+1) "
                   + ") "
                   + "ORDER BY YEAR(mth_to_scrap) ASC, MONTH(mth_to_scrap) " ;
        List<SRInventory> mthToScrapList = new ArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRInventory sampleInventory = new SRInventory();
                sampleInventory.setMthToScrapView(rs.getString("mthToScrapView"));
                sampleInventory.setMthToScrapDb(rs.getString("mthToScrapDb"));
                mthToScrapList.add(sampleInventory);
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
        return mthToScrapList;
    }
}