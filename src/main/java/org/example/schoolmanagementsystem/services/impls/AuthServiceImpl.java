package org.example.schoolmanagementsystem.services.impls;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.exceptions.ResourceNotFoundException;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.security.AppUserDetailsService;
import org.example.schoolmanagementsystem.services.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;


    @Value("${jwt.secret}")
    private String secretKey;
    private final Long expirationTime = 60 * 60 * 1000L;


    @Override
    public UserDetails authenticate(String email, String password) {
        System.out.printf("Authenticating user with email: {}", email);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        System.out.printf("Loaded user details for email: {}", email);
        return userDetails;
    }


    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername()) // kjo është email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public UserDetails validateToken(String token) {
        String email = extractUsername(token);
        return userDetailsService.loadUserByUsername(email);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
        // ne body qe na u kthy prej tokenit, po i thojme qe me marre veq sub, qe ne rastin tone osht email
    }


    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getLoggedInUserEmail() {
        Authentication authentication = getAuthentication();
        return authentication.getName();
    }

    public static String getLoggedInUserRole() {
        Authentication authentication = getAuthentication();
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }


    @Override
    public boolean sendPasswordResetEmail(String email) {
        boolean exists = adminRepository.findByEmail(email).isPresent() ||
                studentRepository.findByEmail(email).isPresent() ||
                teacherRepository.findByEmail(email).isPresent();

        if (exists) {
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
