package org.river.entities;

import javafx.scene.image.Image;

/**
 * @author - Haribo
 */
public class Restaurant {
    private Integer id;
    private String name;
    private String description;
    private FoodCategory foodCategory;
    private Image image;
    private String address;
    private Area area;

    public Restaurant() {
    }

    public Restaurant(String name, FoodCategory foodCategory, String address, Area area) {
        this.name = name;
        this.foodCategory = foodCategory;
        this.address = address;
        this.area = area;
    }

    public Restaurant(Integer id, String name, String description, FoodCategory foodCategory, Image image, String address, Area area) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.foodCategory = foodCategory;
        this.image = image;
        this.address = address;
        this.area = area;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public FoodCategory getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
