package org.example.schoolmanagementsystem.dtos.grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.GradeEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGradeDto {
    private Long id;
    private GradeEnum grade;
    private String academicYear;
    private String semester;
}
