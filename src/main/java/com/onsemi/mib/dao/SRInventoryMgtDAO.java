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
import com.onsemi.mib.model.SRInventoryMgt;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SRInventoryMgtDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SRInventoryMgtDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public SRInventoryMgtDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertInventoryDetails(SRInventoryMgt srInventoryMgt) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_inventory_mgt (rack_month, rack_id, shelf_id, outer_id, status, flag, modified_date, date_created) VALUES (?,?,?,?,?,?,NOW(),NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, srInventoryMgt.getRackMonth());
            ps.setString(2, srInventoryMgt.getRackId());
            ps.setString(3, srInventoryMgt.getShelfId());
            ps.setString(4, srInventoryMgt.getOuterId());
            ps.setString(5, srInventoryMgt.getStatus());
            ps.setString(6, srInventoryMgt.getFlag());
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

    public QueryResult updateInventoryDetails(SRInventoryMgt srInventoryMgt) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_inventory_mgt SET outer_id = ?, req_id = ?, status = ?, flag = ?, modified_date = NOW() WHERE shelf_id = ? "
            );
            ps.setString(1, srInventoryMgt.getOuterId());
            ps.setString(2, srInventoryMgt.getReqId());
            ps.setString(3, srInventoryMgt.getStatus());
            ps.setString(4, srInventoryMgt.getFlag());
            ps.setString(5, srInventoryMgt.getShelfId());
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

    //BEFORE
