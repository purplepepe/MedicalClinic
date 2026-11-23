package com.maksles.medicalclinic.repository;

import com.maksles.medicalclinic.model.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByEmailIgnoreCase(String email);

    @Override
    @EntityGraph(attributePaths = "facilities")
    List<Doctor> findAll();
}
