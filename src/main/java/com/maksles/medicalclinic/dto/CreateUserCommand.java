package com.maksles.medicalclinic.dto;

import com.maksles.medicalclinic.exception.ValidationException;

public record CreateUserCommand(String username, String password) {

    // walidacja przy tworzeniu komendy
    public CreateUserCommand {
        if (username == null || username.isBlank()) {
            throw new ValidationException("Username is required");
        }
        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }
    }
}
