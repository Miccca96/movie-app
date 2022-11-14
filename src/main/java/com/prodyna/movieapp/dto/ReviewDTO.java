package com.prodyna.movieapp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class ReviewDTO {

    @NotBlank
    @Size(min = 3,max = 100)
    private String title;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;
    @Size(max = 1000)
    private String description;

}
