package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Object login(String email, String password) {

        Optional<AdminEntity> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && passwordEncoder.matches(password, admin.get().getPassword())) {
            return admin.get();
        }

        Optional<StudentEntity> student = studentRepository.findByEmail(email);
        if (student.isPresent() && passwordEncoder.matches(password, student.get().getPassword())) {
            return student.get();
        }

        Optional<TeacherEntity> teacher = teacherRepository.findByEmail(email);
        if (teacher.isPresent() && passwordEncoder.matches(password, teacher.get().getPassword())) {
            return teacher.get();
        }

        throw new EntityNotFoundException("Invalid email or password.");
    }

    @Override
    public boolean sendPasswordResetEmail(String email) {
        boolean exists = adminRepository.findByEmail(email).isPresent() ||
                studentRepository.findByEmail(email).isPresent() ||
                teacherRepository.findByEmail(email).isPresent();

        if (exists) {
            // Simulate email sending (Replace with actual email logic)
            System.out.println("Password reset email sent to: " + email);
            return true;
        }
        return false;
    }

    @Override
    public void changePassword(Long adminId, String password) {
        AdminEntity admin = adminRepository.findById(adminId).orElseThrow(() -> new EntityNotFoundException("Admin with id " + adminId + " does not exist"));
        admin.setPassword(passwordEncoder.encode(password));
        adminRepository.save(admin);
    }
}
