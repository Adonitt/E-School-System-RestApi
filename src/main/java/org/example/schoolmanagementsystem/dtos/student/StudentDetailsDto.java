package org.example.schoolmanagementsystem.dtos.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private LocalDate registeredDate;

    private String academicYear;

    private String currentSemester;

    private double gpa;

    private int completedSemesters;

    private boolean graduated;

    private boolean active;

    private String guardianName;

    private String guardianPhoneNumber;

    private String guardianEmail;

    private GuardianEnum relationshipToStudent;

    private String emergencyContactPhone;

    private List<Long> attendanceRecordIds;

    private List<Long> gradeIds;
}
