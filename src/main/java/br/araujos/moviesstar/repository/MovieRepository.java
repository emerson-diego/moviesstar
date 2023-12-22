package br.araujos.moviesstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.araujos.moviesstar.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
