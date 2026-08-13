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
import com.onsemi.mib.model.ItemMaverick;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemMaverickDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemMaverickDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemMaverickDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemMaverick(ItemMaverick itemmaverick) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_maverick (mib_item_id, module, submodule, disposition_1, disposition_remarks_1, disposition_1_by, disposition_1_date, disposition_2, disposition_2_remarks, disposition_2_by, disposition_2_date, status, flag, created_by, created_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemmaverick.getMibItemId());
            ps.setString(2, itemmaverick.getModule());
            ps.setString(3, itemmaverick.getSubmodule());
            ps.setString(4, itemmaverick.getDisposition1());
            ps.setString(5, itemmaverick.getDispositionRemarks1());
            ps.setString(6, itemmaverick.getDisposition1By());
            ps.setString(7, itemmaverick.getDisposition1Date());
            ps.setString(8, itemmaverick.getDisposition2());
            ps.setString(9, itemmaverick.getDisposition2Remarks());
            ps.setString(10, itemmaverick.getDisposition2By());
            ps.setString(11, itemmaverick.getDisposition2Date());
            ps.setString(12, itemmaverick.getStatus());
            ps.setString(13, itemmaverick.getFlag());
            ps.setString(14, itemmaverick.getCreatedBy());
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

    public QueryResult updateItemMaverick(ItemMaverick itemmaverick) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_maverick SET mib_item_id = ?, module = ?, submodule = ?, disposition_1 = ?, disposition_remarks_1 = ?, disposition_1_by = ?, disposition_1_date = ?, disposition_2 = ?, disposition_2_remarks = ?, disposition_2_by = ?, disposition_2_date = ?, status = ?, flag = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, itemmaverick.getMibItemId());
            ps.setString(2, itemmaverick.getModule());
            ps.setString(3, itemmaverick.getSubmodule());
            ps.setString(4, itemmaverick.getDisposition1());
            ps.setString(5, itemmaverick.getDispositionRemarks1());
            ps.setString(6, itemmaverick.getDisposition1By());
            ps.setString(7, itemmaverick.getDisposition1Date());
            ps.setString(8, itemmaverick.getDisposition2());
            ps.setString(9, itemmaverick.getDisposition2Remarks());
            ps.setString(10, itemmaverick.getDisposition2By());
            ps.setString(11, itemmaverick.getDisposition2Date());
            ps.setString(12, itemmaverick.getStatus());
            ps.setString(13, itemmaverick.getFlag());
            ps.setString(14, itemmaverick.getCreatedBy());
            ps.setString(15, itemmaverick.getCreatedDate());
            ps.setString(16, itemmaverick.getId());
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

    public QueryResult deleteItemMaverick(String itemmaverickId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_maverick WHERE id = '" + itemmaverickId + "'"
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

    public ItemMaverick getItemMaverick(String itemmaverickId) {
        String sql = "SELECT * FROM item_maverick WHERE id = '" + itemmaverickId + "'";
        ItemMaverick itemmaverick = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemmaverick = new ItemMaverick();
                itemmaverick.setId(rs.getString("id"));
                itemmaverick.setItemId(rs.getString("mib_item_id"));
                itemmaverick.setModule(rs.getString("module"));
                itemmaverick.setSubmodule(rs.getString("submodule"));
                itemmaverick.setDisposition1(rs.getString("disposition_1"));
                itemmaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                itemmaverick.setDisposition1By(rs.getString("disposition_1_by"));
                itemmaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                itemmaverick.setDisposition2(rs.getString("disposition_2"));
                itemmaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                itemmaverick.setDisposition2By(rs.getString("disposition_2_by"));
                itemmaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                itemmaverick.setStatus(rs.getString("status"));
                itemmaverick.setFlag(rs.getString("flag"));
                itemmaverick.setCreatedBy(rs.getString("created_by"));
                itemmaverick.setCreatedDate(rs.getString("created_date"));
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
        return itemmaverick;
    }

    public List<ItemMaverick> getItemMaverickList() {
        String sql = "SELECT * FROM item_maverick ORDER BY id ASC";
        List<ItemMaverick> itemmaverickList = new ArrayList<ItemMaverick>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemMaverick itemmaverick;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemmaverick = new ItemMaverick();
                itemmaverick.setId(rs.getString("id"));
                itemmaverick.setItemId(rs.getString("mib_item_id"));
                itemmaverick.setModule(rs.getString("module"));
                itemmaverick.setSubmodule(rs.getString("submodule"));
                itemmaverick.setDisposition1(rs.getString("disposition_1"));
                itemmaverick.setDispositionRemarks1(rs.getString("disposition_remarks_1"));
                itemmaverick.setDisposition1By(rs.getString("disposition_1_by"));
                itemmaverick.setDisposition1Date(rs.getString("disposition_1_date"));
                itemmaverick.setDisposition2(rs.getString("disposition_2"));
                itemmaverick.setDisposition2Remarks(rs.getString("disposition_2_remarks"));
                itemmaverick.setDisposition2By(rs.getString("disposition_2_by"));
                itemmaverick.setDisposition2Date(rs.getString("disposition_2_date"));
                itemmaverick.setStatus(rs.getString("status"));
                itemmaverick.setFlag(rs.getString("flag"));
                itemmaverick.setCreatedBy(rs.getString("created_by"));
                itemmaverick.setCreatedDate(rs.getString("created_date"));
                itemmaverickList.add(itemmaverick);
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
        return itemmaverickList;
    }

    public List<ItemMaverick> getItemMaverickListFlagZero() {
        String sql = "SELECT mav.*,DATE_FORMAT(mav.created_date,'%d %M %Y %h:%i %p') AS createdDate, it.item_id, it.item_type "
                + "FROM item_maverick mav, item it "
                + "WHERE mav.mib_item_id = it.id AND mav.flag = '0' ORDER BY id ASC";
        List<ItemMaverick> itemmaverickList = new ArrayList<ItemMaverick>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemMaverick itemmaverick;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemmaverick = new ItemMaverick();
                itemmaverick.setId(rs.getString("mav.id"));
                itemmaverick.setMibItemId(rs.getString("mav.mib_item_id"));
                itemmaverick.setModule(rs.getString("mav.module"));
                itemmaverick.setSubmodule(rs.getString("mav.submodule"));
                itemmaverick.setDisposition1(rs.getString("mav.disposition_1"));
                itemmaverick.setDispositionRemarks1(rs.getString("mav.disposition_remarks_1"));
                itemmaverick.setDisposition1By(rs.getString("mav.disposition_1_by"));
                itemmaverick.setDisposition1Date(rs.getString("mav.disposition_1_date"));
                itemmaverick.setDisposition2(rs.getString("mav.disposition_2"));
                itemmaverick.setDisposition2Remarks(rs.getString("mav.disposition_2_remarks"));
                itemmaverick.setDisposition2By(rs.getString("mav.disposition_2_by"));
                itemmaverick.setDisposition2Date(rs.getString("mav.disposition_2_date"));
                itemmaverick.setStatus(rs.getString("mav.status"));
                itemmaverick.setFlag(rs.getString("mav.flag"));
                itemmaverick.setCreatedBy(rs.getString("mav.created_by"));
                itemmaverick.setCreatedDate(rs.getString("createdDate"));
                itemmaverick.setItemId(rs.getString("it.item_id"));
                itemmaverick.setItemType(rs.getString("it.item_type"));
                itemmaverickList.add(itemmaverick);
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
        return itemmaverickList;
    }

    public Integer getCountFlagZero() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item_maverick inc WHERE inc.flag = '0'"
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

    public Integer getCountVm(String month, String year) {
        Integer count = null;
        try ( PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) AS COUNT "
                + "FROM item_maverick inc "
                + "WHERE inc.submodule = 'Visual Inspection' AND MONTH(inc.created_date) = ? AND YEAR(inc.created_date) = ?"
        )) {
            ps.setString(1, month);
            ps.setString(2, year);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count = rs.getInt("count");
                }
            }
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

    public Integer getCountFt(String month, String year) {
        Integer count = null;
        try ( PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) AS COUNT "
                + "FROM item_maverick inc "
                + "WHERE inc.submodule != 'Visual Inspection' AND MONTH(inc.created_date) = ? AND YEAR(inc.created_date) = ?"
        )) {
            ps.setString(1, month);
            ps.setString(2, year);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count = rs.getInt("count");
                }
            }
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
