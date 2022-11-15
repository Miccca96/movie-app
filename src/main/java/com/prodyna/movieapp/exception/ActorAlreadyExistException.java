package com.prodyna.movieapp.exception;

public class ActorAlreadyExistException extends RuntimeException{

    public ActorAlreadyExistException(String message) {
        super(message);
    }
}
