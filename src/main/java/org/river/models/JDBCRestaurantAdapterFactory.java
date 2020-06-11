package org.river.models;

/**
 * @author - Haribo
 */
public class JDBCRestaurantAdapterFactory implements RestaurantAdapterFactory {
    @Override
    public RestaurantAdapter create() {
        return new JDBCRestaurantAdapter();
    }
}
