package com.maksles.medicalclinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maksles.medicalclinic.dto.ChangePasswordCommand;
import com.maksles.medicalclinic.dto.UpdatePatientCommand;
import com.maksles.medicalclinic.exception.InvalidPasswordException;
import com.maksles.medicalclinic.exception.PatientValidationException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    // WRITE_ONLY - hasło można wysłać w żądaniu, ale nigdy nie wraca w odpowiedzi
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    public void update(UpdatePatientCommand command) {
        if (command.idCardNo() != null && !command.idCardNo().equals(this.idCardNo)) {
            throw new PatientValidationException("Numer dowodu nie może zostać zmieniony");
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

    private void validatePassword(ChangePasswordCommand command) {
        if (command.newPassword() == null || command.newPassword().isBlank()) {
            throw new PatientValidationException("Hasło nie może zostać puste");
        }
        if(!this.password.equals(command.oldPassword())) {
            throw new InvalidPasswordException();
        }
        if (command.newPassword().equals(command.oldPassword())) {
            throw new PatientValidationException("Nowe hasło musi różnić sie od obecnego");
        }
    }
}
