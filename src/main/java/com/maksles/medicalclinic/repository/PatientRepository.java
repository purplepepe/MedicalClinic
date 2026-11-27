package com.maksles.medicalclinic.repository;

import com.maksles.medicalclinic.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUserId(Long userId);

    @Override
    @EntityGraph(attributePaths = "user")
    Page<Patient> findAll(Pageable pageable);
}
