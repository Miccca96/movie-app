package com.prodyna.movieapp.service;

import java.io.IOException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ValidationService {

    private final Validator validator;

    @Autowired
    public ValidationService(Validator validator) {
        this.validator = validator;
    }

    public <T> void validate(T entity) throws IOException {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            for (ConstraintViolation violation : violations) {
                log.warn(violation.getMessage());
            }
            throw new IOException("Validation error");
        }
    }
}

