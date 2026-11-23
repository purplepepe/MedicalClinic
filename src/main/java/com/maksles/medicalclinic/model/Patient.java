package com.maksles.medicalclinic.model;

import com.maksles.medicalclinic.dto.ChangePasswordCommand;
import com.maksles.medicalclinic.dto.CreatePatientCommand;
import com.maksles.medicalclinic.dto.UpdatePatientCommand;
import com.maksles.medicalclinic.exception.InvalidPasswordException;
import com.maksles.medicalclinic.exception.ValidationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String idCardNo;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    //relacja 1:1 - pacjent ma jedno konto użytkownika
    // FK + UNIQUE = jeden user może należeć tylko do jednego pacjenta
    // brak kaskady: usunięcie pacjenta nie usuwa konta usera
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public static Patient create(CreatePatientCommand command) {
        validate(command);

        Patient patient = new Patient();
        patient.email = command.email();
        patient.password = command.password();
        patient.idCardNo = command.idCardNo();
        patient.firstName = command.firstName();
        patient.lastName = command.lastName();
        patient.phoneNumber = command.phoneNumber();
        patient.birthday = command.birthday();
        return patient;
    }

    public void assignUser(User user) {
        this.user = user;
    }

    public void update(UpdatePatientCommand command) {
        if (command.idCardNo() != null && !command.idCardNo().equals(this.idCardNo)) {
            throw new ValidationException("ID card number cannot be changed");
        }
        this.firstName = command.firstName();
        this.lastName = command.lastName();
        this.phoneNumber = command.phoneNumber();
        this.birthday = command.birthday();
    }

    public void changePassword(ChangePasswordCommand command) {
        validatePassword(command);
        this.password = command.newPassword();
    }

    public static void validate(CreatePatientCommand command) {
        if (command.email() == null || command.email().isBlank()) {
            throw new ValidationException("Email is required");
        }
        if (command.password() == null || command.password().isBlank()) {
            throw new ValidationException("Password is required");
        }
        if (command.idCardNo() == null || command.idCardNo().isBlank()) {
            throw new ValidationException("ID card number is required");
        }
        if (command.userId() == null) {
            throw new ValidationException("User id is required");
        }
    }

    private void validatePassword(ChangePasswordCommand command) {
        if (command.newPassword() == null || command.newPassword().isBlank()) {
            throw new ValidationException("New password cannot be empty");
        }
        if(!this.password.equals(command.oldPassword())) {
            throw new InvalidPasswordException();
        }
        if (command.newPassword().equals(command.oldPassword())) {
            throw new ValidationException("New password must be different from the current one");
        }
    }
}
