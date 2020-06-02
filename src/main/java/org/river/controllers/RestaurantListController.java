package org.river.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.river.entities.Area;
import org.river.entities.FoodCategory;
import org.river.entities.Restaurant;
import org.river.entities.User;
import org.river.models.RestaurantAdapter;
import org.river.models.RestaurantAdapterFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author - Haribo
 */
public class RestaurantListController implements Initializable {
    private RestaurantAdapterFactory restaurantAdapterFactory;
    private RestaurantAdapter restaurantAdapter;
    private User currentUser;

    @FXML
    private ComboBox<String> foodCategoryComboBox;
    @FXML
    private ComboBox<String> areaComboBox;
    @FXML
    private ComboBox<String> restaurantNameComboBox;

    @FXML
    private Button querySelectedRestaurantBtn;

    @FXML
    private TableView<Restaurant> restaurantTable;
    @FXML
    private TableColumn<Restaurant, String> nameCol;
    @FXML
    private TableColumn<Restaurant, String> addressCol;

    @FXML
    private ObservableList<Restaurant> restaurantTableObservableList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> foodCategoryComboboxObservableList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> areaComboBoxObservableList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> restaurantNameComboboxObservableList = FXCollections.observableArrayList();

    public RestaurantListController(RestaurantAdapterFactory RestaurantAdapterFactory, User currentUser) {
        this.restaurantAdapterFactory = RestaurantAdapterFactory;
        this.restaurantAdapter = restaurantAdapterFactory.create();
        this.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Restaurant> restaurantList = restaurantAdapter.queryRestaurants();
            //TODO if the return list is null, JDBCRestaurantAdapter needs to throw Resource not found exception, or it'll throw nullPointerException
            restaurantTableObservableList.addAll(restaurantList);
            restaurantTable.setItems(restaurantTableObservableList);

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

            for (Restaurant restaurant: restaurantList) {
                restaurantNameComboboxObservableList.add(restaurant.getName());
            }
            restaurantNameComboBox.setItems(restaurantNameComboboxObservableList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initRestaurantTable();
        //querySelectedRestaurantBtn.setDisable(true);

    }

    private void initRestaurantTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void refreshRestaurantTable(List<Restaurant> restaurantList) {
        restaurantTableObservableList.clear();
        restaurantTableObservableList.addAll(restaurantList);
        restaurantTable.setItems(restaurantTableObservableList);
    }

    public void queryRestaurantByFoodCategoryHandler(ActionEvent event) {
        try {
            FoodCategory foodCategory = restaurantAdapter.queryFoodCategory(foodCategoryComboBox.getValue());
            List<Restaurant> restaurantList = restaurantAdapter.queryRestaurants(foodCategory);
            //refresh restaurant table
            refreshRestaurantTable(restaurantList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryRestaurantByAreaHandler(ActionEvent event) {
        try {
            Area area = restaurantAdapter.queryArea(areaComboBox.getValue());
            List<Restaurant> restaurantList = restaurantAdapter.queryRestaurants(area);
            //refresh restaurant table
            refreshRestaurantTable(restaurantList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryRestaurantByNameHandler(ActionEvent event) {
        try {
            List<Restaurant> restaurantList = new ArrayList<>();
            Restaurant restaurant = restaurantAdapter.queryRestaurant(restaurantNameComboBox.getValue());
            restaurantList.add(restaurant);
            //refresh restaurant table
            refreshRestaurantTable(restaurantList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryHottestRestaurantHandler(ActionEvent event) {
        try {
            List<Restaurant> restaurantList = restaurantAdapter.queryWeeklyHottestRestaurants();
            //refresh restaurant table
            refreshRestaurantTable(restaurantList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userClickedOnRestaurantTable(ActionEvent event) {
        querySelectedRestaurantBtn.setDisable(false);
    }

    public void querySelectedRestaurantHandler(ActionEvent event) {
        try {
            Restaurant selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
            loadRestaurantDetailsView(event, selectedRestaurant);
            //refresh restaurant table
            List<Restaurant> restaurantList = restaurantAdapter.queryRestaurants();
            refreshRestaurantTable(restaurantList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRestaurantDetailsView(ActionEvent event, Restaurant selectedRestaurant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RestaurantDetails.fxml"));

            RestaurantDetailsController restaurantDetailsController = new RestaurantDetailsController(restaurantAdapterFactory, selectedRestaurant, this.currentUser);
            loader.setController(restaurantDetailsController);

            Parent restaurantDetailsParent = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(restaurantDetailsParent));
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createRestaurantHandler(ActionEvent event) {
        try {
            loadCreateRestaurantView(event);
            //refresh restaurant table
            List<Restaurant> restaurantList = restaurantAdapter.queryRestaurants();
            refreshRestaurantTable(restaurantList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCreateRestaurantView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifyRestaurant.fxml"));

            CreateRestaurantController modifyRestaurantController = new CreateRestaurantController(restaurantAdapterFactory, this.currentUser);
            loader.setController(modifyRestaurantController);

            Parent modifyRestaurantParent = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create Restaurant");
            stage.setScene(new Scene(modifyRestaurantParent));
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editRestaurantHandler(ActionEvent event) {
        try {
            Restaurant selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
            loadEditRestaurantView(event, selectedRestaurant);
            //refresh restaurant table
            List<Restaurant> restaurantList = restaurantAdapter.queryRestaurants();
            refreshRestaurantTable(restaurantList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEditRestaurantView(ActionEvent event, Restaurant selectedRestaurant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifyRestaurant.fxml"));

            EditRestaurantController editRestaurantController = new EditRestaurantController(restaurantAdapterFactory, this.currentUser, selectedRestaurant);
            loader.setController(editRestaurantController);

            Parent modifyRestaurantParent = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Restaurant");
            stage.setScene(new Scene(modifyRestaurantParent));
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRestaurantHandler(ActionEvent event) {
        try {
            Restaurant selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
            selectedRestaurant = restaurantAdapter.deleteRestaurant(selectedRestaurant);
            //refresh restaurant table
            List<Restaurant> restaurantList = restaurantAdapter.queryRestaurants();
            refreshRestaurantTable(restaurantList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
