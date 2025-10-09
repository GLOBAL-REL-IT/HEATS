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
import com.onsemi.mib.model.UserManual;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManualDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManualDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public UserManualDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertUserManual(UserManual userManual) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_user_manual (path, filename, flag) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, userManual.getPath());
            ps.setString(2, userManual.getFilename());
            ps.setString(3, userManual.getFlag());
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

    public QueryResult updateUserManual(UserManual userManual) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_user_manual SET path = ?, filename = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, userManual.getPath());
            ps.setString(2, userManual.getFilename());
            ps.setString(3, userManual.getFlag());
            ps.setString(4, userManual.getId());
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

    public QueryResult deleteUserManual(String userManualId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_user_manual WHERE id = '" + userManualId + "'"
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

    public UserManual getUserManual() {
        String sql = "SELECT * FROM sr_user_manual WHERE flag = '0'";
        UserManual userManual = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userManual = new UserManual();
                userManual.setId(rs.getString("id"));
                userManual.setPath(rs.getString("path"));
                userManual.setFilename(rs.getString("filename"));
                userManual.setFlag(rs.getString("flag"));
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
        return userManual;
    }

    public List<UserManual> getUserManualList() {
        String sql = "SELECT * FROM sr_user_manual ORDER BY id ASC";
        List<UserManual> userManualList = new ArrayList<UserManual>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserManual userManual;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userManual = new UserManual();
                userManual.setId(rs.getString("id"));
                userManual.setPath(rs.getString("path"));
                userManual.setFilename(rs.getString("filename"));
                userManual.setFlag(rs.getString("flag"));
                userManualList.add(userManual);
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
        return userManualList;
    }
}
