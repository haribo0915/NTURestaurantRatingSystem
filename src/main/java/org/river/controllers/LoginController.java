package org.river.controllers;

import javafx.event.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import org.river.entities.User;
import org.river.models.UserAdapter;
/**
 * @author - Haribo
 */
public class LoginController {
    private UserAdapter;

    public LoginController(UserAdapter userAdapter)

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;


    public void loginHandler(ActionEvent event) {

        try {
            User user = UserAdapter.queryUser(userName.getText()m)
        }
        if (userName.getText().equals("users") && password.getText().equals("pass")) {
            System.out.println("Login success !!");
        } else {
            System.out.println("Login failed !!");
        }
    }
}
