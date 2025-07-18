package org.example.schoolmanagementsystem.dtos.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.CountryEnum;
import org.example.schoolmanagementsystem.enums.GenderEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentListingDto {
    private Long id;

    private String photo;

    private String personalNumber;

    private String name;

    private String surname;

    private GenderEnum gender;

    private CountryEnum country;

    private int classNumber;

}
