package com.prodyna.movieapp.exception;

public class ObjectAlreadyExistException extends RuntimeException {

    public ObjectAlreadyExistException(String className, Long id) {
        super(className + " with id: " + id + " already exist in database");
    }
}
