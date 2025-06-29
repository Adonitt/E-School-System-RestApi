package org.example.schoolmanagementsystem.dtos.student;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.dtos.user.UserDto;
import org.example.schoolmanagementsystem.enums.GuardianEnum;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentDto extends UserDto {

    private Long id;  // Unique school-assigned number

    private String photo;

    @PastOrPresent(message = "Registered date must be in the past or present")
    private LocalDate registeredDate;  // Date the student joined

    @NotBlank(message = "Section is required")
    @NotNull(message = "Section is required")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{4}$", message = "Academic year must follow format YYYY-YYYY")
    private String academicYear;

    @NotBlank(message = "Current semester is required")
    @NotNull(message = "Current semester is required")
    @Pattern(regexp = "^(Fall|Spring|Summer)\\s20\\d{2}$", message = "Semester must be like 'Fall 2024' or 'Spring 2025'")
    private String currentSemester;  // E.g., "Fall 2024", "Spring 2025"

    private boolean active;

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

    @NotNull(message = "Relationship to student is required")
    private GuardianEnum relationshipToStudent;
}
