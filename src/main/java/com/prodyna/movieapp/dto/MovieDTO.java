package com.prodyna.movieapp.dto;

import com.prodyna.movieapp.domain.Genre;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDTO {

    private String name;
    private List<ActorDTO> actors;
    private Integer releaseDate;
    private Double durationMins;
    private String description;
    private List<ReviewDTO> reviews;

    private Genre genre;


}
