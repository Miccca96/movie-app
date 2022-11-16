package com.prodyna.movieapp.repository;

import com.prodyna.movieapp.domain.Movie;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;


public interface MovieRepository extends Neo4jRepository<Movie,Long> {

    Optional<Movie> findByName(String name);

    Optional<Movie> findByNameAndReleaseDate(String name, LocalDate releaseDate);

    @Query("match (m:Movie)-[rel]->(r:Review)\n" +
            "where id(m) = $id\n" +
            "detach delete m,rel,r")
    void deleteMovieAndReviews(Long id);

}
