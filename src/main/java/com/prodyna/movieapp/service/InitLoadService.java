package com.prodyna.movieapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.dto.JsonMoviesDTO;
import com.prodyna.movieapp.mapper.Mapper;
import com.prodyna.movieapp.repository.ActorRepository;
import com.prodyna.movieapp.repository.MovieRepository;
import com.prodyna.movieapp.repository.ReviewRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InitLoadService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final Mapper mapper;

    private final ValidationService validationService;

    @Autowired
    public InitLoadService(ActorRepository actorRepository, MovieRepository movieRepository, ReviewRepository reviewRepository, Mapper mapper, ValidationService validationService) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
        this.validationService = validationService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void fillDatabase() throws Exception {
        log.info("Application started and method was invoked");
        JsonMoviesDTO data = new JsonMoviesDTO();

        data = convertJsonToObjects("movies.json", JsonMoviesDTO.class);

        processData(data);

    }

    private void processData(JsonMoviesDTO data) throws IOException {

        List<Movie> movies = data.getMovies().stream().map(m -> mapper.mapMovieDTOToMovie(m)).collect(Collectors.toList());

        movies.stream().forEach(movie -> {
            movie.getActors().forEach(actor -> {
                try {
                    validationService.validate(actor);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        movies.stream().forEach(movieInList -> {
            movieInList.getReviews().forEach(review -> {
                try {
                    validationService.validate(review);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            movies.stream().
                    filter(movieForInsert -> !movieRepository.findByNameAndReleaseDate(movieForInsert.getName(), movieForInsert.getReleaseDate()).isPresent()).
                    forEach(movieForInsert -> saveMovie(movieForInsert));
//           Optional<Movie> movie1 = movieRepository.findByNameAndReleaseDate(m.getName(), m.getReleaseDate());

        });
    }

    ;


//        for (MovieDTO movie : data.getMovies()) {
//            Movie m = mapper.mapMovieDTOToMovie(movie);
//            validationService.validate(m);
//            for (Actor actor:m.getActors()) {
//                validationService.validate(actor);
//            }
//            for (Review review:m.getReviews()){
//                validationService.validate(review);
//            }
//


    private void saveMovie(Movie m) {
        List<Actor> actors = new ArrayList<>();
        for (Actor a : m.getActors()) {
            Optional<Actor> actor = actorRepository.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            if (actor.isPresent()) {
                Actor actorDB = actor.get();
                actors.add(actorDB);
            } else {
                Actor actor2 = actorRepository.save(a);
                actors.add(actor2);
            }
        }
        m.setActors(actors);
        movieRepository.save(m);
    }

    private <T> T convertJsonToObjects(String fileName, Class<T> classType) {
        T t = null;
        File file = new File("src/main/resources/" + fileName);

        try {
            ObjectMapper mapper = new ObjectMapper();
            //mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            t = mapper.readValue(file, classType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }
}
