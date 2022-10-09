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
public class PopularMovieController {
    
    @Autowired
    private MovieService movieService;

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
