package com.prodyna.movieapp.controller;

import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.service.ActorService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/actors")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public ResponseEntity<String> createActor(@RequestBody @Valid ActorDTO actor) {

        actorService.createActor(actor);
        return new ResponseEntity<>("Actor was successfully created", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ActorDTO>> getActorsSortByName() {
        List<ActorDTO> actors = actorService.getActorsSortByName();
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ActorDTO findActorById(@PathVariable Long id) {
        ActorDTO actor = actorService.findActorById(id);
        return actor;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ActorDTO> updateActor(@PathVariable Long id, @RequestBody @Valid ActorDTO actor) {
        ActorDTO updatedActor = actorService.updateActor(id, actor);
        return new ResponseEntity<>(updatedActor, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return new ResponseEntity<>("Actor with id: " + id + " was successfully deleted", HttpStatus.OK);
    }
}
