package com.maksles.medicalclinic.dto;

import com.maksles.medicalclinic.exception.ValidationException;

public record CreateDoctorCommand(
        String email,
        String password,
        String firstName,
        String lastName,
        String specialization
) {
    // walidacja przy tworzeniu komendy - Java wywołuje to sama
    public CreateDoctorCommand {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Doctor email is required");
        }
        if (password == null || password.isBlank()) {
            throw new ValidationException("Doctor password is required");
        }
    }
}
