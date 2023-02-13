package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.entities.*;
import dk.easv.presentation.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.Serial;
import java.net.URL;
import java.util.*;

public class AppController implements Initializable {
    @FXML
    private BorderPane borderPane;
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
    private VBox menuBarVBox;
    private AppModel model = new AppModel();
    private long timerStartMillis = 0;
    private String timerMsg = "";
    private MenuController menuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/Login.fxml"));
        try {
            GridPane loginGridPane = loader.load();
            LogInController logInController = loader.getController();
            logInController.setAppController(this);
            borderPane.setCenter(loginGridPane);
        } catch (Exception e) {
            System.out.println(e);
        }
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

    public void openMenu() {
        try {
            // Load menu from fxml file.
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/Menu.fxml"));
            borderPane.setLeft(loader.load());
            menuController = loader.getController();
            menuController.setAppController(this);
            openIntroScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private FXMLLoader openCenterScreen(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
        borderPane.setCenter(fxmlLoader.load());
        return fxmlLoader;
    }

    public void openIntroScreen() throws IOException {
        openCenterScreen("/dk/easv/presentation/view/IntroScreen.fxml");
    }
    public FXMLLoader openSearchScreen() throws IOException {
        FXMLLoader fxmlLoader = openCenterScreen("/dk/easv/presentation/view/SearchView.fxml");
        SearchController searchController = fxmlLoader.getController();
        searchController.setAppModel(model);
        return fxmlLoader;
    }

    public void openFavouritesScreen() throws IOException {
        openCenterScreen("/dk/easv/presentation/view/FavouritesView.fxml");
        menuController.setFocusOnFavourites();
    }

    public void openLogInScreen() throws IOException {
        borderPane.setLeft(null);
        FXMLLoader loader = openCenterScreen("/dk/easv/presentation/view/LogIn.fxml");
        LogInController logInController = loader.getController();
        logInController.setAppController(this);
    }
}

