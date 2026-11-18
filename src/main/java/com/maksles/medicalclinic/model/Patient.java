package com.maksles.medicalclinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
}
