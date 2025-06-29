package org.example.schoolmanagementsystem.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Long id;

    // Constructor për AdminEntity
    public AppUserDetails(AdminEntity admin) {
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority(admin.getRole().name()));
        this.id = admin.getId();
    }

    // Constructor për TeacherEntity
    public AppUserDetails(TeacherEntity teacher) {
        this.email = teacher.getEmail();
        this.password = teacher.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority(teacher.getRole().name()));
        this.id = teacher.getId();
    }

    // Constructor për StudentEntity
    public AppUserDetails(StudentEntity student) {
        this.email = student.getEmail();
        this.password = student.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority(student.getRole().name()));
        this.id = student.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public Long getId() {
        return id;
    }
}
