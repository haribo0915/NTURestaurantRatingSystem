package org.river.models;
import org.river.entities.*;
import org.river.exceptions.ResourceNotFoundException;

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
    public Restaurant createRestaurant(Restaurant restaurant) {
        Random rand = new Random();
        Integer id = rand.nextInt(50);
        id += 1;
        return new Restaurant(id, restaurant.getAreaId(), restaurant.getFoodCategoryId(), restaurant.getName(),
        restaurant.getDescription(), restaurant.getImage(), restaurant.getAddress());
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        return restaurant;
    }

    @Override
    public Restaurant deleteRestaurant(Restaurant restaurant) {
        return restaurant;
    }

    @Override
    public List<Restaurant> queryRestaurants(String restaurantName, Area area, FoodCategory foodCategory) throws ResourceNotFoundException {
        List<Restaurant> restaurantList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            Random rand = new Random();
            Integer id = rand.nextInt(50);
            id += 1;
            Restaurant restaurant = new Restaurant(i, id, id, "testRestaurant"+id,
                    "test restaurant description "+i,"file:./src/main/resources/images/fire.png", "restaurant address "+i);
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
    public List<Restaurant> queryWeeklyHottestRestaurants() throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Area createArea(Area area){
        return null;
    }

    @Override
    public Area updateArea(Area area) {
        return null;
    }

    @Override
    public Area deleteArea(Area area) {
        return null;
    }

    @Override
    public Area queryArea(Integer id) throws ResourceNotFoundException {
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        return new Area(id, "area "+randomId);
    }

    @Override
    public Area queryArea(String name) throws ResourceNotFoundException {
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        return new Area(randomId, name);
    }

    @Override
    public List<Area> queryAreas() throws ResourceNotFoundException {
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
    public FoodCategory createFoodCategory(FoodCategory foodCategory) {
        return null;
    }

    @Override
    public FoodCategory updateFoodCategory(FoodCategory foodCategory) {
        return null;
    }

    @Override
    public FoodCategory deleteFoodCategory(FoodCategory foodCategory) {
        return null;
    }

    @Override
    public FoodCategory queryFoodCategory(Integer id) {
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        return new FoodCategory(id, "food category "+randomId);
    }

    @Override
    public FoodCategory queryFoodCategory(String name) throws ResourceNotFoundException {
        Random rand = new Random();
        Integer randomId = rand.nextInt(50);
        randomId += 1;
        return new FoodCategory(randomId, name);
    }

    @Override
    public List<FoodCategory> queryFoodCategories() throws ResourceNotFoundException {
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
    public Comment createComment(Comment comment) {
        Random rand = new Random();
        Integer id = rand.nextInt(50);
        id += 1;
        return new Comment(id, comment.getUserId(), comment.getRestaurantId(), comment.getRate(),
        comment.getDescription(), comment.getImage(), comment.getTimestamp());
    }

    @Override
    public Comment updateComment(Comment comment) {
        return comment;
    }

    @Override
    public Comment deleteComment(Comment comment) {
        return comment;
    }

    @Override
    public List<Comment> queryComments(User user) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public List<Comment> queryComments(Restaurant restaurant) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public List<UserComment> queryUserComments(Restaurant restaurant) throws ResourceNotFoundException {
        List<UserComment> userCommentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            UserComment userComment = new UserComment(2, "admin", "admin", "admin", "admin@gmail.com", "CSIE",
                    i, restaurant.getId(), 4, "the restaurant is good "+i , "file:./src/main/resources/images/fire.png",
                    new Timestamp(System.currentTimeMillis()));
            userCommentList.add(userComment);
        }
        return userCommentList;
    }

    @Override
    public Comment queryComment(Integer userId, Integer restaurantId) throws ResourceNotFoundException {
        Random rand = new Random();
        Integer id = rand.nextInt(50);
        id += 1;
        return new Comment(id, userId, restaurantId, 5, "the restaurant is very good",
                "file:./src/main/resources/images/fire.png", new Timestamp(System.currentTimeMillis()));
    }
}
