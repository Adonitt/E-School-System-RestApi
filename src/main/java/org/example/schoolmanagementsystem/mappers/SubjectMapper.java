package org.example.schoolmanagementsystem.mappers;

import jdk.jfr.Name;
import org.example.schoolmanagementsystem.dtos.subject.*;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubjectMapper extends SimpleMapper<SubjectEntity, CreateSubjectDto> {

    // Single subject to listing DTO
    @Named("toListingDto")
    @Mapping(target = "teacherNames", expression = "java(mapTeacherNames(subject.getTeachers()))")
    @Mapping(target = "students", expression = "java(mapStudentsToIds(subject.getStudents()))")
    SubjectDto toListingDto(SubjectEntity subject);

    // LIST of subjects to listing DTOs
    @IterableMapping(qualifiedByName = "toListingDto")
    List<SubjectDto> toListingDtoList(List<SubjectEntity> subjects);

    // Single subject to details DTO
    @Mapping(target = "teacherNames", expression = "java(mapTeacherNames(subject.getTeachers()))")
    @Mapping(target = "students", expression = "java(mapStudentsToIds(subject.getStudents()))")
    SubjectDto toDetailsDto(SubjectEntity subject);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "credits", source = "credits")
    @Mapping(target = "totalHours", source = "totalHours")
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "semester", source = "semester")
    SubjectEntity fromCreateDto(CreateSubjectDto dto);

    @Mapping(target = "semester", source = "semester")
    UpdateSubjectDto toUpdateDto(SubjectEntity subject);

    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "credits", source = "credits")
    @Mapping(target = "totalHours", source = "totalHours")
    SubjectEntity fromUpdateDto(UpdateSubjectDto dto);

    // Helpers
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
        return null; // e implementon nese duhet
    }

    @Named("mapStringsToSemesterEnums")
    default List<SemesterEnum> mapStringsToSemesterEnums(List<String> semesters) {
        if (semesters == null) return null;
        return semesters.stream()
                .map(s -> {
                    System.out.println("Parsing semester: " + s); // DEBUG
                    try {
                        return SemesterEnum.valueOf(s);
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid semester enum value: " + s, e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Named("mapStudentsToIds")
    default List<Long> mapStudentsToIds(List<StudentEntity> students) {
        if (students == null) return null;
        return students.stream()
                .map(StudentEntity::getId)
                .collect(Collectors.toList());
    }





}
