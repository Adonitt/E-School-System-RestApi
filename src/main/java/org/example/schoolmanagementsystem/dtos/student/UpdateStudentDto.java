package org.example.schoolmanagementsystem.dtos.student;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.CitiesEnum;
import org.example.schoolmanagementsystem.enums.CountryEnum;
import org.example.schoolmanagementsystem.enums.GenderEnum;
import org.example.schoolmanagementsystem.enums.GuardianEnum;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentDto {

    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 10)
    private String personalNumber;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String surname;

    private GenderEnum gender;

    @Past
    private LocalDate birthDate;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String address;

    private CitiesEnum city;

    private CountryEnum country;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 5)
    private String postalCode;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 20)
    private String phoneNumber;

    private String notes;

    @Email
    private String email;

    @NotNull
    @NotBlank
    private String academicYear;

    @NotNull
    @NotBlank
    private String currentSemester;

    private boolean graduated;

    private boolean active;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    private String guardianName;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 20)
    private String guardianPhoneNumber;

    @Email
    @NotNull
    @NotBlank
    private String guardianEmail;

    @NotNull
    @NotBlank
    private String emergencyContactPhone;

    private GuardianEnum relationshipToStudent;

    private String photo;
}
