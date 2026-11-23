package com.maksles.medicalclinic.repository;

import com.maksles.medicalclinic.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    boolean existsByNameIgnoreCase(String name);
}
