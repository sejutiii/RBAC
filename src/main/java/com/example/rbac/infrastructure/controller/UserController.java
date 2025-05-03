package com.example.rbac.infrastructure.controller;

import com.example.rbac.application.UserService;

import com.example.rbac.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UUID> createUser(@Valid @RequestBody CreateUserRequest request) {
        UUID userId = userService.createUser(request.name(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @PostMapping("/{userId}/assign-role/{roleId}")
    public ResponseEntity<String> assignRoleToUser(@PathVariable UUID userId, @PathVariable UUID roleId) {
        userService.assignRole(userId, roleId);
        return ResponseEntity.ok("Role assigned successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }
}

record CreateUserRequest(@NotBlank String name, @NotBlank @Email String email) {}