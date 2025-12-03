package com.onsemi.mib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.Email;
import java.sql.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HimsEmailDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(HimsEmailDAO.class);
    private final Connection conn;

    public HimsEmailDAO() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://mysed-rel-app03:3306/cdars?serverTimezone=UTC&useLegacyDatetimeCode=false", "root", "root");
        this.conn = conn;
    }

    public Email getEmail() {
        String sql = "SELECT * FROM cdars_email";
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
}
