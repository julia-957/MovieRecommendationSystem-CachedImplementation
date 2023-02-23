package dk.easv.presentation.controller.menuControllers;

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
import java.util.List;
import java.util.ResourceBundle;

public class FavouritesController extends BudgetMother implements Initializable {
    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;
    private final AppModel model = AppModel.getInstance();
    private ObservableList<Movie> favouriteMovies = FXCollections.observableArrayList();
    private final ObservableList<HBox> shownMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.vvalueProperty().addListener(this::scrolled);

        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());
    }

    void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
        ScrollBar bar = getVerticalScrollbar(scrollPane);
        if (value == bar.getMax()) {
            double targetValue = value * shownMovies.size();
            if (shownMovies.size() < favouriteMovies.size())
                addMovies(6);
            bar.setValue(targetValue / shownMovies.size());
        }
    }

    public void addMovies(int amount){
        amount = Math.min(favouriteMovies.size(), amount);
        List[] results = super.addMovies(amount, favouriteMovies);
        shownMovies.addAll(results[0]);
        favouriteMovies = FXCollections.observableArrayList(results[1]);
        flowPane.getChildren().setAll(shownMovies);
    }

    public void clearShownMovies(){
        flowPane.getChildren().clear();
        shownMovies.clear();
        scrollPane.setVvalue(0);
        favouriteMovies = FXCollections.observableArrayList(model.getObsLoggedInUser().getFavouriteMovies());
    }
}
