package com.maksles.medicalclinic.service;

import com.maksles.medicalclinic.dto.CreateVisitCommand;
import com.maksles.medicalclinic.exception.DoctorNotFoundException;
import com.maksles.medicalclinic.exception.PatientNotFoundException;
import com.maksles.medicalclinic.exception.VisitNotFoundException;
import com.maksles.medicalclinic.exception.VisitOverlapException;
import com.maksles.medicalclinic.model.Doctor;
import com.maksles.medicalclinic.model.Patient;
import com.maksles.medicalclinic.model.Visit;
import com.maksles.medicalclinic.repository.DoctorRepository;
import com.maksles.medicalclinic.repository.PatientRepository;
import com.maksles.medicalclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public Page<Visit> getVisits(Pageable pageable) {
        return visitRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Visit> getPatientVisits(Long patientId, Pageable pageable) {
        validatePatientExists(patientId);
        return visitRepository.findAllByPatientId(patientId, pageable);
    }

    // lekarz tworzy wolną wizytę (bez pacjenta)
    @Transactional
    public Visit addVisit(CreateVisitCommand command) {
        Doctor doctor = findDoctorOrThrow(command.doctorId());
        Visit visit = Visit.create(command, doctor);
        validateNoOverlap(doctor.getId(), visit);
        return visitRepository.save(visit);
    }

    // pacjent zapisuje się na wolną wizytę
    @Transactional
    public Visit assignPatient(Long visitId, Long patientId) {
        Visit visit = findVisitOrThrow(visitId);
        Patient patient = findPatientOrThrow(patientId);
        visit.assignPatient(patient);
        return visit;   // UPDATE wykona się sam na końcu transakcji
    }

    // pytamy bazę tylko o to, czy istnieje nakładająca się wizyta, zamiast pobierać wszystkie wizyty lekarza
    private void validateNoOverlap(Long doctorId, Visit visit) {
        if (visitRepository.existsOverlappingVisit(doctorId, visit.getStartTime(), visit.getEndTime())) {
            throw new VisitOverlapException(doctorId);
        }
    }

    private void validatePatientExists(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException(patientId);
        }
    }

    private Visit findVisitOrThrow(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException(id));
    }

    private Doctor findDoctorOrThrow(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
    }

    private Patient findPatientOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }
}