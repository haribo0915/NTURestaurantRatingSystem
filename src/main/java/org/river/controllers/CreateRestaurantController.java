package org.river.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.river.entities.Area;
import org.river.entities.FoodCategory;
import org.river.entities.Restaurant;
import org.river.entities.User;
import org.river.exceptions.ResourceNotFoundException;
import org.river.models.RestaurantAdapter;
import org.river.models.RestaurantAdapterFactory;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The create restaurant controller is used to create a new restaurant information.
 *
 * @author - Haribo
 */
public class CreateRestaurantController implements Initializable {
    private RestaurantAdapterFactory restaurantAdapterFactory;
    private RestaurantAdapter restaurantAdapter;
    private Restaurant restaurant;
    private User currentUser;

    @FXML
    private TextField restaurantNameTextField;
    @FXML
    private TextField restaurantAddressTextField;
    @FXML
    private TextField restaurantDescriptionTextField;
    @FXML
    private TextField uploadImagePathTextField;
    @FXML
    private ComboBox<String> foodCategoryComboBox;
    @FXML
    private ComboBox<String> areaComboBox;

    @FXML
    private ObservableList<String> foodCategoryComboboxObservableList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> areaComboBoxObservableList = FXCollections.observableArrayList();

    public CreateRestaurantController(RestaurantAdapterFactory restaurantAdapterFactory, User currentUser) {
        this.restaurantAdapterFactory = restaurantAdapterFactory;
        this.restaurantAdapter = restaurantAdapterFactory.create();
        this.currentUser = currentUser;
        this.restaurant = new Restaurant();
    }

    /**
     * Initialize food category combo box, area combo box for administrator to choose.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<FoodCategory> foodCategoryList = restaurantAdapter.queryFoodCategories();
            for (FoodCategory foodCategory: foodCategoryList) {
                foodCategoryComboboxObservableList.add(foodCategory.getName());
            }
            foodCategoryComboBox.setItems(foodCategoryComboboxObservableList);

            List<Area> areaList = restaurantAdapter.queryAreas();
            for (Area area: areaList) {
                areaComboBoxObservableList.add(area.getName());
            }
            areaComboBox.setItems(areaComboBoxObservableList);

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadImageBtnHandler(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File imageFile = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        uploadImagePathTextField.setText(imageFile.getAbsolutePath());
        restaurant.setImage("file:"+imageFile.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
    }

    /**
     * Handle the save restaurant event. It will check the user input first
     * and convert illegal input like null or empty string to legal one.
     *
     * @param event
     */
    @SuppressWarnings("DuplicatedCode")
    public void saveRestaurantHandler(ActionEvent event) {
        try {
            restaurant.setName(restaurantNameTextField.getText());
            restaurant.setAddress(restaurantAddressTextField.getText());
            restaurant.setDescription(restaurantDescriptionTextField.getText());
            FoodCategory foodCategory = restaurantAdapter.queryFoodCategory(foodCategoryComboBox.getValue());
            restaurant.setFoodCategoryId(foodCategory.getId());
            Area area = restaurantAdapter.queryArea(areaComboBox.getValue());
            restaurant.setAreaId(area.getId());
            String imageFilePath = (restaurant.getImage() == null)? "" : restaurant.getImage();
            restaurant.setImage(imageFilePath);

            restaurant = restaurantAdapter.createRestaurant(restaurant);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
