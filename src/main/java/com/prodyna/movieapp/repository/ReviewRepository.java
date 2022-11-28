package com.prodyna.movieapp.repository;

import com.prodyna.movieapp.domain.Review;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ReviewRepository extends Neo4jRepository<Review, Long> {


    @Query("MATCH (m:Movie)-[rel]->(r:Review)\n" +
            "WHERE id(m) = $id\n" +
            "return collect(r)")
    List<Review> getReviewsForMovie(Long id);

    @Query("MATCH (m:Movie)-[rel]->(r:Review)\n" +
            "WHERE id(m) = $movieId AND id(r) = $reviewId\n" +
            "delete rel,r")
    void delete(Long movieId,Long reviewId);
}
