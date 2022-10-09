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
    private String releaseYear;
    private String posterPath;
    private float rating;
    private static String ratingColour;
    private String runtime;
    private String status;
    private String link;
    private String queryString;
    private List<String> genres;
    private List<String> countries;
    private List<String> languages;
    private String addedDateTime;

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

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRatingColour() {
        return ratingColour;
    }

    public void setRatingColour(String ratingColour) {
        Movies.ratingColour = ratingColour;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(String addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return "Movies [id=" + id + ", title=" + title + ", overview=" + overview + ", releaseDate=" + releaseDate + ", releaseYear=" + releaseYear + 
        ", posterPath=" + posterPath + ", rating=" + rating + ", runtime=" + runtime + ", link=" + link + 
        ", queryString=" + queryString + ", genres=" + genres + ", countries=" + countries + ", languages=" + languages + ", ratingColour=" + ratingColour + "]";
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
                float rating = jobj.getJsonNumber("vote_average").bigDecimalValue().floatValue();
                int scale = (int) Math.pow(10, 1);
                rating = (float) Math.round(rating * scale) / scale;
                if(rating >= 7.5) {
                    ratingColour = "green";
                } 
                else if(rating >= 5) {
                    ratingColour = "orange";
                }
                else {
                    ratingColour = "red";
                }

                movies.setId(id);
                movies.setTitle(title);
                movies.setOverview(overview);
                movies.setReleaseDate(releaseDate);
                movies.setPosterPath(imageUrl + posterPath);
                movies.setRating(rating);
                movies.setRatingColour(ratingColour);

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

            for (int i = 0; i < length; i++) {
                Movies movies = new Movies();
                String imageUrl = "https://image.tmdb.org/t/p/original/";
                
                JsonObject jobj = ja.getJsonObject(i);
                String id = jobj.getJsonNumber("id").toString();
                String title = jobj.getString("title");
                String overview = jobj.getString("overview");
                String releaseDate = jobj.getString("release_date");
                String posterPath = jobj.getString("poster_path");
                float rating = jobj.getJsonNumber("vote_average").bigDecimalValue().floatValue();
                int scale = (int) Math.pow(10, 1);
                rating = (float) Math.round(rating * scale) / scale;
                if(rating >= 7.5) {
                    ratingColour = "green";
                } 
                else if(rating >= 5) {
                    ratingColour = "orange";
                }
                else {
                    ratingColour = "red";
                }

                movies.setId(id);
                movies.setTitle(title);
                movies.setOverview(overview);
                movies.setReleaseDate(releaseDate);
                movies.setPosterPath(imageUrl + posterPath);
                movies.setRating(rating);
                movies.setRatingColour(ratingColour);
                movies.setQueryString(queryString);

                movieList.add(movies);

                //Check to see if the list movies is created 
                //System.out.println(movies);
                logger.info("movie added: " + movies.toString());
            }
        }
        return movieList;
    }

    public static Movies createJsonMovieDetails(String json) throws IOException {

        Movies movies = new Movies();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray jaGenres = jo.getJsonArray("genres");
            List<String> genres = new LinkedList<>();
            JsonArray jaCountries = jo.getJsonArray("production_countries");
            List<String> countries = new LinkedList<>();
            JsonArray jaLang = jo.getJsonArray("spoken_languages");
            List<String> languages = new LinkedList<>();

            for (int i = 0; i < jaGenres.size(); i++) {
                JsonObject item = jaGenres.getJsonObject(i);
                String genre = item.getString("name");
                genres.add(genre);
            }

            for (int i = 0; i < jaCountries.size(); i++) {
                JsonObject item = jaCountries.getJsonObject(i);
                String country = item.getString("name");
                countries.add(country);
            }

            for (int i = 0; i < jaLang.size(); i++) {
                JsonObject item = jaLang.getJsonObject(i);
                String lang = item.getString("name");
                languages.add(lang);
            }

            String imageUrl = "https://image.tmdb.org/t/p/original/";

            String id = jo.getJsonNumber("id").toString();
            String title = jo.getString("title");
            String overview = jo.getString("overview");
            String releaseDate = jo.getString("release_date");
            String[] releaseDateArr = releaseDate.split("-");
            String releaseYear = releaseDateArr[0];
            String posterPath = jo.getString("poster_path");
            float rating = jo.getJsonNumber("vote_average").bigDecimalValue().floatValue();
            int scale = (int) Math.pow(10, 1);
            rating = (float) Math.round(rating * scale) / scale;
            if(rating >= 7.5) {
                ratingColour = "green";
            } 
            else if(rating >= 5) {
                ratingColour = "orange";
            }
            else {
                ratingColour = "red";
            }
            String runtime = jo.getJsonNumber("runtime").toString();
            String status = jo.getString("status");
            String link = jo.getString("homepage");

            movies.setId(id);
            movies.setTitle(title);
            movies.setOverview(overview);
            movies.setReleaseDate(releaseDate);
            movies.setReleaseYear(releaseYear);
            movies.setPosterPath(imageUrl + posterPath);
            movies.setRating(rating);
            movies.setRatingColour(ratingColour);
            movies.setRuntime(runtime);
            movies.setStatus(status);
            movies.setLink(link);
            movies.setGenres(genres);
            movies.setCountries(countries);
            movies.setLanguages(languages);

            //Check to see if the list movies is created 
            //System.out.println(movies);
        }
        return movies;
    }
}
