package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.presentation.controller.util.MovieViewFactory;
import dk.easv.presentation.model.AppModel;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BudgetMother {
    private final MovieViewFactory movieViewFactory = new MovieViewFactory();
    private final AppModel model = AppModel.getInstance();

    protected ScrollBar getVerticalScrollbar(ScrollPane scrollPane) {
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

    protected List[] addMovies(int amount, List<Movie> moviesToLoad){
        List<HBox> shownMovies = new ArrayList<>();
        amount = Math.min(moviesToLoad.size(), amount);
        model.loadMovies(amount, moviesToLoad);
        for (int i = 0; i < amount; i++) {
            shownMovies.add(moviesToLoad.get(0).getMovieView());
            moviesToLoad.remove(0);
        }
        return new List[] {shownMovies, moviesToLoad};
    }
}
