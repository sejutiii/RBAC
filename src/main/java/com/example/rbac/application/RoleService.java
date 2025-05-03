package com.example.rbac.application;

import com.example.rbac.application.interfaces.RoleRepository;
import com.example.rbac.domain.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UUID createRole(@NotBlank String roleName) {
        Role role = new Role(roleName);
        roleRepository.save(role);
        return role.getId();
    }

    public Role findRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }
}