package dk.easv.presentation.controller.menuControllers;

import dk.easv.Main;
import dk.easv.presentation.controller.AppController;
import dk.easv.presentation.controller.searchControllers.ForYouController;
import dk.easv.presentation.controller.searchControllers.GenresController;
import dk.easv.presentation.controller.searchControllers.TitleSearchController;
import dk.easv.presentation.controller.util.MovieViewFactory;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML private BorderPane searchBorderPane;
    private AppController appController;
    private TitleSearchController titleSearchController = new TitleSearchController();
    private GenresController genresController = new GenresController();
    private ForYouController forYouController = new ForYouController();
    private AppModel appModel;
    private Node searchTitlesScene, genresScene, forYouScene;
    private MovieViewFactory movieViewFactory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader searchTitlesfxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/searchViews/TitleSearchView.fxml"));
        searchTitlesfxmlLoader.setController(titleSearchController);
        titleSearchController.setAppModel(appModel);
        titleSearchController.setMovieViewFactory(movieViewFactory);

        FXMLLoader genresFxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/searchViews/GenresFilterView.fxml"));
        genresFxmlLoader.setController(genresController);
        genresController.setModel(appModel);

        FXMLLoader forYouFxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/searchViews/ForYouView.fxml"));
        forYouFxmlLoader.setController(forYouController);
        forYouController.setModel(appModel);
        forYouController.setMovieViewFactory(movieViewFactory);

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
    }

    @FXML
    private void genresAction(ActionEvent actionEvent) throws IOException {
        searchBorderPane.setCenter(genresScene);
    }

    @FXML
    private void searchTitlesAction(ActionEvent actionEvent) throws IOException {
        searchBorderPane.setCenter(searchTitlesScene);
    }

    @FXML
    public void forYouAction(ActionEvent actionEvent) throws IOException {
        forYouController.setBestSimilarMovies(appModel.getObsTopMoviesSimilarUsers());
        forYouController.addMovies(24);
        searchBorderPane.setCenter(forYouScene);
    }

    public void setAppModel(AppModel model) {
        this.appModel = model;
    }

    public void setMovieViewFactory(MovieViewFactory movieViewFactory) {
        this.movieViewFactory = movieViewFactory;
    }
}
