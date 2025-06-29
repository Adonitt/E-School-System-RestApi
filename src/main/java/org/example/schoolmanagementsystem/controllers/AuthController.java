package org.example.schoolmanagementsystem.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.auth.AuthResponse;
import org.example.schoolmanagementsystem.dtos.auth.LoginDto;
import org.example.schoolmanagementsystem.services.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        var user = service.authenticate(loginDto.getEmail(), loginDto.getPassword());

        var token = service.generateToken(user);

        var authResponse = new AuthResponse(token, 86400000L);

        return ResponseEntity.ok(authResponse);
    }

}
