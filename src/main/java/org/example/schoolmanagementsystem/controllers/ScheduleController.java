package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.schedule.CRUDScheduleDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.services.interfaces.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<CRUDScheduleDto> create(@Valid @RequestBody CRUDScheduleDto dto) {
        return ResponseEntity.ok(scheduleService.add(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<CRUDScheduleDto>> getAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CRUDScheduleDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<CRUDScheduleDto> modify(@Valid @PathVariable Long id, @RequestBody CRUDScheduleDto dto) {
        return ResponseEntity.ok(scheduleService.modify(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.removeById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/class/{classNumber}/students")
    public ResponseEntity<List<StudentListingDto>> getStudentsByClass(@PathVariable Integer classNumber) {
        List<StudentListingDto> students = scheduleService.getStudentsByClass(classNumber);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/default")
    public CRUDScheduleDto getDefaultSchedule() {
        return new CRUDScheduleDto();
    }

}
