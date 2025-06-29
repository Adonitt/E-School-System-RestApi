package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.subject.*;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubjectMapper extends SimpleMapper<SubjectEntity, CreateSubjectDto> {

    @Mapping(target = "teacherNames", expression = "java(mapTeacherNames(subject.getTeachers()))")
    SubjectListingDto toListingDto(SubjectEntity subject);

    @Mapping(target = "teacherNames", expression = "java(mapTeacherNames(subject.getTeachers()))")
    SubjectDetailsDto toDetailsDto(SubjectEntity subject);

    @Mapping(target = "teachers", ignore = true)
    SubjectEntity fromCreateDto(CreateSubjectDto dto);

    UpdateSubjectDto toUpdateDto(SubjectEntity subject);

    @Mapping(target = "teachers", ignore = true)
    SubjectEntity fromUpdateDto(UpdateSubjectDto dto);

    default List<String> mapTeacherNames(List<org.example.schoolmanagementsystem.entities.administration.TeacherEntity> teachers) {
        if (teachers == null) return null;
        return teachers.stream()
                .map(t -> t.getName() + " " + t.getSurname())
                .collect(Collectors.toList());
    }
}
