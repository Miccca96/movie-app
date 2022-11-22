package com.prodyna.movieapp.mapper;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.domain.Review;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.dto.MovieDTO;
import com.prodyna.movieapp.dto.ReviewDTO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

@Component
public class Mapper {

    private static final Logger log = LoggerFactory.getLogger(Mapper.class);
    private final ValidatorFactory factory;
    private final Validator validator;

    public Mapper(ValidatorFactory factory, Validator validator) {
        this.factory = factory;
        this.validator = validator;
    }

    public Movie mapMovieDTOToMovie(MovieDTO movieDTO) {

        Movie movie = new Movie();
        List<Actor> actors = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        movie.setName(movieDTO.getName());
        movie.setDesc(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDurationMins(movieDTO.getDurationMins());

        movieDTO.getActors().stream().
                forEach(actorDTO -> {
                    Actor actor = new Actor(actorDTO.getFirstName(), actorDTO.getLastName(), actorDTO.getBiography());
                    actors.add(actor);
                });

        movie.setActors(actors);

        movieDTO.getReviews().stream().
                forEach(reviewDTO -> {
                    Review review = new Review(reviewDTO.getRating(), reviewDTO.getTitle(), reviewDTO.getDescription());
                    reviews.add(review);
                });

        movie.setReviews(reviews);

        return movie;
    }

}
