package com.java.springdatajpaapplication.service;

import com.java.springdatajpaapplication.dto.RoleUserRequest;
import com.java.springdatajpaapplication.dto.UserRequest;
import com.java.springdatajpaapplication.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(UserRequest userRequest);

    void addRoleToUser(RoleUserRequest roleUserRequest);

    User getUser(String username);

    List<User> getUsers();

}
