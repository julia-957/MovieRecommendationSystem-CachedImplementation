package dk.easv.presentation.model;

import dk.easv.entities.*;
import dk.easv.logic.LogicManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class AppModel {
    private static AppModel instance = null;
    private final LogicManager logic = new LogicManager();
    private final ObservableList<User>  obsUsers = FXCollections.observableArrayList();
    private final ObservableList<Movie> obsTopMovieSeen = FXCollections.observableArrayList();
    private final ObservableList<Movie> obsTopMovieNotSeen = FXCollections.observableArrayList();
    private final ObservableList<UserSimilarity>  obsSimilarUsers = FXCollections.observableArrayList();
    private final ObservableList<TopMovie> obsTopMoviesSimilarUsers = FXCollections.observableArrayList();
    private final ObservableList<Movie> topMoviesSimilarUsersMovies = FXCollections.observableArrayList();
    private ObservableList<Movie> favouriteMovies = FXCollections.observableArrayList();
    private final SimpleObjectProperty<User> obsLoggedInUser = new SimpleObjectProperty<>();
    private Map<Integer, Movie> allMoviesHashMap = new HashMap<>();

    public static AppModel getInstance(){
        if(instance == null)
            instance = new AppModel();
        return instance;
    }

    public void loadUsers(){
        obsUsers.clear();
        obsUsers.addAll(logic.getAllUsers());
    }

    public void loadData(List<Movie> topAverageRatedMovies,List<Movie> topAverageRatedMoviesUserDidNotSee, List<UserSimilarity> topSimilarUsers, List<TopMovie> topMoviesFromSimilarPeople){
        long timerStartMillis = System.currentTimeMillis();
        obsTopMovieSeen.clear();
        obsTopMovieSeen.addAll(topAverageRatedMovies);
        loadMovies(20, obsTopMovieSeen);

        obsTopMovieNotSeen.clear();
        obsTopMovieNotSeen.addAll(topAverageRatedMoviesUserDidNotSee);
        loadMovies(20, obsTopMovieNotSeen);

        obsSimilarUsers.clear();
        obsSimilarUsers.addAll(topSimilarUsers);

        obsTopMoviesSimilarUsers.clear();
        obsTopMoviesSimilarUsers.addAll(topMoviesFromSimilarPeople);
        for (TopMovie topMovie: obsTopMoviesSimilarUsers){
            topMoviesSimilarUsersMovies.add(topMovie.getMovie());
        }
        loadMovies(20, topMoviesSimilarUsersMovies);

        favouriteMovies.setAll(obsLoggedInUser.get().getFavouriteMovies());
        loadMovies(20,favouriteMovies);

        setAllMovies();
        System.out.println("Loading took : " + (System.currentTimeMillis() - timerStartMillis) + "ms");
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

    public User loginUserFromUsername(String userName) {
        User u = logic.getUser(userName);
        obsLoggedInUser.set(u);
        if (u!=null)
            return u;
        else{
            return null;
        }
    }

    public List<String> getAllGenres(){
        return logic.getAllGenres();
    }
    public LogicManager getLogic() { return logic; }

    public ObservableList<Movie> searchMovies(String query) {
        return logic.searchMovies(query);
    }

    public Map<Integer, Movie> getAllMovies() {
        return logic.getAllMovies();
    }

        public ObservableList<Movie> getTopMoviesSimilarUsersMovies() {
        loadMovies(20, topMoviesSimilarUsersMovies);
        return topMoviesSimilarUsersMovies;
    }

    public void addMovieToFavourites(Movie movie, User user){
        logic.addMovieToFavourites(movie, user);
    }

    public void removeMovieFromFavourites(Movie movie, User user){
        logic.removeMovieFromFavourites(movie, user);
    }

    public void setAllMovies(){
        allMoviesHashMap = logic.getAllMovies();
    }

    public void loadMovies(int amount, List<Movie> list){
        List<Movie> removed = new ArrayList<>();
        if (list.size() > 0) {
            int size = Math.min(list.size(), amount);
            int i = 0;
            while (i < size) {
                if (list.get(0).getMovieView() == null){
                    list.get(0).setMovieView(new MovieView(list.get(0)));
                }
                i++;
                removed.add(list.remove(0));
            }
            for (i = removed.size()-1; i>=0; i--) {
                list.add(0, removed.get(i));
            }
        }
    }

    public void addRating(Rating rating){
        logic.addRating(rating);
    }

    public void removeRating(Rating rating){
        logic.removeRating(rating);
    }

    public void editRating(Rating rating){
        logic.editRating(rating);
    }
}
