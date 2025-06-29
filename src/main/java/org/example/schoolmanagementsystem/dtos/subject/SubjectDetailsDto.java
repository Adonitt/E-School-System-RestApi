package org.example.schoolmanagementsystem.dtos.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDetailsDto {

    private Long id;
    private String name;
    private String description;

    private List<String> teacherNames;
}
