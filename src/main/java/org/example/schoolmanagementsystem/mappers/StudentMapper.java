package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.dtos.student.UpdateStudentDto;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = "spring")
public interface StudentMapper extends SimpleMapper<StudentEntity, CreateStudentDto> {

    List<StudentListingDto> toListingDto(List<StudentEntity> entities);

    UpdateStudentDto toUpdateDto(StudentEntity entity);

    StudentEntity fromUpdateDto(UpdateStudentDto dto);
}
