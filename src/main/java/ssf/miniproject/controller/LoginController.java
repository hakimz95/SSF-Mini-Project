package ssf.miniproject.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssf.miniproject.model.Movies;
import ssf.miniproject.repositories.MovieRepository;
import ssf.miniproject.services.MovieService;

@Controller
public class LoginController {

    @Autowired
    MovieService movieService;

    @Autowired 
    MovieRepository movieRepository;

    @PostMapping("/index")
    public String login(Model model) {
        model.addAttribute("redisUserObject", new MovieRepository());
        return "index";
    }

    @PostMapping("/trending")
    public String redirectToTrending(@RequestParam(value = "username", required = true) String username, Model model) {
        movieRepository.createUser(username);

        Optional<List<Movies>> optTrendingMovies = movieService.getTrendingMovies();
        
        if (optTrendingMovies.isEmpty()) {
            model.addAttribute("trendingMovies", new LinkedList<Movies>());
            return "trending";
        }

        List<Movies> trendingMovieList = optTrendingMovies.get();
        model.addAttribute("trendingMovies", trendingMovieList);
        return "trending";
    }
}
