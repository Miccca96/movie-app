package com.prodyna.movieapp.advice;

import com.prodyna.movieapp.exception.ObjectAlreadyExistException;
import com.prodyna.movieapp.exception.ObjectNotFoundException;
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
        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ObjectNotFoundException.class)
    public Map<String, String> handleBusinessException(ObjectNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message ", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ObjectAlreadyExistException.class)
    public Map<String, String> handleBusinessException(ObjectAlreadyExistException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message ", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleBusinessException(RuntimeException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message ", ex.getMessage());
        return errorMap;
    }

}
