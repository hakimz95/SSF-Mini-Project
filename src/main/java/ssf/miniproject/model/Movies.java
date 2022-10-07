package ssf.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Movies implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Movies.class);

    private String id;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private String rating;
    private String queryString;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public String toString() {
        return "Movies [id=" + id + ", title=" + title + ", overview=" + overview + ", releaseDate= " + releaseDate + ", posterPath = " + posterPath + ", rating=" + rating + "]";
    }

    public static List<Movies> createJsonGetMovies(String json) throws IOException {

        List<Movies> movieList = new LinkedList<>();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray ja = jo.getJsonArray("results");

            for (int i = 0; i < ja.size(); i++) {
                Movies movies = new Movies();
                String imageUrl = "https://image.tmdb.org/t/p/original/";
                
                JsonObject jobj = ja.getJsonObject(i);
                String id = jobj.getJsonNumber("id").toString();
                String title = jobj.getString("title");
                String overview = jobj.getString("overview");
                String releaseDate = jobj.getString("release_date");
                String posterPath = jobj.getString("poster_path");
                String rating = jobj.getJsonNumber("vote_average").toString();

                movies.setId(id);
                movies.setTitle(title);
                movies.setOverview(overview);
                movies.setReleaseDate(releaseDate);
                movies.setPosterPath(imageUrl + posterPath);
                movies.setRating(rating);

                movieList.add(movies);

                //Check to see if the list movies is created 
                //System.out.println(movies);
            }
        }

        return movieList;
    }

    public static List<Movies> createJsonSearchMovies(String queryString, String json) throws IOException {

        List<Movies> movieList = new LinkedList<>();
        
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray ja = jo.getJsonArray("results");

            int length;
            if (ja.size() < 2) {
                length = ja.size();
            }
            else {
                length = 2;
            }

            for (int i = 0; i < ja.size(); i++) {
                Movies movies = new Movies();
                String imageUrl = "https://image.tmdb.org/t/p/original/";
                
                JsonObject jobj = ja.getJsonObject(i);
                String id = jobj.getJsonNumber("id").toString();
                String title = jobj.getString("title");
                String overview = jobj.getString("overview");
                String releaseDate = jobj.getString("release_date");
                String posterPath = jobj.getString("poster_path");
                String rating = jobj.getJsonNumber("vote_average").toString();

                movies.setId(id);
                movies.setTitle(title);
                movies.setOverview(overview);
                movies.setReleaseDate(releaseDate);
                movies.setPosterPath(imageUrl + posterPath);
                movies.setRating(rating);
                movies.setQueryString(queryString);

                movieList.add(movies);

                //Check to see if the list movies is created 
                //System.out.println(movies);
            }
        }
        return movieList;
    }
}
