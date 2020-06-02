package org.river.controllers;

import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.river.entities.User;
import org.river.exceptions.QueryException;
import org.river.models.RestaurantAdapterFactory;
import org.river.models.UserAdapter;
import org.river.models.UserAdapterFactory;

import java.io.IOException;

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

    public LoginController() {
    }

    public void setUserAdapterFactory(UserAdapterFactory userAdapterFactory) {
        this.userAdapterFactory = userAdapterFactory;
    }

    public void setRestaurantAdapterFactory(RestaurantAdapterFactory restaurantAdapterFactory) {
        this.restaurantAdapterFactory = restaurantAdapterFactory;
    }

    public void loginHandler(ActionEvent event) {

        try {
            UserAdapter userAdapter = userAdapterFactory.create();
            User currentUser = userAdapter.queryUser(userName.getText(), password.getText());
            System.out.println("login success!!");
            loadRestaurantListView(event, currentUser);
        } catch (QueryException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadRestaurantListView(ActionEvent event, User currentUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RestaurantList.fxml"));

            RestaurantListController restaurantListController= new RestaurantListController(restaurantAdapterFactory, currentUser);
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

    public void registerHandler(ActionEvent event) {
        try {
            loadRegisterView(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRegisterView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Register.fxml"));

            RegisterController registerController = new RegisterController(userAdapterFactory);
            loader.setController(registerController);

            Parent registerParent = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(registerParent));
            stage.setTitle("Register");
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
