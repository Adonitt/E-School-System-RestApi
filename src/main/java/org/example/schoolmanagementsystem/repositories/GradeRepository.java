package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
    boolean existsByStudentAndSubjectAndTeacher(StudentEntity student, SubjectEntity subject, TeacherEntity teacher);

    List<GradeEntity> findAllByStudentId(Long studentId);


}
