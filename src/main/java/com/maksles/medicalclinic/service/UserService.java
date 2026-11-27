package com.maksles.medicalclinic.service;

import com.maksles.medicalclinic.dto.CreateUserCommand;
import com.maksles.medicalclinic.dto.UpdateUserCommand;
import com.maksles.medicalclinic.exception.UserAlreadyExistsException;
import com.maksles.medicalclinic.exception.UserAssignedToPatientException;
import com.maksles.medicalclinic.exception.UserNotFoundException;
import com.maksles.medicalclinic.model.User;
import com.maksles.medicalclinic.repository.PatientRepository;
import com.maksles.medicalclinic.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    //Read: jeden user po nazwie
    public User getUser(Long id) {
        return findUserOrThrow(id);
    }

    //Create: oba pola wymagane, nazwa unikalna
    @Transactional
    public User addUser(CreateUserCommand command) {
        User user = User.create(command);
        validateUsernameNotTaken(command.username());
        return userRepository.save(user);
    }

    //Update: zmiana nazwy i hasła
    @Transactional
    public User updateUser(Long id, UpdateUserCommand command) {
        User user = findUserOrThrow(id);
        validateUsernameNotTakenByOtherUser(command.username(), id);
        user.update(command);
        return user;
    }

    //Delete: nie pozwalamy usunąć konta które należy do pacjenta
    @Transactional
    public void deleteUser(Long id) {
        User user = findUserOrThrow(id);
        validateUserNotAssignedToPatient(id);
        userRepository.delete(user);

    }

    private void validateUsernameNotTaken(String username) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new UserAlreadyExistsException(username);
        }
    }

    private void validateUsernameNotTakenByOtherUser(String username, Long id) {
        if (userRepository.existsByUsernameIgnoreCaseAndIdNot(username, id)) {
            throw new UserAlreadyExistsException(username);
        }
    }

    private void validateUserNotAssignedToPatient(Long userId) {
        if (patientRepository.existsByUserId(userId)) {
            throw new UserAssignedToPatientException(userId);
        }
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
