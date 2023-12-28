package br.araujos.moviesstar.dto;

import java.util.Objects;

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

    // Construtores, getters e setters

    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String posterPath, String overview) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
    }

    public MovieDTO(Long id, String title, String posterPath, String overview, String director, String mainActors,
            String genreDescription, String posterUrl) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.director = director;
        this.mainActors = mainActors;
        this.genreDescription = genreDescription;
        this.posterUrl = posterUrl;
    }

    public MovieDTO(Long id, String title, String posterPath, String overview, String director, String mainActors,
            String genreDescription, String posterUrl, String nationality, String trailerUrl, String imdbRating,
            String releaseDate) {
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
    }

    public MovieDTO(Long id, String title, String posterPath, String overview, String director, String mainActors,
            String genreDescription, String posterUrl, String nationality, String trailerUrl, String imdbRating,
            String releaseDate, String braziliamTitle) {
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
    }

    public MovieDTO(Long id, String title, String posterPath, String overview, String director, String mainActors,
            String genreDescription, String posterUrl, String nationality, String trailerUrl, String imdbRating,
            String releaseDate, String braziliamTitle, String popularity) {
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
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public MovieDTO id(Long id) {
        setId(id);
        return this;
    }

    public MovieDTO title(String title) {
        setTitle(title);
        return this;
    }

    public MovieDTO posterPath(String posterPath) {
        setPosterPath(posterPath);
        return this;
    }

    public MovieDTO overview(String overview) {
        setOverview(overview);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MovieDTO)) {
            return false;
        }
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(id, movieDTO.id) && Objects.equals(title, movieDTO.title)
                && Objects.equals(posterPath, movieDTO.posterPath) && Objects.equals(overview, movieDTO.overview);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, posterPath, overview);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", title='" + getTitle() + "'" +
                ", posterPath='" + getPosterPath() + "'" +
                ", overview='" + getOverview() + "'" +
                "}";
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMainActors() {
        return this.mainActors;
    }

    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }

    public String getGenreDescription() {
        return this.genreDescription;
    }

    public void setGenreDescription(String genreDescription) {
        this.genreDescription = genreDescription;
    }

    public String getPosterUrl() {
        return this.posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTrailerUrl() {
        return this.trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getImdbRating() {
        return this.imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBraziliamTitle() {
        return this.braziliamTitle;
    }

    public void setBraziliamTitle(String braziliamTitle) {
        this.braziliamTitle = braziliamTitle;
    }

    public String getPopularity() {
        return this.popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

}
