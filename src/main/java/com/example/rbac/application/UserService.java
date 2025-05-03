package com.example.rbac.application;

import com.example.rbac.application.interfaces.UserRepository;
import com.example.rbac.domain.Role;
import com.example.rbac.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public UUID createUser(@NotBlank String name, @Email String email) {
        User user = new User(name, email);
        userRepository.save(user);
        return user.getId();
    }

    public void assignRole(@NotBlank UUID userId, @NotBlank UUID roleId) {
        User user = this.findUserById(userId);
        Role role= roleService.findRoleById(roleId);
        user.addRole(role);
        userRepository.save(user);
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
