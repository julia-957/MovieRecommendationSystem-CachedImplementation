package dk.easv.entities;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int id;
    private String title, genre, posterFilepath, movieDescription;
    private String ratingIMDB;
    private int year;
    private List<Rating> ratings;

    public Movie(int id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.ratings = new ArrayList<>();
        this.genre = "Genre: N/A";
        this.posterFilepath = "\\/images/cats_2_3.png\\";
        this.ratingIMDB = "N/A";
    }

    public Movie(int id, String title, int year, String genre, String posterFilepath,String movieDescription, String ratingIMDB){
        this(id, title, year);
        this.genre = genre;
        this.movieDescription = movieDescription;
        this.posterFilepath = posterFilepath;
        this.ratingIMDB = ratingIMDB;
    }

    public double getAverageRating(){
        double sum = 0;
        for (Rating r: ratings){
            sum+=r.getRating();
        }
        if(ratings.size()==0)
            return 0;
        return sum/ratings.size();
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPosterFilepath() {
        return posterFilepath;
    }

    public void setPosterFilepath(String posterFilepath) {
        this.posterFilepath = posterFilepath;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getRatingIMDB() {
        return ratingIMDB;
    }

    public void setRatingIMDB(String ratingIMDB) {
        this.ratingIMDB = ratingIMDB;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public int getRatingsSize(){
        return ratings.size();
    }

    @Override
    public String toString() {
        return  title + ", " + year +
                ", ratings=" + ratings.size() +
                ", avg="+ Math.round(getAverageRating()*10)/10.0;
    }
}
