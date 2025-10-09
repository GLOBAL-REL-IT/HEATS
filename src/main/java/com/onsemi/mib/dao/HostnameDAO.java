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
import com.onsemi.mib.model.Hostname;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostnameDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(HostnameDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public HostnameDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertHostname(Hostname hostname) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_hostname (hostname, flag) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, hostname.getHostname());
            ps.setString(2, hostname.getFlag());
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

    public QueryResult updateHostname(Hostname hostname) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_hostname SET hostname = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, hostname.getHostname());
            ps.setString(2, hostname.getFlag());
            ps.setString(3, hostname.getId());
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

    public QueryResult deleteHostname(String hostnameId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_hostname WHERE id = '" + hostnameId + "'"
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

    public Hostname getHostname(String hostnameId) {
        String sql = "SELECT * FROM sr_hostname WHERE id = '" + hostnameId + "'";
        Hostname hostname = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hostname = new Hostname();
                hostname.setId(rs.getString("id"));
                hostname.setHostname(rs.getString("hostname"));
                hostname.setFlag(rs.getString("flag"));
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
        return hostname;
    }

    public Hostname getHostnameFlagZero() {
        String sql = "SELECT * FROM sr_hostname WHERE flag = '0'";
        Hostname hostname = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hostname = new Hostname();
                hostname.setId(rs.getString("id"));
                hostname.setHostname(rs.getString("hostname"));
                hostname.setFlag(rs.getString("flag"));
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
        return hostname;
    }

    public List<Hostname> getHostnameList() {
        String sql = "SELECT * FROM sr_hostname ORDER BY id ASC";
        List<Hostname> hostnameList = new ArrayList<Hostname>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Hostname hostname;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hostname = new Hostname();
                hostname.setId(rs.getString("id"));
                hostname.setHostname(rs.getString("hostname"));
                hostname.setFlag(rs.getString("flag"));
                hostnameList.add(hostname);
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
        return hostnameList;
    }
}
