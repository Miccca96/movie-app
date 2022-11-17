package com.prodyna.movieapp.exception;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String className,Long id){
        super(className + " with id: "+ "doesn't exist in database");
    }
}
