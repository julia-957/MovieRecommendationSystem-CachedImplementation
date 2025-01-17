package dk.easv.presentation.controller.menuControllers;

import dk.easv.Main;
import dk.easv.entities.Movie;
import dk.easv.entities.MovieView;
import dk.easv.entities.User;
import dk.easv.presentation.controller.BudgetMother;
import dk.easv.presentation.controller.util.RoundImageCorners;
import dk.easv.presentation.model.AppModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;
import java.util.List;

public class IntroScreenController extends BudgetMother implements Initializable {
    @FXML private HBox featuredMovieView;
    @FXML private VBox saveUsJebus;
    @FXML private Rectangle lineDivider;
    @FXML private ImageView featuredMoviePoster, iconIMDBrating, iconUserRatings, iconRatingCurrentUser, carouselRightView, carouselLeftView, favouriteHeart, starOne,starTwo,starThree,starFour,starFive;
    @FXML private Label featuredMovieDescription, featuredMovieTitle, carouselGenreTxt, carouselRatingIMDB, carouselRatingUsers, carouselYearTxt;
    @FXML private Button favouriteBtn, carouselLeft, carouselRight, btnOneStar, btnTwoStar, btnThreeStar, btnFourStar, btnFiveStar;
    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;
    private int moviePosition = 0;
    private List<Movie> featuredMovies = new ArrayList<>();
    private final ObservableList<Movie> movieBestSimilarMovies = FXCollections.observableArrayList();
    private final ObservableList<MovieView> shownMovies = FXCollections.observableArrayList();
    private User user;
    private final AppModel model = AppModel.getInstance();
    private RoundImageCorners roundImageCorners = new RoundImageCorners();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carouselSetup();

        scrollPane.vvalueProperty().addListener(this::scrolled);

        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());
    }

    private void carouselSetup() {
         carouselLeftView.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/arrow-left.png"))));
         carouselLeft.setText("");
         carouselLeft.setGraphic(carouselLeftView);

        carouselRightView.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/arrow-right.png"))));
        carouselRight.setText("");
        carouselRight.setGraphic(carouselRightView);

        iconIMDBrating.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/imdb_icon.png"))));
        iconUserRatings.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/users-icon.png"))));
        iconUserRatings.setFitWidth(30);
        iconUserRatings.setFitHeight(20);

        //iconRatingCurrentUser.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/user.png"))));
        //iconRatingCurrentUser.setFitWidth(30);
        //iconRatingCurrentUser.setFitHeight(20);

        starOne.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-bold.png"))));
        btnOneStar.setGraphic(starOne);
        starTwo.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-bold.png"))));
        btnTwoStar.setGraphic(starTwo);
        starThree.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-bold.png"))));
        btnThreeStar.setGraphic(starThree);
        starFour.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-bold.png"))));
        btnFourStar.setGraphic(starFour);
        starFive.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/star-bold.png"))));
        btnFiveStar.setGraphic(starFive);
     }

     private void setFeaturedMovie(List<Movie> m, int moviePosition) {
        if(!m.isEmpty()) {
            featuredMovieTitle.setText(m.get(moviePosition).getTitle());
            featuredMoviePoster.setImage(new Image(m.get(moviePosition).getPosterFilepath()));
            saveUsJebus.getStyleClass().addAll("rounded");
            roundImageCorners.clipImage(featuredMoviePoster);
            featuredMovieDescription.setText(m.get(moviePosition).getMovieDescription());
            carouselRatingIMDB.setText(m.get(moviePosition).getRatingIMDB());
            carouselRatingUsers.setText(String.format(Locale.US,"%.1f",(m.get(moviePosition).getAverageRating())));
            carouselYearTxt.setText(String.valueOf(m.get(moviePosition).getYear()));
            setFavouriteHeart();
        }
     }

    public void clickFavourite(ActionEvent actionEvent) {
        if(user.getFavouriteMovies().contains(featuredMovies.get(moviePosition))){
            favouriteHeart.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart-outline.png"))));
            model.removeMovieFromFavourites(featuredMovies.get(moviePosition), user);
            user.getFavouriteMovies().remove(featuredMovies.get(moviePosition));
        }

        else {
            favouriteHeart.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart.png"))));
            model.addMovieToFavourites(featuredMovies.get(moviePosition), user);
            user.getFavouriteMovies().add(featuredMovies.get(moviePosition));
        }

        favouriteBtn.setText("");
        favouriteBtn.setGraphic(favouriteHeart);

        for (MovieView m: shownMovies) {
            if (m.getMovie() == featuredMovies.get(moviePosition)){
                featuredMovies.get(moviePosition).getMovieView().updateHeart();
                return;
            }
        }
    }

    private void setFavouriteHeart() {
        if(user.getFavouriteMovies().contains(featuredMovies.get(moviePosition))) {
            favouriteHeart.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart.png"))));
        }
        else {
            favouriteHeart.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart-outline.png"))));
        }
        favouriteBtn.setText("");
        favouriteBtn.setGraphic(favouriteHeart);
    }

    public void clickPrevious(ActionEvent actionEvent) {
        if(moviePosition==0) {
            moviePosition = featuredMovies.size();
        }
        moviePosition--;
        setFeaturedMovie(featuredMovies, moviePosition);
    }

    public void clickNext(ActionEvent actionEvent) {
        if(moviePosition==featuredMovies.size()-1){
            moviePosition = -1;
        }
        moviePosition++;
        setFeaturedMovie(featuredMovies, moviePosition);
    }

    void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
        ScrollBar bar = getVerticalScrollbar(scrollPane);
        if (value == bar.getMax()) {
            double targetValue = value * shownMovies.size();
            addMovies(6);
            bar.setValue(targetValue / shownMovies.size());
        }
    }

    public void addMovies(int amount){
        shownMovies.addAll(super.addMovies(amount, movieBestSimilarMovies));
        flowPane.getChildren().setAll(shownMovies);
    }

    public void clearShownMovies(){
        shownMovies.clear();
        flowPane.getChildren().clear();
        scrollPane.setVvalue(0);
        movieBestSimilarMovies.clear();
        movieBestSimilarMovies.addAll(model.getTopMoviesSimilarUsersMovies());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFeaturedMovies(){
        featuredMovies = model.getObsTopMovieNotSeen();
        setFeaturedMovie(featuredMovies, moviePosition);
        setFavouriteHeart();
    }
}

