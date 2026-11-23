package com.maksles.medicalclinic.dto;

public record FacilityDto(
        Long id,
        String name,
        String city,
        String zipCode,
        String street,
        String buildingNumber
) {
}
