package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.UserResponse;
import com.maksles.medicalclinic.model.User;
import com.maksles.medicalclinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getUsers().stream()
                .map(UserResponse::from)
                .toList();
    }

    @GetMapping("/{username}")
    public UserResponse getUser(@PathVariable String username) {
        return UserResponse.from(userService.getUser(username));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addUser(@RequestBody User user) {
        return UserResponse.from(userService.addUser(user));
    }

    @PutMapping("/{username}")
    public UserResponse updateUser(@PathVariable String username, @RequestBody User user) {
        return UserResponse.from(userService.updateUser(username, user));
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }
}
