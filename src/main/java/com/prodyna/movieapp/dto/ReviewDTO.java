package com.prodyna.movieapp.dto;

public class ReviewDTO {

    private String title;
    private Integer rating;
    private String description;

    public ReviewDTO(){

    }

    public ReviewDTO(String title, Integer rating, String description) {
        this.title = title;
        this.rating = rating;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
