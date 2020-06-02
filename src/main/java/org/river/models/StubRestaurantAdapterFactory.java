package org.river.models;

/**
 * @author - Haribo
 */
public class StubRestaurantAdapterFactory implements RestaurantAdapterFactory {
    @Override
    public RestaurantAdapter create() {
        return new StubRestaurantAdapter();
    }
}
