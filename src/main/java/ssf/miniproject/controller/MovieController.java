package ssf.miniproject.controller;

import java.util.List;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ssf.miniproject.model.Arrays;
import ssf.miniproject.services.MovieService;

@Controller
public class MovieController {
    
    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String discoverPage(Model model) {
        Arrays arrays = new Arrays();
        Arrays movieArrays = movieService.getMovies();

        if (movieArrays == null) {
            model.addAttribute("discover", new Arrays());
            return "discover";
        }

        List<Data> movies = Arrays.getMovies();
        model.addAttribute("arrays", arrays);
        model.addAttribute("movies", movies);
        return "discover";
    }
}
