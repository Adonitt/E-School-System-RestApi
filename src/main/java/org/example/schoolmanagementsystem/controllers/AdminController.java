package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.administration.AdminDetailsDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.dtos.administration.UpdateAdminDto;
import org.example.schoolmanagementsystem.services.interfaces.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<AdminDto> create(
            @Valid @RequestPart("dto") AdminDto dto,
            @RequestPart(value = "photo", required = false) MultipartFile file
    ) {
        return ResponseEntity.ok(service.create(dto, file));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<UpdateAdminDto> modify(@PathVariable Long id,
                                                 @Valid @RequestPart("dto") UpdateAdminDto dto,
                                                 @RequestPart(value = "photo", required = false) MultipartFile file
    ) {
        return ResponseEntity.ok(service.modify(id, dto, file));
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

    @GetMapping("/default/update")
    public UpdateAdminDto getDefaultUpdateAdmin() {
        return new UpdateAdminDto();
    }

}

