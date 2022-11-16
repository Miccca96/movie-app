package com.prodyna.movieapp.mapstruct;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.dto.ActorDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorDTO mapActorToActorDTO(Actor actor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    Actor mapActorDTOToActor(ActorDTO actorDTO);

    List<ActorDTO> mapListOfActorsToListOfActorDTOs(List<Actor> actors);

}
