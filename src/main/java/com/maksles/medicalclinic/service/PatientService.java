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

    private final PatientRepository repository;
    private final UserRepository userRepository;

    //wyswietlenie danych wszystkich pacjentow
    public List<Patient> getPatients() {
        return repository.findAll();
    }

    //wyswietlenie danych konkretnego pacjenta poprzez email
    public Patient getPatient(String email) {
       return findPatientOrThrow(email);
    }

    @Transactional
    public Patient addPatient(CreatePatientCommand request) {
        // walidacje przed zapytaniami do bazy
        if (isBlank(request.email())) {
            throw new PatientValidationException("Email jest wymagany");
        }
        if (request.userId() == null) {
            throw new PatientValidationException("Użytkownik jest wymagany");
        }

        // unikalny email
        if (repository.findByEmailIgnoreCase(request.email()).isPresent()) {
            throw new PatientAlreadyExistsException(request.email());
        }

        //user musi istnieć
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        //relacja 1:1 - user nie może należeć juz do innego pacjenta
        if (repository.existsByUserId(user.getId())) {
            throw new UserAssignedToPatientException(
                    "Użytkownik o id " + user.getId() + " jest przypisany do innego pacjenta"
            );
        }

        //budujemy encję z DTO, id ustawi baza
        Patient patient = new Patient();
        patient.setEmail(request.email());
        patient.setPassword(request.password());
        patient.setIdCardNo(request.idCardNo());
        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setPhoneNumber(request.phoneNumber());
        patient.setBirthday(request.birthday());
        patient.setUser(user);

        return repository.save(patient);
    }

    @Transactional
    public void deletePatient(String email) {
        if(repository.deleteByEmailIgnoreCase(email) == 0){
            throw new PatientNotFoundException(email);
        }
    }

   @Transactional
   public Patient updatePatient(String email, UpdatePatientCommand command) {
        Patient existing = findPatientOrThrow(email);
        existing.update(command);
        return existing;
   }

   @Transactional
    public void changePassword(String email, ChangePasswordCommand command) {
        Patient patient = findPatientOrThrow(email);
        patient.changePassword(command);
   }

   //wspólna logika wyszukiwania używana wewnątrz serwsisu
    private Patient findPatientOrThrow(String email) {
        return repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new PatientNotFoundException(email));
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
