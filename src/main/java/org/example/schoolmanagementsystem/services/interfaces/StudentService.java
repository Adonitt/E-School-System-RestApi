package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.student.StudentDetailsDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.dtos.student.UpdateStudentDto;
import org.example.schoolmanagementsystem.services.base_services.FindAll;
import org.example.schoolmanagementsystem.services.base_services.FindById;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService extends
        FindAll<StudentListingDto>,
        FindById<StudentDetailsDto, Long> {

    CreateStudentDto create(CreateStudentDto dto, MultipartFile file);

    UpdateStudentDto modify(Long id, UpdateStudentDto dto, MultipartFile file);

    List<StudentDetailsDto> getStudentsByClass(int classNumber);

    List<StudentDetailsDto> getStudentsForTeacherAndSubject(String teacherEmail, Long subjectId);

    public String deactivateStudent(Long id);

    public String activateStudent(Long id);
}
