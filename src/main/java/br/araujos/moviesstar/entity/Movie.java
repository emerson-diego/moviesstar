package br.araujos.moviesstar.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // Construtores, getters e setters

    public Movie() {
    }

    public Movie(String title, String posterPath, String overview) {
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
    }

    public Movie(Long id, String title, String posterPath, String overview) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
    }

    public Movie(Long id, String title, String posterPath, String overview, String director, String mainActors,
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

    public Movie id(Long id) {
        setId(id);
        return this;
    }

    public Movie title(String title) {
        setTitle(title);
        return this;
    }

    public Movie posterPath(String posterPath) {
        setPosterPath(posterPath);
        return this;
    }

    public Movie overview(String overview) {
        setOverview(overview);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) && Objects.equals(title, movie.title)
                && Objects.equals(posterPath, movie.posterPath) && Objects.equals(overview, movie.overview);
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

}
