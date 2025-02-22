package org.example.schoolmanagementsystem.dtos.administration;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.dtos.user.UserDto;
import org.example.schoolmanagementsystem.enums.DepartmentEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto extends UserDto {


    public Long adminNumber;

    @Enumerated
    private DepartmentEnum department;

    private boolean active;

    @AssertTrue(message = "You must accept the terms and conditions")
    private boolean acceptTermsAndConditions;

    private String photo;

}
