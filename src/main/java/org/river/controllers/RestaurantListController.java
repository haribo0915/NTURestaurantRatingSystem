package org.river.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import org.river.entities.Area;
import org.river.entities.FoodCategory;
import org.river.entities.Restaurant;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author - Haribo
 */
public class RestaurantListController implements Initializable {

    @FXML
    private TableView<Restaurant> restaurantTable;
    @FXML
    private TableColumn<Restaurant, String> nameCol;
    @FXML
    private TableColumn<Restaurant, Integer> foodCategoryCol;
    @FXML
    private TableColumn<Restaurant, Integer> areaCol;
    @FXML
    private TableColumn<Restaurant, String> addressCol;
    @FXML
    private ObservableList<Restaurant> restaurantObservableList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection conn = DBConnector.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select  * from restaurant");

            while(rs.next()) {
                Restaurant restaurant = new Restaurant(rs.getInt("id"), rs.getInt("food_category_id"), rs.getInt("area_id"),
                rs.getString("name"), rs.getString("description"), (Image) rs.getBlob("image"), rs.getString("address"));
                restaurantObservableList.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initTable();
        restaurantTable.setItems(restaurantObservableList);

    }
    private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodCategoryCol.setCellValueFactory(new PropertyValueFactory<>("foodCategoryId"));
        areaCol.setCellValueFactory(new PropertyValueFactory<>("areaId"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }
}
