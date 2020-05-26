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
    List<Restaurant> queryWeeklyHottestRestaurant();
    List<Restaurant> queryWorstRestaurant();
    List<Restaurant> queryRestaurant(Area area);
    List<Restaurant> queryRestaurant(FoodCategory foodCategory);
    Restaurant queryRestaurant(String name);

    Comment createComment(Comment comment);
    Comment updateComment(Comment comment);
    Comment deleteComment(Comment comment);
    List<Comment> queryComment(User user);
    List<Comment> queryComment(Restaurant restaurant);
}
