package br.araujos.moviesstar.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.araujos.moviesstar.services.MovieService;

@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String getAllMovies() {
        try {
            return movieService.fetchAllMovies();
        } catch (IOException e) {
            return "Erro ao buscar filmes: " + e.getMessage();
        }
    }
}
