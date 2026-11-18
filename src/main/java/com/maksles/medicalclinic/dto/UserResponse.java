package com.maksles.medicalclinic.dto;

import com.maksles.medicalclinic.model.User;

//to co zwracamy o userze bez hasła
public record UserResponse(Long id, String username) {

    //zmiana encji na DTO, null gdy pacjent nie ma przypisanego konta
    public static UserResponse from (User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(user.getId(), user.getUsername());
    }
}
