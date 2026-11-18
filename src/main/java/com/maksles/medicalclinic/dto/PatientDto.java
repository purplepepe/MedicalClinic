package com.maksles.medicalclinic.dto;

import com.maksles.medicalclinic.model.Patient;

import java.time.LocalDate;

public record PatientDto(
        Long id,
        String email,
        String idCardNo,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthday,
        UserResponse user
) {

    public static PatientDto from(Patient patient) {
        return new PatientDto(
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
