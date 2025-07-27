package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    boolean existsByPersonalNumber(String personalNumber);

    boolean existsByEmail(String email);

    Optional<StudentEntity> findByEmail(String email);

    List<StudentEntity> findByClassNumber(int classNumber);

    List<StudentEntity> findByClassNumberIn(List<Integer> classNumbers);


    List<StudentEntity> findByClassNumberInAndCurrentSemesterIn(List<Integer> classNumbers, List<SemesterEnum> semesters);

    List<StudentEntity> findByCurrentSemesterIn(List<SemesterEnum> semesters);

}
