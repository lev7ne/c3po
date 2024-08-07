package ru.mf.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mf.user.UserDto;
import ru.mf.user.service.UserService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto createDto) {
        log.info("Creating user: {}", createDto);

        var userDto = userService.saveUser(createDto);

        log.info("Created user: {}", userDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDto);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        log.info("Getting all users");

        var dtos = userService.findAllUsers();

        log.info("Found {} users", dtos.size());

        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);
    }
}
