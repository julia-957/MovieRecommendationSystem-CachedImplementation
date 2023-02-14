package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.entities.*;
import dk.easv.presentation.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    private VBox menuBarVBox;
    private AppModel model = new AppModel();
    private long timerStartMillis = 0;
    private String timerMsg = "";
    private MenuController menuController;
    private SearchController searchController = new SearchController();
    private IntroScreenController introScreenController = new IntroScreenController();
    private FavouritesController favouritesController = new FavouritesController();
    private LogInController logInController = new LogInController();
    private FXMLLoader searchFXMLLoader, introFXMLLoader, favouritesFXMLLoader, logInFXMLLoader;
    private Node searchScene, introScene, favouritesScene, logInScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadScenes();
            openLogInScreen();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void loadScenes() throws IOException {
        searchFXMLLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/SearchView.fxml"));
        searchFXMLLoader.setController(searchController);
        searchController.setAppController(this);
        searchController.setAppModel(model);
        searchScene = searchFXMLLoader.load();

        introFXMLLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/IntroScreen.fxml"));
        introFXMLLoader.setController(introScreenController);
        introScreenController.setModel(model);
        introScene = introFXMLLoader.load();

        favouritesFXMLLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/FavouritesView.fxml"));
        favouritesFXMLLoader.setController(favouritesController);
        favouritesController.setModel(model);
        favouritesScene = favouritesFXMLLoader.load();

        logInFXMLLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/LogIn.fxml"));
        logInFXMLLoader.setController(logInController);
        logInController.setAppController(this);
        logInController.setModel(model);
        logInScene = logInFXMLLoader.load();
    }

    public void openMenu() {
        try {
            // Load menu from fxml file.
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/Menu.fxml"));
            borderPane.setLeft(loader.load());
            menuController = loader.getController();
            menuController.setAppController(this);
            menuController.setModel(model);
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
        borderPane.setCenter(introScene);
    }
    public void openSearchScreen() throws IOException {
        borderPane.setCenter(searchScene);
    }

    public void openFavouritesScreen() throws IOException {
        borderPane.setCenter(favouritesScene);
        menuController.setFocusOnFavourites();
    }

    public void openLogInScreen() throws IOException {
        borderPane.setLeft(null);
        borderPane.setCenter(logInScene);
    }
}

