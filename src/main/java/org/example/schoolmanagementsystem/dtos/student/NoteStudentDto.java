package org.example.schoolmanagementsystem.dtos.student;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.enums.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteStudentDto {

    private Long id;

    private String photo;

    @NotNull(message = "Personal number is required")
    @NotBlank(message = "Personal number is required")
    @Size(min = 10, max = 10, message = "Personal number must be 10 characters long")
    private String personalNumber;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters long")
    private String name;

    @NotBlank(message = "Surname is required")
    @NotNull(message = "Surname is required")
    @Size(min = 1, max = 50, message = "Surname must be between 1 and 50 characters long")
    private String surname;

    private GenderEnum gender;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Address is required")
    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 100, message = "Address must be between 1 and 100 characters long")
    private String address;

    private CitiesEnum city;

    private CountryEnum country;

    @Size(min = 6, max = 20, message = "Phone number must be between 10 and 20 characters long")
    @NotNull(message = "Phone number is required")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Size(max = 255, message = "Notes must be at most 255 characters long")
    private String notes;

    @Email(message = "Invalid email format")
    private String email;

    private GradeEnum grade;

    @NotBlank(message = "Section is required")
    @NotNull(message = "Section is required")
    private String academicYear;

    @NotBlank(message = "Current semester is required")
    @NotNull(message = "Current semester is required")
    private String currentSemester;

    @Positive(message = "GPA must be a positive number")
    private double gpa;  // Grade Point Average

    @Positive(message = "Completed semesters must be a positive number")
    private int completedSemesters;  // Number of completed semesters

    private boolean graduated;  // True if the student has finished school

    private boolean active;  // True if currently enrolled

    private List<AttendanceEntity> attendanceRecords;

    private List<GradeEntity> grades;
}
