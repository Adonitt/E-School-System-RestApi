package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.student.StudentDto;
import org.example.schoolmanagementsystem.dtos.user.UserDto;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface StudentMapper extends SimpleMapper<StudentEntity, StudentDto> {
}
