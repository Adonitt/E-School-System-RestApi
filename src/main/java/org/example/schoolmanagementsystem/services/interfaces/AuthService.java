package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.entities.administration.AdminEntity;

public interface AuthService {
    Object login(String email, String password);
    boolean sendPasswordResetEmail(String email);

}
