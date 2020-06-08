package org.river.entities;

import javafx.scene.image.Image;
import java.sql.Timestamp;

/**
 * @author - Haribo
 */
public class Comment {
    private Integer id;
    private Integer userId;
    private Integer restaurantId;
    private Integer rate;
    private String description;
    private String image;
    private Timestamp timestamp;

    public Comment() {
    }

    public Comment(Integer userId, Integer restaurantId, Integer rate, String description, String image, Timestamp timestamp) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rate = rate;
        this.description = description;
        this.image = image;
        this.timestamp = timestamp;
    }

    public Comment(Integer id, Integer userId, Integer restaurantId, Integer rate, String description, String image, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rate = rate;
        this.description = description;
        this.image = image;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