//    public List<SRInventoryMgt> getInventoryDetailsList(String query) {
//        String sql = "SELECT *, IFNULL(GROUP_CONCAT(I.rmslot_event SEPARATOR ', '),'N/A') AS rmslot_event_concat, IF(COUNT(I.rmslot_event)=0,'N/A',COUNT(I.rmslot_event)) AS count_lot, "
//                   + "DATEDIFF(V.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(V.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
//                   + "DATE_FORMAT(M.modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(M.date_created,'%d/%m/%y %h:%i %p') AS created_date_view, "
//                   + "DATE_FORMAT(V.inventory_date,'%d/%m/%y %h:%i %p') AS inventory_date_view "
//                   + "FROM sr_inventory_mgt M "
//                   + "LEFT JOIN sr_inventory V "
//                   + "ON M.shelf_id = V.inventory_shelf AND V.flag = 0  AND V.`status` = 'Available in Inventory' "
//                   + "LEFT JOIN sr_req_inner I "
//                   + "ON V.req_id = I.req_id "
//                   + query
//                   + "GROUP BY M.shelf_id "
//                   + "ORDER BY M.rack_month ASC, M.shelf_id ASC ";
//
//        List<SRInventoryMgt> srInventoryMgtList = new ArrayList<SRInventoryMgt>();
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            SRInventoryMgt srInventoryMgt;
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                srInventoryMgt = new SRInventoryMgt();
//                srInventoryMgt.setId(rs.getString("M.id"));
//                srInventoryMgt.setRackMonth(rs.getString("rack_month"));
//                srInventoryMgt.setRackId(rs.getString("rack_id"));
//                srInventoryMgt.setShelfId(rs.getString("shelf_id"));
//                srInventoryMgt.setOuterId(rs.getString("V.box_id"));
//                srInventoryMgt.setStatus(rs.getString("M.status"));
//                srInventoryMgt.setFlag(rs.getString("M.flag"));
//                srInventoryMgt.setDateCreated(rs.getString("created_date_view"));
//                srInventoryMgt.setModifiedDate(rs.getString("modified_date_view"));
//                srInventoryMgt.setInvId(rs.getString("V.id"));
//                srInventoryMgt.setBoxId(rs.getString("V.box_id"));
//                srInventoryMgt.setEvent(rs.getString("V.event"));
//                srInventoryMgt.setMthToScrap(rs.getString("mth_to_scrap_view"));
//                srInventoryMgt.setPkgFamily(rs.getString("V.pkg_family"));
//                srInventoryMgt.setGtsNo(rs.getString("V.gts_no"));
//                srInventoryMgt.setReceivedDate(rs.getString("V.received_date"));
//                srInventoryMgt.setCustomNo(rs.getString("V.custom_no"));
//                srInventoryMgt.setCustomDate(rs.getString("V.custom_date"));
//                srInventoryMgt.setInvDate(rs.getString("inventory_date_view"));
//                srInventoryMgt.setInvBy(rs.getString("V.inventory_by"));
//                srInventoryMgt.setAging(rs.getString("aging"));
//                srInventoryMgt.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
//                srInventoryMgt.setCountLot(rs.getString("count_lot"));
//                srInventoryMgtList.add(srInventoryMgt);
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
//        return srInventoryMgtList;
//    }
    //AFTER
    public List<SRInventoryMgt> getInventoryDetailsList(String query) {
        //AFTER
        String sql = "SELECT *, IFNULL(GROUP_CONCAT(I.rmslot_event SEPARATOR ', '),'N/A') AS rmslot_event_concat, IF(COUNT(I.rmslot_event)=0,'N/A',COUNT(I.rmslot_event)) AS count_lot, "
                    + "DATE_FORMAT(M.modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(M.date_created,'%d/%m/%y %h:%i %p') AS created_date_view "
                    + "FROM sr_inventory_mgt M "
                    + "LEFT JOIN sr_req_inner I "
                    + "ON M.req_id = I.req_id "
                    + query
                    + "GROUP BY M.shelf_id "
                    + "ORDER BY M.rack_month ASC, M.shelf_id ASC ";
        List<SRInventoryMgt> srInventoryMgtList = new ArrayList<SRInventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRInventoryMgt srInventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                srInventoryMgt = new SRInventoryMgt();
                srInventoryMgt.setId(rs.getString("M.id"));
                srInventoryMgt.setRackMonth(rs.getString("rack_month"));
                srInventoryMgt.setRackId(rs.getString("rack_id"));
                srInventoryMgt.setShelfId(rs.getString("shelf_id"));
                srInventoryMgt.setOuterId(rs.getString("I.outer_id"));
                srInventoryMgt.setStatus(rs.getString("M.status"));
                srInventoryMgt.setFlag(rs.getString("M.flag"));
                srInventoryMgt.setDateCreated(rs.getString("created_date_view"));
                srInventoryMgt.setModifiedDate(rs.getString("modified_date_view"));
                srInventoryMgt.setBoxId(rs.getString("I.outer_id"));
                srInventoryMgt.setEvent(rs.getString("I.event"));
//                srInventoryMgt.setMthToScrap(rs.getString("mth_to_scrap_view"));
//                srInventoryMgt.setPkgFamily(rs.getString("V.pkg_family"));
//                srInventoryMgt.setGtsNo(rs.getString("V.gts_no"));
//                srInventoryMgt.setReceivedDate(rs.getString("V.received_date"));
//                srInventoryMgt.setCustomNo(rs.getString("V.custom_no"));
//                srInventoryMgt.setCustomDate(rs.getString("V.custom_date"));
//                srInventoryMgt.setInvDate(rs.getString("inventory_date_view"));
//                srInventoryMgt.setAging(rs.getString("aging"));
                srInventoryMgt.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                srInventoryMgt.setCountLot(rs.getString("count_lot"));
                srInventoryMgtList.add(srInventoryMgt);
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
        return srInventoryMgtList;
    }

    public List<SRInventoryMgt> getInventoryDataPerMonthList() {
        String sql = "SELECT SUBSTRING(rack_month,1,2) AS mth_int, SUBSTRING(rack_month,4) AS rack_month_view, COUNT(req_id) AS shelf_used, COUNT(shelf_id)-COUNT(req_id) AS shelf_free, COUNT(shelf_id) AS total_shelf "
                    + "FROM sr_inventory_mgt "
                    + "GROUP BY rack_month "
                    + "ORDER BY mth_int ";
        List<SRInventoryMgt> srInventoryMgtList = new ArrayList<SRInventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRInventoryMgt srInventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                srInventoryMgt = new SRInventoryMgt();
                srInventoryMgt.setIntMth(rs.getString("mth_int"));
                srInventoryMgt.setRackMonth(rs.getString("rack_month_view"));
                srInventoryMgt.setShelfUsed(rs.getString("shelf_used"));
                srInventoryMgt.setShelfFree(rs.getString("shelf_free"));
                srInventoryMgt.setTotalShelf(rs.getString("total_shelf"));
                srInventoryMgtList.add(srInventoryMgt);
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
        return srInventoryMgtList;
    }

    public List<SRInventoryMgt> getInventoryRackList() {
        String sql = "SELECT DISTINCT rack_id FROM sr_inventory_mgt ORDER BY rack_month ASC, rack_id ASC";
        List<SRInventoryMgt> whInventoryMgtList = new ArrayList<SRInventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRInventoryMgt whInventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventoryMgt = new SRInventoryMgt();
                whInventoryMgt.setRackId(rs.getString("rack_id"));
                whInventoryMgtList.add(whInventoryMgt);
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
        return whInventoryMgtList;
    }

    public List<SRInventoryMgt> getInventoryMonthList() {
        String sql = "SELECT DISTINCT SUBSTRING(rack_month,4) AS rack_mth FROM sr_inventory_mgt ORDER BY rack_month ASC";
        List<SRInventoryMgt> whInventoryMgtList = new ArrayList<SRInventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRInventoryMgt whInventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventoryMgt = new SRInventoryMgt();
                whInventoryMgt.setRackMonth(rs.getString("rack_mth"));
                whInventoryMgtList.add(whInventoryMgt);
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
        return whInventoryMgtList;
    }

    public Integer getCountShelf(String shelfId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_inventory_mgt WHERE shelf_id = ? "
            );
            ps.setString(1, shelfId);
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

    public Integer getCountShelfUsedPerMth(String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_inventory_mgt WHERE shelf_id LIKE ? AND flag = '1' "
            );
            ps.setString(1, "%"+month+"%");
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

    public Integer getCountJanFree() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_inventory_mgt WHERE shelf_id LIKE '%JAN%' AND flag = '0' "
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

    public List<SRInventoryMgt> getInventoryList() {
        String sql = "SELECT *, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(date_created,'%d/%m/%y %h:%i %p') AS created_date_view "
                    + "FROM sr_inventory_mgt "
                    + "ORDER BY rack_month ASC, shelf_id ASC ";
        List<SRInventoryMgt> srInventoryMgtList = new ArrayList<SRInventoryMgt>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRInventoryMgt srInventoryMgt;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                srInventoryMgt = new SRInventoryMgt();
                srInventoryMgt.setId(rs.getString("id"));
                srInventoryMgt.setRackMonth(rs.getString("rack_month"));
                srInventoryMgt.setRackId(rs.getString("rack_id"));
                srInventoryMgt.setShelfId(rs.getString("shelf_id"));
                srInventoryMgt.setOuterId(rs.getString("outer_id"));
                srInventoryMgt.setReqId(rs.getString("req_id"));
                srInventoryMgt.setStatus(rs.getString("status"));
                srInventoryMgt.setFlag(rs.getString("flag"));
                srInventoryMgt.setDateCreated(rs.getString("created_date_view"));
                srInventoryMgt.setModifiedDate(rs.getString("modified_date_view"));
                srInventoryMgtList.add(srInventoryMgt);
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
        return srInventoryMgtList;
    }

    //new function by Arif
    public Integer getCountReady() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_ftp_data WHERE flag = '0'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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

    public Integer getCountRetrieval() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_retrieve WHERE flag = '0'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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

    public Integer getCountSample() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_inventory_mgt WHERE STATUS = 'Not Available'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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

    public Integer getCountShelf() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_inventory_mgt WHERE STATUS = 'Shelf Available'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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