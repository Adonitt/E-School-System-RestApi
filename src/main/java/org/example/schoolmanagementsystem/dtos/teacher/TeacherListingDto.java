package org.example.schoolmanagementsystem.dtos.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.CitiesEnum;
import org.example.schoolmanagementsystem.enums.CountryEnum;
import org.example.schoolmanagementsystem.enums.RoleEnum;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherListingDto {

    private Long id;

    private String photo;

    private String name;

    private String surname;

    private String email;

    private String specialization;

    private CountryEnum country;


}
