package org.example.schoolmanagementsystem.services.impls;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.auth.ChangePasswordDto;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.exceptions.ResourceNotFoundException;
import org.example.schoolmanagementsystem.inheritance.UserBaseInfo;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.security.AppUserDetails;
import org.example.schoolmanagementsystem.security.AppUserDetailsService;
import org.example.schoolmanagementsystem.services.interfaces.AuthService;
import org.example.schoolmanagementsystem.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailsService userDetailsService;
    private final EmailService emailService;


    @Value("${jwt.secret}")
    private String secretKey;
    private final Long expirationTime = 60 * 60 * 1000L;


    @Override
    public UserDetails authenticate(String email, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("Password mismatch!");
            throw new BadCredentialsException("Invalid email or password");
        }

        var auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return userDetails;
    }


    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        AppUserDetails appUser = (AppUserDetails) userDetails;

        claims.put("id", appUser.getId());
        claims.put("role", appUser.getRole());
        claims.put("email", appUser.getUsername());
        claims.put("fullName", appUser.getFullName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername()) // email
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
    public void changePassword(ChangePasswordDto request, String userEmail) {
        UserBaseInfo user = findUserByEmail(userEmail);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        emailService.sendChangePasswordNotification(user);

        saveUser(user);
    }

    private void saveUser(UserBaseInfo user) {
        if (user instanceof AdminEntity) {
            adminRepository.save((AdminEntity) user);
        } else if (user instanceof TeacherEntity) {
            teacherRepository.save((TeacherEntity) user);
        } else if (user instanceof StudentEntity) {
            studentRepository.save((StudentEntity) user);
        } else {
            throw new IllegalStateException("Unknown user type");
        }
    }

    private UserBaseInfo findUserByEmail(String email) {
        Optional<AdminEntity> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) return adminOpt.get();

        Optional<TeacherEntity> teacherOpt = teacherRepository.findByEmail(email);
        if (teacherOpt.isPresent()) return teacherOpt.get();

        Optional<StudentEntity> studentOpt = studentRepository.findByEmail(email);
        if (studentOpt.isPresent()) return studentOpt.get();

        throw new UsernameNotFoundException("User with email " + email + " not found");
    }


}
