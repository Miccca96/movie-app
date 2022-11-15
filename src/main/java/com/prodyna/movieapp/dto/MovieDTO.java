package com.prodyna.movieapp.dto;

import com.prodyna.movieapp.domain.Genre;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDTO {

    @NotBlank
    private String name;
    private List<ActorDTO> actors;
    @NotNull
    private Integer releaseDate;
    @NotNull
    private Double durationMins;
    @Size(max = 1000)
    private String description;
    private List<ReviewDTO> reviews;
    @NotNull
    private Genre genre;
    private Double averageRating;

}
