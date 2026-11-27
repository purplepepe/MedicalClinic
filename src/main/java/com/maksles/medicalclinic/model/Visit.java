package com.maksles.medicalclinic.model;

import com.maksles.medicalclinic.dto.CreateVisitCommand;
import com.maksles.medicalclinic.exception.ValidationException;
import com.maksles.medicalclinic.exception.VisitAlreadyBookedException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@NoArgsConstructor   // wymagany przez JPA
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
        return new Visit(null, doctor, null, command.startTime(), command.endTime());
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
}
