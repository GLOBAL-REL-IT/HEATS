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
import com.onsemi.mib.model.BulkRetrieve;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BulkRetrieveDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulkRetrieveDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public BulkRetrieveDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertBulkRetrieve(BulkRetrieve bulkRetrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_bulk_retrieve (requestor, date, flag) VALUES (?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, bulkRetrieve.getRequestor());
            ps.setString(2, bulkRetrieve.getFlag());
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

    public QueryResult updateBulkRetrieve(BulkRetrieve bulkRetrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_bulk_retrieve SET requestor = ?, date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, bulkRetrieve.getRequestor());
            ps.setString(2, bulkRetrieve.getDate());
            ps.setString(3, bulkRetrieve.getFlag());
            ps.setString(4, bulkRetrieve.getId());
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

    public QueryResult updateBulkRetrieveForFlag(BulkRetrieve bulkRetrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_bulk_retrieve SET flag = ? WHERE id = ?"
            );
            ps.setString(1, bulkRetrieve.getFlag());
            ps.setString(2, bulkRetrieve.getId());
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

    public QueryResult deleteBulkRetrieve(String bulkRetrieveId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_bulk_retrieve WHERE id = ? "
            );
            ps.setString(1, bulkRetrieveId);
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

    public BulkRetrieve getBulkRetrieve(String bulkRetrieveId) {
        String sql = "SELECT * FROM sr_bulk_retrieve WHERE id = ? ";
        BulkRetrieve bulkRetrieve = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, bulkRetrieveId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bulkRetrieve = new BulkRetrieve();
                bulkRetrieve.setId(rs.getString("id"));
                bulkRetrieve.setRequestor(rs.getString("requestor"));
                bulkRetrieve.setDate(rs.getString("date"));
                bulkRetrieve.setFlag(rs.getString("flag"));
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
        return bulkRetrieve;
    }

    public List<BulkRetrieve> getBulkRetrieveList() {
        String sql = "SELECT * FROM sr_bulk_retrieve ORDER BY id ASC";
        List<BulkRetrieve> bulkRetrieveList = new ArrayList<BulkRetrieve>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            BulkRetrieve bulkRetrieve;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bulkRetrieve = new BulkRetrieve();
                bulkRetrieve.setId(rs.getString("id"));
                bulkRetrieve.setRequestor(rs.getString("requestor"));
                bulkRetrieve.setDate(rs.getString("date"));
                bulkRetrieve.setFlag(rs.getString("flag"));
                bulkRetrieveList.add(bulkRetrieve);
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
        return bulkRetrieveList;
    }

    public Integer getCountBulkByUserIdWithFlagZero(String userId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_bulk_retrieve WHERE requestor = ? AND flag = '0'");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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

    public Integer getCountBulkId(String bulkId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_bulk_retrieve WHERE id = ? ");
            ps.setString(1, bulkId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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

    public BulkRetrieve getBulkRetrieveByUserIdAndFlagZero(String requestor) {
        String sql = "SELECT * FROM sr_bulk_retrieve WHERE requestor = ? AND flag = '0'";
        BulkRetrieve bulkRetrieve = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bulkRetrieve = new BulkRetrieve();
                bulkRetrieve.setId(rs.getString("id"));
                bulkRetrieve.setRequestor(rs.getString("requestor"));
                bulkRetrieve.setDate(rs.getString("date"));
                bulkRetrieve.setFlag(rs.getString("flag"));
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
        return bulkRetrieve;
    }

}