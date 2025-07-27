package org.example.schoolmanagementsystem.services.interfaces;

public interface SemesterUpdateService {
    void updateCurrentSemesterForAllStudents();

    void updateAcademicYearForAllStudents();

    double calculateGpaForStudent(Long studentId);
}