package com.maksles.medicalclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VisitNotFoundException extends RuntimeException{

    public VisitNotFoundException(Long id) {
        super("Visit with id " + id + " not found");
    }
}
