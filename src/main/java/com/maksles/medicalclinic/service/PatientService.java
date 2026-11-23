package com.maksles.medicalclinic.service;

import com.maksles.medicalclinic.dto.ChangePasswordCommand;
import com.maksles.medicalclinic.dto.UpdatePatientCommand;
import com.maksles.medicalclinic.dto.CreatePatientCommand;
import com.maksles.medicalclinic.exception.*;
import com.maksles.medicalclinic.model.Patient;
import com.maksles.medicalclinic.model.User;
import com.maksles.medicalclinic.repository.PatientRepository;
import com.maksles.medicalclinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    //wyswietlenie danych wszystkich pacjentow
    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    //wyswietlenie danych konkretnego pacjenta poprzez email
    public Patient getPatient(Long id) {
        return findPatientOrThrow(id);
    }

    @Transactional
    public Patient addPatient(CreatePatientCommand command) {
        Patient patient = Patient.create(command);   // walidacja pól, bez zapytań do bazy
        validateEmailNotTaken(command.email());
        User user = findUserOrThrow(command.userId());
        validateUserNotAssigned(user.getId());
        patient.assignUser(user);
        return patientRepository.save(patient);
    }

@Transactional
public Patient updatePatient(Long id, UpdatePatientCommand command) {
    Patient patient = findPatientOrThrow(id);
    patient.update(command);
    return patient;
}

@Transactional
public void changePassword(Long id, ChangePasswordCommand command) {
    Patient patient = findPatientOrThrow(id);
    patient.changePassword(command);
}

    @Transactional
    public void deletePatient(Long id) {
        patientRepository.delete(findPatientOrThrow(id));
    }

    private void validateEmailNotTaken(String email) {
        if (patientRepository.existsByEmailIgnoreCase(email)) {
            throw new PatientAlreadyExistsException(email);
        }
    }

    private void validateUserNotAssigned(Long userId) {
        if (patientRepository.existsByUserId(userId)) {
            throw new UserAssignedToPatientException(userId);
        }
    }

    private Patient findPatientOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
