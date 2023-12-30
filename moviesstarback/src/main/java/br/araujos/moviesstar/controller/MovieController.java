package br.araujos.moviesstar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.araujos.moviesstar.dto.MovieDTO;
import br.araujos.moviesstar.entity.Movie;
import br.araujos.moviesstar.exceptions.MovieNotFoundException;
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
            // Chame o serviço para buscar o filme por ID no banco de dados
            MovieDTO movie = movieService.getMovieById(id);

            if (movie == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(movie);
        } catch (Exception e) {
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

    @PutMapping("/{movieId}/watched")
    public ResponseEntity<MovieDTO> markMovieAsWatched(@PathVariable Long movieId, @RequestParam boolean watched) {
        try {
            Movie updatedMovie = movieService.markMovieAsWatched(movieId, watched);
            MovieDTO updatedMovieDTO = movieService.convertToMovieDTO(updatedMovie);
            return ResponseEntity.ok(updatedMovieDTO);
        } catch (MovieNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/random")
    public ResponseEntity<MovieDTO> getRandomMovie() {
        MovieDTO movie = movieService.getRandomMovie();
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/duel")
    public ResponseEntity<Void> updateMovieScores(@RequestParam Long winnerId, @RequestParam Long loserId) {
        movieService.updateMovieScores(winnerId, loserId);
        return ResponseEntity.ok().build();
    }
}
