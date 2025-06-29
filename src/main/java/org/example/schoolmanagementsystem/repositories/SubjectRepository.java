package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
}
