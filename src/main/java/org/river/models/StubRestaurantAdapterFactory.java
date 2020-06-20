package org.river.models;

/**
 * Factory used to create StubRestaurantAdapter dynamically
 *
 * @author - Haribo
 */
public class StubRestaurantAdapterFactory implements RestaurantAdapterFactory {
    @Override
    public RestaurantAdapter create() {
        return new StubRestaurantAdapter();
    }
}
