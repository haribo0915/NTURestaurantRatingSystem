package org.river.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.river.entities.Comment;
import org.river.entities.Restaurant;
import org.river.entities.User;
import org.river.entities.UserComment;
import org.river.exceptions.ResourceNotFoundException;
import org.river.models.RestaurantAdapter;
import org.river.models.RestaurantAdapterFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

/**
 * The restaurant details controller is used to show all the user comments of
 * the restaurant, create a new comment for a user and sanitize comment with
 * foul language.
 *
 * @author - Haribo
 */
public class RestaurantDetailsController implements Initializable {
    private RestaurantAdapterFactory restaurantAdapterFactory;
    private RestaurantAdapter restaurantAdapter;
    private Restaurant restaurant;
    private User currentUser;
    private Sanitizer sanitizer;
    private ExecutorService cachedThreadPool = SingletonCachedThreadPool.getInstance();

    @FXML
    private Label restaurantNameLabel;
    @FXML
    private Label restaurantAddressLabel;
    @FXML
    private Label restaurantDescriptionLabel;
    @FXML
    private Label restaurantRateLabel;
    @FXML
    private ImageView restaurantImage;

    @FXML
    private Button querySelectedUserCommentBtn;

    @FXML
    private TableView<UserComment> userCommentTable;
    @FXML
    private TableColumn<UserComment, String> nameCol;
    @FXML
    private TableColumn<UserComment, String> departmentCol;
    @FXML
    private TableColumn<UserComment, Integer> rateCol;
    @FXML
    private TableColumn<UserComment, String> commentCol;
    @FXML
    private TableColumn<UserComment, Timestamp> dateCol;
    @FXML
    private ObservableList<UserComment> userCommentTableObservableList = FXCollections.observableArrayList();



    public RestaurantDetailsController(RestaurantAdapterFactory restaurantAdapterFactory, Restaurant restaurant, User currentUser) {
        this.restaurantAdapterFactory = restaurantAdapterFactory;
        this.restaurantAdapter = restaurantAdapterFactory.create();
        this.restaurant = restaurant;
        this.currentUser = currentUser;
        this.sanitizer = new FoulLanguageMapSanitizer();
    }

    /**
     * Initialize the comment table view.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            sanitizeCommentWithFoulLanguage();

            restaurantNameLabel.setText(restaurant.getName());
            restaurantAddressLabel.setText(restaurant.getAddress());
            restaurantDescriptionLabel.setText(restaurant.getDescription());
            restaurantImage.setImage(new Image(restaurant.getImage(), 100, 150, true, true));
            List<UserComment> userCommentList = restaurantAdapter.queryUserComments(restaurant);
            refreshRestaurantRate(userCommentList);

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            initUserCommentsTable();
            querySelectedUserCommentBtn.setDisable(true);
        }
    }

    private void sanitizeCommentWithFoulLanguage() {
        List<Comment> commentList = restaurantAdapter.queryComments(restaurant);
        List<Comment> commentWithFoulLanguageList = sanitizer.sanitize(commentList);
        for (Comment comment: commentWithFoulLanguageList) {
            restaurantAdapter.deleteComment(comment);
        }
    }

    public void userClickedOnUserCommentTable(Event event) {
        querySelectedUserCommentBtn.setDisable(false);
    }

    private void initUserCommentsTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        commentCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }

    private void refreshUserCommentTable(List<UserComment> userCommentList) {
        userCommentTableObservableList.clear();
        userCommentTableObservableList.addAll(userCommentList);
        userCommentTable.setItems(userCommentTableObservableList);
    }

    /**
     * Calculate the current rate of the restaurant with specific user comment list.
     *
     * @param userCommentList
     */
    private void refreshRestaurantRate(List<UserComment> userCommentList) {
        userCommentTableObservableList.clear();
        userCommentTableObservableList.addAll(userCommentList);
        userCommentTable.setItems(userCommentTableObservableList);
        Double restaurantAverageRate = 0.0;
        if (userCommentList.size() != 0) {
            for (UserComment userComment : userCommentList) {
                restaurantAverageRate += userComment.getRate();
            }
            restaurantAverageRate /= userCommentList.size();
        }
        restaurantRateLabel.setText(String.format("%.1f", restaurantAverageRate));
    }

    /**
     * Handle the query selected user comment event. It will show the
     * comment details of the specific user.
     *
     * @param event
     */
    public void querySelectedUserCommentHandler(ActionEvent event) {
        try {
            UserComment selectedUserComment = userCommentTable.getSelectionModel().getSelectedItem();
            loadUserCommentDetailsView(event, selectedUserComment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUserCommentDetailsView(ActionEvent event, UserComment selectedUserComment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserCommentDetails.fxml"));

            UserCommentDetailsController userCommentDetailsController = new UserCommentDetailsController(selectedUserComment, this.currentUser);
            loader.setController(userCommentDetailsController);

            Parent UserCommentDetailsParent = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("User Comment Details");
            stage.setScene(new Scene(UserCommentDetailsParent));
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the create comment event. It will create new comment if the
     * description of the comment doesn't contain foul language.
     *
     * @param event
     */
    public void createCommentHandler(ActionEvent event) {
        List<UserComment> userCommentList = new ArrayList<>();

        try {
            loadCommentDialogView(event);
            //refresh user comment table
            sanitizeCommentWithFoulLanguage();
            userCommentList = restaurantAdapter.queryUserComments(this.restaurant);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            refreshUserCommentTable(userCommentList);
            refreshRestaurantRate(userCommentList);
        }
    }

    private void loadCommentDialogView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CommentDialog.fxml"));

            CommentDialogController commentDialogController = new CommentDialogController(restaurantAdapterFactory, this.currentUser, restaurant.getId());
            loader.setController(commentDialogController);

            Parent commentDialogParent = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modify User Comment");
            stage.setScene(new Scene(commentDialogParent));
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go back to restaurant list.
     *
     * @param event
     */
    public void goBackHandler(ActionEvent event) {
        try {
            loadRestaurantListView(event, currentUser);
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
