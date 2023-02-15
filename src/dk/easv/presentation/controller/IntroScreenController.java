package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.entities.Movie;
import dk.easv.entities.TopMovie;
import dk.easv.entities.User;
import dk.easv.presentation.controller.util.MovieViewFactory;
import dk.easv.presentation.controller.util.RoundImageCorners;
import dk.easv.presentation.model.AppModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class IntroScreenController implements Initializable {
    @FXML
    private HBox featuredMovieView;
    @FXML
    private VBox saveUsJebus;
    @FXML
    private ImageView featuredMoviePoster, iconIMDBrating, iconUserRatings, carouselRightView, carouselLeftView, favouriteHeart;
    @FXML
    private Label featuredMovieDescription, featuredMovieTitle, carouselGenreTxt, carouselRatingIMDB, carouselRatingUsers, carouselYearTxt;
    @FXML
    private Button favouriteBtn, carouselLeft, carouselRight;
    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;

    private int moviePosition = 0;
    private List<Movie> featuredMovies;
    private ObservableList<Movie> bestSimilarMovies = FXCollections.observableArrayList();
    private final ObservableList<HBox> shownMovies = FXCollections.observableArrayList();
    private User user = new User();
    private AppModel model;
    private MovieViewFactory movieViewFactory;
    private HashMap<Integer, HBox> loadedMovies;

    private RoundImageCorners roundImageCorners = new RoundImageCorners();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carouselSetup();
        featuredMovies = model.getTopAverageRatedMoviesUserDidNotSee(model.getObsLoggedInUser());
        setFeaturedMovie(featuredMovies, moviePosition);
        setFavouriteHeart();

        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());

        //Retrieve loaded movies
        bestSimilarMovies.setAll(featuredMovies);
        addMovies(6);
    }

    private void carouselSetup(){
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
     }

     private void setFeaturedMovie(List<Movie> m, int moviePosition){
        if(!m.isEmpty()) {
            featuredMovieTitle.setText(m.get(moviePosition).getTitle());
            //featuredMoviePoster.setImage(new Image(m.get(moviePosition).getPosterFilepath()));
            featuredMoviePoster.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cats_2_3.png"))));
            saveUsJebus.getStyleClass().addAll("movieDisplayHBox", "rounded");
            roundImageCorners.clipImage(featuredMoviePoster);
            //featuredMovieDescription.setText(m.get(moviePosition).getMovieDescription());
            //carouselRatingIMDB.setText(m.get(moviePosition).getRatingIMDB().toString());
            carouselRatingUsers.setText(String.format(Locale.US,"%.1f",(m.get(moviePosition).getAverageRating())));
            carouselYearTxt.setText(String.valueOf(m.get(moviePosition).getYear()));
            setFavouriteHeart();
        }
     }


    public void clickFavourite(ActionEvent actionEvent) {
        if(user.getFavouriteMovies().contains(featuredMovies.get(moviePosition))){
            favouriteHeart.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart-outline.png"))));
            user.getFavouriteMovies().remove(featuredMovies.get(moviePosition));
        }

        else {
            favouriteHeart.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart.png"))));
            user.getFavouriteMovies().add(featuredMovies.get(moviePosition));
        }
        favouriteBtn.setText("");
        favouriteBtn.setGraphic(favouriteHeart);
    }

    private void setFavouriteHeart(){
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

    public void setModel(AppModel model) {
        this.model = model;
    }

    public void setMovieViewFactory(MovieViewFactory movieViewFactory) {
        this.movieViewFactory = movieViewFactory;
    }

    public void addMovies(int amount){
        loadedMovies = movieViewFactory.getLoadedMovies();
        if (bestSimilarMovies.size() > 0){
            int size = (bestSimilarMovies.size() > amount) ? amount : bestSimilarMovies.size();
            HBox movieView;
            int i = 0;

            while (i < size){
                if (loadedMovies.get(bestSimilarMovies.get(0).getId()) == null) {
                    movieView = movieViewFactory.constructMovieView(bestSimilarMovies.get(0));
                } else {
                    movieView = loadedMovies.get(bestSimilarMovies.get(0).getId());
                }
                shownMovies.add(movieView);
                bestSimilarMovies.remove(0);
                i++;
            }
            flowPane.getChildren().setAll(shownMovies);
        }
    }
}
