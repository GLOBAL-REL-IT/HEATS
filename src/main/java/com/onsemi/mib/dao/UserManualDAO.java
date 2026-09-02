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
    private final DataSource dataSource;

    public UserManualDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }
    
    private static final String SQL_INSERT_USER_MANUAL = "INSERT INTO sr_user_manual (path, filename, flag) VALUES (?,?,?) ";
    private static final String SQL_UPDATE_USER_MANUAL = "UPDATE sr_user_manual SET path = ?, filename = ?, flag = ? WHERE id = ? ";
    private static final String SQL_DELETE_USER_MANUAL = "DELETE FROM sr_user_manual WHERE id = ? ";
    private static final String SQL_GET_USER_MANUAL = "SELECT * FROM sr_user_manual WHERE flag = ? ";
    private static final String SQL_GET_USER_MANUAL_LIST = "SELECT * FROM sr_user_manual ORDER BY id ASC ";

    public QueryResult insertUserManual(UserManual userManual) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_USER_MANUAL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, userManual.getPath());
            ps.setString(2, userManual.getFilename());
            ps.setString(3, userManual.getFlag());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error inserting user manual", e);
        }
        return queryResult;
    }

    public QueryResult updateUserManual(UserManual userManual) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_USER_MANUAL)) {
            ps.setString(1, userManual.getPath());
            ps.setString(2, userManual.getFilename());
            ps.setString(3, userManual.getFlag());
            ps.setString(4, userManual.getId());
            queryResult.setResult(ps.executeUpdate());

        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error updating user manual", e);
        }
        return queryResult;
    }

    public QueryResult deleteUserManual(String userManualId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_USER_MANUAL)) {
            ps.setString(1, userManualId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error deleting user manual", e);
        }
        return queryResult;
    }

    public UserManual getUserManual() {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_USER_MANUAL)) {
            ps.setString(1, "0");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserManual userManual = new UserManual();
                    userManual.setId(rs.getString("id"));
                    userManual.setPath(rs.getString("path"));
                    userManual.setFilename(rs.getString("filename"));
                    userManual.setFlag(rs.getString("flag"));
                    return userManual;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving user manual", e);
        }
        return null;
    }

    public List<UserManual> getUserManualList() {
        List<UserManual> userManualList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_USER_MANUAL_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UserManual userManual = new UserManual();
                userManual.setId(rs.getString("id"));
                userManual.setPath(rs.getString("path"));
                userManual.setFilename(rs.getString("filename"));
                userManual.setFlag(rs.getString("flag"));
                userManualList.add(userManual);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving user manual list", e);
        }
        return userManualList;
    }

}