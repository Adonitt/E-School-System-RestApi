package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.subject.SubjectDto;
import org.example.schoolmanagementsystem.dtos.teacher.*;
import org.example.schoolmanagementsystem.services.base_services.*;

import java.util.List;

public interface TeacherService extends
        Addable<CreateTeacherDto>,
        Modifiable<UpdateTeacherDto, Long>,
        Removable<Long>,
        FindAll<TeacherListingDto>,
        FindById<TeacherDetailsDto, Long> {

    List<SubjectDto> getSubjectsByTeacherEmail(String email);

}
