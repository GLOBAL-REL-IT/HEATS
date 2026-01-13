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
import com.onsemi.mib.model.ItemRecallCsvFile;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemRecallCsvFileDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemRecallCsvFileDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemRecallCsvFileDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemRecallCsvFile(ItemRecallCsvFile itemrecallCsvFile) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_recall_csv_file (file, active, remarks) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemrecallCsvFile.getFile());
            ps.setString(2, itemrecallCsvFile.getActive());
            ps.setString(3, itemrecallCsvFile.getRemarks());
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

    public QueryResult updateItemRecallCsvFile(ItemRecallCsvFile itemrecallCsvFile) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_recall_csv_file SET file = ?, active = ?, remarks = ? WHERE id = ?"
            );
            ps.setString(1, itemrecallCsvFile.getFile());
            ps.setString(2, itemrecallCsvFile.getActive());
            ps.setString(3, itemrecallCsvFile.getRemarks());
            ps.setString(4, itemrecallCsvFile.getId());
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

    public QueryResult deleteItemRecallCsvFile(String itemrecallCsvFileId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_recall_csv_file WHERE id = '" + itemrecallCsvFileId + "'"
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

    public ItemRecallCsvFile getItemRecallCsvFile(String itemrecallCsvFileId) {
        String sql = "SELECT * FROM item_recall_csv_file WHERE id = '" + itemrecallCsvFileId + "'";
        ItemRecallCsvFile itemrecallCsvFile = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemrecallCsvFile = new ItemRecallCsvFile();
                itemrecallCsvFile.setId(rs.getString("id"));
                itemrecallCsvFile.setFile(rs.getString("file"));
                itemrecallCsvFile.setActive(rs.getString("active"));
                itemrecallCsvFile.setRemarks(rs.getString("remarks"));
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
        return itemrecallCsvFile;
    }

    public ItemRecallCsvFile getItemRecallCsvFileForActiveLocation() {
        String sql = "SELECT * FROM item_recall_csv_file fi WHERE fi.active = 'Active'";
        ItemRecallCsvFile itemrecallCsvFile = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemrecallCsvFile = new ItemRecallCsvFile();
                itemrecallCsvFile.setId(rs.getString("id"));
                itemrecallCsvFile.setFile(rs.getString("file"));
                itemrecallCsvFile.setActive(rs.getString("active"));
                itemrecallCsvFile.setRemarks(rs.getString("remarks"));
                itemrecallCsvFile.setEmailCsv(rs.getString("email_csv"));
                itemrecallCsvFile.setEmailNotification(rs.getString("email_notification"));
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
        return itemrecallCsvFile;
    }

    public List<ItemRecallCsvFile> getItemRecallCsvFileList() {
        String sql = "SELECT * FROM item_recall_csv_file ORDER BY id ASC";
        List<ItemRecallCsvFile> itemrecallCsvFileList = new ArrayList<ItemRecallCsvFile>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemRecallCsvFile itemrecallCsvFile;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemrecallCsvFile = new ItemRecallCsvFile();
                itemrecallCsvFile.setId(rs.getString("id"));
                itemrecallCsvFile.setFile(rs.getString("file"));
                itemrecallCsvFile.setActive(rs.getString("active"));
                itemrecallCsvFile.setRemarks(rs.getString("remarks"));
                itemrecallCsvFileList.add(itemrecallCsvFile);
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
        return itemrecallCsvFileList;
    }
}
