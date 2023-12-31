package br.araujos.moviesstar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDTO {

    private Long id;
    private String title;
    private String posterPath;
    private String overview;
    private String director;
    private String mainActors;
    private String genreDescription;
    private String posterUrl;

    private String nationality;
    private String trailerUrl;
    private String imdbRating;
    private String releaseDate;

    private String braziliamTitle;

    private String popularity;
    private Integer score;

    private boolean watched;

    private int duelParticipations;

    public MovieDTO(
            Long id,
            String title,
            String posterPath,
            String overview,
            String director,
            String mainActors,
            String genreDescription,
            String posterUrl,
            String nationality,
            String trailerUrl,
            String imdbRating,
            String releaseDate,
            String braziliamTitle,
            String popularity, int duelParticipations) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.director = director;
        this.mainActors = mainActors;
        this.genreDescription = genreDescription;
        this.posterUrl = posterUrl;
        this.nationality = nationality;
        this.trailerUrl = trailerUrl;
        this.imdbRating = imdbRating;
        this.releaseDate = releaseDate;
        this.braziliamTitle = braziliamTitle;
        this.popularity = popularity;
        this.duelParticipations = duelParticipations;
        // Os campos não incluídos no construtor são inicializados com valores padrão
        // (null)
    }

}
