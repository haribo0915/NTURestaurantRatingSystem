package org.river.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author - Haribo
 */
public class DBConnector {
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ntu_restaurant_rating_system", "root", "880915");
        return connection;
    }
}
