package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.schedule.CRUDScheduleDto;
import org.example.schoolmanagementsystem.dtos.student.StudentDetailsDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.services.base_services.*;

import java.util.List;

public interface ScheduleService extends Addable<CRUDScheduleDto>,
        Modifiable<CRUDScheduleDto, Long>,
        FindAll<CRUDScheduleDto>,
        FindById<CRUDScheduleDto, Long>,
        Removable<Long> {

    List<CRUDScheduleDto> getScheduleByClass(int classNumber);

    List<StudentListingDto> getStudentsByClass(Integer classNumber);


}
