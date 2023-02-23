package dk.easv.presentation.controller.util;

import dk.easv.Main;
import dk.easv.entities.Movie;
import dk.easv.presentation.model.AppModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.*;

public class MovieViewFactory {
    private final RoundImageCorners roundImageCorners = new RoundImageCorners();

    public HBox constructMovieView(Movie movie){
        AppModel model = AppModel.getInstance();
        long timerStartMillis = System.currentTimeMillis();

        //Create an HBox to hold everything
        HBox mainContainer = new HBox(10);
        mainContainer.getStyleClass().addAll("movieDisplayHBox", "rounded", "shadow");
        mainContainer.setPrefWidth(400);
        mainContainer.setPrefHeight(200);
        mainContainer.setPadding(new Insets(10,10,10,10));

        ImageView moviePoster;
        try{
            moviePoster = new ImageView(new Image(movie.getPosterFilepath()));
        }
        catch (Exception e){
            moviePoster = new ImageView(new Image( Objects.requireNonNull(Main.class.getResourceAsStream("/images/cats_2_3.png"))));
        }
        moviePoster.setFitWidth(120);
        moviePoster.setFitHeight(180);
        roundImageCorners.clipImage(moviePoster);

        //Movie title, genres, release year labels
        Label movieTitle = new Label(movie.getTitle());
        movieTitle.setStyle("-fx-font-size: 20px;");
        movieTitle.setWrapText(true);

        Label genres = new Label(movie.getGenre());
        genres.setStyle("-fx-font-size: 12px;");

        Label releaseYear = new Label(String.valueOf(movie.getYear()));
        genres.setStyle("-fx-font-size: 12px;");

        //Create IMDB rating icon and text
        ImageView imdbIcon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/imdb_icon.png"))));
        imdbIcon.setFitWidth(20);
        imdbIcon.setFitHeight(20);
        Label imdbRating = new Label(movie.getRatingIMDB());

        //Create average user rating icon and text
        ImageView userIcon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream(   "/icons/electricLilac/users-icon.png"))));
        userIcon.setFitWidth(30);
        userIcon.setFitHeight(20);
        Label userRating = new Label(String.format(Locale.US, "%.1f", movie.getAverageRating()));

        //Create a like button
        ImageView likeButtonGraphic = new ImageView();
        Button likeButton = new Button("", likeButtonGraphic);
        likeButton.setMaxWidth(20);
        likeButton.setMaxHeight(20);
        likeButtonGraphic.setFitWidth(20);
        likeButtonGraphic.setFitHeight(18.4);

        if(model.getObsLoggedInUser().getFavouriteMovies().contains(movie))
            likeButtonGraphic.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart.png"))));
        else
            likeButtonGraphic.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart-outline.png"))));

        //Create an HBox to hold the ratings
        VBox fillerVBox = new VBox();
        HBox ratings = new HBox(10, imdbIcon, imdbRating, userIcon, userRating, fillerVBox, likeButton);
        ratings.setAlignment(Pos.BOTTOM_LEFT);
        ratings.setHgrow(fillerVBox, Priority.SOMETIMES);

        //Create a VBox to hold the labels and ratings
        HBox fillerHBox = new HBox();
        VBox movieInfo = new VBox(10, movieTitle, releaseYear, genres, fillerHBox, ratings);
        movieInfo.setPadding(new Insets(5));
        movieInfo.setVgrow(fillerHBox, Priority.ALWAYS);

        //Add all elements into the main HBox and add it into a HashMap of loaded movie views in model
        mainContainer.getChildren().addAll(moviePoster, movieInfo);
        mainContainer.setHgrow(movieInfo, Priority.ALWAYS);
        model.updateHashMap(movie.getId(), mainContainer);

        //System.out.println(movie.getTitle() + "took : " + (System.currentTimeMillis() - timerStartMillis) + "ms");
        return  mainContainer;
    }
}
