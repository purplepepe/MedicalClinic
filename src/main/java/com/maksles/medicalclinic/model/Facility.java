package com.maksles.medicalclinic.model;

import com.maksles.medicalclinic.dto.CreateFacilityCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facilities")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
        return new Facility(null, command.name(), command.city(), command.zipCode(),
                command.street(), command.buildingNumber());
    }
}
