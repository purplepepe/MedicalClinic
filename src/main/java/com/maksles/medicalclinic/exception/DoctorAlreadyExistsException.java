package com.maksles.medicalclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DoctorAlreadyExistsException extends RuntimeException {

    public DoctorAlreadyExistsException(String email) {
        super("Doctor with email " + email + " already exists");
    }
}
