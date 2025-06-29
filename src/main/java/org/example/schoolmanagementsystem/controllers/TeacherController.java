package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.teacher.CreateTeacherDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherDetailsDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherListingDto;
import org.example.schoolmanagementsystem.dtos.teacher.UpdateTeacherDto;
import org.example.schoolmanagementsystem.services.interfaces.TeacherService;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService service;

    @GetMapping()
    public ResponseEntity<List<TeacherListingDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDetailsDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CreateTeacherDto> create(@Valid @RequestBody CreateTeacherDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<UpdateTeacherDto> modify(@PathVariable Long id, @Valid @RequestBody UpdateTeacherDto dto) {
        return ResponseEntity.ok(service.modify(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        service.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/default")
    public CreateTeacherDto getDefaultTeacher() {
        return new CreateTeacherDto();
    }



}
