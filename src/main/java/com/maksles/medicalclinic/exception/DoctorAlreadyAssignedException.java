package com.maksles.medicalclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DoctorAlreadyAssignedException extends RuntimeException {

    public DoctorAlreadyAssignedException(Long doctorId, Long facilityId) {
        super("Doctor with id " + doctorId + " is already assigned to facility with id " + facilityId);
    }
}
