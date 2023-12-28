package br.araujos.moviesstar.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import br.araujos.moviesstar.dto.MovieDTO;
import br.araujos.moviesstar.services.MovieService;

public class CustomWriter implements ItemWriter<MovieDTO> {

    private final MovieService movieService;

    public CustomWriter(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void write(Chunk<? extends MovieDTO> chunk) throws Exception {

        if (chunk != null) {
            for (MovieDTO movieDTO : chunk) {
                // Chama o método saveOrUpdateMovie para cada MovieDTO
                movieService.saveOrUpdateMovie(movieDTO);
            }

            System.out.println("Completed writing data.");
        }

    }
}