package org.river.models;

import org.river.entities.*;
import org.river.exceptions.*;

import java.util.List;

/**
 * @author - Haribo
 */
public interface RestaurantAdapter {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(Restaurant restaurant);
    Restaurant deleteRestaurant(Restaurant restaurant);
    List<Restaurant> queryRestaurants(String restaurantName, Area area, FoodCategory foodCategory) throws ResourceNotFoundException;
    List<Restaurant> queryWeeklyHottestRestaurants() throws ResourceNotFoundException;

    Area createArea(Area area);
    Area updateArea(Area area);
    Area deleteArea(Area area);
    Area queryArea(Integer id) throws ResourceNotFoundException;
    Area queryArea(String name) throws ResourceNotFoundException;
    List<Area> queryAreas() throws ResourceNotFoundException;

    FoodCategory createFoodCategory(FoodCategory foodCategory);
    FoodCategory updateFoodCategory(FoodCategory foodCategory);
    FoodCategory deleteFoodCategory(FoodCategory foodCategory);
    FoodCategory queryFoodCategory(Integer id) throws ResourceNotFoundException;
    FoodCategory queryFoodCategory(String name) throws ResourceNotFoundException;
    List<FoodCategory> queryFoodCategories() throws ResourceNotFoundException;

    Comment createComment(Comment comment);
    Comment updateComment(Comment comment);
    Comment deleteComment(Comment comment);
    List<Comment> queryComments(User user) throws ResourceNotFoundException;
    List<Comment> queryComments(Restaurant restaurant) throws ResourceNotFoundException;
    List<UserComment> queryUserComments(Restaurant restaurant) throws ResourceNotFoundException;
    Comment queryComment(Integer userId, Integer restaurantId) throws ResourceNotFoundException;

}
