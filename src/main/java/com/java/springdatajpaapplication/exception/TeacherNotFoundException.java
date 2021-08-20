package com.java.springdatajpaapplication.exception;

public class TeacherNotFoundException extends RuntimeException{

    public TeacherNotFoundException(String message) {
        super(message);
    }
}
