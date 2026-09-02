package com.onsemi.mib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.UserGroup;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserGroupDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupDAO.class);
    private final DataSource dataSource;

    public UserGroupDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_INSERT_GROUP = "INSERT INTO user_group (code, name, created_by, created_time) VALUES (?,?,?,NOW()) ";
    private static final String SQL_UPDATE_GROUP = "UPDATE user_group SET code = ?, name = ?, modified_by = ?, modified_time = NOW() WHERE id = ? ";
    private static final String SQL_DELETE_GROUP = "DELETE FROM user_group WHERE id = ? ";
    private static final String SQL_GET_GROUP = "SELECT * FROM user_group WHERE id = ? ";
    private static final String SQL_GET_GROUP_LIST = "SELECT id, code, name, IF(id = ?, 'selected=\"selected\"', '') AS selected FROM user_group ORDER BY nam";

    public QueryResult insertGroup(UserGroup userGroup) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_GROUP, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, userGroup.getCode());
            ps.setString(2, userGroup.getName());
            ps.setString(3, userGroup.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error inserting group", e);
        }
        return queryResult;
    }

    public QueryResult updateGroup(UserGroup userGroup) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_GROUP)) {
            ps.setString(1, userGroup.getCode());
            ps.setString(2, userGroup.getName());
            ps.setString(3, userGroup.getModifiedBy());
            ps.setString(4, userGroup.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error updating group", e);
        }
        return queryResult;
    }

    public QueryResult deleteGroup(String groupId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_GROUP)) {
            ps.setString(1, groupId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error deleting group", e);
        }
        return queryResult;
    }

    public UserGroup getGroup(String groupId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_GROUP)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserGroup(
                            rs.getString("id"),
                            rs.getString("code"),
                            rs.getString("name"),
                            rs.getString("created_by"),
                            rs.getString("created_time"),
                            rs.getString("modified_by"),
                            rs.getString("modified_time")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving group", e);
        }
        return null;
    }

    public List<UserGroup> getGroupList(String groupId) {
        List<UserGroup> userGroupList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_GROUP_LIST)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserGroup userGroup = new UserGroup(
                            rs.getString("id"),
                            rs.getString("code"),
                            rs.getString("name"),
                            rs.getString("selected")
                    );
                    userGroupList.add(userGroup);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving group list", e);
        }
        return userGroupList;
    }

}