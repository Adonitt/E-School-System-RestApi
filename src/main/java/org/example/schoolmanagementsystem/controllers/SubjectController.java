package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.student.StudentDetailsDto;
import org.example.schoolmanagementsystem.dtos.subject.CreateSubjectDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectDto;
import org.example.schoolmanagementsystem.dtos.subject.UpdateSubjectDto;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.example.schoolmanagementsystem.services.interfaces.StudentService;
import org.example.schoolmanagementsystem.services.interfaces.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService service;
    private final StudentService studentService;

    @GetMapping("")
    public ResponseEntity<List<SubjectDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CreateSubjectDto> create(@Valid @RequestBody CreateSubjectDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<UpdateSubjectDto> modify(@PathVariable Long id, @RequestBody UpdateSubjectDto dto) {
        return ResponseEntity.ok(service.modify(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        service.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/default")
    public CreateSubjectDto getDefaultSubject() {
        return new CreateSubjectDto();
    }

    @GetMapping("/default/update")
    public UpdateSubjectDto getDefaultUpdateSubject() {
        return new UpdateSubjectDto();
    }


    @GetMapping("/by-semester/{semester}")
    public List<SubjectDto> getSubjectsBySemester(@PathVariable SemesterEnum semester) {
        return service.findBySemester(semester);
    }



}
