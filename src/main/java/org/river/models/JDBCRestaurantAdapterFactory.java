package org.river.models;

/**
 * Factory used to create JDBCRestaurantAdapter dynamically
 *
 * @author - Haribo
 */
public class JDBCRestaurantAdapterFactory implements RestaurantAdapterFactory {
    @Override
    public RestaurantAdapter create() {
        return new JDBCRestaurantAdapter();
    }
}
