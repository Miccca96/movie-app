package com.prodyna.movieapp.service;

import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.domain.Review;
import com.prodyna.movieapp.dto.ReviewDTO;
import com.prodyna.movieapp.exception.ObjectNotFoundException;
import com.prodyna.movieapp.mapstruct.ReviewMapper;
import com.prodyna.movieapp.repository.MovieRepository;
import com.prodyna.movieapp.repository.ReviewRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final ReviewMapper reviewMapper;


    @Transactional
    public void createReview(Long id, ReviewDTO reviewDTO) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new ObjectNotFoundException(Movie.class.getSimpleName(), id);
        }
        Review review = reviewMapper.mapReviewDTOToReview(reviewDTO);

        Movie moviedb = movie.get();
        moviedb.getReviews().add(review);
        movieRepository.save(moviedb);
    }

    @Transactional
    public void deleteReview(Long movieId, Long reviewId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            throw new ObjectNotFoundException(Movie.class.getSimpleName(), movieId);
        }
        Movie moviedb = movie.get();
        moviedb.getReviews().stream()
                .filter(r -> r.getId().equals(reviewId))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(Review.class.getSimpleName(), reviewId));

        reviewRepository.delete(movieId, reviewId);
    }
}
