package org.river.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.river.entities.User;
import org.river.exceptions.ResourceNotFoundException;
import org.river.models.RestaurantAdapterFactory;
import org.river.models.UserAdapter;
import org.river.models.UserAdapterFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * The login controller is used to handle the login event,
 * verify the user and coordinate with views and models.
 *
 * @author - Haribo
 */
public class LoginController {
    private UserAdapterFactory userAdapterFactory;
    private RestaurantAdapterFactory restaurantAdapterFactory;
    private ExecutorService cachedThreadPool = SingletonCachedThreadPool.getInstance();

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

    /**
     * Handle the login event. It will load the travel itinerary list if user
     * login successfully; otherwise it will pop up an alert box to warn illegal user.
     *
     * @param event
     */
    public void loginHandler(ActionEvent event) {
        cachedThreadPool.execute(() -> {
            try {
                UserAdapter userAdapter = userAdapterFactory.create();
                User currentUser = userAdapter.queryUser(userName.getText(), password.getText());
                Platform.runLater(() -> loadRestaurantListView(event, currentUser));
            } catch (ResourceNotFoundException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void loadRestaurantListView(ActionEvent event, User currentUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RestaurantList.fxml"));

            RestaurantListController restaurantListController= new RestaurantListController(restaurantAdapterFactory, currentUser);
            loader.setController(restaurantListController);

            Parent restaurantListParent = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Restaurant List");
            stage.setScene(new Scene(restaurantListParent));
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the register page for new user
     *
     * @param event
     */
    public void registerHandler(ActionEvent event) {
        cachedThreadPool.execute(() -> {
            try {
                Platform.runLater(() -> loadRegisterView(event));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void loadRegisterView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Register.fxml"));

            RegisterController registerController = new RegisterController(restaurantAdapterFactory, userAdapterFactory);
            loader.setController(registerController);

            Parent registerParent = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(registerParent));
            stage.setTitle("Register");
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
