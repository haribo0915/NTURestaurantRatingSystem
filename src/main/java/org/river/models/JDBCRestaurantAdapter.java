package org.river.models;

import org.river.entities.*;

import java.util.List;

/**
 * @author - Haribo
 */
public class JDBCRestaurantAdapter implements RestaurantAdapter {
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return null;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        return null;
    }

    @Override
    public Restaurant deleteRestaurant(Restaurant restaurant) {
        return null;
    }

    @Override
    public List<Restaurant> queryWeeklyHottestRestaurants() {
        return null;
    }

    @Override
    public List<Restaurant> queryWorstRestaurants() {
        return null;
    }

    @Override
    public List<Restaurant> queryRestaurants(Area area) {
        return null;
    }

    @Override
    public List<Restaurant> queryRestaurants(FoodCategory foodCategory) {
        return null;
    }

    @Override
    public Restaurant queryRestaurant(String name) {
        return null;
    }

    @Override
    public Comment createComment(Comment comment) {
        return null;
    }

    @Override
    public Comment updateComment(Comment comment) {
        return null;
    }

    @Override
    public Comment deleteComment(Comment comment) {
        return null;
    }

    @Override
    public List<Comment> queryComments(User user) {
        return null;
    }

    @Override
    public List<Comment> queryComments(Restaurant restaurant) {
        return null;
    }
}
