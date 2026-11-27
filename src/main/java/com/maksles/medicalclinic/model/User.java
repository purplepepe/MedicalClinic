package com.maksles.medicalclinic.model;

import com.maksles.medicalclinic.dto.CreateUserCommand;
import com.maksles.medicalclinic.dto.UpdateUserCommand;
import com.maksles.medicalclinic.exception.ValidationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public static User create(CreateUserCommand command) {
        validate(command.username(), command.password());

        User user = new User();
        user.username = command.username();
        user.password = command.password();
        return user;
    }

    public void update(UpdateUserCommand command) {
        validate(command.username(), command.password());

        this.username = command.username();
        this.password = command.password();
    }

    private static void validate(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new ValidationException("Username is required");
        }
        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }
    }
}
