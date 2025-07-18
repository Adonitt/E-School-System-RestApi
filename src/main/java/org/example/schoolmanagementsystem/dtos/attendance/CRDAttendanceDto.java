package org.example.schoolmanagementsystem.dtos.attendance;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CRDAttendanceDto {

    private Long id;

    @NotNull
    private Long studentId;

    @NotNull
    private Long subjectId;

    @NotNull
    private Boolean present;

    private String notes;
}
