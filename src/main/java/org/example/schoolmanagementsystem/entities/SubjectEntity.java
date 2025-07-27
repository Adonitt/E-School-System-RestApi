// SubjectEntity.java
package org.example.schoolmanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "subject_semesters", joinColumns = @JoinColumn(name = "subject_id"))
    @Column(name = "semester")
    private List<SemesterEnum> semester;

    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private List<StudentEntity> students;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "subject_class_numbers", joinColumns = @JoinColumn(name = "subject_id"))
    @Column(name = "class_number")
    private List<Integer> classNumber;

}
