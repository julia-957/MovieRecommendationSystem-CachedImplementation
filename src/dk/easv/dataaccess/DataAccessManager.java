package dk.easv.dataaccess;

import dk.easv.entities.Movie;
import dk.easv.entities.Rating;
import dk.easv.entities.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class DataAccessManager {
    private HashMap<Integer, User> users = new HashMap<>();
    private HashMap<Integer, Movie> movies = new HashMap<>();
    private List<String> genres = new ArrayList<>();
    private List<Rating> ratings = new ArrayList<>();

    // Loads all data from disk and stores in memory
    // For performance, data is only updated if updateCacheFromDisk() is called
    public DataAccessManager() {
        updateCacheFromDisk();
    }

    public Map<Integer, User> getAllUsers() {
        return users;
    }

    public Map<Integer, Movie> getAllMovies() {
        return movies;
    }

    public List<Rating> getAllRatings(){
        return ratings;
    }


    public void updateCacheFromDisk(){
        loadAllRatings();
    }

    private void loadAllMovies() {
        try {
            List<String> movieLines = Files.readAllLines(Path.of("data/movie_titles.txt"));
            for (String movieLine : movieLines) {
                String[] split = movieLine.split(",");
                Movie movie = null;
                var id = Integer.parseInt(split[0]);
                var title = split[2];
                var year = Integer.parseInt(split[1]);
                var length = split.length;
                if(length >= 6){
                    var posterFilepath = Arrays.stream(split).filter(s -> s.contains("https")).findFirst().orElse("N/A");
                    var posterFilepathIndex = Arrays.asList(split).indexOf(posterFilepath);
                    var movieGenres = Arrays.stream(split).skip(posterFilepathIndex+1).limit(split.length-posterFilepathIndex-2).collect(Collectors.joining(", "));
                    for (var genre : movieGenres.split(", ")
                        ) {
                        if(!genres.contains(genre))
                            genres.add(genre);
                    }
                    var movieDescription = Arrays.stream(split).skip(3).limit(posterFilepathIndex-3).collect(Collectors.joining(","));
                    var ratingIMDB = split[length-1].equals("N/A") ? 0.0+"" : split[length-1];
                    movie = new Movie(id, title, year, movieGenres, posterFilepath, movieDescription, ratingIMDB);
                }
                else
                    movie = new Movie(id, title, year);
                movies.put(movie.getId(), movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAllUsers() {
        try {
            List<String> userLines = Files.readAllLines(Path.of("data/users.txt"));
            for (String userLine : userLines) {
                String[] split = userLine.split(",");
                User user = new User(Integer.parseInt(split[0]), split[1]);
                users.put(user.getId(), user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads all ratings, users and movies must be loaded first
    // Users holds a list of ratings and movies holds a list of ratings
    private void loadAllRatings() {
        loadAllMovies();
        loadAllUsers();
        try {
            List<String> ratingLines = Files.readAllLines(Path.of("data/ratings.txt"));
            for (String ratingLine : ratingLines) {
                String[] split = ratingLine.split(",");
                int movieId = Integer.parseInt(split[0]);
                int userId = Integer.parseInt(split[1]);
                int rating = Integer.parseInt(split[2]);
                Rating ratingObj = new Rating(users.get(userId), movies.get(movieId), rating);
                ratings.add(ratingObj);
                users.get(userId).getRatings().add(ratingObj);
                movies.get(movieId).getRatings().add(ratingObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
