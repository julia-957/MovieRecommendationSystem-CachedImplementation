package seedData;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class omdbController {
    /**
     * Getting data(Name,Poste,Rating,Genres) from the omdbapi - open IMDB api.
     * @param movieName - Name of the movie you want to search for.
     * @return JSONObject - All the movie data for the searched movie
     */
    private JSONObject getDataFromImdb(String key, String movieName, String movieYear){
        var url = "http://www.omdbapi.com/?t="+movieName.trim()+"&y="+movieYear.trim()+"&apikey="+key;
        HttpResponse<String> searchResponse = Unirest.get(url)
                .asString();
        return new JSONObject(searchResponse.getBody());
    }

    /**
     * Get the data from the IMDB
     * @param nameOfMove
     * @param yearOfMovie
     */
    public String searchImdb(String key,String nameOfMove, String yearOfMovie){
        JSONObject movieObject = getDataFromImdb(key,nameOfMove,yearOfMovie);
        if(tryGetValueFromJsonObject(movieObject,"Error").equals("Movie not found!"))
            movieObject = searchImdbByName(nameOfMove);
        var poster = tryGetValueFromJsonObject(movieObject,"Poster");
        var genres = tryGetValueFromJsonObject(movieObject,"Genre");
        var description = tryGetValueFromJsonObject(movieObject,"Plot");
        var imdbRating = tryGetValueFromJsonObject(movieObject,"imdbRating");
        return description+","+poster+","+genres+","+imdbRating;
    }
    private String tryGetValueFromJsonObject(JSONObject jsonObject,String value){
        try {
            return jsonObject.getString(value);
        }catch (Exception e){
            return "";
        }
    }
    /**
     * Getting data(Name,Poste,Rating,Genres) from the omdbapi - open IMDB api.
     * @param searchData - Name of the movie you want to search for.
     * @return JSONObject - The all the movie data for the searched movie
     */
    private JSONObject getDataFromImdb(String searchData){
        // Search the movie
        HttpResponse<String> searchResponse = Unirest.get("http://www.omdbapi.com/?s="+searchData.trim()+"&apikey=b712184d")
                .asString();
        var searchDataResponse = new JSONObject(searchResponse.getBody());
        try {
            // If no movie is found throw an Exception
            // From the list of movies, get the ID of the first movie.
            String imdbID = new JSONObject(searchResponse.getBody()).getJSONArray("Search").getJSONObject(0).getString("imdbID");
            // Search the movie again, but with corresponding id, to get all the data for the corresponding movie.
            HttpResponse<String> movieResponse = Unirest.get("http://www.omdbapi.com/?i="+imdbID+"&apikey=b712184d")
                    .asString();
            return new JSONObject(movieResponse.getBody());
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * Search the movie in the IMDB and fill the fields with the data from the IMDB
     */
    private JSONObject searchImdbByName(String searchData){
        // Get all the movie data for the searched MovieTitle
        String splitSearchData = searchData.split(" ")[0];
        while (splitSearchData.length() > 0){
            JSONObject movieObject = getDataFromImdb(splitSearchData);
            if(movieObject != null)
                return movieObject;
            splitSearchData = splitSearchData.substring(0,splitSearchData.length()-1);
        }
        return null;
    }
}
