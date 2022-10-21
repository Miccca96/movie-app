package com.prodyna.movieapp.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class Review {

    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    @Min(1)
    @Max(5)
    private Integer rating;
    @NotNull
    @NotBlank
    @Min(3)
    @Size(min = 3,max = 100)
    private String title;

    @Size(max = 1000)
    private String desc;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


    public Review(){
    }

    public Review(Long id, Integer rating, String title, String desc, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.rating = rating;
        this.title = title;
        this.desc = desc;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
