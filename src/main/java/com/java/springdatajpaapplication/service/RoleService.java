package com.java.springdatajpaapplication.service;

import com.java.springdatajpaapplication.dto.RoleRequest;
import com.java.springdatajpaapplication.entity.Role;

public interface RoleService {

    Role saveRole(RoleRequest role);
}
