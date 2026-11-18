package com.maksles.medicalclinic.dto;

import com.maksles.medicalclinic.model.Patient;

import java.time.LocalDate;

public record PatientResponse(
        Long id,
        String email,
        String idCardNo,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthday,
        UserResponse user
) {

    public static PatientResponse from(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getEmail(),
                patient.getIdCardNo(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getBirthday(),
                UserResponse.from(patient.getUser())
        );
    }
}
