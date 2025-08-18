package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.administration.AdminDetailsDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.dtos.administration.UpdateAdminDto;
import org.example.schoolmanagementsystem.services.base_services.*;
import org.hibernate.sql.Update;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService extends
        FindAll<AdminListingDto>,
        FindById<AdminDetailsDto, Long>,
        Removable<Long> {
    AdminDto create(AdminDto dto, MultipartFile file);

    UpdateAdminDto modify(Long id, UpdateAdminDto dto, MultipartFile file);

}
