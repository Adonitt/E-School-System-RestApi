package org.example.schoolmanagementsystem.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public enum RoleEnum {

    ADMINISTRATOR(Set.of(
            PermissionEnum.ADMIN_READ,
            PermissionEnum.ADMIN_CREATE,
            PermissionEnum.ADMIN_UPDATE,
            PermissionEnum.ADMIN_DELETE
    )),
    TEACHER(Set.of(
            PermissionEnum.TEACHER_READ,
            PermissionEnum.TEACHER_UPDATE
    )),
    STUDENT(Set.of(
            PermissionEnum.STUDENT_READ,
            PermissionEnum.STUDENT_UPDATE
    ));

    private final Set<PermissionEnum> permissions;

    RoleEnum(Set<PermissionEnum> permissions) {
        this.permissions = permissions;
    }


    public List<SimpleGrantedAuthority> getAuthorities() {
        var authority = new ArrayList<>(
                getAuthorities().stream()
                        .map(permission -> new SimpleGrantedAuthority(
                                permission.getAuthority()))
                        .toList());
        authority.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authority;
    }
}
