package dk.easv.presentation.model;

import dk.easv.entities.*;
import dk.easv.logic.LogicManager;
import dk.easv.presentation.controller.util.MovieViewFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.List;

public class AppModel {
    private static AppModel instance = null;
    LogicManager logic = new LogicManager();
    // Models of the data in the view
    User user = new User();
    private final ObservableList<User>  obsUsers = FXCollections.observableArrayList();
    private final ObservableList<Movie> obsTopMovieSeen = FXCollections.observableArrayList();
    private final ObservableList<Movie> obsTopMovieNotSeen = FXCollections.observableArrayList();
    private final ObservableList<UserSimilarity>  obsSimilarUsers = FXCollections.observableArrayList();
    private final ObservableList<TopMovie> obsTopMoviesSimilarUsers = FXCollections.observableArrayList();
    private final ObservableList<Movie> topMoviesSimilarUsersMovies = FXCollections.observableArrayList();

    private final SimpleObjectProperty<User> obsLoggedInUser = new SimpleObjectProperty<>();

    private final HashMap<Integer, HBox> loadedMovies = new HashMap<>();
    //private MovieViewFactory movieViewFactory;

    public static AppModel getInstance(){
        if(instance == null){
            instance = new AppModel();
        } return instance;
    }

    public void loadUsers(){
        obsUsers.clear();
        obsUsers.addAll(logic.getAllUsers());
    }

    public void loadData(User user) {
        obsTopMovieSeen.clear();
        obsTopMovieSeen.addAll(logic.getTopAverageRatedMovies(user));

        obsTopMovieNotSeen.clear();
        obsTopMovieNotSeen.addAll(logic.getTopAverageRatedMoviesUserDidNotSee(user));

        obsSimilarUsers.clear();
        obsSimilarUsers.addAll(logic.getTopSimilarUsers(user));

        obsTopMoviesSimilarUsers.clear();
        obsTopMoviesSimilarUsers.addAll(logic.getTopMoviesFromSimilarPeople(user));


        for (TopMovie topMovie: obsTopMoviesSimilarUsers){
            topMoviesSimilarUsersMovies.add(topMovie.getMovie());
        }

        //loadMovies(50, obsTopMovieNotSeen);
        //loadMovies(24, topMoviesSimilarUsersMovies);
    }

    public List<Movie> getTopAverageRatedMoviesUserDidNotSee(User u) {
        return logic.getTopAverageRatedMoviesUserDidNotSee(user);
    }

    public ObservableList<User> getObsUsers() {
        return obsUsers;
    }

    public ObservableList<Movie> getObsTopMovieSeen() {
        return obsTopMovieSeen;
    }

    public ObservableList<Movie> getObsTopMovieNotSeen() {
        return obsTopMovieNotSeen;
    }

    public ObservableList<UserSimilarity> getObsSimilarUsers() {
        return obsSimilarUsers;
    }

    public ObservableList<TopMovie> getObsTopMoviesSimilarUsers() {
        return obsTopMoviesSimilarUsers;
    }

    public User getObsLoggedInUser() {
        return obsLoggedInUser.get();
    }

    public SimpleObjectProperty<User> obsLoggedInUserProperty() {
        return obsLoggedInUser;
    }

    public void setObsLoggedInUser(User obsLoggedInUser) {
        this.obsLoggedInUser.set(obsLoggedInUser);
    }

    public boolean loginUserFromUsername(String userName) {
        User u = logic.getUser(userName);
        obsLoggedInUser.set(u);
        if (u==null)
            return false;
        else{
            loadData(u);
            return true;
        }
    }

    public ObservableList<Movie> searchMovies(String query) {
        return logic.searchMovies(query);
    }

    public HashMap<Integer, HBox> getLoadedMovies() {
        return loadedMovies;
    }

    public void updateHashMap(int movieID, HBox mainContainer){
        loadedMovies.put(movieID, mainContainer);
    }

    /*
    private void loadMovies(int amount, List<Movie> list){
        if (list.size() > 0) {
            int size = (list.size() > amount) ? amount : list.size();

            int i = 0;
            while (i < size) {
                if (loadedMovies.get(list.get(0).getId()) == null)
                    movieViewFactory.constructMovieView(list.get(0));
                list.remove(0);
                i++;
            }
        }
    }

     */

    public ObservableList<Movie> getTopMoviesSimilarUsersMovies() {
        return topMoviesSimilarUsersMovies;
    }
}
