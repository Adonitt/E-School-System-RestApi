package org.example.schoolmanagementsystem.mappers;

import org.example.schoolmanagementsystem.dtos.administration.AdminDetailsDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = "spring")
public interface AdminMapper extends SimpleMapper<AdminEntity, AdminDto> {
    List<AdminListingDto> toListingDto(List<AdminEntity> entities);

    AdminDetailsDto toAdminDetailsDto(AdminEntity entity);
}
