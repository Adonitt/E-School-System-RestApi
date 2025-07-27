package org.example.schoolmanagementsystem.dtos.subject;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.enums.SemesterEnum;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {

    private Long id;
    private String name;
    private String description;

    private List<String> teacherNames;

    private int credits;
    private int totalHours;
    private List<SemesterEnum> semester;
    private List<Integer> classNumber;
    private List<Long> students;

}
