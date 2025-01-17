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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.*;

public class TitleSearchController extends BudgetMother implements Initializable {
    @FXML private TextField txtSearchBar;
    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;
    private final AppModel model = AppModel.getInstance();
    private final ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
    private final ObservableList<HBox> shownMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //Prevent flowPane from shrinking with less than 12 movies
        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());

        setUpListeners();
    }

    private void setUpListeners(){
        //Add listeners
        scrollPane.vvalueProperty().addListener(this::scrolled);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtSearchBar.getText().isEmpty()){
                shownMovies.clear();
                filteredMovies.setAll(model.getObsTopMovieNotSeen());
                addMovies(20);
            }
        });

        txtSearchBar.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                shownMovies.clear();
                scrollPane.setVvalue(0);
                filteredMovies.setAll(model.searchMovies(txtSearchBar.getText().trim().toLowerCase()));
                addMovies(15);
            }
        });
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

    public void addMovies(int amount){
        shownMovies.addAll(super.addMovies(amount, filteredMovies));
        flowPane.getChildren().setAll(shownMovies);
    }

    public void clearShownMovies(){
        shownMovies.clear();
        flowPane.getChildren().clear();
        scrollPane.setVvalue(0);
        filteredMovies.setAll(model.getObsTopMovieNotSeen());
    }
}
