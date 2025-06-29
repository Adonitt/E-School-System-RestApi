package org.example.schoolmanagementsystem.dtos.student;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.enums.*;
import org.example.schoolmanagementsystem.infrastructure.groups.OnPost;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentDto {

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

    @NotNull(message = "Postal code is required")
    @NotBlank(message = "Postal code is required")
    @Size(min = 5, max = 5, message = "Postal code must be 5 characters long")
    private String postalCode;

    @Size(min = 6, max = 20, message = "Phone number must be between 10 and 20 characters long")
    @NotNull(message = "Phone number is required")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private RoleEnum role;

    @Size(max = 255, message = "Notes must be at most 255 characters long")
    private String notes;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Section is required")
    @NotNull(message = "Section is required")
    private String academicYear;  // E.g., "2024-2025"

    @NotBlank(message = "Current semester is required")
    @NotNull(message = "Current semester is required")
    private String currentSemester;  // E.g., "Fall 2024", "Spring 2025"

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

    @NotNull(message = "Emergency contact name is required")
    @NotBlank(message = "Emergency contact name is required")
    private String emergencyContactPhone;

    private GuardianEnum relationshipToStudent;

}
