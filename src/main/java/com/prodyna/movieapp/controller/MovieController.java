package com.prodyna.movieapp.controller;

import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.dto.MovieDTO;
import com.prodyna.movieapp.dto.MovieDTOPatch;
import com.prodyna.movieapp.dto.ReviewDTO;
import com.prodyna.movieapp.service.MovieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/movies")
public class MovieController {

    private final MovieService movieService;


    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<String> createMovie(@RequestBody MovieDTO movieDTO) {
            movieService.createMovie(movieDTO);
            return new ResponseEntity<>("Movie was successfully created", HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<MovieDTO> partialUpdateMovie(@PathVariable Long id, @RequestBody MovieDTOPatch movieDTO) {
        MovieDTO movie = movieService.partialUpdateMovie(id,movieDTO);
        return new ResponseEntity<>(movie,HttpStatus.CREATED);
    }

        @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteMovieWithReviews(@PathVariable Long id){
        movieService.deleteMovieWithReviews(id);
        return new ResponseEntity<>("Movie was successfully deleted",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMoviesByRatings() {
        List<MovieDTO> movies = movieService.getMoviesByRatings();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/actors")
    public ResponseEntity<List<ActorDTO>> getActorsInMovie(@PathVariable Long id) {
        List<ActorDTO> actors = movieService.getActorsInMovie(id);
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsForMovieMovie(@PathVariable Long id) {
        List<ReviewDTO> reviewsDTO = movieService.getReviewsForMovie(id);
        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        MovieDTO movieDTO = movieService.getMovieById(id);
        return new ResponseEntity<>(movieDTO, HttpStatus.OK);
    }



}
