package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.presentation.controller.menuControllers.*;
import dk.easv.presentation.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppController implements Initializable {
    @FXML private BorderPane borderPane;
    @FXML private VBox menuBarVBox;
    private MenuController menuController;
    private final AppModel model = AppModel.getInstance();
    private final IntroScreenController introScreenController = new IntroScreenController();
    private final SearchController searchController = new SearchController();
    private final FavouritesController favouritesController = new FavouritesController();
    private final LogInController logInController = new LogInController();
    private Node introScene, searchScene, favouritesScene, logInScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadScenes();
            openLogInScreen();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void loadScenes() throws IOException {
        FXMLLoader logInFXMLLoader = new FXMLLoader(Main.class.getResource("/views/menuViews/LogIn.fxml"));
        logInFXMLLoader.setController(logInController);
        logInController.setAppController(this);
        logInScene = logInFXMLLoader.load();

        FXMLLoader introFXMLLoader = new FXMLLoader(Main.class.getResource("/views/menuViews/IntroScreen.fxml"));
        introFXMLLoader.setController(introScreenController);
        introScene = introFXMLLoader.load();

        FXMLLoader searchFXMLLoader = new FXMLLoader(Main.class.getResource("/views/menuViews/SearchView.fxml"));
        searchFXMLLoader.setController(searchController);
        searchController.setAppController(this);
        searchScene = searchFXMLLoader.load();

        FXMLLoader favouritesFXMLLoader = new FXMLLoader(Main.class.getResource("/views/menuViews/FavouritesView.fxml"));
        favouritesFXMLLoader.setController(favouritesController);
        favouritesScene = favouritesFXMLLoader.load();
    }

    public void openMenu() {
        try {
            // Load menu from fxml file.
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/menuViews/Menu.fxml"));
            borderPane.setLeft(loader.load());
            menuController = loader.getController();
            menuController.setAppController(this);
            searchController.setMenuController(menuController);

            openIntroScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openIntroScreen() {
        introScreenController.clearShownMovies();
        introScreenController.addMovies(5);
        introScreenController.setUser(model.getObsLoggedInUser());
        introScreenController.setFeaturedMovies();

        borderPane.setCenter(introScene);
        menuController.setFocusOnHome();
    }

    public void openSearchScreen() throws IOException {
        searchController.forYouAction(null);
        borderPane.setCenter(searchScene);
        menuController.setFocusOnSearch();
    }

    public void openFavouritesScreen() {
        favouritesController.clearShownMovies();
        //favouritesController.addMovies(20);

        borderPane.setCenter(favouritesScene);
        menuController.setFocusOnFavourites();
    }

    public void openLogInScreen() {
        borderPane.setLeft(null);
        borderPane.setCenter(logInScene);
        ((VBox) borderPane.lookup("#imageVBox")).setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK,30,0,5,0));
    }
}

