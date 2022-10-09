package ssf.miniproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssf.miniproject.repositories.MovieRepository;
import ssf.miniproject.model.Movies;
import ssf.miniproject.model.User;

@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieRestController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping(path = "{username}")
    public ResponseEntity<Map<String, String>> getUser(@PathVariable String username) {
        
        Optional<User> optUser = movieRepository.getUser(username);
        User user = optUser.get();
        Map<Movies, String> watchlist = user.getWatchlist();
        List<Movies> movieList = new ArrayList<Movies>(watchlist.keySet());
        Map<String, String> movieMap = new HashMap<>();

        movieMap.put("success", "User" + username + " watchlist");
        for (Movies m : movieList) {
            movieMap.put(m.getAddedDateTime(),m.getTitle());
        }

        ResponseEntity<Map<String, String>> resp = ResponseEntity.status(HttpStatus.OK)
                                                                    .contentType(MediaType.APPLICATION_JSON)
                                                                    .body(movieMap);
        return resp;
    }
}
