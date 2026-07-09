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
    private final Connection conn;
    private final DataSource dataSource;

    public EmailCcDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEmailCc(EmailCc emailCc) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO email_cc (cid, name, location, email, flag) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, emailCc.getCid());
            ps.setString(2, emailCc.getName());
            ps.setString(3, emailCc.getLocation());
            ps.setString(4, emailCc.getEmail());
            ps.setString(5, emailCc.getFlag());
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

    public QueryResult updateEmailCc(EmailCc emailCc) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE email_cc SET cid = ?, name = ?, location = ?, email = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, emailCc.getCid());
            ps.setString(2, emailCc.getName());
            ps.setString(3, emailCc.getLocation());
            ps.setString(4, emailCc.getEmail());
            ps.setString(5, emailCc.getFlag());
            ps.setString(6, emailCc.getId());
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

    public QueryResult deleteEmailCc(String emailCcId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM email_cc WHERE id = '" + emailCcId + "'"
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

    public EmailCc getEmailCc(String emailCcId) {
        String sql = "SELECT * FROM email_cc WHERE id = '" + emailCcId + "'";
        EmailCc emailCc = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailCc = new EmailCc();
                emailCc.setId(rs.getString("id"));
                emailCc.setCid(rs.getString("cid"));
                emailCc.setName(rs.getString("name"));
                emailCc.setLocation(rs.getString("location"));
                emailCc.setEmail(rs.getString("email"));
                emailCc.setFlag(rs.getString("flag"));
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
        return emailCc;
    }

    public List<EmailCc> getEmailCcList() {
        String sql = "SELECT * FROM email_cc ORDER BY location, name ASC";
        List<EmailCc> emailCcList = new ArrayList<EmailCc>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailCc emailCc;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailCc = new EmailCc();
//                emailCc.setId(rs.getString("id"));
//                emailCc.setCid(rs.getString("cid"));
                emailCc.setName(rs.getString("name"));
                emailCc.setLocation(rs.getString("location"));
                emailCc.setEmail(rs.getString("email"));
//                emailCc.setFlag(rs.getString("flag"));
                emailCcList.add(emailCc);
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
        return emailCcList;
    }

    public List<EmailCc> getEmailCcListBySite(String location) {
        String sql = "SELECT * FROM email_cc WHERE location LIKE '" + location + "' ORDER BY name ASC";
        List<EmailCc> emailCcList = new ArrayList<EmailCc>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailCc emailCc;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailCc = new EmailCc();
//                emailCc.setId(rs.getString("id"));
//                emailCc.setCid(rs.getString("cid"));
                emailCc.setName(rs.getString("name"));
                emailCc.setLocation(rs.getString("location"));
                emailCc.setEmail(rs.getString("email"));
//                emailCc.setFlag(rs.getString("flag"));
                emailCcList.add(emailCc);
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
        return emailCcList;
    }

    public Integer getCountEmail(String email) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM email_cc WHERE email = \""+email+"\""
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
}
