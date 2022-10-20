package com.prodyna.movieapp.service;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public void createActor(Actor actor) {
       actorRepository.save(actor);
    }

    public List<Actor> getActors() {
        return actorRepository.findAll();
    }

    public void updateActor(Long id, Actor actor) {
        Optional<Actor> a = actorRepository.findById(id);
        if(a.isPresent()){
            Actor actor1 = a.get();
            if(actor.getBiography() != null && !actor.getBiography().equals("")){
                 actor1.setBiography(actor.getBiography());
            }
            if(actor.getFirstName() != null && !actor.getFirstName().equals("")){
                actor1.setFirstName(actor.getFirstName());
            }
            if(actor.getLastName() != null && !actor.getLastName().equals("")){
                actor1.setFirstName(actor.getFirstName());
            }
            actorRepository.save(actor1);
        }
    }
}
