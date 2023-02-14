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
    private AppModel model;
    private MovieViewFactory movieViewFactory;
    private ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
    private final ObservableList<HBox> shownMovies = FXCollections.observableArrayList();
    private final HashMap<Integer, HBox> loadedMovies = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources){
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
        if (filteredMovies.size() > 0){
            int size = (filteredMovies.size() > 6) ? 6 : filteredMovies.size();
            HBox movieView;
            int i = 0;

            while (i < size){
                if (loadedMovies.get(filteredMovies.get(0).getId()) == null) {
                    movieView = movieViewFactory.constructMovieView(filteredMovies.get(0));
                    loadedMovies.put(filteredMovies.get(0).getId(), movieView);
                } else {
                    movieView = loadedMovies.get(filteredMovies.get(0).getId());
                }
                shownMovies.add(movieView);
                filteredMovies.remove(0);
                i++;
            }

            flowPane.getChildren().setAll(shownMovies);
        }
    }

    private void setInitialMovies() {
        if (filteredMovies.size() > 0) {
            int size = (filteredMovies.size() > 12) ? 12 : filteredMovies.size();
            HBox movieView;
            int i = 0;

            while (i < size) {
                if (loadedMovies.get(filteredMovies.get(0).getId()) == null) {
                    movieView = movieViewFactory.constructMovieView(filteredMovies.get(0));
                    loadedMovies.put(filteredMovies.get(0).getId(), movieView);
                } else {
                    movieView = loadedMovies.get(filteredMovies.get(0).getId());
                }
                shownMovies.add(movieView);
                filteredMovies.remove(0);
                i++;
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
