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

//    public Actor mapActorDTOToActor(ActorDTO actorDTO) throws IOException {
//        Actor actor = new Actor(actorDTO.getFirstName(), actorDTO.getLastName(), actorDTO.getBiography());
//
//        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
//
//        if (!violations.isEmpty()) {
//            for (ConstraintViolation violation : violations) {
//                log.warn(violation.getMessage());
//            }
//            throw new IOException("ActorDTO - validation error");
//        }
//
//        return actor;
//    }
    public Movie mapMovieDTOToMovie(MovieDTO movieDTO) throws IOException {

        Movie movie = new Movie();
        List<Actor> actors = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        movie.setName(movieDTO.getName());
        movie.setDesc(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        LocalDate inputDate = LocalDate.of(movieDTO.getReleaseDate(),1,1);
        movie.setReleaseDate(inputDate);
        movie.setDurationInMin(movieDTO.getDurationMins());

//        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
//
//        if (!violations.isEmpty()) {
//            for (ConstraintViolation violation : violations) {
//                log.warn(violation.getMessage());
//            }
//            throw new IOException("MovieDTO - validation error");
//        }
        for (ActorDTO actorDTO:movieDTO.getActors()) {
            Actor a = new Actor(actorDTO.getFirstName(),actorDTO.getLastName(),actorDTO.getBiography());
            actors.add(a);
            
        }
        movie.setActors(actors);

        for (ReviewDTO reviewDTO: movieDTO.getReviews()) {
            Review r = new Review(reviewDTO.getRating(),reviewDTO.getTitle(),reviewDTO.getDescription());
            reviews.add(r);
        }
        movie.setReviews(reviews);

        return movie;
    }
//    public Review mapReviewDTOToReview(ReviewDTO reviewDTO) throws IOException{
//        Review review = new Review(reviewDTO.getRating(),reviewDTO.getTitle(), reviewDTO.getDescription());
//        Set<ConstraintViolation<Review>> violations = validator.validate(review);
//        if(!violations.isEmpty()){
//            for (ConstraintViolation violation:violations) {
//                log.warn(violation.getMessage());
//            }
//            throw new IOException("ReviewDTO - validation error");
//        }
//        return review;
//    }
}
