package br.araujos.moviesstar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.araujos.dto.MovieDTO;
import br.araujos.moviesstar.services.MovieService;

@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getAllMovies() {

        List<MovieDTO> movies = movieService.fetchAllMovies();
        return ResponseEntity.ok(movies);

    }

    @PostMapping("/savemovies")
    public String saveMovies() {

        List<MovieDTO> movies = movieService.fetchAllMovies();
        movieService.saveMovies(movies);
        return "Filmes salvos com sucesso";

    }
}
