package com.onsemi.mib.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class ModelGenerator {

    public static void main(String[] args) {
        String table = "user_access_control";
        String sql = "SELECT * FROM " + table + " LIMIT 1";
        try {
////            Class.forName("com.mysql.jdbc.Driver");
//            Class.forName("com.mysql.cj.jdbc.Driver");
////            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection conn = null;
////            conn = DriverManager.getConnection("jdbc:sqlserver://MYSE01WS039/GP01QA;databaseName=MIB_SBN;integratedSecurity=true");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mib?serverTimezone=UTC&useLegacyDatetimeCode=false", "root", "root");

            Properties props = new Properties();
            try ( InputStream input = DAOGenerator.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new RuntimeException("db.properties not found");
                }
                props.load(input);
            }
            Class.forName(props.getProperty("jdbc.driver"));

            Connection conn = DriverManager.getConnection(
                    props.getProperty("jdbc.url"),
                    props.getProperty("jdbc.username"),
                    props.getProperty("jdbc.password"));
            if (conn != null) {
                String className = className(table);
                System.out.println("ClassName: " + className);
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData metaData = rs.getMetaData();
                int count = metaData.getColumnCount();
                String packageName = "package com.onsemi.mib.model;";
                String classFileContent = packageName + "\n\npublic class " + className + " {\n\n";
                for (int i = 1; i <= count; i++) {
                    classFileContent += "\tprivate String " + capitalize(metaData.getColumnLabel(i)) + ";\n";
                }
                classFileContent += "\n";
                for (int i = 1; i <= count; i++) {
                    classFileContent += "\tpublic String get" + capitalizeAll(metaData.getColumnLabel(i)) + "() {\n"
                            + "\t\treturn " + capitalize(metaData.getColumnLabel(i)) + ";\n"
                            + "\t}\n\n"
                            + "\tpublic void set" + capitalizeAll(metaData.getColumnLabel(i)) + "(String " + capitalize(metaData.getColumnLabel(i)) + ") {\n"
                            + "\t\tthis." + capitalize(metaData.getColumnLabel(i)) + " = " + capitalize(metaData.getColumnLabel(i)) + ";\n"
                            + "\t}\n\n";
                }
                classFileContent += "}";
                String fileLocation = "C:\\D Drive\\New\\HEATS\\src\\main\\java\\com\\onsemi\\mib\\model\\";
                FileUtils.writeStringToFile(new File(fileLocation + className + ".java"), classFileContent);
                rs.close();
                ps.close();
                conn.close();
            } else {
                System.out.println("Connection is NULL!");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String capitalize(String string) {
        String[] a = string.split("\\_");
        String b = "";
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                b += a[i];
            } else {
                b += StringUtils.capitalize(a[i]);
            }
        }
        return b;
    }

    private static String capitalizeAll(String string) {
        String[] a = string.split("\\_");
        String b = "";
        for (int i = 0; i < a.length; i++) {
            b += StringUtils.capitalize(a[i]);
        }
        return b;
    }

    private static String className(String string) {
        String[] a = string.split("\\_");
        String b = "";
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                b += StringUtils.capitalize(a[i]); //added 18092025
            } else {
                b += StringUtils.capitalize(a[i]);
            }
        }
        return b;
    }

}
