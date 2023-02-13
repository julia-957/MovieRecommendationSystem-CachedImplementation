package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SearchController {
    @FXML private BorderPane searchBorderPane;
    private AppController appController;
    private AppModel appModel;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void myListAction(ActionEvent actionEvent) throws IOException {
        appController.openFavouritesScreen();
    }

    @FXML
    private void genresAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/GenresFilterView.fxml"));
        searchBorderPane.setCenter(fxmlLoader.load());
    }

    @FXML
    private void searchTitlesAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/TitleSearchView.fxml"));
        searchBorderPane.setCenter(fxmlLoader.load());
        TitleSearchController titleSearchController = fxmlLoader.getController();
        titleSearchController.setAppModel(appModel);
    }

    @FXML
    private void forYouAction(ActionEvent actionEvent) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/presentation/view/.fxml"));
        //searchBorderPane.setCenter(fxmlLoader.load);
    }

    public void setAppModel(AppModel model) {
        this.appModel = model;
    }
}
