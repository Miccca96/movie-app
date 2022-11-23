package com.prodyna.movieapp.service;

import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.domain.Review;
import com.prodyna.movieapp.dto.ReviewDTO;
import com.prodyna.movieapp.exception.ObjectNotFoundException;
import com.prodyna.movieapp.mapstruct.ReviewMapper;
import com.prodyna.movieapp.repository.MovieRepository;
import com.prodyna.movieapp.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.reviewMapper = reviewMapper;
    }

    public void createReview(Long id, ReviewDTO reviewDTO) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            Review review = reviewMapper.mapReviewDTOToReview(reviewDTO);
            Movie moviedb = movie.get();
            moviedb.getReviews().add(review);
            movieRepository.save(moviedb);
        } else {
            throw new ObjectNotFoundException(Movie.class.getSimpleName(), id);
        }
    }

    public void deleteReview(Long movieId, Long reviewId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            Movie moviedb = movie.get();
            Optional.of(moviedb.getReviews().stream()
                    .filter(r -> r.getId().equals(reviewId)).findFirst()
                    .orElseThrow(() -> new ObjectNotFoundException(Review.class.getSimpleName(), reviewId)));

            reviewRepository.delete(movieId, reviewId);
        } else {
            throw new ObjectNotFoundException(Movie.class.getSimpleName(), movieId);
        }
    }
}
