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
    private final Connection conn;
    private final DataSource dataSource;

    public StatusDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertStatus(Status status) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_status (request_id, description, created_by, created_date) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, status.getRequestId());
            ps.setString(2, status.getDescription());
            ps.setString(3, status.getCreatedBy());
            ps.setString(4, status.getCreatedDate());
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

    public QueryResult updateStatus(Status status) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_status SET request_id = ?, description = ?, created_by = ?, created_date = ? WHERE id = ? "
            );
            ps.setString(1, status.getRequestId());
            ps.setString(2, status.getDescription());
            ps.setString(3, status.getCreatedBy());
            ps.setString(4, status.getCreatedDate());
            ps.setString(5, status.getId());
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

    public QueryResult deleteStatus(String statusId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_status WHERE id = ? "
            );
            ps.setString(1, statusId);
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

    public Status getStatus(String statusId) {
        String sql = "SELECT * FROM sr_status WHERE id = ? ";
        Status status = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, statusId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = new Status();
                status.setId(rs.getString("id"));
                status.setRequestId(rs.getString("request_id"));
                status.setDescription(rs.getString("description"));
                status.setCreatedBy(rs.getString("created_by"));
                status.setCreatedDate(rs.getString("created_date"));
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
        return status;
    }

    public List<Status> getStatusList() {
        String sql = "SELECT * FROM sr_status ORDER BY id ASC";
        List<Status> statusList = new ArrayList<Status>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Status status;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = new Status();
                status.setId(rs.getString("id"));
                status.setRequestId(rs.getString("request_id"));
                status.setDescription(rs.getString("description"));
                status.setCreatedBy(rs.getString("created_by"));
                status.setCreatedDate(rs.getString("created_date"));
                statusList.add(status);
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
        return statusList;
    }

}