// StudentEntity.java
package org.example.schoolmanagementsystem.entities.administration;

import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonManagedReference; // Kjo mund të hiqet nëse përdoret vetëm @JsonIgnore
import jakarta.persistence.*;
import lombok.*;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.enums.GradeEnum;
import org.example.schoolmanagementsystem.enums.GuardianEnum;
import org.example.schoolmanagementsystem.inheritance.UserBaseInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class StudentEntity extends UserBaseInfo {

    @Column(name = "registered_date")
    private LocalDate registeredDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private GradeEnum grade;

    @Column(name = "academic_year")
    private String academicYear;

    @Column(name = "current_semester", length = 20)
    private String currentSemester;

    @Column(name = "gpa")
    private double gpa;

    @Column(name = "completed_semesters")
    private int completedSemesters;

    @Column(name = "graduated")
    private boolean graduated;

    @Column(name = "active")
    private boolean active;

    @Column(name = "guardian_name", length = 100)
    private String guardianName;

    @Column(name = "guardian_phone_number", length = 20)
    private String guardianPhoneNumber;

    @Column(name = "guardian_email", length = 100)
    private String guardianEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_to_student")
    private GuardianEnum relationshipToStudent;

    @Column(name = "emergency_contact_phone", length = 20)
    private String emergencyContactPhone;

    @ToString.Exclude
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AttendanceEntity> attendanceRecords;

    @ToString.Exclude
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<GradeEntity> grades;

    @Column(name = "class_number", nullable = false)
    private int classNumber;

}