package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.student.StudentDetailsDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.dtos.student.UpdateStudentDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectDto;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper extends SimpleMapper<StudentEntity, CreateStudentDto> {

    // Mapping për listën e studentëve për listing (opsionale nëse e përdor)
    List<StudentListingDto> toListingDtoList(List<StudentEntity> entities);

    // Mapping për një student për listing
    StudentListingDto toListingDto(StudentEntity entity);

    // Mapping për update DTO dhe nga update DTO në entity
    UpdateStudentDto toUpdateDto(StudentEntity entity);

    StudentEntity fromUpdateDto(UpdateStudentDto dto);

    @Mapping(target = "attendanceRecordIds", expression = "java(mapAttendanceIds(entity.getAttendanceRecords()))")
    @Mapping(target = "gradeIds", expression = "java(mapGradeIds(entity.getGrades()))")
    @Mapping(target = "subjectIds", expression = "java(mapSubjectsToIds(entity.getSubjects()))")
    StudentDetailsDto toDetailsDto(StudentEntity entity);

    // Metoda ndihmëse për marre id-të nga attendance
    default List<Long> mapAttendanceIds(List<AttendanceEntity> attendanceRecords) {
        if (attendanceRecords == null) return null;
        return attendanceRecords.stream()
                .map(AttendanceEntity::getId)
                .collect(Collectors.toList());
    }

    // Metoda ndihmëse për marre id-të nga grades
    default List<Long> mapGradeIds(List<GradeEntity> grades) {
        if (grades == null) return null;
        return grades.stream()
                .map(GradeEntity::getId)
                .collect(Collectors.toList());
    }

    // Metoda për konvertimin e listës së StudentEntity në listë StudentDetailsDto
    default List<StudentDetailsDto> toDtoList(List<StudentEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(this::toDetailsDto)
                .collect(Collectors.toList());
    }

    default List<Long> mapSubjectsToIds(List<SubjectEntity> subjects) {
        if (subjects == null) return null;
        return subjects.stream()
                .map(SubjectEntity::getId)
                .collect(Collectors.toList());
    }

    default List<Long> mapStudentsToIds(List<StudentEntity> students) {
        if (students == null) return null;
        return students.stream()
                .map(StudentEntity::getId)
                .collect(Collectors.toList());
    }

    @Mapping(target = "students", expression = "java(mapStudentsToIds(subject.getStudents()))")
    SubjectDto toListingDto(SubjectEntity subject);

}
