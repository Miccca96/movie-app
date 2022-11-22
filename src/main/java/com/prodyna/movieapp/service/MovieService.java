package com.prodyna.movieapp.service;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.dto.MovieDTO;
import com.prodyna.movieapp.dto.MovieDTOPatch;
import com.prodyna.movieapp.dto.ReviewDTO;
import com.prodyna.movieapp.exception.ObjectNotFoundException;
import com.prodyna.movieapp.mapstruct.ActorMapper;
import com.prodyna.movieapp.mapstruct.MovieMapper;
import com.prodyna.movieapp.mapstruct.ReviewMapper;
import com.prodyna.movieapp.repository.ActorRepository;
import com.prodyna.movieapp.repository.MovieRepository;
import com.prodyna.movieapp.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
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
            Movie movieDB = foundMovie.get();
            throw new RuntimeException("Movie " + movieDB.getName() + " released in " + movieDB.getReleaseDate() + " already exist in database");
        }
        List<Actor> actors = new ArrayList<>();
        for (Actor a : movie.getActors()) {
            Optional<Actor> actor = actorRepository.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            if (actor.isPresent()) {
                Actor actorDB = actor.get();
                actors.add(actorDB);
            } else {
                Actor newActor = actorRepository.save(a);
                actors.add(newActor);
            }
        }
        movie.setActors(actors);
        movieRepository.save(movie);
    }

    public MovieDTO updateMovie(Long id, Movie movie) {
        Optional<Movie> m = movieRepository.findById(id);
        if (m.isPresent()) {
            Movie movieDB = m.get();
            movieDB.setName(movie.getName());
            movieDB.setDesc(movie.getDesc());
            movieDB.setGenre(movie.getGenre());
            Movie updatedMovie = movieRepository.save(movieDB);
            return movieMapper.mapMovieToMovieDTO(updatedMovie);
        }
        throw new ObjectNotFoundException(Movie.class.getSimpleName(), id);
    }

    public List<ActorDTO> getActorsInMovie(Long id) {
        Optional<Movie> m = movieRepository.findById(id);
        if (m.isPresent()) {
            List<Actor> actors = actorRepository.findAllActors(id);
            List<ActorDTO> actorDTOS = actorMapper.mapListOfActorsToListOfActorDTOs(actors);
            return actorDTOS;
        }
        throw new ObjectNotFoundException(Movie.class.getSimpleName(), id);
    }

    public Optional<Movie> findMovieByNameAndReleaseDate(String name, Integer releaseDate) {
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
        throw new ObjectNotFoundException(Movie.class.getSimpleName(), id);
    }

    public List<MovieDTO> getMoviesByRatings() {
        List<Movie> movies = movieRepository.findAll();
        log.info("Movies + " + movies);
        List<MovieDTO> movieDTOS = movieMapper.mapListOfMoviesToListOfMoviesDTOs(movies);
        movieDTOS = movieDTOS.stream().
                sorted(Comparator.comparing(MovieDTO::getAverageRating).reversed()).collect(Collectors.toList());
        return movieDTOS;
    }

    public MovieDTO getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return movieMapper.mapMovieToMovieDTO(movie.get());
        }
        throw new ObjectNotFoundException(Movie.class.getSimpleName(), id);
    }

    public void deleteMovieWithReviews(Long id) {

        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            movieRepository.deleteMovieAndReviews(id);
        } else {
            throw new ObjectNotFoundException(Movie.class.getSimpleName(), id);
        }
    }

    public MovieDTO partialUpdateMovie(Long id, MovieDTOPatch movieDTO) {

        boolean needUpdate = false;
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            Movie moviedb = movie.get();
            if (!StringUtils.isBlank(movieDTO.getDescription())) {
                moviedb.setDesc(movieDTO.getDescription());
                needUpdate = true;
            }
            if (!StringUtils.isBlank(movieDTO.getName())) {
                moviedb.setName(movieDTO.getName());
                needUpdate = true;
            }

            if (!ObjectUtils.isEmpty(movieDTO.getReleaseDate())) {
                moviedb.setReleaseDate(movieDTO.getReleaseDate());
                needUpdate = true;
            }

            if (!ObjectUtils.isEmpty(movieDTO.getDurationMins())) {
                moviedb.setDurationMins(movieDTO.getDurationMins());
                needUpdate = true;
            }

            if (needUpdate) {
                Movie patchedMovie = movieRepository.save(moviedb);
                return movieMapper.mapMovieToMovieDTO(patchedMovie);
            }
            return movieMapper.mapMovieToMovieDTO(moviedb);
        }
        throw new ObjectNotFoundException(Movie.class.getSimpleName(), id);
    }
}
