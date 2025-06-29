package org.example.schoolmanagementsystem.security;

import lombok.extern.slf4j.Slf4j;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public AppUserDetailsService(AdminRepository adminRepository,
                                 TeacherRepository teacherRepository,
                                 StudentRepository studentRepository) {
        this.adminRepository = adminRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return adminRepository.findByEmail(email)
                .map(AppUserDetails::new)
                .or(() -> teacherRepository.findByEmail(email).map(AppUserDetails::new))
                .or(() -> studentRepository.findByEmail(email).map(AppUserDetails::new))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
