package com.example.com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TODO: remove because it's useless.
 */
public class DbManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "literarium";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static Connection connInstance;

    public static Connection getConnectionInstance() throws SQLException {

        if(connInstance == null)
            connInstance = DriverManager.getConnection(DB_URL);

        return connInstance;
    }
}
