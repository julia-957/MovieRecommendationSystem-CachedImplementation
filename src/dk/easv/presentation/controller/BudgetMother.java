package dk.easv.presentation.controller;

import dk.easv.entities.Movie;
import dk.easv.entities.MovieView;
import dk.easv.presentation.controller.util.MovieViewFactory;
import dk.easv.presentation.model.AppModel;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.List;

public class BudgetMother {
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

    protected List<MovieView> addMovies(int amount, List<Movie> moviesToLoad){
        List<MovieView> shownMovies = new ArrayList<>();
        amount = Math.min(moviesToLoad.size(), amount);
        model.loadMovies(amount, moviesToLoad);
        for (int i = 0; i < amount; i++) {
            shownMovies.add(moviesToLoad.get(0).getMovieView());
            moviesToLoad.remove(0);
        }
        return shownMovies;
    }
}
