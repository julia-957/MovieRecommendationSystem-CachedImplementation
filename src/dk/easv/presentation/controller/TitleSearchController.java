package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.presentation.model.AppModel;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TitleSearchController implements Initializable {
    @FXML private TextField txtSearchBar;
    @FXML private FlowPane flowPane;
    @FXML private ListView<Movie> listView;
    private AppModel appModel;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) ->
                listView.setItems(appModel.searchMovies(txtSearchBar.getText().toLowerCase().trim()))
        );
    }

    public void searchMovies(ActionEvent actionEvent) {
        List<Movie> movies = appModel.searchMovies(txtSearchBar.getText().trim().toLowerCase());
        ObservableList<Movie> movieObservableList = FXCollections.observableArrayList();
        movieObservableList.addAll(movies);
        listView.setItems(movieObservableList);
    }

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
    }
}
