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
        HttpResponse<String> searchResponse = Unirest.get("http://www.omdbapi.com/?t="+movieName.trim()+"&y="+movieYear.trim()+"&apikey="+key)
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
}
