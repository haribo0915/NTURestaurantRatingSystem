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
 * @author - Haribo
 */
public class EditRestaurantController implements Initializable {
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

    public EditRestaurantController(RestaurantAdapterFactory restaurantAdapterFactory, User currentUser, Restaurant restaurant) {
        this.restaurantAdapterFactory = restaurantAdapterFactory;
        this.restaurantAdapter = restaurantAdapterFactory.create();
        this.restaurant = restaurant;
        this.currentUser = currentUser;
    }

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

            initRestaurantInformation();

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRestaurantInformation() {
        restaurantNameTextField.setText(this.restaurant.getName());
        restaurantAddressTextField.setText(this.restaurant.getAddress());
        restaurantDescriptionTextField.setText(this.restaurant.getDescription());
        initFoodCategoryComboBox();
        initAreaComboBox();
        uploadImagePathTextField.setText(this.restaurant.getImage());
    }

    private void initFoodCategoryComboBox() {
        FoodCategory foodCategory = restaurantAdapter.queryFoodCategory(this.restaurant.getFoodCategoryId());
        foodCategoryComboBox.setValue(foodCategory.getName());
    }

    private void initAreaComboBox() {
        Area area = restaurantAdapter.queryArea(this.restaurant.getAreaId());
        areaComboBox.setValue(area.getName());
    }

    public void uploadImageBtnHandler(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File imageFile = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        uploadImagePathTextField.setText(imageFile.getAbsolutePath());
        restaurant.setImage("file:"+imageFile.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
    }

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

            restaurant = restaurantAdapter.updateRestaurant(restaurant);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
