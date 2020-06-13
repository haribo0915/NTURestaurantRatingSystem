package org.river.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.river.entities.User;
import org.river.entities.UserComment;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author - Haribo
 */
public class UserCommentDetailsController implements Initializable {
    private UserComment userComment;
    private User currentUser;

    @FXML
    private Label userNameLabel;
    @FXML
    private Label userDepartmentLabel;
    @FXML
    private Label userRateLabel;
    @FXML
    private Label userCommentLabel;
    @FXML
    private Label userCommentedTimeLabel;
    @FXML
    private ImageView userUploadImage;

    public UserCommentDetailsController(UserComment userComment, User currentUser) {
        this.userComment = userComment;
        this.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameLabel.setText(userComment.getName());
        userDepartmentLabel.setText(userComment.getDepartment());
        userRateLabel.setText(String.valueOf(userComment.getRate()));
        userCommentLabel.setText(userComment.getDescription());
        userCommentedTimeLabel.setText(String.valueOf(userComment.getTimestamp()));
        userUploadImage.setImage(new Image(userComment.getImage(), 100, 150, true, true));
    }
}
