package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    boolean existsByPersonalNumber(String personalNumber);
    boolean existsByEmail(String email);
    Optional<StudentEntity> findByEmail(String email);

}
