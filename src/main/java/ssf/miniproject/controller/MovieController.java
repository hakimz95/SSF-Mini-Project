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
public class MovieController {
    
    @Autowired
    private MovieService movieService;


    @GetMapping("/trending")
    public String generateTrendingMovies(Model model) {
        Optional<List<Movies>> optTrendingMovies = movieService.getTrendingMovies();
        
        if (optTrendingMovies.isEmpty()) {
            model.addAttribute("trendingMovies", new LinkedList<Movies>());
            return "trending";
        }

        List<Movies> trendingMovieList = optTrendingMovies.get();

        model.addAttribute("trendingMovies", trendingMovieList);

        return "trending";
    }

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

    @GetMapping("/popular")
    public String generatePopularMovies(Model model) {
        Optional<List<Movies>> optPopularMovies = movieService.getPopularMovies();
        
        if (optPopularMovies.isEmpty()) {
            model.addAttribute("popularMovies", new LinkedList<Movies>());
            return "popular";
        }

        List<Movies> popularMovieList = optPopularMovies.get();

        model.addAttribute("popularMovies", popularMovieList);

        return "popular";
    }

    
}
