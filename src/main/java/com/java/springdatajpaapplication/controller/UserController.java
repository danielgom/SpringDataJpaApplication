package com.java.springdatajpaapplication.controller;

import com.java.springdatajpaapplication.dto.RoleUserRequest;
import com.java.springdatajpaapplication.dto.UserRequest;
import com.java.springdatajpaapplication.entity.User;
import com.java.springdatajpaapplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/auth/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody UserRequest userRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/user").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(userRequest));
    }

    @PatchMapping("/")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUserRequest roleUserRequest) {
        userService.addRoleToUser(roleUserRequest);
        return ResponseEntity.ok().build();
    }
}
