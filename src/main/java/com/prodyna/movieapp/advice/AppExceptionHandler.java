package com.prodyna.movieapp.advice;

import com.prodyna.movieapp.exception.ActorAlreadyExistException;
import com.prodyna.movieapp.exception.ActorNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().
                forEach(error -> {
                    errorMap.put(error.getField(), error.getDefaultMessage());
                });
        return errorMap;
    }

    @ExceptionHandler(ActorNotFoundException.class)
    public Map<String, String> handleBusinessException(ActorNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message ", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(ActorAlreadyExistException.class)
    public Map<String, String> handleBusinessException(ActorAlreadyExistException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message ", ex.getMessage());
        return errorMap;
    }

}
