package com.prodyna.movieapp.repository;

import com.prodyna.movieapp.domain.Review;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ReviewRepository extends Neo4jRepository<Review, Long> {


    @Query("match (m:Movie),(r:Review)\n" +
            "where id(m) = $id\n" +
            "return collect(r)")
    List<Review> getReviewsForMovie(Long id);
}
