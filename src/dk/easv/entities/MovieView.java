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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MovieView extends HBox {
    private final Movie movie;
    private final AppModel model = AppModel.getInstance();
    private final RoundImageCorners roundImageCorners = new RoundImageCorners();
    private final ImageView likeButtonGraphic;
    private final Image heartFilled = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart.png")));
    private final Image heartOutline = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart-outline.png")));
    private final Image star = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-bold.png")));
    private final Image starFilled = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-fill.png")));
    private final List<ImageView> starButtonImgViews = new ArrayList<>();

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
        ImageView imgViewOneStar = new ImageView(star);
        imgViewOneStar.setFitWidth(20);
        imgViewOneStar.setFitHeight(20);
        Button btnOneStar = new Button("", imgViewOneStar);
        starButtonImgViews.add(imgViewOneStar);

        ImageView imgViewTwoStar = new ImageView(star);
        imgViewTwoStar.setFitWidth(20);
        imgViewTwoStar.setFitHeight(20);
        Button btnTwoStar = new Button("", imgViewTwoStar);
        starButtonImgViews.add(imgViewTwoStar);

        ImageView imgViewThreeStar = new ImageView(star);
        imgViewThreeStar.setFitWidth(20);
        imgViewThreeStar.setFitHeight(20);
        Button btnThreeStar = new Button("", imgViewThreeStar);
        starButtonImgViews.add(imgViewThreeStar);

        ImageView imgViewFourStar = new ImageView(star);
        imgViewFourStar.setFitWidth(20);
        imgViewFourStar.setFitHeight(20);
        Button btnFourStar = new Button("", imgViewFourStar);
        starButtonImgViews.add(imgViewFourStar);

        ImageView imgViewFiveStar = new ImageView(star);
        imgViewFiveStar.setFitWidth(20);
        imgViewFiveStar.setFitHeight(20);
        Button btnFiveStar = new Button("", imgViewFiveStar);
        starButtonImgViews.add(imgViewFiveStar);

        btnOneStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (imgViewOneStar.getImage() == star){
                    imgViewOneStar.setImage(starFilled);
                    imgViewTwoStar.setImage(star);
                    imgViewThreeStar.setImage(star);
                    imgViewFourStar.setImage(star);
                    imgViewFiveStar.setImage(star);
                }
                else
                    resetStars();
            }
        });

        btnTwoStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (imgViewTwoStar.getImage() == star) {
                    imgViewOneStar.setImage(starFilled);
                    imgViewTwoStar.setImage(starFilled);
                    imgViewThreeStar.setImage(star);
                    imgViewFourStar.setImage(star);
                    imgViewFiveStar.setImage(star);
                }
                else
                    resetStars();
            }
        });

        btnThreeStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (imgViewThreeStar.getImage() == star) {
                    imgViewOneStar.setImage(starFilled);
                    imgViewTwoStar.setImage(starFilled);
                    imgViewThreeStar.setImage(starFilled);
                    imgViewFourStar.setImage(star);
                    imgViewFiveStar.setImage(star);
                }
                else
                    resetStars();
            }
        });

        btnFourStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (imgViewFourStar.getImage() == star) {
                    imgViewOneStar.setImage(starFilled);
                    imgViewTwoStar.setImage(starFilled);
                    imgViewThreeStar.setImage(starFilled);
                    imgViewFourStar.setImage(starFilled);
                    imgViewFiveStar.setImage(star);
                }
                else
                    resetStars();
            }
        });

        btnFiveStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (imgViewFiveStar.getImage() == star) {
                    imgViewOneStar.setImage(starFilled);
                    imgViewTwoStar.setImage(starFilled);
                    imgViewThreeStar.setImage(starFilled);
                    imgViewFourStar.setImage(starFilled);
                    imgViewFiveStar.setImage(starFilled);
                }
                else
                    resetStars();
            }
        });

        btnOneStar.setMaxWidth(20);
        btnOneStar.setMaxHeight(20);
        btnTwoStar.setMaxWidth(20);
        btnTwoStar.setMaxHeight(20);
        btnThreeStar.setMaxWidth(20);
        btnThreeStar.setMaxHeight(20);
        btnFourStar.setMaxWidth(20);
        btnFourStar.setMaxHeight(20);
        btnFiveStar.setMaxWidth(20);
        btnFiveStar.setMaxHeight(20);

        HBox stars = new HBox(5, btnOneStar, btnTwoStar, btnThreeStar, btnFourStar, btnFiveStar);

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

        //Add all elements into the main HBox
        this.getChildren().addAll(moviePoster, movieInfo);
        this.setHgrow(movieInfo, Priority.ALWAYS);
    }

    public void updateHeart(){
        if (likeButtonGraphic.getImage().equals(heartOutline)){
            likeButtonGraphic.setImage(heartFilled);
        }
        else
            likeButtonGraphic.setImage(heartOutline);
    }

    private void resetStars() {
        for (ImageView imageView: starButtonImgViews){
            imageView.setImage(star);
        }
    }

    public Movie getMovie() {
        return movie;
    }
}
