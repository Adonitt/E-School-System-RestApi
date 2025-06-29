package org.example.schoolmanagementsystem.dtos.grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.GradeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequestDto {
    private Long studentId;
    private Long subjectId;     // Optional, or derive from teacher
    private Long teacherId;     // Optional, depends on your use-case
    private GradeEnum grade;
    private String academicYear;
    private String semester;
}
