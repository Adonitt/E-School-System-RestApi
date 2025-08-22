package org.example.schoolmanagementsystem.configs;

import lombok.RequiredArgsConstructor;
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
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // -------------------- PUBLIC --------------------
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/forgot-password").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/enums/**").permitAll()

                        // -------------------- PASSWORD CHANGE --------------------
                        .requestMatchers(HttpMethod.PUT, "/api/v1/auth/change-password").authenticated()

                        // -------------------- ADMIN (FULL ACCESS) --------------------
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN")

                        // -------------------- TEACHER --------------------
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/teacher/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/grades/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/grades/**").hasRole("TEACHER")

                        // -------------------- STUDENT --------------------
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/v1/teacher/**").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/v1/student/**").hasRole("STUDENT")

                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

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
