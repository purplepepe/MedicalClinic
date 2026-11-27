package com.maksles.medicalclinic.dto;

import com.maksles.medicalclinic.exception.ValidationException;

public record CreateFacilityCommand(
        String name,
        String city,
        String zipCode,
        String street,
        String buildingNumber
) {
    // walidacja przy tworzeniu komendy
    public CreateFacilityCommand {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Facility name is required");
        }
    }
}
