package ssf.miniproject.model;

import java.io.IOException;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.JsonObject;
import jakarta.json.JsonString;

public class Movies implements Data {
    private static final Logger logger = LoggerFactory.getLogger(Movies.class);

    private String id;
    private String overview;
    private String imageurl;
    private String title;
    private String rating;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

    public static Movies createJson(JsonObject jo) throws IOException {
        logger.info("Movies json");

        Movies m = new Movies();

        JsonString jsnId = jo.getJsonString("id");
        m.id = jsnId.getString();

        JsonString jsnOverview = jo.getJsonString("overview");
        m.overview = jsnOverview.getString();

        JsonString jsnImageurl = jo.getJsonString("poster_path");
        m.imageurl = jsnImageurl.getString();

        JsonString jsnTitle = jo.getJsonString("title");
        m.title = jsnTitle.getString();

        JsonString jsnRating = jo.getJsonString("vote_average");
        m.rating = jsnRating.getString();

        return m;
    }
}
