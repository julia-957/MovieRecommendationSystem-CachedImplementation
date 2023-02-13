package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class TitleSearchController {
    @FXML private TextField txtSearchBar;
    @FXML private FlowPane flowPane;
    private AppModel appModel;


    public void searchMovies(ActionEvent actionEvent) {
        List<Movie> movies = appModel.searchMovies(txtSearchBar.getText().trim().toLowerCase());

    }
}
