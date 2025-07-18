package org.example.schoolmanagementsystem.dtos.attendance;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAttendanceDto {
    @NotNull
    private Boolean present;

    private String notes;
}
