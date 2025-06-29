// AttendanceEntity.java
package org.example.schoolmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.*;
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
    private StudentEntity student; // StudentEntity tashmë ka @JsonIgnore për attendanceRecords.

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @ToString.Exclude
    private SubjectEntity subject; // SubjectEntity ka @JsonBackReference tek teachers, por këtu nuk ka problem cikli.

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "notes", length = 255)
    private String notes;
}