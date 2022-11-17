package com.prodyna.movieapp.service;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.dto.MovieDTO;
import com.prodyna.movieapp.dto.ReviewDTO;
import com.prodyna.movieapp.exception.ObjectAlreadyExistException;
import com.prodyna.movieapp.exception.ObjectNotFoundException;
import com.prodyna.movieapp.mapstruct.ActorMapper;
import com.prodyna.movieapp.mapstruct.MovieMapper;
import com.prodyna.movieapp.mapstruct.ReviewMapper;
import com.prodyna.movieapp.repository.ActorRepository;
import com.prodyna.movieapp.repository.MovieRepository;
import com.prodyna.movieapp.repository.ReviewRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    MovieRepository movieRepository;
    ActorRepository actorRepository;
    ReviewRepository reviewRepository;
    ActorMapper actorMapper;
    MovieMapper movieMapper;
    ReviewMapper reviewMapper;

    @Autowired
    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, ReviewRepository reviewRepository, ActorMapper actorMapper, MovieMapper movieMapper, ReviewMapper reviewMapper) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.reviewRepository = reviewRepository;
        this.actorMapper = actorMapper;
        this.movieMapper = movieMapper;
        this.reviewMapper = reviewMapper;
    }


    public void createMovie(MovieDTO movieDTO) {
        Movie movie = movieMapper.mapMovieDTOToMovie(movieDTO);
        Optional<Movie> foundMovie = movieRepository.findByNameAndReleaseDate(movie.getName(), movie.getReleaseDate());
        if (foundMovie.isPresent()) {
            throw new ObjectAlreadyExistException(Movie.class.getSimpleName(), movie.getId());
        }
        List<Actor> actors = new ArrayList<>();
        for (Actor a : movie.getActors()) {
            Optional<Actor> actor = actorRepository.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            if (actor.isPresent()) {
                Actor actorDB = actor.get();
                actors.add(actorDB);
            } else {
                Actor actor2 = actorRepository.save(a);
                actors.add(actor2);
            }
        }
        movie.setActors(actors);
        movieRepository.save(movie);
    }

    public void updateMovie(Long id, Movie movie) {
        Optional<Movie> m = movieRepository.findById(id);
        if (m.isPresent()) {
            Movie movie1 = m.get();
            movie1.setName(movie.getName());
            movie1.setDesc(movie.getDesc());
            movie1.setGenre(movie.getGenre());
            movie1.setActors(movie.getActors());
            movie1.setReviews(movie.getReviews());
            movieRepository.save(movie1);
        }
        ;
        System.out.println("Movie with id:" + id + "dont exist in database");
    }

    //    public void deleteMovie(Long id) {
//        movieRepository.deleteById(id);
//    }
//
//    public List<Movie> getMoviesByRatings() {
//    }
//
    public List<ActorDTO> getActorsInMovie(Long id) {
        Optional<Movie> m = movieRepository.findById(id);
        if (m.isPresent()) {
            List<Actor> actors = actorRepository.findAllActors(id);
            List<ActorDTO> actorDTOS = actorMapper.mapListOfActorsToListOfActorDTOs(actors);
            return actorDTOS;
        }
        throw new ObjectNotFoundException(Movie.class.getSimpleName(), id);
    }
//
//    public List<Review> getReviewsForMovie(Long id) {
//    }

    public Optional<Movie> findMovieByNameAndReleaseDate(String name, LocalDate releaseDate) {
        return movieRepository.findByNameAndReleaseDate(name, releaseDate);
    }

    public List<ReviewDTO> getReviewsForMovie(Long id) {
        Optional<Movie> m = movieRepository.findById(id);
        if (m.isPresent()) {
            List<ReviewDTO> reviewsDTO = reviewRepository.getReviewsForMovie(id).stream()
                    .map(r -> {
                        return reviewMapper.mapReviewToReviewDTO(r);
                    }).collect(Collectors.toList());
            return reviewsDTO;
        }
        throw new ObjectNotFoundException(Movie.class.getSimpleName(),id);
    }

    public List<MovieDTO> getMoviesByRatings() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDTOS = movieMapper.mapListOfMoviesToListOfMoviesDTOs(movies).stream().
                sorted(Comparator.comparing(MovieDTO::getAverageRating).reversed()).collect(Collectors.toList());
        return movieDTOS;
    }

    public MovieDTO getMovieById(Long id) {
        Optional<Movie> movie =movieRepository.findById(id);
        if(movie.isPresent()){
        return movieMapper.mapMovieToMovieDTO(movie.get());
    }
        throw new ObjectNotFoundException(Movie.class.getSimpleName(),id);
    }

    public void deleteMovieWithReviews(Long id) {

    }
}
