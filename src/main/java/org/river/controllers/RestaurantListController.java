package org.river.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<Restaurant, String> foodCategoryCol;
    @FXML
    private TableColumn<Restaurant, String> areaCol;
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
                restaurantObservableList.add(new Restaurant(rs.getString("name"), rs.getString("food_category"),
                        rs.getString("address"), rs.getString("area")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodCategoryCol.setCellValueFactory(new PropertyValueFactory<>("foodCategory"));
        areaCol.setCellValueFactory(new PropertyValueFactory<>("area"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void refreshTable() {
        initTable();
        query = "SELECT a.firstname, a.lastname, a.gender, p.position, a.account_ID FROM account as a " +
                "JOIN positions as p ON a.position_ID=p.position_ID " +
                "ORDER BY a.firstname";
        tblview.setItems(dao.getAccountsData(query));

    }
}
