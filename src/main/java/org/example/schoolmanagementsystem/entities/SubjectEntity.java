package org.example.schoolmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "subjects")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;  // e.g., "Mathematics", "Biology"

    @Column(name = "description", length = 255)
    private String description;  // Optional subject description

    @ManyToMany(mappedBy = "subjects")
    private List<TeacherEntity> teachers; // Many-to-Many with teachers


    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GradeEntity> grades;  // Link to grades for this subject
}
