// AdminEntity.java
package org.example.schoolmanagementsystem.entities.administration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter; // Zëvendëson @Data
import lombok.NoArgsConstructor;
import lombok.Setter; // Zëvendëson @Data
import lombok.experimental.SuperBuilder;
import org.example.schoolmanagementsystem.enums.DepartmentEnum;
import org.example.schoolmanagementsystem.inheritance.UserBaseInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "admin")
public class AdminEntity extends UserBaseInfo {

    @Column(name = "admin_number")
    public Long adminNumber;

    @Column(name = "department")
    @Enumerated(EnumType.STRING)
    private DepartmentEnum department;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "accept_terms_and_conditions", nullable = false)
    private boolean acceptTermsAndConditions;

}