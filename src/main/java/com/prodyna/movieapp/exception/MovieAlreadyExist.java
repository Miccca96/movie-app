package com.prodyna.movieapp.exception;

public class MovieAlreadyExist extends RuntimeException{

    public MovieAlreadyExist(String message){
        super(message);
    }
}
