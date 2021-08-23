package com.java.springdatajpaapplication.exception;

public class NewNotFoundException extends RuntimeException{

    public NewNotFoundException(String message) {
        super(message);
    }
}
