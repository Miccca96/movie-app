package com.prodyna.movieapp.repository;

import com.prodyna.movieapp.domain.Movie;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface MovieRepository extends Neo4jRepository<Movie,Long> {

    Optional<Movie> findByName(String name);

    Optional<Movie> findByNameAndReleaseDate(String name, LocalDate releaseDate);


}
