package org.example.schoolmanagementsystem.dtos.administration;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdminDto {
    private Long id;

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

    @NotNull(message = "Department is required")
    private DepartmentEnum department;

    private boolean active;

    private String photo;

    private String modifiedBy;

    private LocalDateTime modifiedDate;
}
