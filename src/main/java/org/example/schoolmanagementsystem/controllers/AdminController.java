package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.administration.AdminDetailsDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.services.interfaces.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<AdminDto> create(@Valid @RequestBody AdminDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<AdminDto> modify(@PathVariable Long id, @Valid @RequestBody AdminDto dto) {
        return ResponseEntity.ok(service.modify(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDetailsDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        service.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/default")
    public AdminDto getDefaultAdmin() {
        return new AdminDto();
    }
}

