package com.prodyna.movieapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Node
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @Size(max = 1000)
    private String desc;

    @NotNull
    private Genre genre;

    @NotNull
    private Integer releaseDate;
    @NotNull
    private Integer durationMins;

    @Transient
    private Double averageRating;

    @Relationship(type = "ACTS IN", direction = Relationship.Direction.INCOMING)
    private List<Actor> actors;
    @Relationship(type = "HAS",direction = Relationship.Direction.OUTGOING)
    private List<Review> reviews;


    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


    public Movie(String name, String desc, Genre genre, Integer releaseDate, Integer durationInMin, List<Actor> actors, List<Review> reviews) {
        this.name = name;
        this.desc = desc;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.durationMins = durationInMin;
        this.actors = actors;
        this.reviews = reviews;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(name, movie.name);
    }

}
