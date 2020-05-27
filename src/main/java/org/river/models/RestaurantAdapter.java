package org.river.models;

import org.river.entities.*;
import org.river.exceptions.*;

import java.util.List;

/**
 * @author - Haribo
 */
public interface RestaurantAdapter {
    Restaurant createRestaurant(Restaurant restaurant) throws CreateException;
    Restaurant updateRestaurant(Restaurant restaurant) throws UpdateException;
    Restaurant deleteRestaurant(Restaurant restaurant) throws DeleteException;
    List<Restaurant> queryWeeklyHottestRestaurants() throws QueryException;
    List<Restaurant> queryWorstRestaurants() throws QueryException;
    List<Restaurant> queryRestaurants(Area area) throws QueryException;
    List<Restaurant> queryRestaurants(FoodCategory foodCategory) throws QueryException;
    Restaurant queryRestaurant(String name) throws QueryException;

    Comment createComment(Comment comment) throws CreateException;
    Comment updateComment(Comment comment) throws UpdateException;
    Comment deleteComment(Comment comment) throws DeleteException;
    List<Comment> queryComments(User user) throws QueryException;
    List<Comment> queryComments(Restaurant restaurant) throws QueryException;

}
