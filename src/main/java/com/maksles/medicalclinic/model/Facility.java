package com.maksles.medicalclinic.model;

import com.maksles.medicalclinic.dto.CreateFacilityCommand;
import com.maksles.medicalclinic.dto.CreatePatientCommand;
import com.maksles.medicalclinic.exception.ValidationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facilities")
@Getter
@NoArgsConstructor
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String city;
    private String zipCode;
    private String street;
    private String buildingNumber;

    public static Facility create(CreateFacilityCommand command) {
        validate(command);

        Facility facility = new Facility();
        facility.name = command.name();
        facility.city = command.city();
        facility.zipCode = command.zipCode();
        facility.street = command.street();
        facility.buildingNumber = command.buildingNumber();
        return facility;
    }

    private static void validate(CreateFacilityCommand command) {
        if (command.name() == null || command.name().isBlank()) {
            throw new ValidationException("Facility name is required");
        }
    }
}
