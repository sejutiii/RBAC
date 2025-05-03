package com.example.rbac.application.interfaces;

import com.example.rbac.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(UUID id);
}
