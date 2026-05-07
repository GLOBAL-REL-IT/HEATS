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
import com.onsemi.mib.model.EmailHwReturnFromStaging;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailHwReturnFromStagingDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailHwReturnFromStagingDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EmailHwReturnFromStagingDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEmailHwReturnFromStaging(EmailHwReturnFromStaging emailhwReturnFromStaging) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO email_hw_return_from_staging (user_name, email, flag, remarks) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, emailhwReturnFromStaging.getUserName());
            ps.setString(2, emailhwReturnFromStaging.getEmail());
            ps.setString(3, emailhwReturnFromStaging.getFlag());
            ps.setString(4, emailhwReturnFromStaging.getRemarks());
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

    public QueryResult updateEmailHwReturnFromStaging(EmailHwReturnFromStaging emailhwReturnFromStaging) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE email_hw_return_from_staging SET user_name = ?, email = ?, flag = ?, remarks = ? WHERE id = ?"
            );
            ps.setString(1, emailhwReturnFromStaging.getUserName());
            ps.setString(2, emailhwReturnFromStaging.getEmail());
            ps.setString(3, emailhwReturnFromStaging.getFlag());
            ps.setString(4, emailhwReturnFromStaging.getRemarks());
            ps.setString(5, emailhwReturnFromStaging.getId());
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

    public QueryResult deleteEmailHwReturnFromStaging(String emailhwReturnFromStagingId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM email_hw_return_from_staging WHERE id = '" + emailhwReturnFromStagingId + "'"
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

    public EmailHwReturnFromStaging getEmailHwReturnFromStaging(String emailhwReturnFromStagingId) {
        String sql = "SELECT * FROM email_hw_return_from_staging WHERE id = '" + emailhwReturnFromStagingId + "'";
        EmailHwReturnFromStaging emailhwReturnFromStaging = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailhwReturnFromStaging = new EmailHwReturnFromStaging();
                emailhwReturnFromStaging.setId(rs.getString("id"));
                emailhwReturnFromStaging.setUserName(rs.getString("user_name"));
                emailhwReturnFromStaging.setEmail(rs.getString("email"));
                emailhwReturnFromStaging.setFlag(rs.getString("flag"));
                emailhwReturnFromStaging.setRemarks(rs.getString("remarks"));
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
        return emailhwReturnFromStaging;
    }

    public List<EmailHwReturnFromStaging> getEmailHwReturnFromStagingList() {
        String sql = "SELECT * FROM email_hw_return_from_staging ORDER BY id ASC";
        List<EmailHwReturnFromStaging> emailhwReturnFromStagingList = new ArrayList<EmailHwReturnFromStaging>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailHwReturnFromStaging emailhwReturnFromStaging;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailhwReturnFromStaging = new EmailHwReturnFromStaging();
                emailhwReturnFromStaging.setId(rs.getString("id"));
                emailhwReturnFromStaging.setUserName(rs.getString("user_name"));
                emailhwReturnFromStaging.setEmail(rs.getString("email"));
                emailhwReturnFromStaging.setFlag(rs.getString("flag"));
                emailhwReturnFromStaging.setRemarks(rs.getString("remarks"));
                emailhwReturnFromStagingList.add(emailhwReturnFromStaging);
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
        return emailhwReturnFromStagingList;
    }
}
