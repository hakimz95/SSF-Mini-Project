package ssf.miniproject.services;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ssf.miniproject.model.Arrays;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    
    //API URL
    private static String discoverUrl = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";

    //Get API key from environment variables in the computer system
    private static String apiKey = System.getenv("TMDB_API_KEY");

    public Arrays getMovies() {

        //Create endpoint URL with query
        String discoverMovieUrl = UriComponentsBuilder.fromUriString(discoverUrl)
                                         .queryParam("api_key", apiKey)
                                         .toUriString();

        logger.info("Complete discover movie URI API address: " + discoverMovieUrl);

        //Make a call to TMDB API
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(discoverMovieUrl, String.class);
            Arrays a = Arrays.create(resp.getBody());
            return a;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

}
