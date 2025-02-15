package org.example.schoolmanagementsystem.entities.administration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.enums.QualificationEnum;
import org.example.schoolmanagementsystem.inheritance.UserBaseInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "teachers")
public class TeacherEntity extends UserBaseInfo {

    @Column(name = "teacher_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "specialization", nullable = false, length = 100)
    private String specialization;

    @Column(name = "years_of_experience", nullable = false)
    private int yearsOfExperience;

    @Column(name = "salary")
    private double salary;

    @Column(name = "employment_date", nullable = false)
    private LocalDate employmentDate;

    @Column(name = "qualification", length = 255)
    private QualificationEnum qualification;

    @ManyToMany
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<SubjectEntity> subjects; // Many-to-Many with subjects

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GradeEntity> grades; // One teacher -> Many grades


}
