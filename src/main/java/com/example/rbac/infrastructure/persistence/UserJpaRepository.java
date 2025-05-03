package com.example.rbac.infrastructure.persistence;

import com.example.rbac.application.interfaces.UserRepository;
import com.example.rbac.domain.User;
import com.example.rbac.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserJpaRepository implements UserRepository {
    private final UserSpringDataRepository springDataRepository;
    private final RoleSpringDataRepository roleSpringDataRepository;

    public UserJpaRepository(UserSpringDataRepository springDataRepository, RoleSpringDataRepository roleSpringDataRepository) {
        this.springDataRepository = springDataRepository;
        this.roleSpringDataRepository = roleSpringDataRepository;
    }

    @Override
    public void save(User user) {
        UserJpaEntity entity = toJpaEntity(user);
        springDataRepository.save(entity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    private UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        List<RoleJpaEntity> roleEntities = user.getRoles().stream()
                .map(role -> {
                    RoleJpaEntity roleEntity = new RoleJpaEntity();
                    roleEntity.setId(role.getId());
                    roleEntity.setRoleName(role.getRoleName());
                    return roleEntity;
                })
                .collect(Collectors.toList());
        entity.setRoles(roleEntities);
        return entity;
    }

    private User toDomain(UserJpaEntity entity) {
        User user = new User(entity.getName(), entity.getEmail());
        try {
            java.lang.reflect.Field idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(user, entity.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set user ID", e);
        }
        entity.getRoles().forEach(roleEntity -> user.addRole(new Role(roleEntity.getRoleName())));
        return user;
    }
}

interface UserSpringDataRepository extends org.springframework.data.jpa.repository.JpaRepository<UserJpaEntity, UUID> {}