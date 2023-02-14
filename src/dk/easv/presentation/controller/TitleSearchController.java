package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.entities.Movie;
import dk.easv.presentation.model.AppModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class TitleSearchController implements Initializable {
    @FXML private TextField txtSearchBar;
    @FXML private FlowPane flowPane;
    private AppModel appModel;
    private final MovieViewFactory movieViewFactory = new MovieViewFactory();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                movieViewFactory.populateFlowPaneWithMovies(appModel.searchMovies(txtSearchBar.getText().toLowerCase().trim()), flowPane);
                }
        );
        movieViewFactory.populateFlowPaneWithMovies(appModel.getTopAverageRatedMoviesUserDidNotSee(appModel.getObsLoggedInUser()).subList(0,12), flowPane);
        //TODO figure this out
        HBox hbox = (HBox) flowPane.getChildren().get(0);
        txtSearchBar.setMaxWidth(hbox.getPrefWidth());
        txtSearchBar.relocate(hbox.getLayoutX(), txtSearchBar.getLayoutY());
    }

    public ObservableList<Movie> searchMovies(ActionEvent actionEvent) {
        List<Movie> movies = appModel.searchMovies(txtSearchBar.getText().trim().toLowerCase());
        ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
        filteredMovies.addAll(movies);
        return filteredMovies;
    }

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
    }
}
