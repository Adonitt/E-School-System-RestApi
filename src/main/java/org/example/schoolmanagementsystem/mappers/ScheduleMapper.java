package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.schedule.CRUDScheduleDto;
import org.example.schoolmanagementsystem.entities.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

@Mapper(componentModel = "spring")
@Primary
public interface ScheduleMapper {
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    ScheduleEntity toEntity(CRUDScheduleDto dto);

    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "teacherId", source = "teacher.id")
    CRUDScheduleDto toDto(ScheduleEntity entity);
}
