package com.onsemi.mib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.UserGroupAccess;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserGroupAccessDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupAccessDAO.class);
    private final DataSource dataSource;

    public UserGroupAccessDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_GET_GROUP_ACCESS = "SELECT * FROM menu_access WHERE group_id = ?";
    private static final String SQL_GET_USER_GROUP_ACCESS = "SELECT m.id AS menu_id, m.parent_code, m.code, m.name, uga.id, uga.group_id, IF(m.id = uga.menu_id, 'checked=\"\"', '') AS selected FROM menu_main m LEFT JOIN menu_access uga ON uga.group_id = ? AND m.id = uga.menu_id ORDER BY m.code";
    private static final String SQL_ADD_ACCESS = "INSERT INTO menu_access (group_id, menu_id) SELECT * FROM (SELECT ? AS group_id, ? AS menu_id) tmp WHERE NOT EXISTS (SELECT id FROM menu_access WHERE group_id = ? AND menu_id = ?) LIMIT 1";
    private static final String SQL_REMOVE_ACCESS_BY_GROUP_ID = "DELETE FROM menu_access WHERE group_id = ?";

    public List<UserGroupAccess> getGroupAccess(String groupId) {
        List<UserGroupAccess> userGroupAccessList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_GROUP_ACCESS)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserGroupAccess userGroupAccess = new UserGroupAccess(
                            rs.getString("id"),
                            rs.getString("group_id"),
                            rs.getString("menu_id")
                    );
                    userGroupAccessList.add(userGroupAccess);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving group access", e);
        }
        return userGroupAccessList;
    }

    public List<UserGroupAccess> getUserGroupAccess(String groupId) {
        List<UserGroupAccess> userGroupAccessList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_USER_GROUP_ACCESS)) {
            ps.setString(1, groupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserGroupAccess userGroupAccess = new UserGroupAccess(
                            rs.getString("id"),
                            rs.getString("group_id"),
                            rs.getString("menu_id"),
                            rs.getString("parent_code"),
                            rs.getString("code"),
                            rs.getString("name"),
                            rs.getString("selected")
                    );
                    userGroupAccessList.add(userGroupAccess);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving user group access", e);
        }
        return userGroupAccessList;
    }

    public QueryResult addAccess(String groupId, String menuId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_ADD_ACCESS)) {
            ps.setString(1, groupId);
            ps.setString(2, menuId);
            ps.setString(3, groupId);
            ps.setString(4, menuId);
            ps.executeUpdate();
            queryResult.setResult(1);
        } catch (SQLException e) {
            queryResult.setResult(0);
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error adding access", e);
        }
        return queryResult;
    }

    public QueryResult removeAccess(String groupId, String[] groupAccess) {
        QueryResult queryResult = new QueryResult();
        if (groupId == null || groupId.trim().isEmpty()) {
            queryResult.setResult(0);
            queryResult.setErrorMessage("Invalid group ID");
            return queryResult;
        }
        try (Connection conn = dataSource.getConnection()) {
            String sql;
            if (groupAccess == null || groupAccess.length == 0) {
                sql = "DELETE FROM menu_access WHERE group_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, groupId);
                    ps.executeUpdate();
                }
            } else {
                StringBuilder placeholders = new StringBuilder();
                for (int i = 0; i < groupAccess.length; i++) {
                    if (i > 0) {
                        placeholders.append(",");
                    }
                    placeholders.append("?");
                }
                sql = "DELETE FROM menu_access WHERE group_id = ? AND menu_id NOT IN (" + placeholders + ")";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    int index = 1;
                    ps.setString(index++, groupId);
                    for (String menuId : groupAccess) {
                        ps.setString(index++, menuId);
                    }
                    ps.executeUpdate();
                }
            }
            queryResult.setResult(1);
        } catch (SQLException e) {
            queryResult.setResult(0);
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error removing access for groupId: {}", groupId, e);
        }
        return queryResult;
    }

    public QueryResult removeAccessByGroupId(String groupId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_REMOVE_ACCESS_BY_GROUP_ID)) {
            ps.setString(1, groupId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage("Database operation failed");
            LOGGER.error("Error removing access by group id", e);
        }
        return queryResult;
    }

}
