package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.attendance.CRDAttendanceDto;
import org.example.schoolmanagementsystem.dtos.attendance.UpdateAttendanceDto;
import org.example.schoolmanagementsystem.services.base_services.*;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService extends Addable<CRDAttendanceDto>,
        Modifiable<UpdateAttendanceDto, Long>,
        FindAll<CRDAttendanceDto>,
        FindById<CRDAttendanceDto, Long>,
        Removable<Long> {

    public List<CRDAttendanceDto> getAttendanceBySubject(Long subjectId);
}
