package ssf.miniproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ssf.miniproject.model.Movies;
import ssf.miniproject.services.MovieService;

@Controller
public class MovieDetailsController {
    
    @Autowired
    private MovieService movieService;

    @GetMapping("/detail/{id}")
    public String generateMovieDetails(Model model, @PathVariable String id) {
        Optional<Movies> optMovie = movieService.getMovieDetails(id);

        if (optMovie.isEmpty()) {
            model.addAttribute("movie", new Movies());
            return "trending";
        }

        Movies movies = optMovie.get();
        List<String> movieCountryList = movies.getCountries();
        List<String> movieGenreList = movies.getGenres();
        List<String> movieLanguageList = movies.getLanguages();

        model.addAttribute("movie", movies);
        model.addAttribute("movieCountryList", movieCountryList);
        model.addAttribute("movieGenreList", movieGenreList);
        model.addAttribute("movieLanguageList", movieLanguageList);

        return "details";
    }
}
