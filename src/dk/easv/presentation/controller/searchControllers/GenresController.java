package dk.easv.presentation.controller.searchControllers;

import dk.easv.entities.Movie;
import dk.easv.presentation.controller.BudgetMother;
import dk.easv.presentation.controller.util.MovieViewFactory;
import dk.easv.presentation.model.AppModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class GenresController extends BudgetMother implements Initializable {
    private AppModel model;
    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;
    private MovieViewFactory movieViewFactory;
    private ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
    private final ObservableList<HBox> shownMovies = FXCollections.observableArrayList();
    private HashMap<Integer, HBox> loadedMovies;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //Prevent flowPane from shrinking with less than 12 movies
        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());

        //Retrieve loaded movies
        loadedMovies = model.getLoadedMovies();

        //Show the initial 50 movies
        filteredMovies.setAll(model.getTopAverageRatedMoviesUserDidNotSee(model.getObsLoggedInUser()));
        //addMovies(50);

        setUpListeners();

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

    private void setUpListeners(){
        //Add listeners
        scrollPane.vvalueProperty().addListener(this::scrolled);
    }

    void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
        ScrollBar bar = getVerticalScrollbar(scrollPane);
        if (value == bar.getMax()) {
            double targetValue = value * shownMovies.size();
            //addMovies(6);
            bar.setValue(targetValue / shownMovies.size());
        }
    }

    /*
    private void addMovies(int amount){
        loadedMovies = model.getLoadedMovies();
        if (filteredMovies.size() > 0){
            int size = (filteredMovies.size() > amount) ? amount : filteredMovies.size();
            HBox movieView;
            int i = 0;

            while (i < size){
                if (loadedMovies.get(filteredMovies.get(0).getId()) == null) {
                    movieView = movieViewFactory.constructMovieView(filteredMovies.get(0));
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

     */

    public void setAppModel(AppModel model) {
        this.model = model;
    }

    public void setMovieViewFactory(MovieViewFactory movieViewFactory) {
        this.movieViewFactory = movieViewFactory;
    }
}
