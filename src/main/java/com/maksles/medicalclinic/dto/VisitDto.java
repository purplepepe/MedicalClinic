package com.maksles.medicalclinic.dto;

import java.time.LocalDateTime;

public record VisitDto(
        Long id,
        Long doctorId,
        Long patientId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
