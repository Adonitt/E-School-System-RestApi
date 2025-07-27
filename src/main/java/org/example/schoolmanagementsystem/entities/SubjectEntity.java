// SubjectEntity.java
package org.example.schoolmanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;

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
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToMany(mappedBy = "subjects")
    @JsonBackReference
    @ToString.Exclude
    private List<TeacherEntity> teachers;

    @Column(name = "credits", nullable = false)
    private int credits;

    @Column(name = "total_hours", nullable = false)
    private int totalHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private List<SemesterEnum> semesters;
}
