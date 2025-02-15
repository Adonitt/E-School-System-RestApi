package org.example.schoolmanagementsystem.exceptions;

import jakarta.persistence.EntityExistsException;

public class PersonalNumberExists extends EntityExistsException {
    public PersonalNumberExists(String message) {
        super(message);
    }

    public PersonalNumberExists() {
        super("Personal number already exists");
    }
}
