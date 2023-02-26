package dk.easv.presentation.controller.searchControllers;

import dk.easv.entities.Movie;
import dk.easv.presentation.controller.BudgetMother;
import dk.easv.presentation.model.AppModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GenresController extends BudgetMother implements Initializable {
    private final AppModel model = AppModel.getInstance();
    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane, genresScrollPane;
    @FXML
    private VBox genresVBox;
    private ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
    private final ObservableList<HBox> shownMovies = FXCollections.observableArrayList();

    private List<String> genres;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //Prevent flowPane from shrinking with less than 12 movies
        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());

        setUpListeners();
    }

    private void setUpListeners(){
        scrollPane.vvalueProperty().addListener(this::scrolled);
    }

    //TODO make bootiful buttons
    public void createGenreButtons(List<String> genres){
        genresVBox.setSpacing(8);
        genresVBox.getChildren().clear();
        for (String g: genres) {
            if(!g.trim().equals("N/A")){
                Button genrebutton = new Button(g);
                genrebutton.getStyleClass().addAll("genresButtons", "rounded");
                genresVBox.getChildren().add(genrebutton);
            }
        }

    }

    void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
        ScrollBar bar = getVerticalScrollbar(scrollPane);
        if (value == bar.getMax()) {
            double targetValue = value * shownMovies.size();
            model.loadMovies(6, filteredMovies);
            addMovies(6);
            bar.setValue(targetValue / shownMovies.size());
        }
    }

    public void clearShownMovies() {
        shownMovies.clear();
        flowPane.getChildren().clear();
        scrollPane.setVvalue(0);
        filteredMovies.setAll(model.getObsTopMovieNotSeen());
    }

    public void addMovies(int amount){
        amount = Math.min(filteredMovies.size(), amount);
        for (int i = 0; i < amount; i++) {
            shownMovies.add(filteredMovies.get(0).getMovieView());
            filteredMovies.remove(0);
        }
        flowPane.getChildren().setAll(shownMovies);
    }
}
