package com.java.springdatajpaapplication.controller;

import com.java.springdatajpaapplication.dto.RoleRequest;
import com.java.springdatajpaapplication.entity.Role;
import com.java.springdatajpaapplication.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/")
    public ResponseEntity<Role> saveRole(RoleRequest roleRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/role").toUriString());
        return ResponseEntity.created(uri).body(roleService.saveRole(roleRequest));
    }
}
