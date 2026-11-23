package com.maksles.medicalclinic.dto;

import java.time.LocalDateTime;

public record CreateVisitCommand(
        Long doctorId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
