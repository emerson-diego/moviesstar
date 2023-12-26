package br.araujos.moviesstar.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.araujos.moviesstar.dto.MovieDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable long id) {
        try {
            MovieDTO movie = movieService.fetchMovieById(id);
            if (movie == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(movie);
        } catch (IOException e) {
            // Tratar a exceção de acordo com a lógica do seu aplicativo
            // Por exemplo, retornar um ResponseEntity com status de erro interno
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
