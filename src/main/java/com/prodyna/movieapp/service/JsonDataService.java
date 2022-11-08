package com.prodyna.movieapp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.dto.JsonMoviesDTO;
import com.prodyna.movieapp.dto.MovieDTO;
import com.prodyna.movieapp.mapper.Mapper;
import com.prodyna.movieapp.repository.ActorRepository;
import com.prodyna.movieapp.repository.MovieRepository;
import com.prodyna.movieapp.repository.ReviewRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JsonDataService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final Mapper mapper;


    @Autowired
    public JsonDataService(ActorRepository actorRepository, MovieRepository movieRepository, ReviewRepository reviewRepository, Mapper mapper) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
    }
    @Transactional(rollbackFor = Exception.class)
    public void fillDatabase() throws Exception {
        System.out.println("hello");
        JsonMoviesDTO data = new JsonMoviesDTO();

        data = convertJsonToObjects("movies.json", JsonMoviesDTO.class);

        saveData(data);

    }

    private void saveData(JsonMoviesDTO data) throws IOException {



        for (MovieDTO movie : data.getMovies()) {
            Movie m = mapper.mapMovieDTOToMovie(movie);
            Optional<Movie> movie1 = movieRepository.findByNameAndReleaseDate(m.getName(), m.getReleaseDate());
            if(movie1.isPresent())
                continue;
               saveMovie(m);
            }
        }

    private void saveMovie(Movie m) {
        List<Actor> actors = new ArrayList<>();
        for (Actor a:m.getActors()) {
            Optional<Actor> actor = actorRepository.findByFirstNameAndLastName(a.getFirstName(),a.getLastName());
            if(actor.isPresent()){
                Actor actorDB = actor.get();
                actors.add(actorDB);
            }else {
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
