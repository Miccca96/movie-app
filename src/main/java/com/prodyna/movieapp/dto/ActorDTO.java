package com.prodyna.movieapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActorDTO {


    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Size(max = 1000)
    private String biography;

}
