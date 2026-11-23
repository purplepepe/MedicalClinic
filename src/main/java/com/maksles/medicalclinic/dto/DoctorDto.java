package com.maksles.medicalclinic.dto;

import java.util.List;

public record DoctorDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String specialization,
        List<FacilityDto> facilities
) {
}
