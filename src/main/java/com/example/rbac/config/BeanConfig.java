package com.example.rbac.config;

import com.example.rbac.application.UserService;
import com.example.rbac.application.RoleService;
import com.example.rbac.application.interfaces.UserRepository;
import com.example.rbac.application.interfaces.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserService userService(UserRepository userRepository, RoleService roleService) {
        return new UserService(userRepository, roleService);
    }

    @Bean
    public RoleService roleService(RoleRepository roleRepository) {
        return new RoleService(roleRepository);
    }
}