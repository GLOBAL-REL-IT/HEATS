package com.onsemi.mib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.Email;
import com.onsemi.mib.model.UserEmail;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailDAO {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailDAO.class);
    private final Connection conn;

    public EmailDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
    }
    
    public Email getEmail() {
        String sql = "SELECT * FROM system_email";
        Email email = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                email = new Email();
                email.setId(rs.getString("id"));
                email.setHost(rs.getString("host"));
                email.setPort(rs.getInt("port"));
                email.setUsername(rs.getString("username"));
                email.setPassword(rs.getString("password"));
                email.setSender(rs.getString("sender"));
                email.setAuth(rs.getBoolean("auth"));
                email.setStartTLS(rs.getBoolean("starttls"));
                email.setDebug(rs.getBoolean("debug"));
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
        return email;
    }
    
    public List<UserEmail> getEmailAll() {
        String sql = "SELECT * "
                   + "FROM user_group g, user_ldap p "
//                   + "WHERE g.id = p.group_id AND (p.group_id = 1) ";
                   + "WHERE g.id = p.group_id AND (p.group_id = 1 OR p.group_id = 3 OR p.group_id = 6) ";
        List<UserEmail> userEmailList = new ArrayList<UserEmail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserEmail userEmail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userEmail = new UserEmail();
                userEmail.setId(rs.getString("p.id"));
                userEmail.setLoginId(rs.getString("login_id"));
                userEmail.setUserId(rs.getString("oncid"));
                userEmail.setFullname(rs.getString("firstname") + " " + rs.getString("lastname"));
                userEmail.setEmail(rs.getString("email"));
                userEmail.setGroupId(rs.getString("p.group_id"));
                userEmail.setIsActive(rs.getString("is_active"));
                userEmail.setCreatedTime(rs.getString("p.created_time"));
                userEmail.setModifiedBy(rs.getString("p.modified_by"));
                userEmail.setModifiedTime(rs.getString("p.modified_time"));
                userEmailList.add(userEmail);
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
        return userEmailList;
    }
    
    public List<UserEmail> getEmailCSV() {
        String sql = "SELECT * "
                   + "FROM user_group g, user_ldap p "
                   + "WHERE g.id = p.group_id AND p.group_id = 7 ";
        List<UserEmail> userEmailList = new ArrayList<UserEmail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserEmail userEmail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userEmail = new UserEmail();
                userEmail.setId(rs.getString("p.id"));
                userEmail.setLoginId(rs.getString("login_id"));
                userEmail.setUserId(rs.getString("oncid"));
                userEmail.setFullname(rs.getString("firstname") + " " + rs.getString("lastname"));
                userEmail.setEmail(rs.getString("email"));
                userEmail.setGroupId(rs.getString("p.group_id"));
                userEmail.setIsActive(rs.getString("is_active"));
                userEmail.setCreatedTime(rs.getString("p.created_time"));
                userEmail.setModifiedBy(rs.getString("p.modified_by"));
                userEmail.setModifiedTime(rs.getString("p.modified_time"));
                userEmailList.add(userEmail);
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
        return userEmailList;
    }
    
    public List<UserEmail> getEmailRL() {
        String sql = "SELECT * "
                   + "FROM user_group g, user_ldap p "
                   + "WHERE g.id = p.group_id AND (p.group_id = 1 OR p.group_id = 3 OR p.group_id = 4) "
                   + "GROUP BY email ";
        List<UserEmail> userEmailList = new ArrayList<UserEmail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserEmail userEmail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userEmail = new UserEmail();
                userEmail.setId(rs.getString("p.id"));
                userEmail.setLoginId(rs.getString("login_id"));
                userEmail.setUserId(rs.getString("oncid"));
                userEmail.setFullname(rs.getString("firstname") + " " + rs.getString("lastname"));
                userEmail.setEmail(rs.getString("email"));
                userEmail.setGroupId(rs.getString("p.group_id"));
                userEmail.setIsActive(rs.getString("is_active"));
                userEmail.setCreatedTime(rs.getString("p.created_time"));
                userEmail.setModifiedBy(rs.getString("p.modified_by"));
                userEmail.setModifiedTime(rs.getString("p.modified_time"));
                userEmailList.add(userEmail);
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
        return userEmailList;
    }
    
    public List<UserEmail> getEmailNotifyRelatedPerson() {
        String sql = "SELECT * "
                   + "FROM user_group g, user_ldap p "
                   + "WHERE g.id = p.group_id AND (p.group_id = 1 OR p.group_id = 3 OR p.group_id = 6 OR p.group_id = 4) "
                   + "GROUP BY email ";
        List<UserEmail> userEmailList = new ArrayList<UserEmail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserEmail userEmail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userEmail = new UserEmail();
                userEmail.setId(rs.getString("p.id"));
                userEmail.setLoginId(rs.getString("login_id"));
                userEmail.setUserId(rs.getString("oncid"));
                userEmail.setFullname(rs.getString("firstname") + " " + rs.getString("lastname"));
                userEmail.setEmail(rs.getString("email"));
                userEmail.setGroupId(rs.getString("p.group_id"));
                userEmail.setIsActive(rs.getString("is_active"));
                userEmail.setCreatedTime(rs.getString("p.created_time"));
                userEmail.setModifiedBy(rs.getString("p.modified_by"));
                userEmail.setModifiedTime(rs.getString("p.modified_time"));
                userEmailList.add(userEmail);
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
        return userEmailList;
    }
}
