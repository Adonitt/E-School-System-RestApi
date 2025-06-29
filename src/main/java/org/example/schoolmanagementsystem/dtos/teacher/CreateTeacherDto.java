package org.example.schoolmanagementsystem.dtos.teacher;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.dtos.user.UserDto;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.enums.QualificationEnum;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeacherDto extends UserDto {
    @NotNull(message = "Teacher ID is required")
    @Size(min = 7, max = 7, message = "Teacher ID must be 7 digits")
    private Long id;

    @NotNull(message = "Specialization is required")
    @Size(min = 3, max = 100, message = "Specialization must be between 3 and 100 characters")
    private String specialization;

    @PositiveOrZero(message = "Years of experience must be a non-negative number")
    private int yearsOfExperience;

    @PositiveOrZero(message = "Salary must be a non-negative number")
    private double salary;

    @PastOrPresent(message = "Employment date must be in the past or present")
    private LocalDate employmentDate;

    private QualificationEnum qualification;

    private List<SubjectEntity> subjects; // Many-to-Many with subjects

    private List<GradeEntity> grades; // One teacher -> Many grades

}
