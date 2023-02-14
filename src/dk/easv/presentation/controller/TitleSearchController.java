package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.presentation.controller.util.MovieViewFactory;
import dk.easv.presentation.model.AppModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.*;

public class TitleSearchController implements Initializable {
    @FXML private TextField txtSearchBar;
    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;
    private AppModel model;
    private MovieViewFactory movieViewFactory;
    private ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
    private ScrollBar scrollBar;
    private ObservableList<Movie> recommendedMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        recommendedMovies.setAll(model.getTopAverageRatedMoviesUserDidNotSee(model.getObsLoggedInUser()));

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMovies = searchMovies(txtSearchBar.getText().trim().toLowerCase());
        });

        //TODO look into threading
        if (recommendedMovies.size() > 15){
            for (int i = 0; i < 15; i++) {
                flowPane.getChildren().add(movieViewFactory.constructMovieView(recommendedMovies.get(i)));
            }
        }
        else{
            for (Movie movie: recommendedMovies){
                flowPane.getChildren().add(movieViewFactory.constructMovieView(movie));
            }
        }

        scrollBar = getVerticalScrollbar(scrollPane);
        //scrollBar.valueProperty().addListener(this::scrolled);


        //TODO figure this out
        //HBox hbox = (HBox) flowPane.getChildren().get(0);
        //txtSearchBar.setMaxWidth(hbox.getPrefWidth());
        //txtSearchBar.relocate(hbox.getLayoutX(), txtSearchBar.getLayoutY());
    }

    public ObservableList<Movie> searchMovies(String query) {
        List<Movie> movies = model.searchMovies(query);
        ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
        filteredMovies.addAll(movies);
        return filteredMovies;
    }

    public void setAppModel(AppModel model) {
        this.model = model;
    }

    public void setMovieViewFactory(MovieViewFactory movieViewFactory) {
        this.movieViewFactory = movieViewFactory;
    }

    public void setInitialItems(List<HBox> movieViews){
        flowPane.getChildren().setAll(movieViews);
    }

    private ScrollBar getVerticalScrollbar(ScrollPane scrollPane) {
        ScrollBar result = null;
        for (Node n : scrollPane.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    result = bar;
                }
            }
        }
        return result;
    }

    void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
        System.out.println("Scrolled to " + value);
        ScrollBar bar = getVerticalScrollbar(scrollPane);

        /*
        if (value == bar.getMax()) {
            double targetValue = value * filteredMovies.size();
            movieViewFactory.populateFlowPaneWithMovies(filteredMovies.subList(0,15));
            bar.setValue(targetValue / filteredMovies.size());
        }

         */
    }
}
