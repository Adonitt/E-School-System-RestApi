package org.example.schoolmanagementsystem.dtos.teacher;

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
public class TeacherDetailsDto {

    private Long id;

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

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;

    private String photo;

    private String specialization;

    private int yearsOfExperience;



    private LocalDate employmentDate;

    private QualificationEnum qualification;

    private List<Long> subjectIds;

}
