package com.example.rbac.application.interfaces;

import com.example.rbac.domain.Role;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    void save(Role role);
    Optional<Role> findById(UUID id);
}