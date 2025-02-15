package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.teacher.TeacherDto;
import org.example.schoolmanagementsystem.dtos.user.UserDto;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface TeacherMapper extends SimpleMapper<TeacherEntity, TeacherDto> {


}
