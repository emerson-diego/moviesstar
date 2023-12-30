package br.araujos.moviesstar.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.araujos.moviesstar.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m ORDER BY m.score DESC, m.imdbRating DESC")
    List<Movie> findTop25ByOrderByScoreDescImdbRatingDesc(Pageable pageable);

    @Query("SELECT m FROM Movie m ORDER BY RANDOM()")
    List<Movie> findRandomMovies(Pageable pageable);
}
