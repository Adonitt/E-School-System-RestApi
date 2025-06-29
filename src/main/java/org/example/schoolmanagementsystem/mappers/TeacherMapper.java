package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.teacher.CreateTeacherDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherDetailsDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherListingDto;
import org.example.schoolmanagementsystem.dtos.teacher.UpdateTeacherDto;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = "spring")
public interface TeacherMapper extends SimpleMapper<TeacherEntity, CreateTeacherDto> {

    List<TeacherListingDto> toListingDto(List<TeacherEntity> entity);

    TeacherDetailsDto toDetailsDto(TeacherEntity entity);

    UpdateTeacherDto toUpdateDto(TeacherEntity entity);

    TeacherEntity fromUpdateDto(UpdateTeacherDto dto);
}
