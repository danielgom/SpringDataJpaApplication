package com.java.springdatajpaapplication.service;

import com.java.springdatajpaapplication.dto.RoleUserRequest;
import com.java.springdatajpaapplication.dto.UserRequest;
import com.java.springdatajpaapplication.entity.Role;
import com.java.springdatajpaapplication.entity.User;
import com.java.springdatajpaapplication.exception.NewNotFoundException;
import com.java.springdatajpaapplication.repository.RoleRepository;
import com.java.springdatajpaapplication.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(UserRequest userRequest) {
        log.info("Saving user {} to the database", userRequest);
        return userRepository.save(mapToEntity(userRequest));
    }

    @Override
    public void addRoleToUser(RoleUserRequest roleUserRequest) {

        log.info("Adding role {} to user {}", roleUserRequest.getRoleName(), roleUserRequest.getUsername());
        User user = userRepository.findByUsername(roleUserRequest.getUsername())
                .orElseThrow(() -> new NewNotFoundException(String.format("user %s, not found", roleUserRequest.getUsername())));

        Role role = roleRepository.findByName(roleUserRequest.getRoleName())
                .orElseThrow(() -> new NewNotFoundException(String.format("role %s, not found", roleUserRequest.getRoleName())));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NewNotFoundException(String.format("role %s, not found", username)));
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    private User mapToEntity(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .username(userRequest.getUsername())
                .build();
    }
}
