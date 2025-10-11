package org.example.schoolmanagementsystem.dtos.student;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.dtos.user.UserDto;
import org.example.schoolmanagementsystem.enums.GuardianEnum;
import org.example.schoolmanagementsystem.enums.SemesterEnum;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentDto extends UserDto {

    private Long id;

    private String photo;

    @PastOrPresent(message = "Registered date must be in the past or present")
    private LocalDate registeredDate;

    @NotNull(message = "Academic year is required")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{4}$", message = "Academic year must follow format YYYY-YYYY")
    private String academicYear;

    private boolean active;

    @NotBlank(message = "Guardian name is required")
    @Size(min = 3, max = 100)
    private String guardianName;

    @NotBlank(message = "Guardian phone number is required")

    private String guardianPhoneNumber;

    @Email(message = "Invalid guardian email")
    @NotBlank(message = "Guardian email is required")
    private String guardianEmail;

    @NotBlank(message = "Emergency contact phone is required")
    private String emergencyContactPhone;

    @NotNull(message = "Relationship to student is required")
    private GuardianEnum relationshipToStudent;

    private int classNumber;

}
