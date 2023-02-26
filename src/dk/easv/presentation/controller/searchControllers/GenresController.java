package dk.easv.presentation.controller.searchControllers;

import dk.easv.entities.Movie;
import dk.easv.presentation.controller.BudgetMother;
import dk.easv.presentation.model.AppModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
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
    private List<Button> buttons = new ArrayList<>();

    private List<String> genres;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //Prevent flowPane from shrinking with less than 12 movies
        flowPane.minWidthProperty().bind(scrollPane.widthProperty());
        flowPane.minHeightProperty().bind(scrollPane.heightProperty());

        //Show the initial 50 movies
        filteredMovies.setAll(model.getTopAverageRatedMoviesUserDidNotSee(model.getObsLoggedInUser()));
        setUpListeners();
    }

    private void setUpListeners(){
        scrollPane.vvalueProperty().addListener(this::scrolled);
    }

    public void createGenreButtons(List<String> genres){
        genresVBox.setSpacing(8);
        genresVBox.getChildren().clear();
        for (String g: genres) {
            if(!g.trim().equals("N/A")){
                Button genreButton = new Button(g);

                genreButton.getStyleClass().addAll("genresButtons", "rounded");

                buttons.add(genreButton);
                genresVBox.getChildren().add(genreButton);
                genreButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for(Button b: buttons){
                            b.getStyleClass().setAll("genresButtons", "rounded");
                        }
                        genreButton.getStyleClass().setAll("genresButtonsFocused", "rounded");
                        setGenreMovieList(genreButton.getText());
                    }
                });
            }
        }
    }

    private void setGenreMovieList(String genre){
        List<Movie> temp = new ArrayList<>();
        filteredMovies.setAll(model.getAllMovies());
        shownMovies.clear();
        flowPane.getChildren().clear();
        for (Movie m: filteredMovies) {
            if (m.getGenre().contains(genre))
                temp.add((m));
        }
        filteredMovies.clear();
        filteredMovies.addAll(temp);
        addMovies(20);
    }

    public void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
        ScrollBar bar = getVerticalScrollbar(scrollPane);
        if (value == bar.getMax()) {
            double targetValue = value * shownMovies.size();
            addMovies(6);
            bar.setValue(targetValue / shownMovies.size());
        }
    }

    public void clearShownMovies() {
        shownMovies.clear();
        flowPane.getChildren().clear();
        scrollPane.setVvalue(0);
        filteredMovies.setAll(model.getAllMovies());
    }

    public void addMovies(int amount){
        amount = Math.min(filteredMovies.size(), amount);
        List[] results = super.addMovies(amount, filteredMovies);
        shownMovies.addAll(results[0]);
        filteredMovies = FXCollections.observableArrayList(results[1]);
        flowPane.getChildren().setAll(shownMovies);
    }
}
