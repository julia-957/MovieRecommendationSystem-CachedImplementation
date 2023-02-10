package dk.easv.presentation.controller;

import dk.easv.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class IntroScreenController implements Initializable {
    @FXML
    private HBox featuredMovieView;
    @FXML
    private ImageView featuredMoviePoster, iconIMDBrating, iconUserRatings, carouselRightView, carouselLeftView;
    @FXML
    private Label featuredMovieDescription, featuredMovieTitle, carouselGenreTxt, carouselRatingIMDB, carouselRatingUsers, carouselYearTxt;
    @FXML
    private Button carouselAddBtn, carouselRemoveBtn, carouselLeft, carouselRight;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carouselSetup();
    }

    public void clickCarouselAdd(ActionEvent actionEvent) {
    }

    public void clickCarouselRemove(ActionEvent actionEvent) {
    }

    private void carouselSetup(){
     carouselLeftView.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/arrow-left.png"))));
     carouselLeft.setText("");
     carouselLeft.setGraphic(carouselLeftView);

     carouselRightView.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/mediumIndigo/arrow-right.png"))));
     carouselRight.setText("");
     carouselRight.setGraphic(carouselRightView);

     featuredMoviePoster.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/cats_2_3.png"))));

     iconIMDBrating.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icons/imdb_icon.png"))));
     }
}
