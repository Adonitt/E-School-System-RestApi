package org.example.schoolmanagementsystem.entities.administration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.schoolmanagementsystem.inheritance.UserBaseInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "admin")
public class AdminEntity extends UserBaseInfo {

    @Column(name = "admin_number", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    public Long adminNumber;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "active", nullable = false)
    private boolean active;
 
}