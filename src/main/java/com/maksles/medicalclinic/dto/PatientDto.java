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
}
