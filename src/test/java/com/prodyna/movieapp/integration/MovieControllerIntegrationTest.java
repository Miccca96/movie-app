package com.prodyna.movieapp.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.prodyna.movieapp.domain.Genre;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.dto.MovieDTO;
import com.prodyna.movieapp.dto.MovieDTOPatch;
import com.prodyna.movieapp.dto.ReviewDTO;
import com.prodyna.movieapp.repository.ActorRepository;
import com.prodyna.movieapp.repository.MovieRepository;
import com.prodyna.movieapp.repository.ReviewRepository;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ReviewRepository reviewRepository;
    MovieDTO inceptionDTO;
    ActorDTO mattDTO, tomDTO;
    ReviewDTO bad;
    Movie inception;
    MovieDTOPatch movieDTOPatch;
    ObjectMapper objectMapper;
    ObjectWriter objectWriter;

    @Container
    static Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:4.0").withStartupTimeout(Duration.ofMinutes(5));

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    private void init() {

        objectMapper = new ObjectMapper();

        objectWriter = objectMapper.writer();

        tomDTO = ActorDTO.builder()
                .firstName("Tomy")
                .lastName("Cruise")
                .biography("Thomas Cruise Mapother IV (born July 3, 1962), known professionally as Tom Cruise, is an " +
                        "American actor and producer. One of the world's highest-paid actors, he has received " +
                        "various accolades, including an Honorary Palme d'Or and three Golden Globe Awards," +
                        " in addition to nominations for three Academy Awards.")
                .build();

        mattDTO = ActorDTO.builder()
                .firstName("Matt")
                .lastName("Damon")
                .biography("Matthew Paige Damon is an American actor, film producer, and screenwriter. " +
                        "Ranked among Forbes' most bankable stars, the films in which he has appeared have " +
                        "collectively earned over $3.88 billion at the North American box office, making him one " +
                        "of the highest-grossing actors of all time.")
                .build();

        bad = ReviewDTO.builder()
                .title("Very bad movie")
                .rating(1)
                .build();

        inceptionDTO = MovieDTO.builder()
                .name("Inception 2")
                .genre(Genre.ACTION)
                .releaseDate(2010)
                .durationMins(148)
                .description("A thief who steals corporate secrets through the use of dream-sharing" +
                        " technology is given the inverse task of planting an idea into the mind of a" +
                        " C.E.O., but his tragic past may doom the project and his team to disaster.")
                .actors(List.of(mattDTO, tomDTO))
                .reviews(List.of(bad))
                .build();

        movieDTOPatch = MovieDTOPatch.builder()
                .name("Inception 3")
                .build();

        inception = movieRepository.save(Movie.builder()
                .name("Inception 2")
                .genre(Genre.ACTION)
                .releaseDate(2019)
                .durationMins(148)
                .desc("A thief who steals corporate secrets through the use of dream-sharing" +
                        " technology is given the inverse task of planting an idea into the mind of a" +
                        " C.E.O., but his tragic past may doom the project and his team to disaster.")
                .build());
    }

    public void emptyDatabase() {

        movieRepository.deleteAll();
        actorRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        emptyDatabase();
        init();
    }

    @Test
    void shouldCreateMovie() throws Exception {

        ResultActions actorResultActions = mockMvc.perform(post("/api/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(tomDTO)));

        actorResultActions.andExpect(status().isOk());

        ResultActions movieSaveResult = mockMvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(inceptionDTO)));

        movieSaveResult.andExpect(status().isOk());
    }

    @Test
    void shouldPatchMovieSuccessfully() throws Exception {

        String content = objectWriter.writeValueAsString(movieDTOPatch);

        ResultActions movieSaveResult = mockMvc.perform(patch("/api/movies/" + inception.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        movieSaveResult.andExpect(status().isCreated());
    }

    @Test
    void shouldGetAllMoviesSuccessfully() throws Exception {
        ResultActions allMovies = mockMvc.perform(MockMvcRequestBuilders.get("/api/movies")
                .contentType(MediaType.APPLICATION_JSON));

        allMovies.andExpect(status().isOk());
    }


}