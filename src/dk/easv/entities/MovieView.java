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

import java.util.*;

public class MovieView extends HBox {
    private final Movie movie;
    private User user;
    private final AppModel model = AppModel.getInstance();
    private final RoundImageCorners roundImageCorners = new RoundImageCorners();
    private Rating rating = null;

    private final ImageView likeButtonGraphic;
    private final Image heartFilled = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart.png")));
    private final Image heartOutline = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart-outline.png")));
    private final Image star = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-bold.png")));
    private final Image starFilled = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-fill.png")));
    private final List<ImageView> starButtonImgViews = new ArrayList<>();
    private Button btnOneStar, btnTwoStar, btnThreeStar, btnFourStar, btnFiveStar;
    private final HashMap<Button, Integer> starButtons = new HashMap<>();

    public MovieView(Movie movie) {
        super(10);
        this.movie = movie;
        this.user = model.getObsLoggedInUser();

        var select = user.getRatings().stream().filter(r -> r.getMovie().getId() == movie.getId()).findFirst();
        this.rating = select.orElse(null);

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

        setUpStarButtons();
        HBox stars = new HBox(5, btnOneStar, btnTwoStar, btnThreeStar, btnFourStar, btnFiveStar);

        //Create a like button
        likeButtonGraphic = new ImageView();
        Button likeButton = new Button("", likeButtonGraphic);
        likeButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (user.getFavouriteMovies().contains(movie)) {
                    likeButtonGraphic.setImage(heartOutline);
                    model.removeMovieFromFavourites(movie, user);
                    user.getFavouriteMovies().remove(movie);
                } else {
                    likeButtonGraphic.setImage(heartFilled);
                    model.addMovieToFavourites(movie, user);
                    user.getFavouriteMovies().add(movie);
                }
                likeButton.setGraphic(likeButtonGraphic);
            }
        });
        likeButton.setMaxWidth(20);
        likeButton.setMaxHeight(20);
        likeButtonGraphic.setFitWidth(20);
        likeButtonGraphic.setFitHeight(18.4);

        if(user.getFavouriteMovies().contains(movie))
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
            imageView.getStyleClass().setAll("empty");
        }
    }

    private void setUpStarButtons(){
        ImageView imgViewOneStar = new ImageView(star);
        btnOneStar = new Button("", imgViewOneStar); //one star = -5
        starButtons.put(btnOneStar, -5);
        starButtonImgViews.add(imgViewOneStar);

        ImageView imgViewTwoStar = new ImageView(star);
        btnTwoStar = new Button("", imgViewTwoStar); //two stars = -3
        starButtons.put(btnTwoStar, -3);
        starButtonImgViews.add(imgViewTwoStar);

        ImageView imgViewThreeStar = new ImageView(star);
        btnThreeStar = new Button("", imgViewThreeStar); //three stars = 1
        starButtons.put(btnThreeStar, 1);
        starButtonImgViews.add(imgViewThreeStar);

        ImageView imgViewFourStar = new ImageView(star);
        btnFourStar = new Button("", imgViewFourStar); //four stars = 3
        starButtons.put(btnFourStar, 3);
        starButtonImgViews.add(imgViewFourStar);

        ImageView imgViewFiveStar = new ImageView(star);
        btnFiveStar = new Button("", imgViewFiveStar); //five stars = 5
        starButtons.put(btnFiveStar, 5);
        starButtonImgViews.add(imgViewFiveStar);

        for (Button btn: starButtons.keySet()) {
            btn.setMaxWidth(20);
            btn.setMaxHeight(20);
        }

        for (ImageView imageView: starButtonImgViews) {
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
        }
        updateStars(rating);

        //TODO threading? making it work again
        btnOneStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var select = starButtonImgViews.stream().filter(iv -> iv.getStyleClass().get(0) == "filled").toArray();
                int filledCount = select.length;
                if (filledCount == 0) {
                    rating = new Rating(user, movie, -5);
                    model.addRating(rating);
                    updateStars(rating);
                } else if (filledCount == 1) {
                    resetStars();
                    model.removeRating(new Rating(user, movie, -5));
                    rating = null;
                } else {
                    model.removeRating(rating);
                    rating = new Rating(user, movie, -5);
                    model.addRating(rating);
                    updateStars(rating);
                }
            }
        });

        btnTwoStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var select = starButtonImgViews.stream().filter(iv -> iv.getStyleClass().get(0) == "filled").toArray();
                int filledCount = select.length;
                if (filledCount == 0) {
                    rating = new Rating(user, movie, -3);
                    model.addRating(rating);
                    updateStars(rating);
                } else if (filledCount == 2) {
                    resetStars();
                    model.removeRating(new Rating(user, movie, -3));
                    rating = null;
                } else {
                    model.removeRating(rating);
                    rating = new Rating(user, movie, -3);
                    model.addRating(rating);
                    updateStars(rating);
                }
            }
        });

        btnThreeStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var select = starButtonImgViews.stream().filter(iv -> iv.getStyleClass().get(0) == "filled").toArray();
                int filledCount = select.length;
                if (filledCount == 0) {
                    rating = new Rating(user, movie, 1);
                    model.addRating(rating);
                    updateStars(rating);
                } else if (filledCount == 3) {
                    resetStars();
                    model.removeRating(new Rating(user, movie, 1));
                    rating = null;
                } else {
                    model.removeRating(rating);
                    rating = new Rating(user, movie, 1);
                    model.addRating(rating);
                    updateStars(rating);
                }
            }
        });

        btnFourStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var select = starButtonImgViews.stream().filter(iv -> iv.getStyleClass().get(0) == "filled").toArray();
                int filledCount = select.length;
                if (filledCount == 0) {
                    rating = new Rating(user, movie, 3);
                    model.addRating(rating);
                    updateStars(rating);
                } else if (filledCount == 4) {
                    resetStars();
                    model.removeRating(new Rating(user, movie, 3));
                    rating = null;
                } else {
                    model.removeRating(rating);
                    rating = new Rating(user, movie, 3);
                    model.addRating(rating);
                    updateStars(rating);
                }
            }
        });

        btnFiveStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var select = starButtonImgViews.stream().filter(iv -> iv.getStyleClass().get(0) == "filled").toArray();
                int filledCount = select.length;
                if (filledCount == 0) {
                    rating = new Rating(user, movie, 5);
                    model.addRating(rating);
                    updateStars(rating);
                } else if (filledCount == 5) {
                    resetStars();
                    model.removeRating(new Rating(user, movie, 5));
                    rating = null;
                } else {
                    model.removeRating(rating);
                    rating = new Rating(user, movie, 5);
                    model.addRating(rating);
                    updateStars(rating);
                }
            }
        });
    }

    private void updateStars(Rating rating){
        if (rating == null){
            for (ImageView iv: starButtonImgViews) {
                iv.setImage(star);
                iv.getStyleClass().setAll("empty");
            }
        }
        else {
            switch (rating.getRating()){
                case -5:
                    starButtonImgViews.get(0).setImage(starFilled);
                    starButtonImgViews.get(0).getStyleClass().setAll("filled");
                    for (int i = 1; i <= starButtonImgViews.size()-1; i++){
                        starButtonImgViews.get(i).setImage(star);
                        starButtonImgViews.get(i).getStyleClass().setAll("empty");
                    }
                    break;
                case -3:
                    for (int i = 0; i <= starButtonImgViews.size()-1; i++){
                        if (i < 2){
                            starButtonImgViews.get(i).setImage(starFilled);
                            starButtonImgViews.get(i).getStyleClass().setAll("filled");
                        }
                        else{
                            starButtonImgViews.get(i).setImage(star);
                            starButtonImgViews.get(i).getStyleClass().setAll("empty");
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i <= starButtonImgViews.size()-1; i++){
                        if (i < 3){
                            starButtonImgViews.get(i).setImage(starFilled);
                            starButtonImgViews.get(i).getStyleClass().setAll("filled");
                        }
                        else {
                            starButtonImgViews.get(i).setImage(star);
                            starButtonImgViews.get(i).getStyleClass().setAll("empty");
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i <= starButtonImgViews.size()-1; i++){
                        if (i < 4){
                            starButtonImgViews.get(i).setImage(starFilled);
                            starButtonImgViews.get(i).getStyleClass().setAll("filled");
                        }
                        else {
                            starButtonImgViews.get(i).setImage(star);
                            starButtonImgViews.get(i).getStyleClass().setAll("empty");
                        }
                    }
                    break;
                case 5:
                    for (int i = 0; i <= starButtonImgViews.size()-1; i++){
                        starButtonImgViews.get(i).setImage(starFilled);
                        starButtonImgViews.get(i).getStyleClass().setAll("filled");
                    }
                    break;
            }
        }
    }

    public Movie getMovie() {
        return movie;
    }
}
