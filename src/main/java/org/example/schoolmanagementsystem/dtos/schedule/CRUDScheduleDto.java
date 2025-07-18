package org.example.schoolmanagementsystem.dtos.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CRUDScheduleDto {

    private Long id;

    private int classNumber;

    private Long subjectId;

    private Long teacherId;

    private int dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;
}
