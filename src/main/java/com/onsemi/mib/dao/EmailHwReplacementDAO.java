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
import com.onsemi.mib.model.EmailHwReplacement;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailHwReplacementDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailHwReplacementDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EmailHwReplacementDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEmailHwReplacement(EmailHwReplacement emailhwReplacement) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO email_hw_replacement (user_name, email, flag, remarks) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, emailhwReplacement.getUserName());
            ps.setString(2, emailhwReplacement.getEmail());
            ps.setString(3, emailhwReplacement.getFlag());
            ps.setString(4, emailhwReplacement.getRemarks());
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

    public QueryResult updateEmailHwReplacement(EmailHwReplacement emailhwReplacement) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE email_hw_replacement SET user_name = ?, email = ?, flag = ?, remarks = ? WHERE id = ?"
            );
            ps.setString(1, emailhwReplacement.getUserName());
            ps.setString(2, emailhwReplacement.getEmail());
            ps.setString(3, emailhwReplacement.getFlag());
            ps.setString(4, emailhwReplacement.getRemarks());
            ps.setString(5, emailhwReplacement.getId());
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

    public QueryResult deleteEmailHwReplacement(String emailhwReplacementId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM email_hw_replacement WHERE id = '" + emailhwReplacementId + "'"
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

    public EmailHwReplacement getEmailHwReplacement(String emailhwReplacementId) {
        String sql = "SELECT * FROM email_hw_replacement WHERE id = '" + emailhwReplacementId + "'";
        EmailHwReplacement emailhwReplacement = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailhwReplacement = new EmailHwReplacement();
                emailhwReplacement.setId(rs.getString("id"));
                emailhwReplacement.setUserName(rs.getString("user_name"));
                emailhwReplacement.setEmail(rs.getString("email"));
                emailhwReplacement.setFlag(rs.getString("flag"));
                emailhwReplacement.setRemarks(rs.getString("remarks"));
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
        return emailhwReplacement;
    }

    public List<EmailHwReplacement> getEmailHwReplacementList() {
        String sql = "SELECT * FROM email_hw_replacement ORDER BY id ASC";
        List<EmailHwReplacement> emailhwReplacementList = new ArrayList<EmailHwReplacement>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailHwReplacement emailhwReplacement;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailhwReplacement = new EmailHwReplacement();
                emailhwReplacement.setId(rs.getString("id"));
                emailhwReplacement.setUserName(rs.getString("user_name"));
                emailhwReplacement.setEmail(rs.getString("email"));
                emailhwReplacement.setFlag(rs.getString("flag"));
                emailhwReplacement.setRemarks(rs.getString("remarks"));
                emailhwReplacementList.add(emailhwReplacement);
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
        return emailhwReplacementList;
    }
}
