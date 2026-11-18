package com.maksles.medicalclinic.service;

import com.maksles.medicalclinic.exception.UserAlreadyExistsException;
import com.maksles.medicalclinic.exception.UserAssignedToPatientException;
import com.maksles.medicalclinic.exception.UserNotFoundException;
import com.maksles.medicalclinic.exception.UserValidationException;
import com.maksles.medicalclinic.model.User;
import com.maksles.medicalclinic.repository.PatientRepository;
import com.maksles.medicalclinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    //Read: wszyscy userzy
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //Read: jeden user po nazwie
    public User getUser(String username) {
        return findUserOrThrow(username);
    }

    //Create: oba pola wymagane, nazwa unikalna
    @Transactional
    public User addUser(User user) {
        validate(user);
        if (userRepository.findByUsernameIgnoreCase(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        user.setId(null); // id nadaje baza
        return userRepository.save(user);
    }

    //Update: zmiana nazwy i hasła
    @Transactional
    public User updateUser(String username, User updatedUser) {
        User existing = findUserOrThrow(username);
        validate(updatedUser);

        //nowa nazwa nie moze należeć do innego usera (porównujemy po id)
        userRepository.findByUsernameIgnoreCase(updatedUser.getUsername())
                .filter(other -> !other.getId().equals(existing.getId()))
                .ifPresent(other -> {
                    throw new UserAlreadyExistsException(updatedUser.getUsername());
                });

        existing.setUsername(updatedUser.getUsername());
        existing.setPassword(updatedUser.getPassword());
        return existing;
    }

    //Delete: nie pozwalamy usunąć konta które należy do pacjenta
    @Transactional
    public void deleteUser(String username) {
        User user = findUserOrThrow(username);
        if (patientRepository.existsByUserId(user.getId())) {
            throw new UserAssignedToPatientException(
                    "Użytkownik " + username + " jest przypisany do pacjenta i nie może zostać usunięty"
            );
        }
        userRepository.delete(user);
    }

    private User findUserOrThrow(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    private void validate(User user) {
        if (isBlank(user.getUsername())) {
            throw new UserValidationException("Nazwa użytkownika jest wymagana");
        }
        if (isBlank(user.getPassword())) {
            throw new UserValidationException("Hasło jest wymagane");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
