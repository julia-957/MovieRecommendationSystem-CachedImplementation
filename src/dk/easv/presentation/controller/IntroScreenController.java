package dk.easv.presentation.controller;

import dk.easv.Main;
import dk.easv.entities.Movie;
import dk.easv.entities.User;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class IntroScreenController implements Initializable {

    @FXML
    private HBox featuredMovieView;
    @FXML
    private ImageView featuredMoviePoster, iconIMDBrating, iconUserRatings, carouselRightView, carouselLeftView, favouriteHeart;
    @FXML
    private Label featuredMovieDescription, featuredMovieTitle, carouselGenreTxt, carouselRatingIMDB, carouselRatingUsers, carouselYearTxt;
    @FXML
    private Button favouriteBtn, carouselLeft, carouselRight;
    private int moviePosition = 0;

    User user = new User();
    AppModel model = new AppModel();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carouselSetup();
        setFeaturedMovie(getTopMovies(), moviePosition);
    }


    private void carouselSetup(){
         carouselLeftView.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/arrow-left.png"))));
         carouselLeft.setText("");
         carouselLeft.setGraphic(carouselLeftView);

         carouselRightView.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/arrow-right.png"))));
         carouselRight.setText("");
         carouselRight.setGraphic(carouselRightView);

         favouriteHeart.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart-outline.png"))));
         favouriteBtn.setText("");
         favouriteBtn.setGraphic(favouriteHeart);

         iconIMDBrating.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/imdb_icon.png"))));
     }

     private List<Movie> getTopMovies(){
        return model.getTopAverageRatedMoviesUserDidNotSee(model.getObsLoggedInUser());
     }

     private void setFeaturedMovie(List<Movie> m, int moviePosition){
        if(!m.isEmpty()) {
            featuredMovieTitle.setText(m.get(moviePosition).getTitle());
            featuredMoviePoster.setImage(new Image(m.get(moviePosition).getPosterFilepath()));
            featuredMovieDescription.setText(m.get(moviePosition).getMovieDescription());
            carouselRatingIMDB.setText(m.get(moviePosition).getRatingIMDB().toString());
            carouselRatingUsers.setText(String.valueOf(m.get(moviePosition).getAverageRating()));
            carouselYearTxt.setText(String.valueOf(m.get(moviePosition).getYear()));
        }
     }


    public void clickFavourite(ActionEvent actionEvent) {
        user.getFavouriteMovies().add(getTopMovies().get(moviePosition));
        favouriteHeart.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/electricLilac/heart.png"))));
        favouriteBtn.setText("");
        favouriteBtn.setGraphic(favouriteHeart);
    }

    public void clickPrevious(ActionEvent actionEvent) {
        if(moviePosition==0) {
            setFeaturedMovie(getTopMovies(), getTopMovies().size()-1);
        }
        setFeaturedMovie(getTopMovies(), moviePosition--);

    }

    public void clickNext(ActionEvent actionEvent) {
        if(moviePosition==getTopMovies().size()-1){
            setFeaturedMovie(getTopMovies(), 0);
        }
        setFeaturedMovie(getTopMovies(), moviePosition++);
    }
}
