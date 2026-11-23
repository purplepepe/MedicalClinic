package com.maksles.medicalclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VisitOverlapException extends RuntimeException{

    public VisitOverlapException(Long doctorId) {
        super("Doctor with id " + doctorId + " already has a visit in this time range");
    }
}
