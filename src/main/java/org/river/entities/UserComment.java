package org.river.entities;

import javafx.scene.image.Image;
import java.sql.Timestamp;

/**
 * @author - Haribo
 */
public class UserComment {
    private Integer roleId;
    private String name;
    private String account;
    private String password;
    private String email;
    private String department;

    private Integer userId;
    private Integer restaurantId;
    private Integer rate;
    private String description;
    private String image;
    private Timestamp timestamp;

    public UserComment() {
    }

    public UserComment(Integer roleId, String name, String account, String password, String email, String department, Integer userId, Integer restaurantId, Integer rate, String description, String image, Timestamp timestamp) {
        this.roleId = roleId;
        this.name = name;
        this.account = account;
        this.password = password;
        this.email = email;
        this.department = department;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rate = rate;
        this.description = description;
        this.image = image;
        this.timestamp = timestamp;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
