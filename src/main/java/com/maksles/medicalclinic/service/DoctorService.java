package com.maksles.medicalclinic.service;

import com.maksles.medicalclinic.dto.CreateDoctorCommand;
import com.maksles.medicalclinic.exception.DoctorAlreadyExistsException;
import com.maksles.medicalclinic.exception.DoctorNotFoundException;
import com.maksles.medicalclinic.exception.FacilityNotFoundException;
import com.maksles.medicalclinic.model.Doctor;
import com.maksles.medicalclinic.model.Facility;
import com.maksles.medicalclinic.repository.DoctorRepository;
import com.maksles.medicalclinic.repository.FacilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final FacilityRepository facilityRepository;

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional
    public Doctor addDoctor(CreateDoctorCommand command) {
        Doctor doctor = Doctor.create(command);
        validateEmailNotTaken(command.email());
        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor assignFacility(Long doctorId, Long facilityId) {
        Doctor doctor = findDoctorOrThrow(doctorId);
        Facility facility = findFacilityOrThrow(facilityId);
        doctor.assignFacility(facility);
        return doctor;
    }

    private void validateEmailNotTaken(String email) {
        if (doctorRepository.existsByEmailIgnoreCase(email)) {
            throw new DoctorAlreadyExistsException(email);
        }
    }

    private Doctor findDoctorOrThrow(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
    }

    private Facility findFacilityOrThrow(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new FacilityNotFoundException(id));
    }
}
