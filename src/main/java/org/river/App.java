package org.river;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.river.controllers.LoginController;
import org.river.models.JDBCRestaurantAdapterFactory;
import org.river.models.JDBCUserAdapterFactory;

import java.io.IOException;

/**
 * JavaFX App
 *
 * The entry point of NTU Restaurant Rating System
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setUserAdapterFactory(new JDBCUserAdapterFactory());
            loginController.setRestaurantAdapterFactory(new JDBCRestaurantAdapterFactory());

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