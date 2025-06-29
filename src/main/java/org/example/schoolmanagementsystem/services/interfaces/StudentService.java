package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.dtos.student.UpdateStudentDto;
import org.example.schoolmanagementsystem.services.base_services.*;

public interface StudentService extends
        Addable<CreateStudentDto>,
        FindAll<StudentListingDto>,
        FindById<CreateStudentDto, Long>,
        Modifiable<UpdateStudentDto,Long>,
        Removable<Long> {
}
