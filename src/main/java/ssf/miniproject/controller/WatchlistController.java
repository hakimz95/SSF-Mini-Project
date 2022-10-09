package ssf.miniproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ssf.miniproject.model.Movies;
import ssf.miniproject.repositories.MovieRepository;
import ssf.miniproject.services.MovieService;

@Controller
public class WatchlistController {
    
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    //Add to watchlist 
    @PostMapping("/detail/{id}/favourite")
    public String addMovie(Model model,
            @PathVariable(name = "username", required = true) String username,
            @PathVariable(name = "id", required = true) String id) {

        Optional<Movies> optMovies = movieService.getMovieDetails(id);
        if (optMovies.isEmpty()) {
            model.addAttribute("movie", new Movies());
            return "watchlist";
        }

        Movies movies = optMovies.get();

        movieRepository.addMovie(username, movies);
        return "redirect:/trending";
    }

    //Remove from watchlist
    @PostMapping("/{username}/unfavourite")
    public String deleteMovie(@PathVariable(name = "username", required = true) 
            String username, @ModelAttribute Movies m, Model model) {
        
        String id = m.getId();

        Optional<Movies> optMovies = movieService.getMovieDetails(id);
        if (optMovies.isEmpty()) {
            model.addAttribute("movies", new Movies());
            return "watchlist";
        }
        Movies movies = optMovies.get();
        movieRepository.deleteMovie(username, movies);

        return "redirect:/watchlist/";
    }

    @RequestMapping(value = "/watchlist", method = { RequestMethod.POST, RequestMethod.GET})
    public String generateWatchList(@PathVariable(name = "username", required = true) String username,
            @ModelAttribute Movies m, Model model) {
        
        Map<Movies, String> watchlist = movieRepository.getWatchlist(username);
        List<Movies> movieList = new ArrayList<Movies>(watchlist.keySet());
        List<String> dateTimeList = new ArrayList<String>(watchlist.values());

        model.addAttribute("movieObj", new Movies());
        model.addAttribute("username", username);
        model.addAttribute("movieList", movieList);
        model.addAttribute("dateTimeList", dateTimeList);

        return "watchlist";
    }
}
