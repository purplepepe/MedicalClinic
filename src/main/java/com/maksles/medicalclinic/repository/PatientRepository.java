package com.maksles.medicalclinic.repository;

import com.maksles.medicalclinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmailIgnoreCase(String email);

    long deleteByEmailIgnoreCase(String email);

    boolean existsByUserId(Long userId);
}
