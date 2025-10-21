package org.example.schoolmanagementsystem.dtos.grade;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.GradeEnum;
import org.example.schoolmanagementsystem.enums.SemesterEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CRDGradeDto {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    private String studentName;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    private String subjectName;

    @NotNull(message = "Grade is required")
    private GradeEnum grade;

    private String academicYear;

    private SemesterEnum semester;

    private String teacher;

    private Long teacherId;

    private Double attendancePercentageUsed;

}
