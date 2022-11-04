package com.prodyna.movieapp.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private LocalDate releaseDate;
    @NotNull
    private Double durationInMin;

    @Transient
    private Double averageRating;


    @Relationship(type = "ACTS IN", direction = Relationship.Direction.INCOMING)
    private List<Actor> actors;
    @Relationship(type = "HAS",direction = Relationship.Direction.OUTGOING)
    private List<Review> reviews;

    public Movie() {
    }
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;



    public void calculateAverageRating() {
        double sum = 0;
        int count = 0;
        for (Review r:reviews
             ) {
            sum += r.getRating();
            count++;
        }
        if(count == 0)
            this.averageRating = Double.valueOf(0);
        this.averageRating = Double.valueOf((double) sum/count);
    }

    public Movie(String name, String desc, Genre genre, LocalDate releaseDate, Double durationInMin, List<Actor> actors, List<Review> reviews) {
        this.name = name;
        this.desc = desc;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.durationInMin = durationInMin;
        this.actors = actors;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getAverageRating() {
        return averageRating;
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
        return releaseDate;
    }

    public void setRelaseDate(LocalDate relaseDate) {
        this.releaseDate = relaseDate;
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
                ", relaseDate=" + releaseDate +
                ", durationInMin=" + durationInMin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(name, movie.name);
    }

}
