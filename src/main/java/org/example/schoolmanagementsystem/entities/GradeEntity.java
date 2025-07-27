// GradeEntity.java
package org.example.schoolmanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.enums.GradeEnum;
import org.example.schoolmanagementsystem.enums.SemesterEnum;

import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    private GradeEnum grade;

    private String academicYear;

    private SemesterEnum semester;

    private LocalDate dateGiven;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference
    @ToString.Exclude
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @ToString.Exclude
    private TeacherEntity teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @ToString.Exclude
    private SubjectEntity subject;

    @Column(name = "attendance_percentage_used")
    private Double attendancePercentageUsed;

}
