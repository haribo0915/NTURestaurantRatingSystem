package org.river.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.river.entities.Comment;
import org.river.entities.User;
import org.river.exceptions.ResourceNotFoundException;
import org.river.models.RestaurantAdapter;
import org.river.models.RestaurantAdapterFactory;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * The comment dialog controller is used to create or modify the comment
 * of specific user.
 *
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

    /**
     * It will load the old comment if the user has commented the restaurant before;
     * otherwise it will create a new comment form for the user.
     *
     * @param url
     * @param resourceBundle
     */
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
        comment.setImage("file:"+imageFile.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"));
    }

    /**
     * Handle the save comment event. It will check the user input first
     * and convert illegal input like null or empty string to legal one.
     *
     * @param event
     */
    public void saveCommentHandler(ActionEvent event) {
        try {
            Integer rate = Integer.valueOf(rateTextField.getText());
            String description = commentTextField.getText();
            String imageFilePath = (comment.getImage() == null)? "" : comment.getImage();
            setComment(currentUser.getId(), restaurantId, rate, description, imageFilePath, new Timestamp(System.currentTimeMillis()));
            comment = (comment.getId() == null)? restaurantAdapter.createComment(comment) : restaurantAdapter.updateComment(comment);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
