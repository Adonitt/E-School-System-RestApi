package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.attendance.CRDAttendanceDto;
import org.example.schoolmanagementsystem.dtos.attendance.UpdateAttendanceDto;
import org.example.schoolmanagementsystem.services.interfaces.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService service;


    @GetMapping("")
    public ResponseEntity<List<CRDAttendanceDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<CRDAttendanceDto> create(@Valid @RequestBody CRDAttendanceDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CRDAttendanceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @PutMapping("/modify/{id}")
    public ResponseEntity<UpdateAttendanceDto> modify(@PathVariable Long id, @Valid @RequestBody UpdateAttendanceDto dto) {
        return ResponseEntity.ok(service.modify(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        service.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/default")
    public CRDAttendanceDto getDefaultAttendance() {
        return new CRDAttendanceDto();
    }

    @GetMapping("/default/update")
    public UpdateAttendanceDto getDefaultUpdateAttendance() {
        return new UpdateAttendanceDto();
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<CRDAttendanceDto>> getAttendanceBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(service.getAttendanceBySubject(subjectId));
    }
}
