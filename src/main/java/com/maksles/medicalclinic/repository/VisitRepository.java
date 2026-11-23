package com.maksles.medicalclinic.repository;

import com.maksles.medicalclinic.model.Visit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    boolean existsByDoctorIdAndStartTimeBeforeAndEndTimeAfter(Long doctorId, LocalDateTime endTime, LocalDateTime startTime);

    default boolean existsOverlappingVisit(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        return  existsByDoctorIdAndStartTimeBeforeAndEndTimeAfter(doctorId, endTime, startTime);
    }

    @EntityGraph(attributePaths =  {"doctor", "patient"})
    List<Visit> findAllByPatientIdOrderByStartTime(Long patientId);

    @Override
    @EntityGraph(attributePaths = {"doctor", "patient"})
    List<Visit> findAll();
}
