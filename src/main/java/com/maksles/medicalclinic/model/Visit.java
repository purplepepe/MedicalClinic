package com.maksles.medicalclinic.model;

import com.maksles.medicalclinic.dto.CreateVisitCommand;
import com.maksles.medicalclinic.exception.ValidationException;
import com.maksles.medicalclinic.exception.VisitAlreadyBookedException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@NoArgsConstructor   // wymagany przez JPA
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many To One: wiele wizyt należy do jednego lekarza; Visit jest właścicielem relacji (ma kolumnę doctor_id)
    // LAZY: lekarz nie jest wczytywany, dopóki go nie potrzebujemy
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Many To One: pacjent może mieć wiele wizyt; null = wizyta jeszcze wolna
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    public static Visit create(CreateVisitCommand command, Doctor doctor) {
        validate(command);

        Visit visit = new Visit();
        visit.doctor = doctor;
        visit.startTime = command.startTime();
        visit.endTime = command.endTime();
        return visit;
    }

    public void assignPatient(Patient patient) {
        if (this.startTime.isBefore(LocalDateTime.now())) {
            throw new ValidationException("Cannot book a visit in the past");
        }
        if (this.patient != null) {
            throw new VisitAlreadyBookedException(this.id);
        }
        this.patient = patient;
    }

    private static void validate(CreateVisitCommand command) {
        if (command.startTime() == null || command.endTime() == null) {
            throw new ValidationException("Visit start and end time are required");
        }
        if (!command.startTime().isBefore(command.endTime())) {
            throw new ValidationException("Visit start time must be before end time");
        }
        if (command.startTime().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Visit cannot start in the past");
        }
        validateFullQuarter(command.startTime(), "Start time");
        validateFullQuarter(command.endTime(), "End time");
    }

    private static void validateFullQuarter(LocalDateTime time, String fieldName) {
        if (time.getMinute() % 15 != 0 || time.getSecond() != 0 || time.getNano() != 0) {
            throw new ValidationException(fieldName + " must be a full quarter hour (:00, :15, :30, :45)");
        }
    }
}
