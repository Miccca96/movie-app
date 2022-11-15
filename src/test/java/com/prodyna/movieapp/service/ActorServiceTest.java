package com.prodyna.movieapp.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.exception.ActorNotFoundException;
import com.prodyna.movieapp.mapstruct.ActorMapper;
import com.prodyna.movieapp.repository.ActorRepository;
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
class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;
    @Mock
    private ActorMapper actorMapper;

    @InjectMocks
    private ActorService actorService;
    private ActorDTO actorDTO;
    private Actor savedActor;

    private ActorDTO savedActorDTO;

    private Actor actorForSaving;
    private Actor actorTom;

    private Actor updateActor;

    private Actor updatedActor;

    private ActorDTO updatedActorDTO;

    private void init() {
        actorDTO = ActorDTO.builder()
                .firstName("Klara")
                .lastName("Mitrovic")
                .biography("Java developer")
                .build();

        savedActor = Actor.builder()
                .id(1L)
                .firstName("Klara")
                .lastName("Mitrovic")
                .biography("Java developer")
                .build();

        savedActorDTO = ActorDTO.builder()
                .id(1L)
                .firstName("Klara")
                .lastName("Mitrovic")
                .biography("Java developer")
                .build();

        actorForSaving = Actor.builder()
                .firstName("Klara")
                .lastName("Mitrovic")
                .biography("Java developer")
                .build();

        actorTom = Actor.builder()
                .firstName("Tom")
                .lastName("Cruise")
                .biography("Tom Cruise is american star")
                .build();

        updateActor = Actor.builder()
                .firstName("Kleo")
                .lastName("Morgan")
                .biography("This is my biography")
                .build();

        updatedActor = Actor.builder()
                .id(1L)
                .firstName("Kleo")
                .lastName("Morgan")
                .biography("This is my biography")
                .build();

        updatedActorDTO = ActorDTO.builder()
                .firstName("Kleo")
                .lastName("Morgan")
                .biography("This is my biography")
                .build();

    }

    @BeforeEach
    public void setup() {
        init();
    }

    @Test
    void shouldPostActor() {

        Mockito.when(actorMapper.mapActorDTOToActor(actorDTO)).thenReturn(actorForSaving);

        Actor actorForMapping = actorMapper.mapActorDTOToActor(actorDTO);
        Assertions.assertEquals(actorForMapping, actorForSaving);

        when(actorRepository.save(actorForSaving)).thenReturn(savedActor);
        when(actorMapper.mapActorToActorDTO(savedActor)).thenReturn(savedActorDTO);

        ActorDTO actorDB = actorService.createActor(actorDTO);

        Assertions.assertDoesNotThrow(() -> {
            actorService.createActor(actorDTO);
        });
        Assertions.assertNotNull(actorDB);
        Assertions.assertEquals(savedActorDTO, actorDB);

    }

    @Test
    void shouldReturnActorsSortByName() {

        List<Actor> actorList = List.of(actorForSaving, actorTom).stream().sorted(Comparator.comparing(Actor::getFirstName)).collect(Collectors.toList());

        given(actorRepository.findAllActorsSortByName()).willReturn(actorList);

        List<ActorDTO> actors = actorService.getActorsSortByName();

        Assertions.assertNotNull(actors);
        Assertions.assertEquals(2, actors.size());

    }

    @Test
    void shouldUpdateActor() {

        given(actorRepository.findById(1L)).willReturn(Optional.of(savedActor));

        given(actorRepository.save(updatedActor)).willReturn(updatedActor);
        given(actorMapper.mapActorToActorDTO(updatedActor)).willReturn(updatedActorDTO);

        try {
            ActorDTO actorUpdated = actorService.updateActor(1L, updatedActorDTO);
            Assertions.assertNotNull(actorUpdated);
            Assertions.assertEquals(actorUpdated, updatedActorDTO);
        } catch (ActorNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void shouldDeleteActor() {

        given(actorRepository.findById(1L)).willReturn(Optional.of(savedActor));
        willDoNothing().given(actorRepository).deleteById(1L);

        try {
            actorService.deleteActor(1L);
        } catch (ActorNotFoundException e) {
            throw new RuntimeException(e);
        }
        verify(actorRepository, times(1)).deleteById(1L);
    }


}