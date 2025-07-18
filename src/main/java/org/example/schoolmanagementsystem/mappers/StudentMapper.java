package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.student.StudentDetailsDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.dtos.student.UpdateStudentDto;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper extends SimpleMapper<StudentEntity, CreateStudentDto> {

    List<StudentListingDto> toListingDtoList(List<StudentEntity> entities);

    StudentListingDto toListingDto(StudentEntity entity);

    UpdateStudentDto toUpdateDto(StudentEntity entity);

    StudentEntity fromUpdateDto(UpdateStudentDto dto);


    @Mapping(target = "attendanceRecordIds", expression = "java(mapAttendanceIds(entity.getAttendanceRecords()))")
    @Mapping(target = "gradeIds", expression = "java(mapGradeIds(entity.getGrades()))")
    StudentDetailsDto toDetailsDto(StudentEntity entity);

    default List<Long> mapAttendanceIds(List<AttendanceEntity> attendanceRecords) {
        if (attendanceRecords == null) return null;
        return attendanceRecords.stream()
                .map(AttendanceEntity::getId)
                .collect(Collectors.toList());
    }

    default List<Long> mapGradeIds(List<GradeEntity> grades) {
        if (grades == null) return null;
        return grades.stream()
                .map(GradeEntity::getId)
                .collect(Collectors.toList());
    }
}
