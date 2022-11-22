package com.prodyna.movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTOPatch {

    private String name;
    private String description;
    private  Integer releaseDate;
    private Integer durationMins;
}
