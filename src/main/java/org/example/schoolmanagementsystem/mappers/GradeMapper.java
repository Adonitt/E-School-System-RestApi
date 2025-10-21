package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.grade.CRDGradeDto;
import org.example.schoolmanagementsystem.dtos.grade.UpdateGradeDto;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.context.annotation.Primary;

@Mapper(componentModel = "spring")
@Primary
public interface GradeMapper extends SimpleMapper<GradeEntity, CRDGradeDto> {

    @Override
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "teacherId", source = "teacher", qualifiedByName = "mapTeacherId")
    @Mapping(target = "teacher", source = "teacher", qualifiedByName = "mapTeacherName")
    @Mapping(target = "studentName", source = "student", qualifiedByName = "mapStudentName")
    @Mapping(target = "subjectName", source = "subject", qualifiedByName = "mapSubjectName")
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

    @Mapping(target = "studentName", source = "student", qualifiedByName = "mapStudentName")
    UpdateGradeDto toUpdateDto(GradeEntity entity);

    @Named("mapStudentName")
    default String mapStudentName(StudentEntity student) {
        if (student == null) return null;
        return student.getName() + " " + student.getSurname();
    }

    @Named("mapSubjectName")
    default String mapSubjectName(SubjectEntity subject) {
        if (subject == null) return null;
        return subject.getName();
    }


    @Named("mapTeacherName")
    default String mapTeacherName(TeacherEntity teacher) {
        if (teacher == null) return null;
        return teacher.getName() + " " + teacher.getSurname();
    }

    @Named("mapTeacherId")
    default Long mapTeacherId(TeacherEntity teacher) {
        return teacher != null ? teacher.getId() : null;
    }
}
