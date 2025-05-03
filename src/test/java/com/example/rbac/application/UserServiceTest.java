package com.example.rbac.application;

import com.example.rbac.application.interfaces.UserRepository;
import com.example.rbac.domain.User;
import com.example.rbac.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;

    private User user;
    private Role role;
    private UUID userId;
    private UUID roleId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        roleId = UUID.randomUUID();
        user = new User("John Doe", "john@example.com");
        role = new Role("ADMIN");
    }

    @Test
    void createUser_success() {
        doNothing().when(userRepository).save(any(User.class));
        UUID id = userService.createUser("John Doe", "john@example.com");
        assertNotNull(id);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void assignRoleToUser_success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleService.findRoleById(roleId)).thenReturn(role);
        userService.assignRole(userId, roleId);
        verify(userRepository).save(user);
        assertFalse(user.getRoles().isEmpty());
    }

    @Test
    void assignRoleToUser_userNotFound_throwsException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.assignRole(userId, roleId));
    }

    @Test
    void findUserById_success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User foundUser = userService.findUserById(userId);
        assertEquals(user, foundUser);
    }

    @Test
    void findUserById_notFound_throwsException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.findUserById(userId));
    }
}