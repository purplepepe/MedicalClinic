package com.maksles.medicalclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("Użytkownik " + username + " nie został znaleziony");
    }

    public UserNotFoundException(Long id) {
        super("Użytkownik o id " + id + " nie został znaleziony");
    }
}
