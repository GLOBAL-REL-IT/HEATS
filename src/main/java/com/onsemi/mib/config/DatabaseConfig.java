package com.onsemi.mib.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:db.properties")
public class DatabaseConfig {

    @Autowired
    private Environment env;

    @Bean
//    @Primary //new 8.09.2025
    public DataSource dataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setLeakDetectionThreshold(4000);
        return dataSource;
    }

//    @Bean
//    public JdbcTemplate primaryJdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }

    @Bean
    public DataSource dataSourceCdars() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(env.getProperty("cdars.jdbc.url"));
        dataSource.setUsername(env.getProperty("cdars.jdbc.username"));
        dataSource.setPassword(env.getProperty("cdars.jdbc.password"));
        dataSource.setLeakDetectionThreshold(4000);
        return dataSource;
    }

//    @Bean
//    public JdbcTemplate secondaryJdbcTemplate() {
//        return new JdbcTemplate(dataSourceCdars());
//    }
}
