package org.river.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The jdbc connection pool is used to maintain a collection of database connections
 * and reuse them to improve performance in our multithreading environment. It will
 * create new connection if there isn't any free one in the pool; otherwise it will
 * return the old connection.
 *
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

    /**
     * Instead of block the whole method, in order to increase performance,
     * we use synchronized modifier only when the object are going to create
     * and promise that JDBCConnectionPool is Singleton in multithreading condition
     *
     * @return jdbcConnectionPool
     */
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

    /**
     * Check whether the connection is closed
     *
     * @param connection connection
     * @return true if the connection hasn't closed; otherwise, return false
     */
    @Override
    boolean validate(Connection connection) {
        try {
            return !(connection.isClosed());

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Close the connection with database
     *
     * @param connection connection
     */
    @Override
    void dead(Connection connection) {
        try {
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
