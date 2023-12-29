package br.araujos.moviesstar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Movie {
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(length = 1000) // Ajuste o tamanho conforme necessário
    private String overview;

    private String director;
    private String mainActors; // Pode ser uma String JSON ou usar uma abordagem diferente
    private String genreDescription;
    private String posterUrl;

    private String nationality;
    private String trailerUrl;
    private String imdbRating;
    private String releaseDate;

    private String braziliamTitle;

    private String popularity;

    private Integer score;

}
