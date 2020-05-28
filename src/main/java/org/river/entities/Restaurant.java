package org.river.entities;

import javafx.scene.image.Image;

/**
 * @author - Haribo
 */
public class Restaurant {
    private Integer id;
    private Integer area_id;
    private Integer foodCategory_id;
    private String name;
    private String description;
    private Image image;
    private String address;

    public Restaurant() {
    }

    public Restaurant(Integer id, Integer area_id, Integer foodCategory_id, String name, String description, Image image, String address) {
        this.id = id;
        this.area_id = area_id;
        this.foodCategory_id = foodCategory_id;
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

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }

    public Integer getFoodCategory_id() {
        return foodCategory_id;
    }

    public void setFoodCategory_id(Integer foodCategory_id) {
        this.foodCategory_id = foodCategory_id;
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
