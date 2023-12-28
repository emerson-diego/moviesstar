package br.araujos.moviesstar.batch;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;

import br.araujos.moviesstar.dto.MovieDTO;
import br.araujos.moviesstar.services.MovieService;

public class MovieItemReader implements ItemReader<MovieDTO> {

    private final Iterator<MovieDTO> movieIterator;

    public MovieItemReader(MovieService movieService) {
        // Aqui você deve carregar os dados dos filmes.
        // Por exemplo, fazendo uma chamada de API e convertendo a resposta em uma lista
        // de MovieDTO.
        List<MovieDTO> movies = movieService.fetchAllMovies();
        this.movieIterator = movies.iterator();
    }

    @Override
    public MovieDTO read() {
        if (this.movieIterator.hasNext()) {
            return this.movieIterator.next();
        } else {
            return null; // Retorna null para indicar que não há mais dados a serem lidos
        }
    }

}