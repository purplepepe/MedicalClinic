package com.maksles.medicalclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FacilityAlreadyExistsException extends RuntimeException {

    public FacilityAlreadyExistsException(String name) {
        super("Facility with name " + name + "already exists");
    }
}
