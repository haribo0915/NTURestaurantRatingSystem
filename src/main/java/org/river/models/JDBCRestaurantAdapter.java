package org.river.models;

import org.river.entities.*;
import org.river.exceptions.*;
import java.util.List;

/**
 * @author - Haribo
 */
public class JDBCRestaurantAdapter implements RestaurantAdapter {
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) throws CreateException {
        return null;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) throws UpdateException {
        return null;
    }

    @Override
    public Restaurant deleteRestaurant(Restaurant restaurant) throws DeleteException {
        return null;
    }

    @Override
    public List<Restaurant> queryWeeklyHottestRestaurants() throws QueryException {
        return null;
    }

    @Override
    public List<Restaurant> queryWorstRestaurants() throws QueryException {
        return null;
    }

    @Override
    public List<Restaurant> queryRestaurants(Area area) throws QueryException {
        return null;
    }

    @Override
    public List<Restaurant> queryRestaurants(FoodCategory foodCategory) throws QueryException {
        return null;
    }

    @Override
    public Restaurant queryRestaurant(String name) throws QueryException {
        return null;
    }

    @Override
    public Comment createComment(Comment comment) throws CreateException {
        return null;
    }

    @Override
    public Comment updateComment(Comment comment) throws UpdateException {
        return null;
    }

    @Override
    public Comment deleteComment(Comment comment) throws DeleteException {
        return null;
    }

    @Override
    public List<Comment> queryComments(User user) throws QueryException {
        return null;
    }

    @Override
    public List<Comment> queryComments(Restaurant restaurant) throws QueryException {
        return null;
    }
}
