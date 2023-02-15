package dk.easv.presentation.controller;

import dk.easv.Main;
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
    private AppModel appModel;
    private Node searchTitlesScene, genresScene;
    private MovieViewFactory movieViewFactory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader searchTitlesfxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/TitleSearchView.fxml"));
        searchTitlesfxmlLoader.setController(titleSearchController);
        titleSearchController.setAppModel(appModel);
        titleSearchController.setMovieViewFactory(movieViewFactory);

        FXMLLoader genresFxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/GenresFilterView.fxml"));
        genresFxmlLoader.setController(genresController);
        genresController.setModel(appModel);

        try {
            searchTitlesScene = searchTitlesfxmlLoader.load();
            genresScene = genresFxmlLoader.load();
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
    private void forYouAction(ActionEvent actionEvent) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/.fxml"));
        //searchBorderPane.setCenter(fxmlLoader.load);
    }

    public void setAppModel(AppModel model) {
        this.appModel = model;
    }

    public void setMovieViewFactory(MovieViewFactory movieViewFactory) {
        this.movieViewFactory = movieViewFactory;
    }
}
