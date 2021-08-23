package com.java.springdatajpaapplication.service;

import com.java.springdatajpaapplication.dto.RoleRequest;
import com.java.springdatajpaapplication.entity.Role;
import com.java.springdatajpaapplication.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role saveRole(RoleRequest roleRequest) {
        log.info("Saving user {} to the database", roleRequest.getName());
        return roleRepository.save(mapToEntity(roleRequest));
    }

    private Role mapToEntity(RoleRequest roleRequest) {
        return Role.builder()
                .name(roleRequest.getName())
                .build();
    }
}
