package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.grade.GradeRequestDto;

public interface GradeService {
    void assignGradeToStudent(GradeRequestDto dto);
}
