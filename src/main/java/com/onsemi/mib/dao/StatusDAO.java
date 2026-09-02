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
import com.onsemi.mib.model.Status;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public StatusDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }
    
    private static final String SQL_INSERT_STATUS = "INSERT INTO sr_status (request_id, description, created_by, created_date) VALUES (?,?,?,?)"; 
    private static final String SQL_UPDATE_STATUS = "UPDATE sr_status SET request_id = ?, description = ?, created_by = ?, created_date = ? WHERE id = ?"; 
    private static final String SQL_DELETE_STATUS = "DELETE FROM sr_status WHERE id = ?"; 
    private static final String SQL_GET_STATUS = "SELECT * FROM sr_status WHERE id = ?"; 
    private static final String SQL_GET_STATUS_LIST = "SELECT * FROM sr_status ORDER BY id ASC"; 

    public QueryResult insertStatus(Status status) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_STATUS, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, status.getRequestId());
            ps.setString(2, status.getDescription());
            ps.setString(3, status.getCreatedBy());
            ps.setString(4, status.getCreatedDate());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting status", e);
        }
        return queryResult;
    }

    public QueryResult updateStatus(Status status) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_STATUS)) {
            ps.setString(1, status.getRequestId());
            ps.setString(2, status.getDescription());
            ps.setString(3, status.getCreatedBy());
            ps.setString(4, status.getCreatedDate());
            ps.setString(5, status.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating status. Id: {}", status.getId(), e);
        }
        return queryResult;
    }

    public QueryResult deleteStatus(String statusId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_STATUS)) {
            ps.setString(1, statusId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting status. Id: {}", statusId, e);
        }
        return queryResult;
    }

    public Status getStatus(String statusId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_STATUS)) {
            ps.setString(1, statusId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Status status = new Status();
                    status.setId(rs.getString("id"));
                    status.setRequestId(rs.getString("request_id"));
                    status.setDescription(rs.getString("description"));
                    status.setCreatedBy(rs.getString("created_by"));
                    status.setCreatedDate(rs.getString("created_date"));
                    return status;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting status. Id: {}", statusId, e);
        }
        return null;
    }

    public List<Status> getStatusList() {
        List<Status> statusList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_STATUS_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Status status = new Status();
                status.setId(rs.getString("id"));
                status.setRequestId(rs.getString("request_id"));
                status.setDescription(rs.getString("description"));
                status.setCreatedBy(rs.getString("created_by"));
                status.setCreatedDate(rs.getString("created_date"));
                statusList.add(status);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting status list", e);
        }
        return statusList;
    }

}