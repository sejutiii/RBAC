package com.example.rbac.infrastructure.persistence;

import com.example.rbac.application.interfaces.RoleRepository;
import com.example.rbac.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleJpaRepository implements RoleRepository {
    private final RoleSpringDataRepository springDataRepository;

    public RoleJpaRepository(RoleSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public void save(Role role) {
        RoleJpaEntity entity = toJpaEntity(role);
        springDataRepository.save(entity);
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    private RoleJpaEntity toJpaEntity(Role role) {
        RoleJpaEntity entity = new RoleJpaEntity();
        entity.setId(role.getId());
        entity.setRoleName(role.getRoleName());
        return entity;
    }

    private Role toDomain(RoleJpaEntity entity) {
        Role role = new Role(entity.getRoleName());
        try {
            java.lang.reflect.Field idField = Role.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(role, entity.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set role ID", e);
        }
        return role;
    }
}

interface RoleSpringDataRepository extends org.springframework.data.jpa.repository.JpaRepository<RoleJpaEntity, UUID> {}