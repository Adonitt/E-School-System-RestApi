package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    boolean sendPasswordResetEmail(String email);

    void changePassword(Long adminId, String password);

    UserDetails authenticate(String email, String password);

    String generateToken(UserDetails userDetails);

    UserDetails validateToken(String token);
}
