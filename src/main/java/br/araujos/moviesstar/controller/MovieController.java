package br.araujos.moviesstar.controller;

import java.io.IOException;
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
        try {
            List<MovieDTO> movies = movieService.fetchAllMovies();
            return ResponseEntity.ok(movies);
        } catch (IOException e) {
            // Aqui você pode retornar um objeto de erro personalizado se preferir
            return ResponseEntity.status(500).body("Erro ao buscar filmes: " + e.getMessage());
        }
    }

    @PostMapping("/savemovies")
    public String saveMovies() {
        try {
            List<MovieDTO> movies = movieService.fetchAllMovies();
            movieService.saveMovies(movies);
            return "Filmes salvos com sucesso";
        } catch (IOException e) {
            return "Erro ao buscar ou salvar filmes: " + e.getMessage();
        }
    }
}
