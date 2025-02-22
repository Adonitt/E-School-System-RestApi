package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByPersonalNumber(String personalNumber);

    Optional<AdminEntity> findByEmail(String email);


}
