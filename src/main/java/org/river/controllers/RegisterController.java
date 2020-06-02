package org.river.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.river.entities.User;
import org.river.models.UserAdapter;
import org.river.models.UserAdapterFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author - Haribo
 */
public class RegisterController implements Initializable {
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


    public RegisterController(UserAdapterFactory userAdapterFactory) {
        this.userAdapterFactory = userAdapterFactory;
        this.userAdapter = userAdapterFactory.create();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //roleId = 1 is administrator, roleId = 2 is normal user
    public void registerHandler(ActionEvent event) {
        try {
            User user = new User(2, userNameTextField.getText(), userAccountTextField.getText(), userPasswordTextField.getText(),
                    userEmailTextField.getText(), userDepartmentTextField.getText());
            user = userAdapter.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
