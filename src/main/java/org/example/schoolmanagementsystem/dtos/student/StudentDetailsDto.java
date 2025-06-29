package org.example.schoolmanagementsystem.dtos.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsDto {
    private Long id;

    private String photo;

    private String personalNumber;

    private String name;

    private String surname;

    private GenderEnum gender;

    private LocalDate birthDate;

    private String address;

    private CitiesEnum city;

    private CountryEnum country;

    private String postalCode;

    private String phoneNumber;

    private RoleEnum role;

    private String notes;

    private String email;

    private String password;

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;

    private LocalDate registeredDate;  // Date the student joined

    private GradeEnum grade;  // E.g., "10A", "12B"

    private String academicYear;  // E.g., "2024-2025"

    private String currentSemester;  // E.g., "Fall 2024", "Spring 2025"

    private double gpa;  // Grade Point Average

    private int completedSemesters;  // Number of completed semesters

    private boolean graduated;  // True if the student has finished school

    private boolean active;  // True if currently enrolled

    private String guardianName;

    private String guardianPhoneNumber;

    private String guardianEmail;

    private GuardianEnum relationshipToStudent;

    private String emergencyContactPhone;

    private List<AttendanceEntity> attendanceRecords;

    private List<GradeEntity> grades;
}
