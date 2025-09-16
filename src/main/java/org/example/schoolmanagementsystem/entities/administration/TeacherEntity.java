// TeacherEntity.java
package org.example.schoolmanagementsystem.entities.administration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.enums.QualificationEnum;
import org.example.schoolmanagementsystem.inheritance.UserBaseInfo;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "teachers")

public class TeacherEntity extends UserBaseInfo {

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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @JsonIgnore
    @ToString.Exclude
    private List<SubjectEntity> subjects;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<GradeEntity> grades;
}
