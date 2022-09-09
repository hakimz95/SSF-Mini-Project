package ssf.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Arrays {
    private static final Logger logger = LoggerFactory.getLogger(Arrays.class);

    private static List<Data> movies = new ArrayList<>();

    public static List<Data> getMovies() {
        return movies;
    }

    public static void setMovies(List<Data> movies) {
        Arrays.movies = movies;
    }

    public static Arrays create(String json) throws IOException {
        logger.info("Array json");

        Arrays a = new Arrays();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray ja = jo.getJsonArray("Data");

            if (ja != null) {
                List<Data> output = new ArrayList<>();
                for (Object jm: ja) {
                    JsonObject joMovies = (JsonObject) jm;
                    output.add(Movies.createJson(joMovies));
                }
                logger.info("Data json established");
                Arrays.movies = output;
            }
        }

        return a;
    }
}
