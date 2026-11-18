package com.maksles.medicalclinic.service;

import com.maksles.medicalclinic.dto.CreatePatientRequest;
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
    public Patient addPatient(CreatePatientRequest request) {
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
   public Patient updatePatient(String email, Patient updatedPatient) {
        Patient existing = findPatientOrThrow(email);

        if (updatedPatient.getIdCardNo() != null
            && !updatedPatient.getIdCardNo().equals(existing.getIdCardNo())){
            throw new PatientValidationException("Numer dowodu nie może zostać zmieniony");
        }

        existing.setFirstName(updatedPatient.getFirstName());
        existing.setLastName(updatedPatient.getLastName());
        existing.setPhoneNumber(updatedPatient.getPhoneNumber());
        existing.setBirthday(updatedPatient.getBirthday());
        return existing;
   }

   @Transactional
    public void changePassword(String email, String oldPassword, String newPassword) {
        if (isBlank(newPassword)) {
            throw new PatientValidationException("Hasło nie może zostać puste");
        }
        Patient patient = findPatientOrThrow(email);

        if (!patient.getPassword().equals(oldPassword)) {
            throw new InvalidPasswordException();
        }
        if (newPassword.equals(oldPassword)) {
            throw new PatientValidationException("Nowe hasło musi róznić się od obecnego");
        }
        patient.setPassword(newPassword); //UPDATE wykona się sam przy końcu transkacji
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
