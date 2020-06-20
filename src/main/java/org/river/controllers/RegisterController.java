package org.river.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.river.entities.User;
import org.river.models.RestaurantAdapter;
import org.river.models.RestaurantAdapterFactory;
import org.river.models.UserAdapter;
import org.river.models.UserAdapterFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The register controller is used to handle the register event,
 * help new user to sign up and coordinate with views and models.
 *
 * @author - Haribo
 */
public class RegisterController implements Initializable {
    private RestaurantAdapterFactory restaurantAdapterFactory;
    private RestaurantAdapter restaurantAdapter;
    private UserAdapterFactory userAdapterFactory;
    private UserAdapter userAdapter;

    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField userPasswordTextField;
    @FXML
    private TextField userAccountTextField;
    @FXML
    private TextField userEmailTextField;
    @FXML
    private TextField userDepartmentTextField;


    public RegisterController(RestaurantAdapterFactory restaurantAdapterFactory, UserAdapterFactory userAdapterFactory) {
        this.restaurantAdapterFactory = restaurantAdapterFactory;
        this.restaurantAdapter = restaurantAdapterFactory.create();
        this.userAdapterFactory = userAdapterFactory;
        this.userAdapter = userAdapterFactory.create();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Handle the register event. It will load the travel itinerary list if user
     * sign up successfully; otherwise it will pop up an alert box to warn illegal input format.
     * roleId = 1 is administrator, roleId = 2 is normal user
     * @param event
     */
    public void registerHandler(ActionEvent event) {
        try {
            User user = new User(2, userNameTextField.getText(), userAccountTextField.getText(), userPasswordTextField.getText(),
                    userEmailTextField.getText(), userDepartmentTextField.getText());
            user = userAdapter.createUser(user);
            loadRestaurantListView(event, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
