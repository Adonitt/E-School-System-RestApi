package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.grade.CRDGradeDto;
import org.example.schoolmanagementsystem.dtos.grade.UpdateGradeDto;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

@Mapper(componentModel = "spring")
@Primary
public interface GradeMapper extends SimpleMapper<GradeEntity, CRDGradeDto> {

    @Override
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "subjectId", source = "subject.id")
    CRDGradeDto toDto(GradeEntity entity);

    @Override
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    GradeEntity toEntity(CRDGradeDto dto);

    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "subject", ignore = true)
    GradeEntity fromUpdateDto(UpdateGradeDto dto);

    UpdateGradeDto toUpdateDto(GradeEntity entity);

    default String mapTeacherName(TeacherEntity teacher) {
        if (teacher == null) return null;
        return teacher.getName() + " " + teacher.getSurname();
    }
}
