package com.prodyna.movieapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class JsonMoviesDTO {


    List<MovieDTO> movies = new ArrayList<>();

    public JsonMoviesDTO() {
    }

    public JsonMoviesDTO(List<MovieDTO> movies) {
        this.movies = movies;
    }

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovieDTOS(List<MovieDTO> movies) {
        this.movies = movies;
    }
}
