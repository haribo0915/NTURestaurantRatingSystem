package org.river.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author - Haribo
 */
public class JDBCConnectionPool extends ObjectPool<Connection> {
    private static JDBCConnectionPool jdbcConnectionPool;
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/ntu_restaurant_rating_system?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "root";

    JDBCConnectionPool() {
        try {
            Class.forName(DRIVER_CLASS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Instead of block the whole method, in order to increase performance,
    // we use synchronized modifier only when the object are going to create and promise that
    // JDBCConnectionPool is Singleton in multithreading condition
    public static JDBCConnectionPool getInstance() {
        if (jdbcConnectionPool == null) {
            synchronized(JDBCConnectionPool.class) {
                if (jdbcConnectionPool == null) {
                    jdbcConnectionPool = new JDBCConnectionPool();
                }
            }
        }
        return jdbcConnectionPool;
    }

    @Override
    Connection create() {
        try {
            return (DriverManager.getConnection(DB_CONNECTION_URL, DB_USER, DB_PWD));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    boolean validate(Connection connection) {
        try {
            return !(connection.isClosed());

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    void dead(Connection connection) {
        try {
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
