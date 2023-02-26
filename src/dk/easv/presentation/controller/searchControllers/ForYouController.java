package dk.easv.presentation.controller.searchControllers;

import dk.easv.entities.Movie;
import dk.easv.presentation.controller.BudgetMother;
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
import java.util.ResourceBundle;

public class ForYouController extends BudgetMother implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private FlowPane flowPane;
    private final AppModel model = AppModel.getInstance();
    private ObservableList<Movie> bestSimilarMovies = FXCollections.observableArrayList();
    private final ObservableList<HBox> shownMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.vvalueProperty().addListener(this::scrolled);

        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());
    }

    public void addMovies(int amount){
        amount = Math.min(bestSimilarMovies.size(), amount);
        for (int i = 0; i < amount; i++) {
            shownMovies.add(bestSimilarMovies.get(0).getMovieView());
            bestSimilarMovies.remove(0);
        }
        flowPane.getChildren().setAll(shownMovies);
    }


    void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
        ScrollBar bar = getVerticalScrollbar(scrollPane);
        if (value == bar.getMax()) {
            double targetValue = value * shownMovies.size();
            model.loadMovies(6, bestSimilarMovies);
            addMovies(6);
            bar.setValue(targetValue / shownMovies.size());
        }
    }

    public void clearShownMovies(){
        shownMovies.clear();
        flowPane.getChildren().clear();
        scrollPane.setVvalue(0);
        bestSimilarMovies.setAll(model.getTopMoviesSimilarUsersMovies());
    }
}
