package org.example.schoolmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "grades")
public class GradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;  // Student who received the grade

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subject;  // Linked subject (e.g., Math, Science)

    @Column(name = "grade", nullable = false)
    private double grade;  // Grade received (e.g., 85.5)

    @Column(name = "semester", nullable = false, length = 20)
    private String semester;  // E.g., "Fall 2024"

    @Column(name = "academic_year", nullable = false, length = 10)
    private String academicYear;  // E.g., "2024-2025"

    @Column(name = "comments", length = 255)
    private String comments;  // Optional teacher comments

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher; // Teacher who assigned the grade
}
