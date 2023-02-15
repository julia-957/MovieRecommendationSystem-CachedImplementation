package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.presentation.controller.menuControllers.*;
import dk.easv.presentation.controller.util.MovieViewFactory;
import dk.easv.presentation.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox menuBarVBox;
    private final AppModel model = new AppModel();
    private MenuController menuController;
    private final SearchController searchController = new SearchController();
    private final IntroScreenController introScreenController = new IntroScreenController();
    private final FavouritesController favouritesController = new FavouritesController();
    private final LogInController logInController = new LogInController();
    private FXMLLoader searchFXMLLoader, introFXMLLoader, favouritesFXMLLoader, logInFXMLLoader;
    private Node searchScene, introScene, favouritesScene, logInScene;
    private final MovieViewFactory movieViewFactory = new MovieViewFactory();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            movieViewFactory.setModel(model);
            loadScenes();
            openLogInScreen();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void loadScenes() throws IOException {
        logInFXMLLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/LogIn.fxml"));
        logInFXMLLoader.setController(logInController);
        logInController.setAppController(this);
        logInController.setModel(model);
        logInController.setMovieViewFactory(movieViewFactory);
        logInScene = logInFXMLLoader.load();

        searchFXMLLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/SearchView.fxml"));
        searchFXMLLoader.setController(searchController);
        searchController.setAppController(this);
        searchController.setAppModel(model);
        searchController.setMovieViewFactory(movieViewFactory);
        searchScene = searchFXMLLoader.load();

        introFXMLLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/IntroScreen.fxml"));
        introFXMLLoader.setController(introScreenController);
        introScreenController.setModel(model);
        introScreenController.setMovieViewFactory(movieViewFactory);
        introScene = introFXMLLoader.load();

        favouritesFXMLLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/FavouritesView.fxml"));
        favouritesFXMLLoader.setController(favouritesController);
        favouritesController.setModel(model);
        favouritesScene = favouritesFXMLLoader.load();
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

    public void openIntroScreen() {
        introScreenController.setBestSimilarMovies(model.getObsTopMoviesSimilarUsers());
        introScreenController.addMovies(9);
        borderPane.setCenter(introScene);
    }

    public void openSearchScreen() throws IOException {
        searchController.forYouAction(null);
        borderPane.setCenter(searchScene);
    }

    public void openFavouritesScreen() {
        borderPane.setCenter(favouritesScene);
        menuController.setFocusOnFavourites();
    }

    public void openLogInScreen() {
        borderPane.setLeft(null);
        borderPane.setCenter(logInScene);
    }
}

