package com.maksles.medicalclinic.dto;

import com.maksles.medicalclinic.exception.ValidationException;
import java.time.LocalDateTime;

public record CreateVisitCommand(
        Long doctorId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
    // walidacja przy tworzeniu komendy
    public CreateVisitCommand {
        if (startTime == null || endTime == null) {
            throw new ValidationException("Visit start and end time are required");
        }
        if (!startTime.isBefore(endTime)) {
            throw new ValidationException("Visit start time must be before end time");
        }
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new ValidationException("Visit cannot start in the past");
        }
        validateFullQuarter(startTime, "Start time");
        validateFullQuarter(endTime, "End time");
    }

    private static void validateFullQuarter(LocalDateTime time, String fieldName) {
        if (time.getMinute() % 15 != 0 || time.getSecond() != 0 || time.getNano() != 0) {
            throw new ValidationException(fieldName + " must be a full quarter hour (:00, :15, :30, :45)");
        }
    }
}
