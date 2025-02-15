package org.example.schoolmanagementsystem.dtos.administration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.enums.CountryEnum;
import org.example.schoolmanagementsystem.enums.DepartmentEnum;
import org.example.schoolmanagementsystem.enums.RoleEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminListingDto {

    private Long id;

    public Long adminNumber;

    private String name;

    private String surname;

    private DepartmentEnum department;

    private CountryEnum country;

    private String email;

    private String photo;
}
