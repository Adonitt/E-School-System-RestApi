package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.student.StudentDetailsDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.dtos.student.UpdateStudentDto;
import org.example.schoolmanagementsystem.services.interfaces.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.SpinnerUI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping("")
    public ResponseEntity<List<StudentListingDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetailsDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CreateStudentDto> create(@Valid @RequestBody CreateStudentDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<UpdateStudentDto> modify(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateStudentDto dto) {
        return ResponseEntity.ok(service.modify(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        service.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/default")
    public CreateStudentDto getDefaultStudent() {
        return new CreateStudentDto();
    }
    @GetMapping("/default/update")
    public UpdateStudentDto getDefaultUpdateStudent() {
        return new UpdateStudentDto();
    }
}
