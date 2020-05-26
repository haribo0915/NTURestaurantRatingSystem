package org.river.models;

import org.river.entities.*;

import java.util.List;

/**
 * @author - Haribo
 */
public interface RestaurantAdapter {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(Restaurant restaurant);
    Restaurant deleteRestaurant(Restaurant restaurant);
    List<Restaurant> queryWeeklyHottestRestaurants();
    List<Restaurant> queryWorstRestaurants();
    List<Restaurant> queryRestaurants(Area area);
    List<Restaurant> queryRestaurants(FoodCategory foodCategory);
    Restaurant queryRestaurant(String name);

    Comment createComment(Comment comment);
    Comment updateComment(Comment comment);
    Comment deleteComment(Comment comment);
    List<Comment> queryComments(User user);
    List<Comment> queryComments(Restaurant restaurant);

}
