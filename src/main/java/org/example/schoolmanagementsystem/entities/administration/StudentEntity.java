package org.example.schoolmanagementsystem.entities.administration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.enums.GradeEnum;
import org.example.schoolmanagementsystem.enums.GuardianEnum;
import org.example.schoolmanagementsystem.inheritance.UserBaseInfo;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "students")
public class StudentEntity extends UserBaseInfo {

    @Column(name = "student_id", unique = true)
    private Long id;  // Unique school-assigned number

    @Column(name = "registered_date")
    private LocalDate registeredDate;  // Date the student joined

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private GradeEnum grade;  // E.g., "10A", "12B"


    @Column(name = "academic_year")
    private String academicYear;  // E.g., "2024-2025"

    @Column(name = "current_semester", length = 20)
    private String currentSemester;  // E.g., "Fall 2024", "Spring 2025"

    @Column(name = "gpa")
    private double gpa;  // Grade Point Average

    @Column(name = "completed_semesters")
    private int completedSemesters;  // Number of completed semesters

    @Column(name = "graduated")
    private boolean graduated;  // True if the student has finished school

    @Column(name = "active")
    private boolean active;  // True if currently enrolled

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

    // Relationship with Attendance
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttendanceEntity> attendanceRecords;

    // Relationship with Grades
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GradeEntity> grades;
}
