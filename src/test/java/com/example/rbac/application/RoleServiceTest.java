package com.example.rbac.application;

import com.example.rbac.application.interfaces.RoleRepository;
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
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;
    private UUID roleId;

    @BeforeEach
    void setUp() {
        roleId = UUID.randomUUID();
        role = new Role("ADMIN");
    }

    @Test
    void createRole_success() {
        doNothing().when(roleRepository).save(any(Role.class));
        UUID id = roleService.createRole("ADMIN");
        assertNotNull(id);
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void findRoleById_success() {
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        Role foundRole = roleService.findRoleById(roleId);
        assertEquals(role, foundRole);
    }

    @Test
    void findRoleById_notFound_throwsException() {
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> roleService.findRoleById(roleId));
    }
}