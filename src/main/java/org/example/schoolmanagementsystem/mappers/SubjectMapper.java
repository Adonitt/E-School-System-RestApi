package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.subject.*;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public interface SubjectMapper extends SimpleMapper<SubjectEntity, CreateSubjectDto> {

    @Mapping(target = "teacherNames", expression = "java(mapTeacherNames(subject.getTeachers()))")
    SubjectListingDto toListingDto(SubjectEntity subject);

    @Mapping(target = "teacherNames", expression = "java(mapTeacherNames(subject.getTeachers()))")
    SubjectDetailsDto toDetailsDto(SubjectEntity subject);

    @Mapping(target = "teachers", ignore = true) // Në krijim, injorojmë entitetet e mësuesve
    SubjectEntity fromCreateDto(CreateSubjectDto dto);

    UpdateSubjectDto toUpdateDto(SubjectEntity subject);

    @Mapping(target = "teachers", ignore = true) // Në update, injorojmë listën e entiteteve
    SubjectEntity fromUpdateDto(UpdateSubjectDto dto);

    default List<String> mapTeacherNames(List<TeacherEntity> teachers) {
        if (teachers == null) return null;
        return teachers.stream()
                .map(t -> t.getName() + " " + t.getSurname())
                .collect(Collectors.toList());
    }
}
