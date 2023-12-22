package br.araujos.dto;

import java.util.Objects;

public class MovieDTO {

    private Long id;
    private String title;
    private String posterPath;
    private String overview;

    // Construtores, getters e setters

    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String posterPath, String overview) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
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

}
