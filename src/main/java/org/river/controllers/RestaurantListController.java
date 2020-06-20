package org.river.controllers;

import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.river.entities.Area;
import org.river.entities.FoodCategory;
import org.river.entities.Restaurant;
import org.river.entities.User;
import org.river.exceptions.ResourceNotFoundException;
import org.river.models.RestaurantAdapter;
import org.river.models.RestaurantAdapterFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

/**
 * The restaurant list controller is used to select favorite restaurant with several
 * parameter specified by user, create new restaurant information, modify restaurant information,
 * and delete restaurant.
 *
 * @author - Haribo
 */
public class RestaurantListController implements Initializable {
    private RestaurantAdapterFactory restaurantAdapterFactory;
    private RestaurantAdapter restaurantAdapter;
    private User currentUser;
    private ExecutorService cachedThreadPool = SingletonCachedThreadPool.getInstance();

    @FXML
    private ComboBox<String> foodCategoryComboBox;
    @FXML
    private ComboBox<String> areaComboBox;
    @FXML
    private ComboBox<String> restaurantNameComboBox;

    @FXML
    private Button querySelectedRestaurantBtn;
    @FXML
    private Button createRestaurantBtn;
    @FXML
    private Button editRestaurantBtn;
    @FXML
    private Button deleteRestaurantBtn;

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

    /**
     * Initialize restaurant table view and combo box.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Restaurant> restaurantList = restaurantAdapter.queryRestaurants(null, null, null);
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
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            initRestaurantTable();
            querySelectedRestaurantBtn.setDisable(true);
            //if current isn't administrator, all the modifying operations on restaurant won't allow
            editRestaurantBtn.setDisable(true);
            deleteRestaurantBtn.setDisable(true);
            if (currentUser.getRoleId() != 1) {
                createRestaurantBtn.setDisable(true);
            }
        }

    }

    private void initRestaurantTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    /**
     * Enable restaurant details button if user select a restaurant.
     *
     * @param event
     */
    public void userClickedOnRestaurantTable(MouseEvent event) {
        querySelectedRestaurantBtn.setDisable(false);
        //The modifying button will open if current is administrator and he've selected a restaurant
        if (currentUser.getRoleId() == 1) {
            editRestaurantBtn.setDisable(false);
            deleteRestaurantBtn.setDisable(false);
        }
    }

    private void refreshRestaurantTable(List<Restaurant> restaurantList) {
        restaurantTableObservableList.clear();
        restaurantTableObservableList.addAll(restaurantList);
        restaurantTable.setItems(restaurantTableObservableList);
    }

    /**
     * Handle the query restaurant event. It will query restaurant by specific
     * restaurant name, area name or food category name.
     *
     * @param event
     */
    public void queryRestaurantsHandler(ActionEvent event) {
        cachedThreadPool.execute(() -> {
            List<Restaurant> restaurantList = new ArrayList<>();
            try {
                String restaurantName = restaurantNameComboBox.getValue();
                String areaName = areaComboBox.getValue();
                String foodCategoryName = foodCategoryComboBox.getValue();
                restaurantName = (restaurantName == null || restaurantName.equals(""))? null : restaurantName;
                Area area = (areaName == null || areaName.equals(""))? null : restaurantAdapter.queryArea(areaName);
                FoodCategory foodCategory = (foodCategoryName == null || foodCategoryName.equals(""))? null : restaurantAdapter.queryFoodCategory(foodCategoryName);
                restaurantList = restaurantAdapter.queryRestaurants(restaurantName, area, foodCategory);
            } catch (ResourceNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                refreshRestaurantTable(restaurantList);
            }
        });
    }

    /**
     * Handle the query hottest restaurant event.
     * It will query the weekly hottest restaurants.
     *
     * @param event
     */
    public void queryHottestRestaurantHandler(ActionEvent event) {
        cachedThreadPool.execute(() -> {
            List<Restaurant> restaurantList = new ArrayList<>();
            try {
                restaurantList = restaurantAdapter.queryWeeklyHottestRestaurants();
            } catch (ResourceNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                refreshRestaurantTable(restaurantList);
            }
        });
    }

    /**
     * Handle the query selected restaurant event.
     * It will query the selected restaurant.
     *
     * @param event
     */
    public void querySelectedRestaurantHandler(ActionEvent event) {
        cachedThreadPool.execute(() -> {
            try {
                Restaurant selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
                //Use Platform.runLater() to do UI stuff from outside the FXThread
                Platform.runLater(() -> loadRestaurantDetailsView(event, selectedRestaurant));
            } catch (ResourceNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void loadRestaurantDetailsView(ActionEvent event, Restaurant selectedRestaurant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RestaurantDetails.fxml"));

            RestaurantDetailsController restaurantDetailsController = new RestaurantDetailsController(restaurantAdapterFactory, selectedRestaurant, this.currentUser);
            loader.setController(restaurantDetailsController);

            Parent restaurantDetailsParent = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Restaurant Details");
            stage.setScene(new Scene(restaurantDetailsParent));
            stage.sizeToScene();
            stage.show();
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the create restaurant event. It will create new restaurant information.
     *
     * @param event
     */
    public void createRestaurantHandler(ActionEvent event) {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            loadCreateRestaurantView(event);
            restaurantList = restaurantAdapter.queryRestaurants(null, null, null);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            refreshRestaurantTable(restaurantList);
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

    /**
     * Handle the edit restaurant event. It will modify restaurant information.
     *
     * @param event
     */
    public void editRestaurantHandler(ActionEvent event) {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            Restaurant selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
            loadEditRestaurantView(event, selectedRestaurant);
            restaurantList = restaurantAdapter.queryRestaurants(null, null, null);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            refreshRestaurantTable(restaurantList);
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

    /**
     * Handle the delete restaurant event. It will delete selected restaurant information.
     *
     * @param event
     */
    public void deleteRestaurantHandler(ActionEvent event) {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            Restaurant selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
            restaurantAdapter.deleteRestaurant(selectedRestaurant);
            restaurantList = restaurantAdapter.queryRestaurants(null, null, null);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            refreshRestaurantTable(restaurantList);
        }
    }

}
