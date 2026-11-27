package com.maksles.medicalclinic.model;

import com.maksles.medicalclinic.dto.CreateDoctorCommand;
import com.maksles.medicalclinic.exception.DoctorAlreadyAssignedException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@NoArgsConstructor   // wymagany przez JPA
@AllArgsConstructor(access = AccessLevel.PRIVATE)   // konstruktor tylko dla metody create
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;
    private String specialization;

    @ManyToMany
    @JoinTable(
            name = "doctor_facility",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private Set<Facility> facilities = new HashSet<>();

    public static Doctor create(CreateDoctorCommand command) {
        return new Doctor(null, command.email(), command.password(), command.firstName(),
                command.lastName(), command.specialization(), new HashSet<>());
    }

    public void assignFacility(Facility facility) {
        if (facilities.contains(facility)) {
            throw new DoctorAlreadyAssignedException(this.id, facility.getId());
        }
        facilities.add(facility);
    }
}