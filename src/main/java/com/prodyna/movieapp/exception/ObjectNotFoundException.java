package com.prodyna.movieapp.exception;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String className,Long id){
        super(className + " with id: " + id + " doesn't exist in database");
    }
}
