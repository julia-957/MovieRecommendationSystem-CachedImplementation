package dk.easv.presentation.controller.searchControllers;

import dk.easv.entities.TopMovie;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ForYouController extends BudgetMother implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private FlowPane flowPane;
    private AppModel model;
    private MovieViewFactory movieViewFactory;
    private HashMap<Integer, HBox> loadedMovies;
    private final ObservableList<TopMovie> bestSimilarMovies = FXCollections.observableArrayList();
    private final ObservableList<HBox> shownMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.vvalueProperty().addListener(this::scrolled);

        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());
    }

    public void addMovies(int amount){
        loadedMovies = model.getLoadedMovies();
        if (bestSimilarMovies.size() > 0){
            int size = (bestSimilarMovies.size() > amount) ? amount : bestSimilarMovies.size();
            HBox movieView;
            int i = 0;

            while (i < size){
                if (loadedMovies.get(bestSimilarMovies.get(0).getMovie().getId()) == null) {
                    movieView = movieViewFactory.constructMovieView(bestSimilarMovies.get(0).getMovie());
                } else {
                    movieView = loadedMovies.get(bestSimilarMovies.get(0).getMovie().getId());
                }
                shownMovies.add(movieView);
                bestSimilarMovies.remove(0);
                i++;
            }
            flowPane.getChildren().setAll(shownMovies);
        }
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

    public void setModel(AppModel model) {
        this.model = model;
    }

    public void setMovieViewFactory(MovieViewFactory movieViewFactory) {
        this.movieViewFactory = movieViewFactory;
    }

    public void setBestSimilarMovies(ObservableList<TopMovie> bestSimilarMovies) {
        shownMovies.clear();
        this.bestSimilarMovies.clear();
        this.bestSimilarMovies.setAll(bestSimilarMovies);
    }
}
