package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.entities.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MovieViewFactory {
    RoundImageCorners roundImageCorners = new RoundImageCorners();
    public void populateFlowPaneWithMovies(List<Movie> movies, FlowPane flowPane){
        List<HBox> movieView = new ArrayList<>();
        for (Movie movie: movies){
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

            ImageView imdbIcon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/imdb_icon.png"))));
            imdbIcon.setFitWidth(20);
            imdbIcon.setFitHeight(20);
            Label imdbRating = new Label(String.valueOf(movie.getRatingIMDB()));

            ImageView userIcon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream(   "/icons/electricLilac/users-icon.png"))));
            userIcon.setFitWidth(30);
            userIcon.setFitHeight(20);
            Label userRating = new Label(String.format(Locale.US, "%.1f", movie.getAverageRating()));

            VBox fillerVBox = new VBox();
            HBox ratings = new HBox(5, imdbIcon, imdbRating, fillerVBox, userIcon, userRating);
            ratings.setAlignment(Pos.BOTTOM_LEFT);
            ratings.setHgrow(fillerVBox, Priority.SOMETIMES);

            HBox fillerHBox = new HBox();
            VBox movieInfo = new VBox(10, movieTitle, genres, fillerHBox, ratings);
            movieInfo.setPadding(new Insets(5));
            movieInfo.setVgrow(fillerHBox, Priority.ALWAYS);

            mainContainer.getChildren().addAll(moviePoster, movieInfo);
            mainContainer.setHgrow(movieInfo, Priority.ALWAYS);
            movieView.add(mainContainer);
        }
        flowPane.getChildren().addAll(movieView);
    }
}
