package br.araujos.moviesstar.batch;

import java.util.Iterator;

import org.springframework.batch.item.ItemReader;

import br.araujos.moviesstar.dto.MovieDTO;
import br.araujos.moviesstar.services.MovieService;

public class CustomReader implements ItemReader<MovieDTO> {

    private Iterator<MovieDTO> movieIterator;

    private final MovieService movieService;
    private boolean initialized = false;

    public CustomReader(MovieService movieService) {
        this.movieService = movieService;
        // movieIterator = movieService.fetchAllMovies().iterator();
    }

    @Override
    public MovieDTO read() throws Exception {

        // Inicializa o iterador na primeira chamada de read()
        if (!initialized) {
            movieIterator = movieService.fetchAllMovies().iterator();
            initialized = true;
        }

        if (movieIterator.hasNext()) {
            MovieDTO movie = movieIterator.next();
            System.out.println("Movie: " + movie.getTitle());
            return movie;
        } else {
            return null; // Retorna null para indicar que não há mais dados a serem lidos
        }
    }

}