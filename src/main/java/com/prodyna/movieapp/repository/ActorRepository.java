package com.prodyna.movieapp.repository;

import com.prodyna.movieapp.domain.Actor;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ActorRepository extends Neo4jRepository<Actor,Long> {

}
