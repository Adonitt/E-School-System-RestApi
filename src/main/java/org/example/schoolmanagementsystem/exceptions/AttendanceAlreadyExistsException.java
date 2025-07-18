package org.example.schoolmanagementsystem.exceptions;

public class AttendanceAlreadyExistsException extends RuntimeException {
    public AttendanceAlreadyExistsException(String message) {
        super(message);
    }
}
