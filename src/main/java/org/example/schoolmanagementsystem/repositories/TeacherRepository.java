package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    boolean existsByPersonalNumber(String personalNumber);

    boolean existsByEmail(String email);

    Optional<TeacherEntity> findByEmail(String email);

}
