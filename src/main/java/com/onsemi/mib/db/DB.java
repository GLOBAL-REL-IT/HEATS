package com.onsemi.mib.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Repository
public class DB extends SpringBeanAutowiringSupport {

    @Autowired
    private DataSource dataSource;

//    @Autowired
//    private DataSource dataSourceCdars;

    public Connection getConnection() {
        Connection conn;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return conn;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

//    //new connection 8.09.2025
//    public Connection getConnectionCdars() {
//        Connection conn;
//        try {
//            conn = dataSourceCdars.getConnection();
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//        return conn;
//    }
//
//    public DataSource getDataSourceCdars() {
//        return dataSourceCdars;
//    }
}
