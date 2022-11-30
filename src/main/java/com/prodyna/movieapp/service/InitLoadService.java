package com.prodyna.movieapp.service;

import ch.qos.logback.core.pattern.ConverterUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.Resource;

@Service
@Slf4j
public class InitLoadService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final Mapper mapper;

    private final ValidationService validationService;

    private static final String path = "src/main/resources/";

    private final Resource resourceFile;

    @Autowired
    public InitLoadService(ActorRepository actorRepository, MovieRepository movieRepository, ReviewRepository reviewRepository, Mapper mapper, ValidationService validationService,@Value("classpath:movies.json") Resource resourceFile) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
        this.validationService = validationService;
        this.resourceFile = resourceFile;
    }

    @Transactional(rollbackFor = Exception.class)
    public void fillDatabase() throws Exception {
        log.info("Application started and method was invoked");
        JsonMoviesDTO data = new JsonMoviesDTO();

        data = convertJsonToObjects(resourceFile, JsonMoviesDTO.class);

        processData(data);

    }

    private void processData(JsonMoviesDTO data) throws IOException {

        List<Movie> movies = data.getMovies().stream().
                map(m -> mapper.mapMovieDTOToMovie(m)).
                collect(Collectors.toList());

        
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
        });
    }

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


    public <T> T convertJsonToObjects(Resource resourceFile, Class<T> classType) throws IOException {
        T target = null;

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        target = mapper.readValue(resourceFile.getInputStream(), classType);

        return target;
    }
}
