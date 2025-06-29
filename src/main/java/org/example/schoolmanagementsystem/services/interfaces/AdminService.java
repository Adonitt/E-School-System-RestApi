package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.administration.AdminDetailsDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.services.base_services.*;

public interface AdminService extends
        Addable<AdminDto>,
        FindAll<AdminListingDto>,
        FindById<AdminDetailsDto, Long>,
        Modifiable<AdminDto, Long>,
        Removable<Long> {

}
