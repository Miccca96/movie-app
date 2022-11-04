package com.prodyna.movieapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Genre;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.domain.Review;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataNeo4jTest
@Transactional(propagation = Propagation.NEVER)
public class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    private static final Logger log = LoggerFactory.getLogger(
            ActorRepositoryTest.class);

    @AfterEach
    void tearDown() {
        actorRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    @DisplayName("Should find actors by movie ID")
    void shouldFindActorsByMovieID() {
        List<Actor> resultActors = new ArrayList<>();
        List<Actor> localActors = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        //given
        Actor actor = new Actor("Julia", "Roberts", "bio");
        Actor actor1 = new Actor("Adam", "Smith", "bio");
        actors.add(actor1);
        actors.add(actor);

        Review review = new Review(3, "Bad", "Very bad");
        reviews.add(review);
        Movie movie = new Movie("Kiss", "This is teenage movie", Genre.DRAMA, LocalDate.of(2023, 10, 23), Double.valueOf(56.3), actors, reviews);
        Movie createdMovie = movieRepository.save(movie);
        //when
        log.info("Movie id {}", createdMovie.getId());
        resultActors = actorRepository.findAllActors(createdMovie.getId());


        //then

        localActors = createdMovie.getActors();
        assertThat(resultActors).isEqualTo(localActors);


    }

    @Test
    void shouldReturnEmprtyIfMovieIdDoesntExist() {

        List<Actor> allActors = actorRepository.findAllActors(23l);
        assertEquals(new ArrayList<>(), allActors);

    }


}