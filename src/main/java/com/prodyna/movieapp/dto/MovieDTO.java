package com.prodyna.movieapp.dto;

import com.prodyna.movieapp.domain.Genre;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieDTO {
    @Id
    private Long id;
    @NotBlank
    private String name;
    private List<ActorDTO> actors;
    @NotNull
    private  Integer releaseDate;
    @NotNull
    private Integer durationMins;
    @Size(max = 1000)
    private String description;
    private List<ReviewDTO> reviews;
    @NotNull
    private Genre genre;
    private Double averageRating;

}
