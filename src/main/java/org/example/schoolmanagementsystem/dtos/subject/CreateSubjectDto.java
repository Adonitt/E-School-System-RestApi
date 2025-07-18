package org.example.schoolmanagementsystem.dtos.subject;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubjectDto {

    @NotBlank(message = "Subject name is required")
    @Size(max = 100, message = "Subject name must be at most 100 characters")
    private String name;

    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @NotEmpty(message = "At least one teacher ID is required")
    private List<Long> teachers;

    @Min(value = 1, message = "Credits must be greater than 0")
    private int credits;

    @Min(value = 1, message = "Total hours must be greater than 0")
    private int totalHours;



}
