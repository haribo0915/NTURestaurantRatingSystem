package org.river;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.river.controllers.LoginController;
import org.river.controllers.RestaurantListController;
import org.river.entities.Restaurant;
import org.river.models.*;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setUserAdapterFactory(new StubUserAdapterFactory());
            loginController.setRestaurantAdapterFactory(new StubRestaurantAdapterFactory());

            stage.setTitle("NTU Restaurant Rating System");
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}