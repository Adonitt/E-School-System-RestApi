package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.auth.AuthResponse;
import org.example.schoolmanagementsystem.dtos.auth.ChangePasswordDto;
import org.example.schoolmanagementsystem.dtos.auth.LoginDto;
import org.example.schoolmanagementsystem.services.impls.AuthServiceImpl;
import org.example.schoolmanagementsystem.services.interfaces.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService service;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            service.authenticate(loginDto.getEmail(), loginDto.getPassword());
            var userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
            var token = service.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token, 86400000L));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDto request) {
        String email = AuthServiceImpl.getLoggedInUserEmail();
        service.changePassword(request, email);
        return ResponseEntity.ok("Password changed successfully");
    }

    @GetMapping("/default-change-password")
    public ChangePasswordDto getDefaultChangePassword() {
        return new ChangePasswordDto();
    }


}
