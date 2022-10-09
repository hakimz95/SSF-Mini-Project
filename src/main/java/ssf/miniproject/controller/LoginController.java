package ssf.miniproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String redirectToTrending(@RequestParam String username) {
        movieRepository.createUser(username);
        return "trending";
    }
}
