package br.araujos.moviesstar.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import br.araujos.moviesstar.dto.MovieDTO;
import br.araujos.moviesstar.services.MovieService;

public class MovieItemWriter implements ItemWriter<MovieDTO> {

    private MovieService movieService;

    public MovieItemWriter(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void write(Chunk<? extends MovieDTO> chunk) throws Exception {
        List<MovieDTO> movieDTOs = new ArrayList<>();
        for (MovieDTO dto : chunk) {
            movieDTOs.add(dto);
        }
        movieService.saveMovies(movieDTOs);
    }
}