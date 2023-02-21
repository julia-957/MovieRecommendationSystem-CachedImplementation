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
    private AppModel model;
    private final MovieViewFactory movieViewFactory = new MovieViewFactory();

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

    protected List<HBox> addMovies(int amount, List<Movie> moviesToLoad){
        HashMap<Integer, HBox> loadedMovies = model.getLoadedMovies();
        List<HBox> shownMovies = new ArrayList<>();
        if (moviesToLoad.size() > 0){
            int size = (moviesToLoad.size() > amount) ? amount : moviesToLoad.size();
            HBox movieView;
            int i = 0;

            while (i < size){
                if (loadedMovies.get(moviesToLoad.get(0).getId()) == null) {
                    movieView = movieViewFactory.constructMovieView(moviesToLoad.get(0));
                } else {
                    movieView = loadedMovies.get(moviesToLoad.get(0).getId());
                }
                shownMovies.add(movieView);
                moviesToLoad.remove(0);
                i++;
            }
        }
        return shownMovies;
    }

    public void setModel(AppModel model) {
        this.model = model;
    }
}
