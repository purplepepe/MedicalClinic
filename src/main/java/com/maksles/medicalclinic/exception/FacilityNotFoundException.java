package com.maksles.medicalclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacilityNotFoundException extends RuntimeException {

    public FacilityNotFoundException(Long id){
        super("Facility with id " + id + " not found");
    }
}
