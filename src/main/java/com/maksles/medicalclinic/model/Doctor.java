package com.maksles.medicalclinic.model;

import com.maksles.medicalclinic.dto.CreateDoctorCommand;
import com.maksles.medicalclinic.exception.DoctorAlreadyAssignedException;
import com.maksles.medicalclinic.exception.ValidationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@NoArgsConstructor
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
        validate(command);

        Doctor doctor = new Doctor();
        doctor.email = command.email();
        doctor.password = command.password();
        doctor.firstName = command.firstName();
        doctor.lastName = command.lastName();
        doctor.specialization = command.specialization();
        return doctor;
    }

    public void assignFacility(Facility facility) {
        if(facilities.contains(facility)) {
            throw new DoctorAlreadyAssignedException(this.id, facility.getId());
        }
        facilities.add(facility);
    }

    private static void validate(CreateDoctorCommand command) {
        if (command.email() == null || command.email().isBlank()) {
            throw new ValidationException("Doctor email is required");
        }
        if (command.password() == null || command.password().isBlank()) {
            throw new ValidationException("Doctor password is required");
        }
    }
}
