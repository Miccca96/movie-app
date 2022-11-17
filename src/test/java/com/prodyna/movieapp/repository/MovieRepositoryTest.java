package com.prodyna.movieapp.repository;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Genre;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.domain.Review;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataNeo4jTest
@Transactional(propagation = Propagation.NEVER)
class MovieRepositoryTest {


    @Autowired
    private MovieRepository movieRepository;


    @AfterEach
    void tearDown(){
        movieRepository.deleteAll();
    }

    private Movie createMovieTest(){
        List<Actor> actors = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        Actor actor = new Actor("Julia", "Roberts", "bio");
        Actor actor1 = new Actor("Adam", "Smith", "bio");
        actors.add(actor1);
        actors.add(actor);
        Review review = new Review(3, "Bad", "Very bad");
        reviews.add(review);
        Movie movie = new Movie("Kiss", "This is teenage movie", Genre.DRAMA, LocalDate.of(2023, 10, 23), Double.valueOf(56.3), actors, reviews);
        return movie;
    }


    @Test
    void shouldPassIfNameExist() {

        Movie movie = createMovieTest();
        Movie createdMovie = movieRepository.save(movie);

        Optional<Movie> movieDatabase = movieRepository.findByNameAndReleaseDate(movie.getName(),movie.getReleaseDate());

        assertThat(movieDatabase.get()).isEqualTo(movie);


    }

    @Test
    void shouldThrowExcIfNameDoesntExist() {

        Assertions.assertThrows(NoSuchElementException.class, () -> movieRepository.findByNameAndReleaseDate("Name example",LocalDate.of(-1,-1,-1)).get());
        //assertNull(movieRepository.findByName("Dont existing name"));

    }

}