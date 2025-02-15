package org.example.schoolmanagementsystem.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.enums.*;
import org.example.schoolmanagementsystem.helpers.FileHelper;
import org.example.schoolmanagementsystem.services.interfaces.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequestMapping("/administration")
@RequiredArgsConstructor
public class AdministrationController {
    private final AdminService adminService;
    private final FileHelper fileHelper;

    @GetMapping("/administrators")
    public String administrators(Model model) {
        model.addAttribute("admins", adminService.findAll());
        return "administration/administrators/administrators";
    }

    @GetMapping("administrators/create")
    public String createUser(Model model) {
        model.addAttribute("adminDto", new AdminDto());
        return enumValuesAdmin(model);
    }

    @PostMapping("administrators/create")
    public String createUser(@Valid AdminDto adminDto, BindingResult br, RedirectAttributes ra, Model model, MultipartFile photoFile) {

        if (br.hasErrors()) {
            br.getAllErrors().forEach(System.out::println);
            return enumValuesAdmin(model);
        }

        String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", ""); // Keep only numbers
        String sevenDigitUuid = uuid.substring(0, 9); // Take the first 7 digits
        adminDto.setAdminNumber(Long.parseLong(sevenDigitUuid)); // Convert to long

        adminDto.setRole(RoleEnum.ADMINISTRATOR);
        adminDto.setActive(true);
        adminDto.setCreatedBy("Admin");
        adminDto.setCreatedDate(LocalDateTime.now());

        adminDto.setPhoto("/assets/img/admin.png");

        adminService.add(adminDto);
        System.out.println("admin added successfully! ");
        ra.addFlashAttribute("adminMsg", "Administrator with admin number " + adminDto.getAdminNumber() + " added successfully!");
        return "redirect:/administration/administrators";
    }

    private String enumValuesAdmin(Model model) {
        model.addAttribute("roles", RoleEnum.values());
        model.addAttribute("genders", GenderEnum.values());
        model.addAttribute("cities", CitiesEnum.values());
        model.addAttribute("countries", CountryEnum.values());
        model.addAttribute("departments", DepartmentEnum.values());
        return "administration/administrators/create";
    }

    @GetMapping("/administrators/{id}/details")
    public String adminDetails(@PathVariable Long id, Model model) {
        model.addAttribute("admin", adminService.findById(id));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = adminService.findById(id).getCreatedDate().format(formatter);

        model.addAttribute("createdDate", formattedDate);
        return "administration/administrators/details";
    }


    @GetMapping("/teachers")
    public String teachers() {
        return "administration/teachers/teachers";
    }

    @GetMapping("/students")
    public String students() {
        return "administration/students/students";
    }


}
