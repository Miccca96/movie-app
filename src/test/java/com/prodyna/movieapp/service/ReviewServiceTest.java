package com.prodyna.movieapp.service;

import static org.mockito.BDDMockito.willDoNothing;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Genre;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    MovieRepository movieRepository;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    ReviewMapper reviewMapper;
    @InjectMocks
    ReviewService reviewService;
    Movie inception;
    Actor matt, tom;
    ReviewDTO newReviewDTO;
    Review good, bad, newReview;
    List<Review> reviews = new ArrayList<>();

    private void init() {
        tom = Actor.builder()
                .id(1L)
                .firstName("Tom")
                .lastName("Cruise")
                .biography("Thomas Cruise Mapother IV (born July 3, 1962), known professionally as Tom Cruise, " +
                        "is an American actor and producer. One of the world's highest-paid actors, he has received " +
                        "various " +
                        "accolades, including an Honorary Palme d'Or and three Golden Globe Awards, in addition to" +
                        " nominations for three Academy Awards.")
                .build();

        matt = Actor.builder()
                .id(2L)
                .firstName("Matt")
                .lastName("Damon")
                .biography("Matthew Paige Damon is an American actor, film producer, and screenwriter. Ranked among" +
                        " Forbes' most bankable stars, the films in which he has appeared have collectively earned" +
                        " over$3.88 billion at the North American box office, making him one of the" +
                        " highest-grossing actors of all time.")
                .build();

        good = Review.builder()
                .id(1L)
                .title("It's a good movie")
                .rating(5)
                .build();

        bad = Review.builder()
                .id(2L)
                .title("It's horrible movie")
                .rating(2)
                .build();

        newReview = Review.builder()
                .id(3L)
                .title("It's awsome movie")
                .rating(5)
                .build();

        newReviewDTO = ReviewDTO.builder()
                .id(3L)
                .title("It's awsome movie")
                .rating(5)
                .build();

        reviews.add(good);

        reviews.add(bad);

        inception = Movie.builder()
                .id(1L)
                .name("Inception")
                .genre(Genre.ACTION)
                .releaseDate(2010)
                .durationMins(148)
                .desc("A thief who steals corporate secrets through the use of dream-sharing" +
                        " technology is given the inverse task of planting an idea into the mind of a" +
                        " C.E.O., but his tragic past may doom the project and his team to disaster.")
                .actors(List.of(matt, tom))
                .averageRating(3.5)
                .reviews(reviews)
                .build();

    }

    @BeforeEach
    public void setup() {
        init();
    }

    @Test
    public void shouldCreateReview() {

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(inception));
        Mockito.when(reviewMapper.mapReviewDTOToReview(newReviewDTO)).thenReturn(newReview);
        Assertions.assertDoesNotThrow(() -> reviewService.createReview(1L, newReviewDTO));
    }

    @Test
    public void shouldDeleteReview() {

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(inception));
        willDoNothing().given(reviewRepository).delete(1L, 2L);
        Assertions.assertDoesNotThrow(() -> reviewService.deleteReview(1L, 2L));
    }

    @Test
    public void shouldThrowExcWhenMovieIdDoesntExistWhenTryingToPostReview() {

        Mockito.when(movieRepository.findById(-100L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> reviewService.createReview(-100L, newReviewDTO));

    }

    @Test
    public void shouldThrowExWhenMovieIdDoesntExistWhenTryingToDeleteReview() {
        Mockito.when(movieRepository.findById(-100L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> reviewService.deleteReview(-100L, 2L));
    }

    @Test
    public void shouldThrowExWhenReviewIdDoesntExistWhenTryingToDeleteReview() {
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(inception));
        Assertions.assertThrows(ObjectNotFoundException.class, () -> reviewService.deleteReview(1L, -5L));
    }
}