package org.river.entities;

import javafx.scene.image.Image;

/**
 * @author - Haribo
 */
public class Restaurant {
    private Integer id;
    private Integer areaId;
    private Integer foodCategoryId;
    private String name;
    private String description;
    private Image image;
    private String address;

    public Restaurant() {
    }

    public Restaurant(Integer id, Integer areaId, Integer foodCategoryId, String name, String description, Image image, String address) {
        this.id = id;
        this.areaId = areaId;
        this.foodCategoryId = foodCategoryId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(Integer foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
