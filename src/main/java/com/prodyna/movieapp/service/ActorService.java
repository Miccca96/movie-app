package com.prodyna.movieapp.service;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.exception.ActorAlreadyExistException;
import com.prodyna.movieapp.exception.ActorNotFoundException;
import com.prodyna.movieapp.mapstruct.ActorMapper;
import com.prodyna.movieapp.mapstruct.ActorMapperImpl;
import com.prodyna.movieapp.repository.ActorRepository;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Autowired
    public ActorService(ActorRepository actorRepository, ActorMapper actorMapper) {
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;
    }

    public void createActor(ActorDTO actor) {
        Optional<Actor> actorDB = actorRepository.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName());
        if (actorDB.isPresent()) {

            throw new ActorAlreadyExistException("Actor with this name already exist in database");
        }
        Actor createdActor = actorMapper.mapActorDTOToActor(actor);
        Actor savedActor = actorRepository.save(createdActor);
    }

    public ActorDTO updateActor(Long id, ActorDTO actor) throws ActorNotFoundException {
        Optional<Actor> actorDB = actorRepository.findById(id);
        if (actorDB.isPresent()) {

            Actor actorForUpdate = actorDB.get();
            actorForUpdate.setFirstName(actor.getFirstName());
            actorForUpdate.setLastName(actor.getLastName());
            actorForUpdate.setBiography(actor.getBiography());

            Actor updatedActor = actorRepository.save(actorForUpdate);
            return actorMapper.mapActorToActorDTO(updatedActor);
        }
        throw new ActorNotFoundException("Actor with id " + id + " doesn't exist");
    }

    public void deleteActor(Long id) throws ActorNotFoundException {
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isPresent()) {

            actorRepository.deleteById(id);
        }
        throw new ActorNotFoundException("Actor with given id: " + id + " doesn't exist in databse");
    }


    public List<ActorDTO> getActorsSortByName() {
        List<Actor> actors = actorRepository.findAllActorsSortByName();
        List<ActorDTO> actorDTOS = actors.stream().map(a -> actorMapper.mapActorToActorDTO(a)).collect(Collectors.toList());
        return actorDTOS;
    }

    public ActorDTO findActorById(Long id) throws ActorNotFoundException {
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isPresent()) {

            Actor foundActor = actor.get();
            return actorMapper.mapActorToActorDTO(foundActor);
        }
        throw new ActorNotFoundException("Actor with given id: " + id + " doesnt exist");
    }
}
