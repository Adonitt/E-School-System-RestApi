package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.administration.AdminDetailsDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.services.base_services.Addable;
import org.example.schoolmanagementsystem.services.base_services.FindAll;
import org.example.schoolmanagementsystem.services.base_services.FindById;

public interface AdminService extends Addable<AdminDto>, FindAll<AdminListingDto>, FindById<AdminDetailsDto, Long> {
}
