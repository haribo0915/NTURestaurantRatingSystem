package org.river.entities;

import javafx.scene.image.Image;
import java.sql.Timestamp;

/**
 * @author - Haribo
 */
public class Comment {
    private Integer id;
    private Integer user_id;
    private Integer restaurant_id;
    private Integer rate;
    private String description;
    private Image image;
    private Timestamp timestamp;

    public Comment() {
    }

    public Comment(Integer id, Integer user_id, Integer restaurant_id, Integer rate, String description, Image image, Timestamp timestamp) {
        this.id = id;
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
