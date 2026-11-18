package com.maksles.medicalclinic.dto;

import java.time.LocalDate;

public record CreatePatientRequest(
        String email,
        String password,
        String idCardNo,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthday,
        Long userId
) {
}
