package ssf.miniproject.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ssf.miniproject.model.Movies;
import ssf.miniproject.services.MovieService;

@Controller
public class MovieController {

    public static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    
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

    @GetMapping("/search")
    public String searchMovie(Model model, String query) {
        String queryString = query;
        logger.info("Query String: " + queryString);

        Optional<List<Movies>> optSearchMovies = movieService.getSearchMovie(queryString);

        if (optSearchMovies.isEmpty()) {
            model.addAttribute("searchMovies", new LinkedList<Movies>());
            return "trending";
        }

        List<Movies> searchMoviesList = optSearchMovies.get();

        model.addAttribute("queryString", queryString);
        model.addAttribute("searchMovies", searchMoviesList);

        return "search";
    }
}
