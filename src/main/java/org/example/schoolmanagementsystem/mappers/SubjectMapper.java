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
    SubjectDto toListingDto(SubjectEntity subject);

    @Mapping(target = "teacherNames", expression = "java(mapTeacherNames(subject.getTeachers()))")
    SubjectDto toDetailsDto(SubjectEntity subject);

    @Mapping(target = "teachers", ignore = true)
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
    default List<Long> mapTeacherEntitiesToIds(List<TeacherEntity> teachers) {
        if (teachers == null) return null;
        return teachers.stream().map(TeacherEntity::getId).collect(Collectors.toList());
    }

    default List<TeacherEntity> mapIdsToTeacherEntities(List<Long> ids) {
        // Optional: Only use if you have access to the repository here (not common in MapStruct)
        return null; // usually resolved in the service
    }

}
