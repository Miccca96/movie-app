package com.prodyna.movieapp.dto;

import com.prodyna.movieapp.domain.Genre;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MovieDTO {

    private String name;
    private List<ActorDTO> actors;
    private Integer releaseDate;
    private Double durationMins;
    private String description;
    private List<ReviewDTO> reviews;

    private Genre genre;

    public MovieDTO(){

    }

    public MovieDTO(String name, List<ActorDTO> actors, Integer releaseDate, Double durationMins, String description, List<ReviewDTO> reviews, Genre genre) {
        this.name = name;
        this.actors = actors;
        this.releaseDate = releaseDate;
        this.durationMins = durationMins;
        this.description = description;
        this.reviews = reviews;
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorDTO> actors) {
        this.actors = actors;
    }

    public Integer getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Integer releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getDurationMins() {
        return durationMins;
    }

    public void setDurationMins(Double durationMins) {
        this.durationMins = durationMins;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}
