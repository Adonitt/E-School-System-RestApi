package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.teacher.CreateTeacherDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherDetailsDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherListingDto;
import org.example.schoolmanagementsystem.dtos.teacher.UpdateTeacherDto;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public interface TeacherMapper extends SimpleMapper<TeacherEntity, CreateTeacherDto> {

    List<TeacherListingDto> toListingDto(List<TeacherEntity> entity);

    @Mapping(target = "subjectIds", source = "subjects", qualifiedByName = "mapSubjectIds")
    TeacherDetailsDto toDetailsDto(TeacherEntity entity);

    UpdateTeacherDto toUpdateDto(TeacherEntity entity);

    @Mapping(target = "subjects", ignore = true)
    TeacherEntity fromUpdateDto(UpdateTeacherDto dto);

    @Named("mapSubjectIds")
    default List<Long> mapSubjectIds(List<SubjectEntity> subjects) {
        if (subjects == null) return null;
        return subjects.stream()
                .map(SubjectEntity::getId)
                .collect(Collectors.toList());
    }
}
