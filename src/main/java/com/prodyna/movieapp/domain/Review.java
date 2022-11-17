package com.prodyna.movieapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@NoArgsConstructor
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    @Size(min = 3,max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Review(Integer rating, String title, String description) {
        this.rating = rating;
        this.title = title;
        this.description = description;
    }


}
