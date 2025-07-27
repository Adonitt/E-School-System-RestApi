package org.example.schoolmanagementsystem.dtos.student;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentDto {

    @NotNull(message = "ID must not be null")
    private Long id;

    @NotBlank(message = "Personal number is required")
    @Size(min = 10, max = 10, message = "Personal number must be exactly 10 characters")
    private String personalNumber;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name can have at most 50 characters")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(max = 50, message = "Surname can have at most 50 characters")
    private String surname;

    private GenderEnum gender;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Address can have at most 100 characters")
    private String address;

    private CitiesEnum city;

    private CountryEnum country;

    @NotBlank(message = "Postal code is required")
    @Size(min = 5, max = 5, message = "Postal code must be 5 characters")
    private String postalCode;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 characters")
    private String phoneNumber;

    private String notes;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Academic year is required")
    private String academicYear;

    private SemesterEnum currentSemester;

    private boolean graduated;

    private boolean active;

    @NotBlank(message = "Guardian name is required")
    @Size(min = 3, max = 100, message = "Guardian name must be between 3 and 100 characters")
    private String guardianName;

    @NotBlank(message = "Guardian phone number is required")
    @Size(min = 10, max = 20, message = "Guardian phone number must be between 10 and 20 characters")
    private String guardianPhoneNumber;

    @NotBlank(message = "Guardian email is required")
    @Email(message = "Guardian email should be valid")
    private String guardianEmail;

    @NotBlank(message = "Emergency contact phone is required")
    private String emergencyContactPhone;

    private GuardianEnum relationshipToStudent;

    private String photo;

    private int classNumber;

}
