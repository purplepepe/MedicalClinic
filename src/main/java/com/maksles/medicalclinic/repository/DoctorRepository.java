package com.maksles.medicalclinic.repository;

import com.maksles.medicalclinic.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByEmailIgnoreCase(String email);

    @Query(value = "select d.id from Doctor d", countQuery = "select count(d) from Doctor d")
    Page<Long> findAllIds(Pageable pageable);

    @EntityGraph(attributePaths = "facilities")
    List<Doctor> findAllByIdIn(Collection<Long> ids, Sort sort);
}
