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
    public List<Restaurant> queryWeeklyHottestRestaurant() {
        return null;
    }

    @Override
    public List<Restaurant> queryWorstRestaurant() {
        return null;
    }

    @Override
    public List<Restaurant> queryRestaurant(Area area) {
        return null;
    }

    @Override
    public List<Restaurant> queryRestaurant(FoodCategory foodCategory) {
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
    public List<Comment> queryComment(User user) {
        return null;
    }

    @Override
    public List<Comment> queryComment(Restaurant restaurant) {
        return null;
    }
}
