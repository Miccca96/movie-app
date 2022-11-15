package com.prodyna.movieapp.controller;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.exception.ActorNotFoundException;
import com.prodyna.movieapp.service.ActorService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/actors")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public ResponseEntity<ActorDTO> createActor(@RequestBody @Valid ActorDTO actor) {
        ActorDTO createdActor = actorService.createActor(actor);
        return new ResponseEntity<>(createdActor, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<ActorDTO>> getActorsSortByName() {
        List<ActorDTO> actors = actorService.getActorsSortByName();
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public Actor findActorById(@PathVariable Long id) throws ActorNotFoundException {
        Actor actor = actorService.findActorById(id);
        return actor;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ActorDTO> updateActor(@PathVariable Long id, @RequestBody @Valid ActorDTO actor) throws ActorNotFoundException {
        ActorDTO updatedActor = actorService.updateActor(id, actor);
        return new ResponseEntity<>(updatedActor, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable Long id) throws ActorNotFoundException {
        actorService.deleteActor(id);
        return new ResponseEntity<>("Actor with id: " + id + " was successfully deleted", HttpStatus.OK);
    }


}
