package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.entities.*;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppController implements Initializable {
    @FXML
    private ListView<User> lvUsers;
    @FXML
    private ListView<Movie> lvTopForUser;
    @FXML
    private ListView<Movie> lvTopAvgNotSeen;
    @FXML
    private ListView<UserSimilarity> lvTopSimilarUsers;
    @FXML
    private ListView<TopMovie> lvTopFromSimilar;
    @FXML
    private Button homeButton, searchButton, favouritesButton, accountButton, carouselLeft, carouselRight;
    @FXML
    private ImageView carouselRightView, carouselLeftView;
    @FXML
    private VBox menuBarVBox;

    private Main mainApp;
    private AppModel model;
    private long timerStartMillis = 0;
    private String timerMsg = "";

    private void startTimer(String message){
        timerStartMillis = System.currentTimeMillis();
        timerMsg = message;
    }

    private void stopTimer(){
        System.out.println(timerMsg + " took : " + (System.currentTimeMillis() - timerStartMillis) + "ms");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonIcons();
    }

    private void setButtonIcons(){
        ImageView homeIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/house-bold.png"))));
        homeIconView.setId("homeIconView");
        homeIconView.setFitHeight(50);
        homeIconView.setFitWidth(50);
        homeButton.setText("");
        homeButton.setGraphic(homeIconView);

        ImageView searchIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/magnifying-glass-bold.png"))));
        searchIconView.setId("searchIconView");
        searchIconView.setFitHeight(50);
        searchIconView.setFitWidth(50);
        searchButton.setText("");
        searchButton.setGraphic(searchIconView);

        ImageView favouritesIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/star-bold.png"))));
        favouritesIconView.setId("favouritesIconView");
        favouritesIconView.setFitHeight(50);
        favouritesIconView.setFitWidth(50);
        favouritesButton.setText("");
        favouritesButton.setGraphic(favouritesIconView);

        ImageView accountIconView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/user-circle-bold.png"))));
        accountIconView.setId("accountIconView");
        accountIconView.setFitHeight(50);
        accountIconView.setFitWidth(50);
        accountButton.setText("");
        accountButton.setGraphic(accountIconView);

        /**USE THIS WHEN OPENING THE INTRO SCREEN
         carouselLeftView.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/arrow-left.png"))));
         carouselLeft.setText("");
         carouselLeft.setGraphic(carouselLeftView);

         carouselRightView.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/arrow-right.png"))));
         carouselRight.setText("");
         carouselRight.setGraphic(carouselRightView);
         **/
    }

    public void setModel(AppModel model) {
        this.model = model;
        /*
        lvUsers.setItems(model.getObsUsers());
        lvTopForUser.setItems(model.getObsTopMovieSeen());
        lvTopAvgNotSeen.setItems(model.getObsTopMovieNotSeen());
        lvTopSimilarUsers.setItems(model.getObsSimilarUsers());
        lvTopFromSimilar.setItems(model.getObsTopMoviesSimilarUsers());

        startTimer("Load users");
        model.loadUsers();
        stopTimer();

        lvUsers.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldUser, selectedUser) -> {
                    startTimer("Loading all data for user: " + selectedUser);
                    model.loadData(selectedUser);
                });

        // Select the logged-in user in the listview, automagically trigger the listener above
        lvUsers.getSelectionModel().select(model.getObsLoggedInUser());
         */
    }

    @FXML
    private void handleHomeButton(ActionEvent actionEvent){
    }

    @FXML
    private void handleSearchButton(ActionEvent actionEvent) throws IOException {
        mainApp.openSearchScreen();
    }

    @FXML
    private void handleFavouritesButton(ActionEvent actionEvent) {
    }

    @FXML
    private void handleAccountButton(ActionEvent actionEvent) throws IOException {
        Button b = (Button) actionEvent.getSource();
        Stage thisStage = (Stage) b.getScene().getWindow();
        thisStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/LogIn.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Budgetflix 2.1");
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/budgetflixIcon.png"))));
        stage.centerOnScreen();
        stage.show();
    }

    private void buttonColors(ActionEvent actionEvent){

    }

    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }
}

