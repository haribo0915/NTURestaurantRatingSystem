package org.river.controllers;

import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.river.entities.User;
import org.river.exceptions.QueryException;
import org.river.models.RestaurantAdapter;
import org.river.models.RestaurantAdapterFactory;
import org.river.models.UserAdapter;
import org.river.models.UserAdapterFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author - Haribo
 */
public class LoginController {
    private UserAdapterFactory userAdapterFactory;
    private RestaurantAdapterFactory restaurantAdapterFactory;

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    public void setUserAdapterFactory(UserAdapterFactory userAdapterFactory) {
        this.userAdapterFactory = userAdapterFactory;
    }

    public void setRestaurantAdapterFactory(RestaurantAdapterFactory restaurantAdapterFactory) {
        this.restaurantAdapterFactory = restaurantAdapterFactory;
    }

    public void loginHandler(ActionEvent event) {

        try {
            UserAdapter userAdapter = userAdapterFactory.create();
            User user = userAdapter.queryUser(userName.getText(), password.getText());
            System.out.println("login success!!");
            loadRestaurantListView(event);
        } catch (QueryException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadRestaurantListView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RestaurantList.fxml"));

            RestaurantListController restaurantListController= new RestaurantListController(restaurantAdapterFactory);
            loader.setController(restaurantListController);

            Parent restaurantListParent = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(restaurantListParent));
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
