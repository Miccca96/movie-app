package com.prodyna.movieapp.controller;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.service.ActorService;
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
    public void createActor(@RequestBody Actor actor){
        try {
            actorService.createActor(actor);
        } catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }

    }
    @GetMapping
    public  ResponseEntity<List<Actor>> getActors(){
        List<Actor> actors = actorService.getActors();
        return new ResponseEntity<>(actors,HttpStatus.OK);
    }

    @PatchMapping (path = "/{id}")
    public void updateActor(@PathVariable Long id,@RequestBody Actor actor){
        actorService.updateActor(id,actor);
    }
}
