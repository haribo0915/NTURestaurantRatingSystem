package org.river.models;

import org.river.entities.*;
import org.river.exceptions.CreateException;
import org.river.exceptions.DeleteException;
import org.river.exceptions.QueryException;
import org.river.exceptions.UpdateException;

import java.util.List;

/**
 * @author - Haribo
 */
public class StubRestaurantAdapter implements RestaurantAdapter {
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
    public Restaurant queryRestaurant(String name) throws QueryException {
        return null;
    }

    @Override
    public List<Restaurant> queryRestaurants() throws QueryException {
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
    public List<Restaurant> queryWeeklyHottestRestaurants() throws QueryException {
        return null;
    }

    @Override
    public Area createArea(Area area) throws CreateException {
        return null;
    }

    @Override
    public Area updateArea(Area area) throws UpdateException {
        return null;
    }

    @Override
    public Area deleteArea(Area area) throws DeleteException {
        return null;
    }

    @Override
    public Area queryArea(Integer id) throws QueryException {
        return null;
    }

    @Override
    public Area queryArea(String name) throws QueryException {
        return null;
    }

    @Override
    public List<Area> queryAreas() throws QueryException {
        return null;
    }

    @Override
    public FoodCategory createFoodCategory(FoodCategory foodCategory) throws CreateException {
        return null;
    }

    @Override
    public FoodCategory updateFoodCategory(FoodCategory foodCategory) throws UpdateException {
        return null;
    }

    @Override
    public FoodCategory deleteFoodCategory(FoodCategory foodCategory) throws DeleteException {
        return null;
    }

    @Override
    public FoodCategory queryFoodCategory(Integer id) throws QueryException {
        return null;
    }

    @Override
    public FoodCategory queryFoodCategory(String name) throws QueryException {
        return null;
    }

    @Override
    public List<FoodCategory> queryFoodCategories() throws QueryException {
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

    @Override
    public List<UserComment> queryUserComments(Restaurant restaurant) throws QueryException {
        return null;
    }

    @Override
    public Comment queryComment(Integer userId, Integer restaurantId) throws QueryException {
        return null;
    }
}
