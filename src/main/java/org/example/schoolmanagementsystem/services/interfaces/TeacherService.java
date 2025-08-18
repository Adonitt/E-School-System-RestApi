package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectDto;
import org.example.schoolmanagementsystem.dtos.teacher.*;
import org.example.schoolmanagementsystem.services.base_services.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService extends
        Removable<Long>,
        FindAll<TeacherListingDto>,
        FindById<TeacherDetailsDto, Long> {

    CreateTeacherDto create(CreateTeacherDto dto, MultipartFile file);

    UpdateTeacherDto modify(Long id, UpdateTeacherDto dto, MultipartFile file);

    List<SubjectDto> getSubjectsByTeacherEmail(String email);

}
