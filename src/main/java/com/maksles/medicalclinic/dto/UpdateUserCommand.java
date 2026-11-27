package com.maksles.medicalclinic.dto;

import com.maksles.medicalclinic.exception.ValidationException;

public record UpdateUserCommand(String username, String password) {

    public UpdateUserCommand {
        if (username == null || username.isBlank()) {
            throw new ValidationException("Username is required");
        }
        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }
    }
}
