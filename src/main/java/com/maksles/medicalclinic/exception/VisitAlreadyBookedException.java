package com.maksles.medicalclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VisitAlreadyBookedException extends RuntimeException{

    public VisitAlreadyBookedException(Long visitId) {
        super("Visit with id " + visitId + " is already booked");
    }
}
