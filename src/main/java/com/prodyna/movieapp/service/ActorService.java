package com.prodyna.movieapp.service;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.exception.ObjectNotFoundException;
import com.prodyna.movieapp.mapstruct.ActorMapper;
import com.prodyna.movieapp.repository.ActorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@AllArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Transactional
    public void createActor(ActorDTO actor) {
        Optional<Actor> actorDB = actorRepository.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName());
        if (actorDB.isPresent()) {

            throw new RuntimeException("Actor " + actorDB.get().getFirstName() + " " + actorDB.get().getLastName() + " already exist in database");
        }
        Actor createdActor = actorMapper.mapActorDTOToActor(actor);
        Actor savedActor = actorRepository.save(createdActor);
    }

    @Transactional
    public ActorDTO updateActor(Long id, ActorDTO actor) {
        Optional<Actor> actorDB = actorRepository.findById(id);
        if (actorDB.isPresent()) {

            Actor actorForUpdate = actorDB.get();
            actorForUpdate.setFirstName(actor.getFirstName());
            actorForUpdate.setLastName(actor.getLastName());
            actorForUpdate.setBiography(actor.getBiography());

            Actor updatedActor = actorRepository.save(actorForUpdate);
            return actorMapper.mapActorToActorDTO(updatedActor);
        }
        throw new ObjectNotFoundException(Actor.class.getSimpleName(), id);
    }

    @Transactional
    public void deleteActor(Long id) {
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isPresent()) {

            actorRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException(Actor.class.getSimpleName(), id);
        }
    }

    public List<ActorDTO> getActorsSortByName() {
        List<Actor> actors = actorRepository.findAllActorsSortByName();
        List<ActorDTO> actorDTOS = actors.stream().map(a -> actorMapper.mapActorToActorDTO(a)).collect(Collectors.toList());
        return actorDTOS;
    }

    public ActorDTO findActorById(Long id) {
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isPresent()) {

            Actor foundActor = actor.get();
            return actorMapper.mapActorToActorDTO(foundActor);
        }
        throw new ObjectNotFoundException(Actor.class.getSimpleName(), id);
    }
}
