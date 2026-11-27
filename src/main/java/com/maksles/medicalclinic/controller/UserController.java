package com.maksles.medicalclinic.controller;

import com.maksles.medicalclinic.dto.CreateUserCommand;
import com.maksles.medicalclinic.dto.PageDto;
import com.maksles.medicalclinic.dto.UpdateUserCommand;
import com.maksles.medicalclinic.dto.UserDto;
import com.maksles.medicalclinic.mapper.UserMapper;
import com.maksles.medicalclinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public PageDto<UserDto> getUsers(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return PageDto.from(userService.getUsers(pageable).map(userMapper::toDto));
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userMapper.toDto(userService.getUser(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody CreateUserCommand command) {
        return userMapper.toDto(userService.addUser(command));
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UpdateUserCommand command) {
        return userMapper.toDto(userService.updateUser(id, command));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
