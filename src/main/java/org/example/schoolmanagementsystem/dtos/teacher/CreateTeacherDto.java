package org.example.schoolmanagementsystem.dtos.teacher;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.dtos.user.UserDto;
import org.example.schoolmanagementsystem.enums.QualificationEnum;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeacherDto extends UserDto {

    private Long id;

    @NotNull(message = "Specialization is required")
    @Size(min = 3, max = 100, message = "Specialization must be between 3 and 100 characters")
    private String specialization;

    @PositiveOrZero(message = "Years of experience must be a non-negative number")
    private int yearsOfExperience;

    @PositiveOrZero(message = "Salary must be a non-negative number")
    private double salary;

    @PastOrPresent(message = "Employment date must be in the past or present")
    @NotNull(message = "Employment date is required")
    private LocalDate employmentDate;

    private QualificationEnum qualification;

    private List<Long> subjectIds;

    private String photo;
}
