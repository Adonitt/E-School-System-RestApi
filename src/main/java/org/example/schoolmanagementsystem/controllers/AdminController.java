package org.example.schoolmanagementsystem.controllers;

import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.services.interfaces.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService service;

    @GetMapping("")
    public ResponseEntity<List<AdminListingDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
