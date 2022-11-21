package com.prodyna.movieapp.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Genre;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.dto.MovieDTO;
import com.prodyna.movieapp.dto.MovieDTOPatch;
import com.prodyna.movieapp.exception.MovieAlreadyExist;
import com.prodyna.movieapp.exception.ObjectNotFoundException;
import com.prodyna.movieapp.mapstruct.ActorMapper;
import com.prodyna.movieapp.mapstruct.MovieMapper;
import com.prodyna.movieapp.repository.ActorRepository;
import com.prodyna.movieapp.repository.MovieRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
 class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;

    @Mock
    private ActorMapper actorMapper;
    @Mock
    private ActorService actorService;
    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private MovieService movieService;
    private Movie inception, avengers, inceptionForSaving,inceptionPatch;
    private MovieDTO inceptionDTO, avengersDTO,inceptionPatchDTO;
    private Actor tom, matt;
    private ActorDTO tomDTO, mattDTO;

    private MovieDTOPatch movieDTOPatch;



    private void init() {

        tomDTO = ActorDTO.builder()
                .firstName("Tom")
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

        inceptionDTO = MovieDTO.builder()
                .name("Inception")
                .genre(Genre.ACTION)
                .releaseDate(2010)
                .durationMins(148)
                .description("A thief who steals corporate secrets through the use of dream-sharing" +
                        " technology is given the inverse task of planting an idea into the mind of a" +
                        " C.E.O., but his tragic past may doom the project and his team to disaster.")
                .actors(List.of(mattDTO, tomDTO))
                .averageRating(0.0)
                .build();

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
                .averageRating(0.0)
                .build();

        inceptionForSaving = Movie.builder()
                .name("Inception")
                .genre(Genre.ACTION)
                .releaseDate(2010)
                .durationMins(148)
                .desc("A thief who steals corporate secrets through the use of dream-sharing" +
                        " technology is given the inverse task of planting an idea into the mind of a" +
                        " C.E.O., but his tragic past may doom the project and his team to disaster.")
                .actors(List.of(matt, tom))
                .averageRating(0.0)
                .build();

        inceptionPatch = Movie.builder()
                .name("Inception 2")
                .build();

        inceptionPatchDTO = MovieDTO.builder()
                .name("Inception 2")
                .genre(Genre.ACTION)
                .releaseDate(2010)
                .durationMins(148)
                .description("A thief who steals corporate secrets through the use of dream-sharing" +
                        " technology is given the inverse task of planting an idea into the mind of a" +
                        " C.E.O., but his tragic past may doom the project and his team to disaster.")
                .actors(List.of(mattDTO, tomDTO))
                .averageRating(0.0)
                .build();

        avengersDTO = MovieDTO.builder()
                .name("Inception")
                .genre(Genre.ACTION)
                .releaseDate(2010)
                .durationMins(148)
                .description("A thief who steals corporate secrets through the use of dream-sharing" +
                        " technology is given the inverse task of planting an idea into the mind of a" +
                        " C.E.O., but his tragic past may doom the project and his team to disaster.")
                .actors(List.of(mattDTO, tomDTO))
                .averageRating(5.0)
                .build();

        avengers = Movie.builder()
                .id(1L)
                .name("Inception")
                .genre(Genre.ACTION)
                .releaseDate(2010)
                .durationMins(148)
                .desc("A thief who steals corporate secrets through the use of dream-sharing" +
                        " technology is given the inverse task of planting an idea into the mind of a" +
                        " C.E.O., but his tragic past may doom the project and his team to disaster.")
                .actors(List.of(matt, tom))
                .averageRating(4.0)
                .build();

        movieDTOPatch = MovieDTOPatch.builder()
                .name("Inception 2")
                .build();


    }

    @BeforeEach
    public void setup() {
        init();
    }

    @Test
    public void shouldCreateMovie() {

        Mockito.when(movieMapper.mapMovieDTOToMovie(inceptionDTO)).thenReturn(inceptionForSaving);

        Mockito.when(movieRepository.findByNameAndReleaseDate(inceptionForSaving.getName(), inceptionForSaving.getReleaseDate())).thenReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> movieRepository.findByNameAndReleaseDate(inceptionDTO.getName(), inceptionDTO.getReleaseDate()));

        Mockito.when(actorRepository.findById(-20L)).thenReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> actorRepository.findById(-20L));

        Mockito.when(movieRepository.save(inceptionForSaving)).thenReturn(inception);

        Assertions.assertDoesNotThrow(() -> movieService.createMovie(inceptionDTO));


    }

    @Test
    void shouldReturnMoviesSortByRating() {

        List<Movie> movieList = List.of(inception, avengers).stream().sorted(Comparator.comparing(Movie::getAverageRating)).collect(Collectors.toList());

        given(movieRepository.findAll()).willReturn(movieList);

        List<MovieDTO> movieDTOS = List.of(inceptionDTO, avengersDTO).stream().sorted(Comparator.comparing(MovieDTO::getAverageRating)).collect(Collectors.toList());

        given(movieMapper.mapListOfMoviesToListOfMoviesDTOs(movieList)).willReturn(movieDTOS);

        List<MovieDTO> movies = movieService.getMoviesByRatings();
        Assertions.assertNotNull(movieDTOS);
        Assertions.assertEquals(2, movieDTOS.size());

    }
    @Test
    void shouldGetAllActorsInMovie(){

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(inception));
        Mockito.when(actorRepository.findAllActors(1L)).thenReturn(List.of(matt, tom));
        Mockito.when(actorMapper.mapListOfActorsToListOfActorDTOs(List.of(matt,tom))).thenReturn(List.of(mattDTO,tomDTO));
        List<ActorDTO> actorDTOS = movieService.getActorsInMovie(1L);
        Assertions.assertEquals(2,actorDTOS.size());
    }

    @Test
    void shouldGetMovieById(){

        when(movieRepository.findById(1L)).thenReturn(Optional.of(inception));
        when(movieMapper.mapMovieToMovieDTO(inception)).thenReturn(inceptionDTO);
        Assertions.assertNotNull(movieService.getMovieById(1L));


    }
    @Test
    void shouldTrowExcIfMovieDoesntExist(){

        when(movieRepository.findById(-100l)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class,()->movieService.getMovieById(-100l));

    }
    @Test
    void shouldDeleteMovieWithReviews(){

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(inception));
        Assertions.assertDoesNotThrow(() -> movieService.deleteMovieWithReviews(1L));

    }

    @Test
    void shouldPatchMovie(){

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(inception));
        boolean needUpdate = true;
        Mockito.when(movieRepository.save(inception)).thenReturn(inceptionPatch);
        Mockito.when(movieMapper.mapMovieToMovieDTO(inception)).thenReturn(inceptionPatchDTO);
        MovieDTO patchedMovie = movieService.partialUpdateMovie(1L,movieDTOPatch);
        Assertions.assertEquals(inceptionPatchDTO,patchedMovie);
    }



}

