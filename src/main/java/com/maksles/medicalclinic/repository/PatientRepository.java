package com.maksles.medicalclinic.repository;

import com.maksles.medicalclinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUserId(Long userId);
}
