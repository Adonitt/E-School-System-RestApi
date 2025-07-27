package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    @Query("SELECT s FROM SubjectEntity s JOIN s.semester sem WHERE sem = :semester")
    List<SubjectEntity> findBySemester(@Param("semester") SemesterEnum semester);

    List<SubjectEntity> findAllByTeachersContaining(TeacherEntity teacher);


}
