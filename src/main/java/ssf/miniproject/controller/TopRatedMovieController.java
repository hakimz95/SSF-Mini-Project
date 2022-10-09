package ssf.miniproject.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ssf.miniproject.model.Movies;
import ssf.miniproject.services.MovieService;

@Controller
public class TopRatedMovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/topRated")
    public String generateTopRatedMovies(Model model) {
        Optional<List<Movies>> optTopRatedMovies = movieService.getTopRatedMovies();
        
        if (optTopRatedMovies.isEmpty()) {
            model.addAttribute("trendingMovies", new LinkedList<Movies>());
            return "topRated";
        }

        List<Movies> topRatedMovieList = optTopRatedMovies.get();

        model.addAttribute("topRatedMovies", topRatedMovieList);

        return "topRated";
    }
}
