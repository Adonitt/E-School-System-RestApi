package org.example.schoolmanagementsystem.dtos.student;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.dtos.user.UserDto;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.enums.GradeEnum;
import org.example.schoolmanagementsystem.enums.GuardianEnum;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends UserDto {

    @NotNull(message = "Student ID is required")
    @NotBlank(message = "Student ID is required")
    @Size(min = 7, max = 7, message = "Student ID must be 7 characters long")
    private Long id;  // Unique school-assigned number

    @NotNull(message = "Registered date is required")
    @NotBlank(message = "Registered date is required")
    @PastOrPresent(message = "Registered date must be in the past or present")
    private LocalDate registeredDate;  // Date the student joined

    private GradeEnum grade;  // E.g., "10A", "12B"

    @NotBlank(message = "Section is required")
    @NotNull(message = "Section is required")
    private String academicYear;  // E.g., "2024-2025"

    @NotBlank(message = "Current semester is required")
    @NotNull(message = "Current semester is required")
    private String currentSemester;  // E.g., "Fall 2024", "Spring 2025"

    @Positive(message = "GPA must be a positive number")
    private double gpa;  // Grade Point Average

    @Positive(message = "Completed semesters must be a positive number")
    private int completedSemesters;  // Number of completed semesters

    private boolean graduated;  // True if the student has finished school

    private boolean active;  // True if currently enrolled

    @NotNull(message = "Guardian name is required")
    @NotBlank(message = "Guardian name is required")
    @Size(min = 3, max = 100, message = "Guardian name must be between 3 and 100 characters long")
    private String guardianName;

    @NotNull(message = "Guardian phone number is required")
    @NotBlank(message = "Guardian phone number is required")
    @Size(min = 10, max = 20, message = "Guardian phone number must be between 10 and 20 characters long")
    private String guardianPhoneNumber;

    @Email(message = "Invalid email address")
    @NotNull(message = "Guardian email is required")
    @NotBlank(message = "Guardian email is required")
    private String guardianEmail;

    private GuardianEnum relationshipToStudent;

    @NotNull(message = "Emergency contact name is required")
    @NotBlank(message = "Emergency contact name is required")
    private String emergencyContactPhone;

    // Relationship with Attendance
    private List<AttendanceEntity> attendanceRecords;

    // Relationship with Grades
    private List<GradeEntity> grades;
}
