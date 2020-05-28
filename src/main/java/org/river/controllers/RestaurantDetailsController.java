package org.river.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.river.entities.Restaurant;
import org.river.entities.UserComment;
import org.river.models.RestaurantAdapter;
import org.river.models.RestaurantAdapterFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author - Haribo
 */
public class RestaurantDetailsController implements Initializable {
    private RestaurantAdapterFactory restaurantAdapterFactory;
    private RestaurantAdapter restaurantAdapter;
    private Restaurant restaurant;

    @FXML
    private Label restaurantNameLabel;
    @FXML
    private Label restaurantAddressLabel;
    @FXML
    private Label restaurantDescriptionLabel;
    @FXML
    private Label restaurantRateLabel;
    @FXML
    private ImageView restaurantImage;

    @FXML
    private TableView<UserComment> userCommentTable;
    @FXML
    private TableColumn<UserComment, String> nameCol;
    @FXML
    private TableColumn<UserComment, String> departmentCol;
    @FXML
    private TableColumn<UserComment, Integer> rateCol;
    @FXML
    private TableColumn<UserComment, String> commentCol;
    @FXML
    private TableColumn<UserComment, Timestamp> dateCol;
    @FXML
    private ObservableList<UserComment> userCommentTableObservableList = FXCollections.observableArrayList();



    public RestaurantDetailsController(RestaurantAdapterFactory restaurantAdapterFactory, Restaurant restaurant) {
        this.restaurantAdapterFactory = restaurantAdapterFactory;
        this.restaurantAdapter = restaurantAdapterFactory.create();
        this.restaurant = restaurant;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image mockImage = new Image("file:src/main/resources/images/fire.png");
            Timestamp mockTimestamp = new Timestamp(System.currentTimeMillis());
            UserComment mockUserComment = new UserComment(1, "mock", "mock account", "mock password", "mock email", "mock department", 1, 1, 3, "mock description", mockImage, mockTimestamp);
            this.restaurant = new Restaurant(1, 1, 1, "mock restaurant", "mock description", mockImage, "mock address");
            //List<UserComment> userCommentList = restaurantAdapter.queryUserComments(restaurant);
            List<UserComment> userCommentList = new ArrayList<>();
            userCommentList.add(mockUserComment);
            userCommentTableObservableList.addAll(userCommentList);
            userCommentTable.setItems(userCommentTableObservableList);

            restaurantNameLabel.setText(restaurant.getName());
            restaurantAddressLabel.setText(restaurant.getAddress());
            restaurantDescriptionLabel.setText(restaurant.getDescription());
            restaurantImage.setImage(restaurant.getImage());

            Double restaurantAverageRate = 0.0;
            if (userCommentList.size() != 0) {
                for (UserComment userComment : userCommentList) {
                    restaurantAverageRate += userComment.getRate();
                }
                restaurantAverageRate /= userCommentList.size();
            }
            restaurantRateLabel.setText(String.valueOf(restaurantAverageRate));

        } catch (Exception e) {
            e.printStackTrace();
        }

        initRestaurantTable();
    }

    private void initRestaurantTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        commentCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }
}
