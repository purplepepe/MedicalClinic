package com.maksles.medicalclinic.model;

import com.maksles.medicalclinic.dto.CreateUserCommand;
import com.maksles.medicalclinic.dto.UpdateUserCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor   // wymagany przez JPA
@AllArgsConstructor(access = AccessLevel.PRIVATE)   // konstruktor tylko dla metody create
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public static User create(CreateUserCommand command) {
        return new User(null, command.username(), command.password());
    }

    // walidacja jest już w UpdateUserCommand
    public void update(UpdateUserCommand command) {
        this.username = command.username();
        this.password = command.password();
    }
}