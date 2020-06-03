package org.river.models;

import javafx.scene.image.Image;
import org.river.entities.*;
import org.river.exceptions.CreateException;
import org.river.exceptions.DeleteException;
import org.river.exceptions.QueryException;
import org.river.exceptions.UpdateException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author - Haribo
 */
public class StubRestaurantAdapter implements RestaurantAdapter {
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) throws CreateException {
        Random rand = new Random();
        Integer id = rand.nextInt(50);
        id += 1;
        return new Restaurant(id, restaurant.getAreaId(), restaurant.getFoodCategoryId(), restaurant.getName(),
        restaurant.getDescription(), restaurant.getImage(), restaurant.getAddress());
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) throws UpdateException {
        return restaurant;
    }

    @Override
    public Restaurant deleteRestaurant(Restaurant restaurant) throws DeleteException {
        return restaurant;
    }

    @Override
    public Restaurant queryRestaurant(String name) throws QueryException {
        Random rand = new Random();
        Integer id = rand.nextInt(50);
        id += 1;
        return new Restaurant(id, 1, 1, name,
                "test restaurant description", new Image("file:./src/main/resources/images/fire.png"), "restaurant address");
    }

    @Override
    public List<Restaurant> queryRestaurants() throws QueryException {
        List<Restaurant> restaurantList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            Integer id = rand.nextInt(50);
            id += 1;
            Restaurant restaurant = new Restaurant(id, 1, 1, "testRestaurant"+id,
                    "test restaurant description "+id, new Image("file:./src/main/resources/images/fire.png"), "restaurant address "+id);
            restaurantList.add(restaurant);
        }
        return restaurantList;
    }

    @Override
    public List<Restaurant> queryRestaurants(Area area) throws QueryException {
        List<Restaurant> restaurantList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            Integer id = rand.nextInt(50);
            id += 1;
            Restaurant restaurant = new Restaurant(id, area.getId(), 1, "testRestaurant"+id,
                    "test restaurant description "+id, new Image("file:./src/main/resources/images/fire.png"), "restaurant address "+id);
            restaurantList.add(restaurant);
        }
        return restaurantList;
    }

    @Override
    public List<Restaurant> queryRestaurants(FoodCategory foodCategory) throws QueryException {
        List<Restaurant> restaurantList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            Integer id = rand.nextInt(50);
            id += 1;
            Restaurant restaurant = new Restaurant(id, 1, foodCategory.getId(), "testRestaurant"+id,
                    "test restaurant description "+id, new Image("file:./src/main/resources/images/fire.png"), "restaurant address "+id);
            restaurantList.add(restaurant);
        }
        return restaurantList;
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
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        return new Area(id, "area "+randomId);
    }

    @Override
    public Area queryArea(String name) throws QueryException {
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        return new Area(randomId, name);
    }

    @Override
    public List<Area> queryAreas() throws QueryException {
        List<Area> areaList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            Integer id = rand.nextInt(50);
            id += 1;
            Area area = new Area(id, "area "+id);
            areaList.add(area);
        }
        return areaList;
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
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        return new FoodCategory(id, "food category "+randomId);
    }

    @Override
    public FoodCategory queryFoodCategory(String name) throws QueryException {
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        return new FoodCategory(randomId, name);
    }

    @Override
    public List<FoodCategory> queryFoodCategories() throws QueryException {
        List<FoodCategory> foodCategoryList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            Integer id = rand.nextInt(50);
            id += 1;
            FoodCategory foodCategory = new FoodCategory(id, "food category "+id);
            foodCategoryList.add(foodCategory);
        }
        return foodCategoryList;
    }

    @Override
    public Comment createComment(Comment comment) throws CreateException {
        Random rand = new Random();
        Integer id = rand.nextInt(50);
        id += 1;
        return new Comment(id, comment.getUserId(), comment.getRestaurantId(), comment.getRate(),
        comment.getDescription(), comment.getImage(), comment.getTimestamp());
    }

    @Override
    public Comment updateComment(Comment comment) throws UpdateException {
        return comment;
    }

    @Override
    public Comment deleteComment(Comment comment) throws DeleteException {
        return comment;
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
        List<UserComment> userCommentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            UserComment userComment = new UserComment(2, "admin", "admin", "admin", "admin@gmail.com", "CSIE",
                    i, restaurant.getId(), 4, "the restaurant is good "+i , new Image("file:./src/main/resources/images/fire.png"),
                    new Timestamp(System.currentTimeMillis()));
            userCommentList.add(userComment);
        }
        return userCommentList;
    }

    @Override
    public Comment queryComment(Integer userId, Integer restaurantId) throws QueryException {
        Random rand = new Random();
        Integer id = rand.nextInt(50);
        id += 1;
        return new Comment(id, userId, restaurantId, 5, "the restaurant is very good",
                new Image("file:./src/main/resources/images/fire.png"), new Timestamp(System.currentTimeMillis()));
    }
}
