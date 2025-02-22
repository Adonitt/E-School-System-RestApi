package org.example.schoolmanagementsystem.dtos.administration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDetailsDto {
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



    public Long adminNumber;

    private DepartmentEnum department;

    private boolean active;

    private boolean acceptTermsAndConditions;

    private String photo;
}
