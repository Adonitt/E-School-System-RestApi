package org.example.schoolmanagementsystem.dtos.teacher;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDetailsDto {

    @Column(name = "teacher_id", unique = true, nullable = false)
    private Long id;


    @Column(name = "personal_number", unique = true, nullable = false, length = 10)
    private String personalNumber;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 7)
    private GenderEnum gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "city", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private CitiesEnum city;

    @Enumerated(EnumType.STRING)
    @Column(name = "country", length = 100)
    private CountryEnum country;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private RoleEnum role;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    private String photo;

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
