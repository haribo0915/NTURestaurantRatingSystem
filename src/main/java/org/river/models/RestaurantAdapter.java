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
    List<Restaurant> queryRestaurants(String restaurantName, Area area, FoodCategory foodCategory) throws QueryException;
    List<Restaurant> queryWeeklyHottestRestaurants() throws QueryException;

    Area createArea(Area area) throws CreateException;
    Area updateArea(Area area) throws UpdateException;
    Area deleteArea(Area area) throws DeleteException;
    Area queryArea(Integer id) throws QueryException;
    Area queryArea(String name) throws QueryException;
    List<Area> queryAreas() throws QueryException;

    FoodCategory createFoodCategory(FoodCategory foodCategory) throws CreateException;
    FoodCategory updateFoodCategory(FoodCategory foodCategory) throws UpdateException;
    FoodCategory deleteFoodCategory(FoodCategory foodCategory) throws DeleteException;
    FoodCategory queryFoodCategory(Integer id) throws QueryException;
    FoodCategory queryFoodCategory(String name) throws QueryException;
    List<FoodCategory> queryFoodCategories() throws QueryException;

    Comment createComment(Comment comment) throws CreateException;
    Comment updateComment(Comment comment) throws UpdateException;
    Comment deleteComment(Comment comment) throws DeleteException;
    List<Comment> queryComments(User user) throws QueryException;
    List<Comment> queryComments(Restaurant restaurant) throws QueryException;
    List<UserComment> queryUserComments(Restaurant restaurant) throws QueryException;
    Comment queryComment(Integer userId, Integer restaurantId) throws QueryException;

}
