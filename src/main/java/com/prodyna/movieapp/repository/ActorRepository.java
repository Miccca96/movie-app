package com.prodyna.movieapp.repository;

import com.prodyna.movieapp.domain.Actor;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ActorRepository extends Neo4jRepository<Actor,Long> {

    @Query("match (a:Actor)-[:`ACTS IN`]->(m:Movie) where id(m)=$id return a order by id(a)")
    List<Actor> findAllActors(Long id);

    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);
}
