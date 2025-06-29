package org.example.schoolmanagementsystem.dtos.administration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.dtos.user.UserDto;
import org.example.schoolmanagementsystem.enums.DepartmentEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto extends UserDto {
    private Long adminNumber;

    @NotNull(message = "Department is required")
    private DepartmentEnum department;

    private boolean active;

    private boolean acceptTermsAndConditions;

    private String photo;


}
