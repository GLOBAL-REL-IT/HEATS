package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.EmailCc;
import com.onsemi.mib.tools.QueryResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailCcDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailCcDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public EmailCcDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    private static final String INSERT_EMAIL_CC = "INSERT INTO email_cc (cid, name, location, email, flag) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_EMAIL_CC = "UPDATE email_cc SET cid = ?, name = ?, location = ?, email = ?, flag = ? WHERE id = ?";
    private static final String DELETE_EMAIL_CC = "DELETE FROM email_cc WHERE id = ?";
    private static final String GET_EMAIL_CC_BY_ID = "SELECT id, cid, name, location, email, flag FROM email_cc WHERE id = ?";
    private static final String GET_EMAIL_CC_LIST = "SELECT name, location, email FROM email_cc ORDER BY location, name ASC";
    private static final String GET_EMAIL_CC_LIST_BY_SITE = "SELECT name, location, email FROM email_cc WHERE location LIKE ? ORDER BY name ASC";
    private static final String GET_COUNT_EMAIL = "SELECT COUNT(*) AS count FROM email_cc WHERE email = ?";

    public QueryResult insertEmailCc(EmailCc emailCc) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_EMAIL_CC,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, emailCc.getCid());
            ps.setString(2, emailCc.getName());
            ps.setString(3, emailCc.getLocation());
            ps.setString(4, emailCc.getEmail());
            ps.setString(5, emailCc.getFlag());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting email_cc. cid: {}, email: {}, error: {}", emailCc.getCid(), emailCc.getEmail(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult updateEmailCc(EmailCc emailCc) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_EMAIL_CC)) {
            ps.setString(1, emailCc.getCid());
            ps.setString(2, emailCc.getName());
            ps.setString(3, emailCc.getLocation());
            ps.setString(4, emailCc.getEmail());
            ps.setString(5, emailCc.getFlag());
            ps.setString(6, emailCc.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating email_cc. id: {}, cid: {}, email: {}, error: {}", emailCc.getId(), emailCc.getCid(), emailCc.getEmail(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult deleteEmailCc(String emailCcId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_EMAIL_CC)) {
            ps.setString(1, emailCcId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting email_cc. id: {}, error: {}", emailCcId, e.getMessage(), e);
        }
        return queryResult;
    }

    public EmailCc getEmailCc(String emailCcId) {
        EmailCc emailCc = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_EMAIL_CC_BY_ID)) {
            ps.setString(1, emailCcId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emailCc = new EmailCc();
                    emailCc.setId(rs.getString("id"));
                    emailCc.setCid(rs.getString("cid"));
                    emailCc.setName(rs.getString("name"));
                    emailCc.setLocation(rs.getString("location"));
                    emailCc.setEmail(rs.getString("email"));
                    emailCc.setFlag(rs.getString("flag"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving email_cc. id: {}, error: {}", emailCcId, e.getMessage(), e);
        }
        return emailCc;
    }

    public List<EmailCc> getEmailCcList() {
        List<EmailCc> emailCcList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_EMAIL_CC_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EmailCc emailCc = new EmailCc();
                emailCc.setName(rs.getString("name"));
                emailCc.setLocation(rs.getString("location"));
                emailCc.setEmail(rs.getString("email"));
                emailCcList.add(emailCc);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving email_cc list. error: {}", e.getMessage(), e);
        }
        return emailCcList;
    }

    public List<EmailCc> getEmailCcListBySite(String location) {
        List<EmailCc> emailCcList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_EMAIL_CC_LIST_BY_SITE)) {
            ps.setString(1, location);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmailCc emailCc = new EmailCc();
                    emailCc.setName(rs.getString("name"));
                    emailCc.setLocation(rs.getString("location"));
                    emailCc.setEmail(rs.getString("email"));
                    emailCcList.add(emailCc);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving email_cc list by site. location: {}, error: {}", location, e.getMessage(), e);
        }
        return emailCcList;
    }

    public Integer getCountEmail(String email) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_COUNT_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting email count. email: {}, error: {}", email, e.getMessage(), e);
        }
        return count;
    }
    
}