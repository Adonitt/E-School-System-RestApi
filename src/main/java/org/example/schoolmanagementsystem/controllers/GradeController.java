package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.grade.CRDGradeDto;
import org.example.schoolmanagementsystem.dtos.grade.UpdateGradeDto;
import org.example.schoolmanagementsystem.services.interfaces.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("/add")
    public ResponseEntity<?> createGrade(
            @Valid @RequestBody CRDGradeDto dto) {
        return ResponseEntity.ok(gradeService.add(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGrade(
            @PathVariable Long id,
            @Valid @RequestBody UpdateGradeDto dto
    ) {
        return ResponseEntity.ok(gradeService.modify(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CRDGradeDto> deleteGrade(@PathVariable Long id) {
        gradeService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CRDGradeDto>> getAllGrades() {
        return ResponseEntity.ok(gradeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CRDGradeDto> getGrade(@PathVariable Long id) {
        return ResponseEntity.ok(gradeService.findById(id));
    }


    @GetMapping("/default")
    public CRDGradeDto getDefaultGrade() {
        return new CRDGradeDto();
    }

    @GetMapping("/group-by-subject")
    public ResponseEntity<Map<String, List<CRDGradeDto>>> groupBySubject() {
        return ResponseEntity.ok(gradeService.groupBySubject());
    }

    @GetMapping("/group-by-academic-year")
    public ResponseEntity<Map<String, List<CRDGradeDto>>> groupByAcademicYear() {
        return ResponseEntity.ok(gradeService.groupByAcademicYear());
    }

    @GetMapping("/group-by-semester")
    public ResponseEntity<Map<String, List<CRDGradeDto>>> groupBySemester() {
        return ResponseEntity.ok(gradeService.groupBySemester());
    }

    @GetMapping("/group-by-student")
    public ResponseEntity<Map<String, List<CRDGradeDto>>> groupByStudent() {
        return ResponseEntity.ok(gradeService.groupByStudent());
    }

    @GetMapping("/group-by-teacher")
    public ResponseEntity<Map<String, List<CRDGradeDto>>> groupByTeacher() {
        return ResponseEntity.ok(gradeService.groupByTeacher());
    }


}
