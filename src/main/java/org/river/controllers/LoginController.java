package org.river.controllers;

import javafx.event.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;
/**
 * @author - Haribo
 */
public class LoginController {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;


    public void handleLogin(ActionEvent event) {
        if (userName.getText().equals("users") && password.getText().equals("pass")) {
            System.out.println("Login success !!");
        } else {
            System.out.println("Login failed !!");
        }
    }
}
