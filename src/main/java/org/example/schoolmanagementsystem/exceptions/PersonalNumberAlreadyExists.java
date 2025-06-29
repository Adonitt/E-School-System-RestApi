package org.example.schoolmanagementsystem.exceptions;

public class PersonalNumberAlreadyExists extends RuntimeException {
    public PersonalNumberAlreadyExists(String message) {
        super(message);
    }
}
