package dk.easv.presentation.controller.menuControllers;

import dk.easv.Main;
import dk.easv.presentation.controller.AppController;
import dk.easv.presentation.controller.searchControllers.ForYouController;
import dk.easv.presentation.controller.searchControllers.GenresController;
import dk.easv.presentation.controller.searchControllers.TitleSearchController;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML private BorderPane searchBorderPane;
    @FXML private Button btnForYou, btnMyList, btnSearchTitles, btnGenres;
    private AppController appController;
    private MenuController menuController;
    private final TitleSearchController titleSearchController = new TitleSearchController();
    private final GenresController genresController = new GenresController();
    private final ForYouController forYouController = new ForYouController();
    private final AppModel model = AppModel.getInstance();
    private Node searchTitlesScene, genresScene, forYouScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader searchTitlesfxmlLoader = new FXMLLoader(Main.class.getResource("/views/searchViews/TitleSearchView.fxml"));
        searchTitlesfxmlLoader.setController(titleSearchController);

        FXMLLoader genresFxmlLoader = new FXMLLoader(Main.class.getResource("/views/searchViews/GenresFilterView.fxml"));
        genresFxmlLoader.setController(genresController);

        FXMLLoader forYouFxmlLoader = new FXMLLoader(Main.class.getResource("/views/searchViews/ForYouView.fxml"));
        forYouFxmlLoader.setController(forYouController);

        try {
            searchTitlesScene = searchTitlesfxmlLoader.load();
            genresScene = genresFxmlLoader.load();
            forYouScene = forYouFxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void myListAction(ActionEvent actionEvent) throws IOException {
        appController.openFavouritesScreen();
        menuController.requestFocusOnFavourites();
    }

    @FXML
    private void genresAction(ActionEvent actionEvent) throws IOException {
        btnForYou.setId("");
        btnSearchTitles.setId("");
        btnGenres.setId("btnGenresFocused");

        searchBorderPane.setCenter(genresScene);
        genresController.clearShownMovies();
        genresController.addMovies(20);
    }

    @FXML
    private void searchTitlesAction(ActionEvent actionEvent) throws IOException {
        btnForYou.setId("");
        btnSearchTitles.setId("btnSearchTitlesFocused");
        btnGenres.setId("");

        searchBorderPane.setCenter(searchTitlesScene);
        titleSearchController.clearShownMovies();
        titleSearchController.addMovies(20);
    }

    @FXML
    public void forYouAction(ActionEvent actionEvent) throws IOException {
        btnForYou.setId("btnForYouFocused");
        btnSearchTitles.setId("");
        btnGenres.setId("");

        forYouController.setBestSimilarMovies(model.getTopMoviesSimilarUsersMovies());
        forYouController.clearShownMovies();
        forYouController.addMovies(20);
        searchBorderPane.setCenter(forYouScene);
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }
}
