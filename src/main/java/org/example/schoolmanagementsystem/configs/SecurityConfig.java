package org.example.schoolmanagementsystem.configs;

import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.enums.*;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.security.AppUserDetailsService;
import org.example.schoolmanagementsystem.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // e krijon nje objekt te tipit PasswordEncoder (BCryptPasswordEncoder) qe do te perdoret per enkriptimin e passwordit
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/admin").permitAll()
                                .anyRequest().authenticated()

//                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/users/forgot-password").permitAll()
//                        .requestMatchers("/uploads/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "api/enums/**").permitAll()
//
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/auth/change-password").authenticated()
//
//                        .requestMatchers(HttpMethod.GET, "/api/v1/users/profile").hasAnyAuthority(
//                                PermissionEnum.STUDENT_READ.name(),
//                                PermissionEnum.ADMIN_READ.name(),
//                                PermissionEnum.TEACHER_READ.name())
//
//                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority(PermissionEnum.ADMIN_READ.name())
//
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/parties/{id}").hasAuthority(PermissionEnum.ADMIN_UPDATE.name())
//                        .requestMatchers(HttpMethod.POST, "/api/v1/parties").hasAuthority(PermissionEnum.ADMIN_CREATE.name())
//                        .requestMatchers(HttpMethod.GET, "/api/v1/parties/**").permitAll()
//
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/candidates/{id}").hasAuthority(PermissionEnum.ADMIN_UPDATE.name())
//                        .requestMatchers(HttpMethod.GET, "/api/v1/candidates/**").permitAll()
//
//                        .requestMatchers(HttpMethod.GET, "/api/v1/votes/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/votes/**").permitAll()
//
//                        .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAuthority(PermissionEnum.ADMIN_READ.name())
//                        .requestMatchers(HttpMethod.POST, "/api/v1/**").hasAuthority(PermissionEnum.ADMIN_CREATE.name())
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasAuthority(PermissionEnum.ADMIN_UPDATE.name())
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasAuthority(PermissionEnum.ADMIN_DELETE.name())
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean per UserDetailsService me te tre repository-t
    @Bean
    public UserDetailsService userDetailsService(AdminRepository adminRepository,
                                                 TeacherRepository teacherRepository,
                                                 StudentRepository studentRepository,
                                                 PasswordEncoder passwordEncoder) {
        String email = "adonit.halili@gmail.com";

        // Kontrollo nëse admin ekziston, nëse jo krijo të ri
        adminRepository.findByEmail(email).orElseGet(() -> {
            AdminEntity newAdmin = AdminEntity.builder()
                    .personalNumber("1234567890")
                    .name("Adonit")
                    .surname("Halili")
                    .gender(GenderEnum.MALE)
                    .birthDate(LocalDate.of(1995, 1, 1))
                    .address("123 Main Street")
                    .city(CitiesEnum.PRISHTINE)
                    .country(CountryEnum.KOSOVO)
                    .postalCode("10000")
                    .phoneNumber("+38344123456")
                    .role(RoleEnum.ADMINISTRATOR)
                    .notes("Initial system admin")
                    .email(email)
                    .password(passwordEncoder.encode("Admin123.")) // perdor passwordEncoder te injektuar
                    .createdBy("System")
                    .createdDate(LocalDateTime.now())
                    .active(true)
                    .acceptTermsAndConditions(true)
                    .department(DepartmentEnum.IT)
                    .adminNumber(1L)
                    .build();
            return adminRepository.save(newAdmin);
        });

        return new AppUserDetailsService(adminRepository, teacherRepository, studentRepository);
    }


}
