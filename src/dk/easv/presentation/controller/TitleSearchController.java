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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class TitleSearchController implements Initializable {
    @FXML private TextField txtSearchBar;
    @FXML private FlowPane flowPane;
    private AppModel appModel;
    private RoundImageCorners roundImageCorners = new RoundImageCorners();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                    appModel.searchMovies(txtSearchBar.getText().toLowerCase().trim());
                }
        );
        for (int i = 0; i < 10; i++) {
            populateFlowPaneWithMovies(appModel.getTopAverageRatedMoviesUserDidNotSee(appModel.getObsLoggedInUser()).get(0));
        }
        //TODO figure this out
        //txtSearchBar.setPrefWidth();
        txtSearchBar.relocate(flowPane.getChildren().get(0).getLayoutX(), txtSearchBar.getLayoutY());
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

    public void populateFlowPaneWithMovies(Movie movie){
        HBox mainContainer = new HBox(10);
        mainContainer.getStyleClass().addAll("movieDisplayHBox", "rounded");
        mainContainer.setPrefWidth(400);
        mainContainer.setPrefHeight(200);
        mainContainer.setPadding(new Insets(10,10,10,10));

        ImageView moviePoster = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/cats_2_3.png"))));
        moviePoster.setFitWidth(120);
        moviePoster.setFitHeight(180);
        roundImageCorners.clipImage(moviePoster);

        Label movieTitle = new Label(movie.getTitle());
        movieTitle.setStyle("-fx-font-size: 20px;");
        movieTitle.setWrapText(true);

        Label genres = new Label(movie.getGenre());
        genres.setStyle("-fx-font-size: 12px;");

        VBox movieInfo = new VBox(10, movieTitle, genres);

        mainContainer.getChildren().addAll(moviePoster, movieInfo);
        flowPane.getChildren().add(mainContainer);
    }
}
