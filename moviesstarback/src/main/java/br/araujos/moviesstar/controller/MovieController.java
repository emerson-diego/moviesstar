package br.araujos.moviesstar.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.araujos.moviesstar.dto.MovieDTO;
import br.araujos.moviesstar.services.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllMovies() {

        List<MovieDTO> movies = movieService.fetchAllMovies();
        return ResponseEntity.ok(movies);

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

    @GetMapping("/top25")
    public ResponseEntity<List<MovieDTO>> getTop25Movies() {
        List<MovieDTO> movies = movieService.fetchTop25MoviesByScoreAndImdbRating();
        return ResponseEntity.ok(movies);
    }
}
