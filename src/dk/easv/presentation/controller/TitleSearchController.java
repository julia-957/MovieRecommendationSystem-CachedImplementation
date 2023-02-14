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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.*;

public class TitleSearchController implements Initializable {
    @FXML private TextField txtSearchBar;
    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;
    private ScrollBar scrollBar;
    private AppModel model;
    private MovieViewFactory movieViewFactory;
    private ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
    private ObservableList<HBox> shownMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        scrollBar = getVerticalScrollbar(scrollPane);
        scrollPane.vvalueProperty().addListener(this::scrolled);

        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());

        filteredMovies.setAll(model.getTopAverageRatedMoviesUserDidNotSee(model.getObsLoggedInUser()));
        setInitialMovies();

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtSearchBar.getText().isEmpty()){
                shownMovies.clear();
                filteredMovies.setAll(model.getTopAverageRatedMoviesUserDidNotSee(model.getObsLoggedInUser()));
                setInitialMovies();
            }
        });

        txtSearchBar.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                shownMovies.clear();
                scrollPane.setVvalue(0);
                filteredMovies = searchMovies(txtSearchBar.getText().trim().toLowerCase());
                setInitialMovies();
            }
        });

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
        ScrollBar bar = getVerticalScrollbar(scrollPane);

        if (value == bar.getMax()) {
            double targetValue = value * shownMovies.size();
            addMovies();
            bar.setValue(targetValue / shownMovies.size());
        }
    }

    private void addMovies(){
        int size = filteredMovies.size();

        if (size > 0){
            if (size >= 6){
                for (int i = 0; i < 6; i++) {
                    shownMovies.add(movieViewFactory.constructMovieView(filteredMovies.get(0)));
                    filteredMovies.remove(0);
                }
            }
            else{
                for (int i = 0; i < size; i++){
                    shownMovies.add(movieViewFactory.constructMovieView(filteredMovies.get(0)));
                    filteredMovies.remove(0);
                }
            }
            flowPane.getChildren().setAll(shownMovies);
        }
    }

    private void setInitialMovies() {
        int size = filteredMovies.size();

        if (size > 0){
            if (size >= 12){
                for (int i = 0; i < 12; i++) {
                    shownMovies.add(movieViewFactory.constructMovieView(filteredMovies.get(0)));
                    filteredMovies.remove(0);
                }
            }
            else{
                for (int i = 0; i < size; i++){
                    shownMovies.add(movieViewFactory.constructMovieView(filteredMovies.get(0)));
                    filteredMovies.remove(0);
                }
            }
        }
        flowPane.getChildren().setAll(shownMovies);
    }

    public void setAppModel(AppModel model) {
        this.model = model;
    }

    public void setMovieViewFactory(MovieViewFactory movieViewFactory) {
        this.movieViewFactory = movieViewFactory;
    }
}
