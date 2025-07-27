package org.example.schoolmanagementsystem.dtos.grade;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.GradeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CRDGradeDto {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Grade is required")
    private GradeEnum grade;

    @NotBlank(message = "Academic year is required")
    @Size(max = 20, message = "Academic year must be at most 20 characters")
    private String academicYear;

    @NotBlank(message = "Semester is required")
    @Size(max = 20, message = "Semester must be at most 20 characters")
    private String semester;

    private String teacher;

    private Double attendancePercentageUsed;

}
