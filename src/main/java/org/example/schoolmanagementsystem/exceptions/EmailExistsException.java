package org.example.schoolmanagementsystem.exceptions;

import jakarta.persistence.EntityExistsException;

public class EmailExistsException extends EntityExistsException {
    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException() {
        super("Email already exists");
    }

}
