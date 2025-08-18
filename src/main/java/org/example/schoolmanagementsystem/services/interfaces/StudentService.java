package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.student.StudentDetailsDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.dtos.student.UpdateStudentDto;
import org.example.schoolmanagementsystem.services.base_services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService extends
        FindAll<StudentListingDto>,
        FindById<StudentDetailsDto, Long> {

    CreateStudentDto create(CreateStudentDto dto, MultipartFile file);

    UpdateStudentDto modify(Long id, UpdateStudentDto dto, MultipartFile file);

    List<StudentDetailsDto> getStudentsByClass(int classNumber);

    public List<StudentDetailsDto> getStudentsForTeacherAndSubject(String teacherEmail, Long subjectId);

}
