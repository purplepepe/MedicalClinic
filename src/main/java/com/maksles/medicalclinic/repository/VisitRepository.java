package com.maksles.medicalclinic.repository;

import com.maksles.medicalclinic.model.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface VisitRepository extends JpaRepository<Visit, Long> {

    boolean existsByDoctorIdAndStartTimeBeforeAndEndTimeAfter(Long doctorId, LocalDateTime endTime, LocalDateTime startTime);

    default boolean existsOverlappingVisit(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        return existsByDoctorIdAndStartTimeBeforeAndEndTimeAfter(doctorId, endTime, startTime);
    }

    @EntityGraph(attributePaths = {"doctor", "patient"})
    Page<Visit> findAllByPatientId(Long patientId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"doctor", "patient"})
    Page<Visit> findAll(Pageable pageable);
}
