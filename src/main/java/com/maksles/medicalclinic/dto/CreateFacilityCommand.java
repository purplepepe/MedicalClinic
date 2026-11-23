package com.maksles.medicalclinic.dto;

public record CreateFacilityCommand(
        String name,
        String city,
        String zipCode,
        String street,
        String buildingNumber
) {
}
