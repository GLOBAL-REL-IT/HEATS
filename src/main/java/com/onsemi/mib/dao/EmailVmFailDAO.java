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
import com.onsemi.mib.model.EmailVmFail;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailVmFailDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVmFailDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EmailVmFailDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEmailVmFail(EmailVmFail emailvmFail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO email_vm_fail (user_name, email, flag, remarks) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, emailvmFail.getUserName());
            ps.setString(2, emailvmFail.getEmail());
            ps.setString(3, emailvmFail.getFlag());
            ps.setString(4, emailvmFail.getRemarks());
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

    public QueryResult updateEmailVmFail(EmailVmFail emailvmFail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE email_vm_fail SET user_name = ?, email = ?, flag = ?, remarks = ? WHERE id = ?"
            );
            ps.setString(1, emailvmFail.getUserName());
            ps.setString(2, emailvmFail.getEmail());
            ps.setString(3, emailvmFail.getFlag());
            ps.setString(4, emailvmFail.getRemarks());
            ps.setString(5, emailvmFail.getId());
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

    public QueryResult deleteEmailVmFail(String emailvmFailId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM email_vm_fail WHERE id = '" + emailvmFailId + "'"
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

    public EmailVmFail getEmailVmFail(String emailvmFailId) {
        String sql = "SELECT * FROM email_vm_fail WHERE id = '" + emailvmFailId + "'";
        EmailVmFail emailvmFail = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailvmFail = new EmailVmFail();
                emailvmFail.setId(rs.getString("id"));
                emailvmFail.setUserName(rs.getString("user_name"));
                emailvmFail.setEmail(rs.getString("email"));
                emailvmFail.setFlag(rs.getString("flag"));
                emailvmFail.setRemarks(rs.getString("remarks"));
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
        return emailvmFail;
    }

    public List<EmailVmFail> getEmailVmFailList() {
        String sql = "SELECT * FROM email_vm_fail ORDER BY id ASC";
        List<EmailVmFail> emailvmFailList = new ArrayList<EmailVmFail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailVmFail emailvmFail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailvmFail = new EmailVmFail();
                emailvmFail.setId(rs.getString("id"));
                emailvmFail.setUserName(rs.getString("user_name"));
                emailvmFail.setEmail(rs.getString("email"));
                emailvmFail.setFlag(rs.getString("flag"));
                emailvmFail.setRemarks(rs.getString("remarks"));
                emailvmFailList.add(emailvmFail);
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
        return emailvmFailList;
    }
    
    public List<EmailVmFail> getEmailMotherboardTechnicianMb() {
        String sql = "SELECT * FROM email_config WHERE module = 'Motherboard Config' ORDER BY id ASC";
        List<EmailVmFail> emailList = new ArrayList<EmailVmFail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailVmFail emailData;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailData = new EmailVmFail();
                emailData.setId(rs.getString("id"));
                emailData.setUserName(rs.getString("name"));
                emailData.setEmail(rs.getString("email"));
                emailList.add(emailData);
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
        return emailList;
    }
    
    public List<EmailVmFail> getEmailMotherboardTechnicianLc() {
        String sql = "SELECT * FROM email_config WHERE module = 'Load Card Config' ORDER BY id ASC";
        List<EmailVmFail> emailList = new ArrayList<EmailVmFail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailVmFail emailData;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailData = new EmailVmFail();
                emailData.setId(rs.getString("id"));
                emailData.setUserName(rs.getString("name"));
                emailData.setEmail(rs.getString("email"));
                emailList.add(emailData);
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
        return emailList;
    }

}