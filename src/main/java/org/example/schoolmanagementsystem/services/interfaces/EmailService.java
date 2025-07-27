package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.enums.GradeEnum;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.example.schoolmanagementsystem.inheritance.UserBaseInfo;

public interface EmailService {
    void sendPasswordChangeEmail(String toEmail, String fullName, String currentPassword);

    void sendWelcomeEmail(String toEmail, String fullName, String role, String email);

    void sendChangePasswordNotification(UserBaseInfo user);

    void sendGradeNotification(StudentEntity student, GradeEntity grade);

    void sendGradeUpdateNotification(StudentEntity student, GradeEntity updatedGrade);

    void sendReexaminationNotification(StudentEntity student, SubjectEntity subject, String academicYear, SemesterEnum semester, double attendance);

    void sendReexaminationNotificationDueToGrade(StudentEntity student, SubjectEntity subject, String academicYear, SemesterEnum semester, GradeEnum grade);
}
