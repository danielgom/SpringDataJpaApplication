package com.java.springdatajpaapplication.dto;

import com.java.springdatajpaapplication.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name;
    private String username;
    private String password;
    private Collection<Role> roles;
}
