package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    boolean existsByStudentIdAndSubjectIdAndDate(Long studentId, Long subjectId, LocalDate date);
}
