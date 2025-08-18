package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.student.StudentDetailsDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectDto;
import org.example.schoolmanagementsystem.dtos.teacher.CreateTeacherDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherDetailsDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherListingDto;
import org.example.schoolmanagementsystem.dtos.teacher.UpdateTeacherDto;
import org.example.schoolmanagementsystem.services.interfaces.StudentService;
import org.example.schoolmanagementsystem.services.interfaces.TeacherService;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final StudentService studentService;
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
    public ResponseEntity<CreateTeacherDto> create(@Valid @RequestPart("dto") CreateTeacherDto dto,
                                                   @RequestPart(value = "photo",required = false) MultipartFile photo) {
        return ResponseEntity.ok(service.create(dto, photo));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<UpdateTeacherDto> modify(@PathVariable Long id,
                                                   @Valid @RequestPart("dto") UpdateTeacherDto dto,
                                                   @RequestPart(value = "photo",required = false) MultipartFile photo) {
        return ResponseEntity.ok(service.modify(id, dto, photo));
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

    @GetMapping("/my-subjects")
    public ResponseEntity<List<SubjectDto>> getSubjectsForCurrentTeacher(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.getSubjectsByTeacherEmail(userDetails.getUsername()));
    }

    @GetMapping("/students-for-subject/{subjectId}")
    public ResponseEntity<List<StudentDetailsDto>> getStudentsForSubject(
            @PathVariable Long subjectId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String teacherEmail = userDetails.getUsername();

        List<StudentDetailsDto> students = studentService.getStudentsForTeacherAndSubject(teacherEmail, subjectId);

        return ResponseEntity.ok(students);
    }


}
