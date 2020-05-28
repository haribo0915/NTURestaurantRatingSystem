package org.river.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author - Haribo
 */
public class DBConnector {
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ntu_restaurant_rating_system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "root", "880915");
        return connection;
    }
}
