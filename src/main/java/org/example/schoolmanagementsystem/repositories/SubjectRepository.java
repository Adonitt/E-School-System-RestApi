package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    @Query("SELECT s FROM SubjectEntity s JOIN s.semesters sem WHERE sem = :semester")
    List<SubjectEntity> findBySemester(@Param("semester") SemesterEnum semester);


}
