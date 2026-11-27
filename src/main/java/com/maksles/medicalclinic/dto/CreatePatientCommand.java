package com.maksles.medicalclinic.dto;

import com.maksles.medicalclinic.exception.ValidationException;
import java.time.LocalDate;

public record CreatePatientCommand(
        String email,
        String password,
        String idCardNo,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthday,
        Long userId
) {
    // walidacja przy tworzeniu komendy
    public CreatePatientCommand {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email is required");
        }
        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }
        if (idCardNo == null || idCardNo.isBlank()) {
            throw new ValidationException("ID card number is required");
        }
        if (userId == null) {
            throw new ValidationException("User id is required");
        }
    }
}
