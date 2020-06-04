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
import java.util.stream.Collectors;

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
    public List<Restaurant> queryRestaurants(String restaurantName, Area area, FoodCategory foodCategory) throws QueryException {
        List<Restaurant> restaurantList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            Random rand = new Random();
            Integer id = rand.nextInt(50);
            id += 1;
            Restaurant restaurant = new Restaurant(i, id, id, "testRestaurant"+id,
                    "test restaurant description "+i, new Image("file:./src/main/resources/images/fire.png"), "restaurant address "+i);
            restaurantList.add(restaurant);
        }

        if (restaurantName != null) {
            restaurantList = restaurantList.stream()
                    .filter((Restaurant restaurant) -> (restaurant.getName().equals(restaurantName)))
                    .collect(Collectors.toList());
        }
        if (area != null) {
            restaurantList = restaurantList.stream()
                    .filter((Restaurant restaurant) -> (restaurant.getAreaId().equals(area.getId())))
                    .collect(Collectors.toList());
        }
        if (foodCategory != null) {
            restaurantList = restaurantList.stream()
                    .filter((Restaurant restaurant) -> (restaurant.getFoodCategoryId().equals(foodCategory.getId())))
                    .collect(Collectors.toList());
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
