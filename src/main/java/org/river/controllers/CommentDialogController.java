package org.river.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.river.entities.Comment;
import org.river.entities.User;
import org.river.exceptions.ResourceNotFoundException;
import org.river.models.RestaurantAdapter;
import org.river.models.RestaurantAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * @author - Haribo
 */
public class CommentDialogController implements Initializable {
    private Integer restaurantId;
    private User currentUser;
    private RestaurantAdapterFactory restaurantAdapterFactory;
    private RestaurantAdapter restaurantAdapter;
    private Comment comment;

    @FXML
    private TextField rateTextField;
    @FXML
    private TextField commentTextField;
    @FXML
    private TextField uploadImagePathTextField;

    public CommentDialogController(RestaurantAdapterFactory restaurantAdapterFactory, User currentUser, Integer restaurantId) {
        this.restaurantAdapterFactory = restaurantAdapterFactory;
        this.restaurantAdapter = restaurantAdapterFactory.create();
        this.currentUser = currentUser;
        this.restaurantId = restaurantId;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            comment = restaurantAdapter.queryComment(currentUser.getId(), restaurantId);
            rateTextField.setText(String.valueOf(comment.getRate()));
            commentTextField.setText(comment.getDescription());
            uploadImagePathTextField.setText(comment.getImage());
        } catch (ResourceNotFoundException e) {
            comment = new Comment();
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadImageBtnHandler(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File imageFile = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        uploadImagePathTextField.setText(imageFile.getAbsolutePath());
        comment.setImage(imageFile.getAbsolutePath());
    }

    public void saveCommentHandler(ActionEvent event) {
        try {
            Integer rate = Integer.valueOf(rateTextField.getText());
            String description = commentTextField.getText();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            setComment(currentUser.getId(), restaurantId, rate, description, comment.getImage(), new Timestamp(System.currentTimeMillis()));

            if (comment.getId() == null) {
                comment = restaurantAdapter.createComment(comment);
            } else {
                comment = restaurantAdapter.updateComment(comment);
            }
            
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setComment(Integer userId, Integer restaurantId, Integer rate, String description, String image, Timestamp timestamp) {
        comment.setUserId(userId);
        comment.setRestaurantId(restaurantId);
        comment.setRate(rate);
        comment.setDescription(description);
        comment.setImage(image);
        comment.setTimestamp(timestamp);
    }
}
