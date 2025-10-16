package org.example.schoolmanagementsystem.exceptions;

public class StudentNotActiveException extends RuntimeException {
    public StudentNotActiveException(String message) {
        super(message);
    }
}
