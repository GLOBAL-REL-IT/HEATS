/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import com.onsemi.mib.model.ItemScrap;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zbqb9x
 */
public class ItemScrapDAO {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemScrapDAO.class);
    private final DataSource dataSource;

    public ItemScrapDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }
    
    private static final String SQL_INSERT_SCRAP = "INSERT INTO item_scrap (`mib_item_id`, `group_id`, `module`, `sub_module`, `status`, `remark`, `created_by`, `created_date`, `flag`) VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
    private static final String SQL1 = "";
    
    public QueryResult insertScrap(ItemScrap scrap) {
        LOGGER.info("MASUK FUNCTION UNTUK INSERT ITEM SCRAP DATA >>>>> "+SQL_INSERT_SCRAP);
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_SCRAP, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, scrap.getMibItemId());
            ps.setString(2, scrap.getGroupId());
            ps.setString(3, scrap.getModule());
            ps.setString(4, scrap.getSubModule());
            ps.setString(5, scrap.getStatus());
            ps.setString(6, scrap.getRemark());
            ps.setString(7, scrap.getCreatedBy());
            ps.setString(8, scrap.getFlag());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting item scrap", e);
        }
        return queryResult;
    }
    
    public QueryResult updateScrap(ItemScrap scrap) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL1)) {
            ps.setString(1, scrap.getMibItemId());
            ps.setString(2, scrap.getGroupId());
            ps.setString(3, scrap.getModule());
            ps.setString(4, scrap.getSubModule());
            ps.setString(5, scrap.getStatus());
            ps.setString(6, scrap.getRemark());
            ps.setString(7, scrap.getScrapBy());
            ps.setString(8, scrap.getFlag());
            ps.setString(9, scrap.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating item scrap id: {}", scrap.getId(), e);
        }
        return queryResult;
    }
    
    public QueryResult deleteScrap(String scrapId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL1)) {
            ps.setString(1, scrapId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting item scrap id: {}", scrapId, e);
        }
        return queryResult;
    }
    
    public QueryResult updateReadyScrap(String scrapId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL1)) {
            ps.setString(1, scrapId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setResult(0);
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating ready item scrap id: {}", scrapId, e);
        }
        return queryResult;
    }
    
    public ItemScrap getScrapById(String scrapId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL1)) {
            ps.setString(1, scrapId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ItemScrap scrap = new ItemScrap();
                    scrap.setId(rs.getString("id"));
                    scrap.setMibItemId(rs.getString("mib_item_id"));
                    scrap.setGroupId(rs.getString("group_id"));
                    scrap.setModule(rs.getString("module"));
                    scrap.setSubModule(rs.getString("sub_module"));
                    scrap.setStatus(rs.getString("status"));
                    scrap.setRemark(rs.getString("remark"));
                    scrap.setCreatedBy(rs.getString("created_by"));
                    scrap.setCreatedDate(rs.getString("created_date"));
                    scrap.setScrapBy(rs.getString("scrap_by"));
                    scrap.setScrapDate(rs.getString("scrap_date"));
                    scrap.setFlag(rs.getString("flag"));
                    return scrap;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting item scrap by id {}", scrapId, e);
        }
        return null;
    }
    
    private static final String SQL_GET_ITEM_SCRAP_LIST = "SELECT * FROM item_scrap WHERE flag = '0' ORDER BY ID DESC";
    private static final String SQL_GET_ITEM_SCRAP_LIST_DETAIL = "SELECT s.id, CASE WHEN s.mib_item_id <> 0 THEN i.item_id ELSE h.item_id END AS item_name, CASE WHEN s.mib_item_id <> 0 THEN i.item_type ELSE h.item_type END AS item_type, s.mib_item_id, s.group_id, s.module, s.sub_module, s.status, s.remark, s.created_by FROM item_scrap s LEFT JOIN item i ON s.mib_item_id = i.id LEFT JOIN rms_booking_hardware h ON SUBSTRING_INDEX(s.group_id, '/', 1) = h.booking_pkid";
    
    public List<ItemScrap> getScrapList() {
        List<ItemScrap> scrapList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_SCRAP_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ItemScrap scrap = new ItemScrap();
                scrap.setId(rs.getString("id"));
                scrap.setMibItemId(rs.getString("mib_item_id"));
                scrap.setGroupId(rs.getString("group_id"));
                scrap.setModule(rs.getString("module"));
                scrap.setSubModule(rs.getString("sub_module"));
                scrap.setStatus(rs.getString("status"));
                scrap.setRemark(rs.getString("remark"));
                scrap.setScrapBy(rs.getString("scrap_by"));
                scrap.setScrapDate(rs.getString("scrap_date"));
                scrap.setCreatedBy(rs.getString("created_by"));
                scrap.setCreatedDate(rs.getString("created_date"));
                scrap.setFlag(rs.getString("flag"));
                scrapList.add(scrap);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting scrap list", e);
        }
        return scrapList;
    }
    
    public List<ItemScrap> getScrapListDetail() {
        List<ItemScrap> scrapList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_SCRAP_LIST_DETAIL); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ItemScrap scrap = new ItemScrap();
                scrap.setId(rs.getString("id"));
                scrap.setItemName(rs.getString("item_name"));
                scrap.setItemType(rs.getString("item_type"));
                scrap.setMibItemId(rs.getString("mib_item_id"));
                scrap.setGroupId(rs.getString("group_id"));
                scrap.setModule(rs.getString("module"));
                scrap.setSubModule(rs.getString("sub_module"));
                scrap.setStatus(rs.getString("status"));
                scrap.setRemark(rs.getString("remark"));
                scrap.setCreatedBy(rs.getString("created_by"));
                scrapList.add(scrap);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting scrap list", e);
        }
        return scrapList;
    }
    
}