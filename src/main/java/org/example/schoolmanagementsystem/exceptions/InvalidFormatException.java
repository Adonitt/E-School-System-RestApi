package org.example.schoolmanagementsystem.exceptions;

import jakarta.persistence.EntityExistsException;

public class InvalidFormatException extends EntityExistsException {
    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException() {
        super("Personal number already exists");
    }
}
