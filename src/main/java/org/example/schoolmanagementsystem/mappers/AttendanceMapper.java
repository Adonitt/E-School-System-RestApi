package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.attendance.CRDAttendanceDto;
import org.example.schoolmanagementsystem.dtos.attendance.UpdateAttendanceDto;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    AttendanceEntity toEntity(CRDAttendanceDto dto);

    @Mapping(target = "studentId", expression = "java(entity.getStudent().getId())")
    @Mapping(target = "subjectId", expression = "java(entity.getSubject().getId())")
    CRDAttendanceDto toDto(AttendanceEntity entity);

    UpdateAttendanceDto toUpdateDto(AttendanceEntity entity);

    List<CRDAttendanceDto> toDtoList(List<AttendanceEntity> entities);

}
