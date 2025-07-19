package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.auth.ChangePasswordDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    boolean sendPasswordResetEmail(String email);

    void changePassword(ChangePasswordDto request, String userEmail);

    UserDetails authenticate(String email, String password);

    String generateToken(UserDetails userDetails);

    UserDetails validateToken(String token);

}
