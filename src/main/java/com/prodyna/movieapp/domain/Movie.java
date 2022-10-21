package com.prodyna.movieapp.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Node
public class Movie {

    @Id @GeneratedValue
    private Long id;
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Size(max = 1000)
    private String desc;

    @NotNull
    private Genre genre;

    @NotNull
    private LocalDate relaseDate;
    @NotNull
    private Double durationInMin;
    @Relationship
    private List<Actor> actors;
    @Relationship
    private List<Review> reviews;

    public Movie() {
    }
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Movie(Long id, String name, String desc, Genre genre, LocalDate relaseDate, Double durationInMin, List<Actor> actors, List<Review> reviews, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.genre = genre;
        this.relaseDate = relaseDate;
        this.durationInMin = durationInMin;
        this.actors = actors;
        this.reviews = reviews;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getRelaseDate() {
        return relaseDate;
    }

    public void setRelaseDate(LocalDate relaseDate) {
        this.relaseDate = relaseDate;
    }

    public Double getDurationInMin() {
        return durationInMin;
    }

    public void setDurationInMin(Double durationInMin) {
        this.durationInMin = durationInMin;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", relaseDate=" + relaseDate +
                ", durationInMin=" + durationInMin +
                '}';
    }
}
