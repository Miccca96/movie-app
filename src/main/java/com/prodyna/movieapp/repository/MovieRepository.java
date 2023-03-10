package com.prodyna.movieapp.repository;

import com.prodyna.movieapp.domain.Movie;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;


public interface MovieRepository extends Neo4jRepository<Movie,Long> {

    Optional<Movie> findByNameAndReleaseDate(String name, Integer releaseDate);

    @Query("match (m:Movie)\n" +
            "where id(m) = $id\n" +
            "optional match (m:Movie)-[rel]->(r:Review)\n" +
            "detach delete m,rel,r")
    void deleteMovieAndReviews(Long id);

}
