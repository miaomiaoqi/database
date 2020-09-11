package com.miaoqi.database.lock.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {

    private static Properties config = new Properties();

    static {
        try {
            InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
            config.load(is);
            Class.forName(config.getProperty("driver"));
        } catch (Exception e) {
            throw new ExceptionInInitializerError();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(config.getProperty("url"),
                    config.getProperty("username"), config.getProperty("password"));
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            st = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

}
