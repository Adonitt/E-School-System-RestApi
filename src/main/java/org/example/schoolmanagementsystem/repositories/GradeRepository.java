package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
    boolean existsByStudentAndSubjectAndTeacherAndSemester(StudentEntity student, SubjectEntity subject, TeacherEntity teacher, SemesterEnum semester);

    List<GradeEntity> findAllByStudentId(Long studentId);

    List<GradeEntity> findBySubject(SubjectEntity subject);

    List<GradeEntity> findBySubjectId(Long subjectId);

}
