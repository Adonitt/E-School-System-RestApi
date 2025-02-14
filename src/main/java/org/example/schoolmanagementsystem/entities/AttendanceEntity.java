package org.example.schoolmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "attendance")
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;  // Student who attended

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subject;

    @Column(name = "date", nullable = false)
    private LocalDate date;  // Date of attendance

    @Column(name = "status", nullable = false, length = 10)
    private String status;  // Present, Absent, Late

    @Column(name = "notes", length = 255)
    private String notes;  // Optional notes (e.g., "Medical Leave")

}
