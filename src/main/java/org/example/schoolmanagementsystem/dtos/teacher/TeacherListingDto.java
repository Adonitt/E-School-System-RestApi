package org.example.schoolmanagementsystem.dtos.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.CitiesEnum;
import org.example.schoolmanagementsystem.enums.RoleEnum;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherListingDto {

    private Long id;

    private String personalNumber;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private CitiesEnum city;

    private RoleEnum role;
}
