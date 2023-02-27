package dk.easv.entities;

import dk.easv.Main;
import dk.easv.presentation.controller.util.RoundImageCorners;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Locale;
import java.util.Objects;

public class MovieView extends HBox {
    private final Movie movie;
    private final AppModel model = AppModel.getInstance();
    private final RoundImageCorners roundImageCorners = new RoundImageCorners();
    private final ImageView likeButtonGraphic;
    private final Image heartFilled = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart.png")));
    private final Image heartOutline = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart-outline.png")));

    public MovieView(Movie movie) {
        super(10);
        this.movie = movie;

        this.getStyleClass().setAll("movieDisplayHBox", "rounded", "shadow");
        this.setPrefWidth(400);
        this.setPrefHeight(200);
        this.setPadding(new Insets(10,10,10,10));

        //Movie poster
        ImageView moviePoster = new ImageView();
        moviePoster.setImage(new Image(movie.getPosterFilepath()));
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
        ImageView userIcon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/users-icon.png"))));
        userIcon.setFitWidth(30);
        userIcon.setFitHeight(20);
        Label userRating = new Label(String.format(Locale.US, "%.1f", movie.getAverageRating()));

        //Create star buttons
        Image star = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-bold.png")));

        ImageView imgViewOneStar = new ImageView(star);
        imgViewOneStar.setFitWidth(15);
        imgViewOneStar.setFitHeight(15);
        Button btnOneStar = new Button("", imgViewOneStar);

        ImageView imgViewTwoStar = new ImageView(star);
        imgViewTwoStar.setFitWidth(15);
        imgViewTwoStar.setFitHeight(15);
        Button btnTwoStar = new Button("", imgViewTwoStar);

        ImageView imgViewThreeStar = new ImageView(star);
        imgViewThreeStar.setFitWidth(15);
        imgViewThreeStar.setFitHeight(15);
        Button btnThreeStar = new Button("", imgViewThreeStar);

        ImageView imgViewFourStar = new ImageView(star);
        imgViewFourStar.setFitWidth(15);
        imgViewFourStar.setFitHeight(15);
        Button btnFourStar = new Button("", imgViewFourStar);

        ImageView imgViewFiveStar = new ImageView(star);
        imgViewFiveStar.setFitWidth(15);
        imgViewFiveStar.setFitHeight(15);
        Button btnFiveStar = new Button("", imgViewFiveStar);

        btnOneStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        btnTwoStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        btnThreeStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        btnFourStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        btnFiveStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        btnOneStar.setMaxWidth(15);
        btnOneStar.setMaxHeight(15);
        btnTwoStar.setMaxWidth(15);
        btnTwoStar.setMaxHeight(15);
        btnThreeStar.setMaxWidth(15);
        btnThreeStar.setMaxHeight(15);
        btnFourStar.setMaxWidth(15);
        btnFourStar.setMaxHeight(15);
        btnFiveStar.setMaxWidth(15);
        btnFiveStar.setMaxHeight(15);

        HBox stars = new HBox(0, btnOneStar, btnTwoStar, btnThreeStar, btnFourStar, btnFiveStar);

        //Create a like button
        likeButtonGraphic = new ImageView();
        Button likeButton = new Button("", likeButtonGraphic);
        likeButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (model.getObsLoggedInUser().getFavouriteMovies().contains(movie)) {
                    likeButtonGraphic.setImage(heartOutline);
                    model.removeMovieFromFavourites(movie, model.getObsLoggedInUser());
                    model.getObsLoggedInUser().getFavouriteMovies().remove(movie);
                } else {
                    likeButtonGraphic.setImage(heartFilled);
                    model.addMovieToFavourites(movie, model.getObsLoggedInUser());
                    model.getObsLoggedInUser().getFavouriteMovies().add(movie);
                }
                likeButton.setGraphic(likeButtonGraphic);
            }
        });
        likeButton.setMaxWidth(20);
        likeButton.setMaxHeight(20);
        likeButtonGraphic.setFitWidth(20);
        likeButtonGraphic.setFitHeight(18.4);

        if(model.getObsLoggedInUser().getFavouriteMovies().contains(movie))
            likeButtonGraphic.setImage(heartFilled);
        else
            likeButtonGraphic.setImage(heartOutline);

        //Create an HBox to hold the ratings
        VBox fillerVBox = new VBox();
        HBox ratings = new HBox(10, imdbIcon, imdbRating, userIcon, userRating, fillerVBox, likeButton);
        ratings.setAlignment(Pos.BOTTOM_LEFT);
        ratings.setHgrow(fillerVBox, Priority.SOMETIMES);

        //Create a VBox to hold the labels and ratings
        HBox fillerHBox = new HBox();
        VBox movieInfo = new VBox(10, movieTitle, releaseYear, genres, stars, fillerHBox, ratings);
        movieInfo.setPadding(new Insets(5));
        movieInfo.setVgrow(fillerHBox, Priority.ALWAYS);

        //Add all elements into the main HBox and add it into a HashMap of loaded movie views in model
        this.getChildren().addAll(moviePoster, movieInfo);
        this.setHgrow(movieInfo, Priority.ALWAYS);
        model.updateHashMap(movie.getId(), this);
    }

    public void updateHeart(){
        if (likeButtonGraphic.getImage().equals(heartOutline)){
            likeButtonGraphic.setImage(heartFilled);
        }
        else
            likeButtonGraphic.setImage(heartOutline);
    }

    public Movie getMovie() {
        return movie;
    }
}
