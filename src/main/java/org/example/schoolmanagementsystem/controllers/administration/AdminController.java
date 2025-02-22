package org.example.schoolmanagementsystem.controllers.administration;

import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.enums.*;
import org.example.schoolmanagementsystem.helpers.FileHelper;
import org.example.schoolmanagementsystem.infrastructure.groups.OnPost;
import org.example.schoolmanagementsystem.services.interfaces.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequestMapping("/administration")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final FileHelper fileHelper;

    @GetMapping("/administrators")
    public String administrators(Model model) {
        model.addAttribute("admins", adminService.findAll());
        return "/administration/administrators/list";
    }

    @GetMapping("administrators/create")
    public String createUser(Model model) {
        model.addAttribute("adminDto", new AdminDto());
        enumValuesAdmin(model);
        return "administration/administrators/create";

    }

    @PostMapping("administrators/create")
    public String createUser(@Valid AdminDto adminDto,
                             BindingResult br,
                             RedirectAttributes ra,
                             Model model,
                             @SessionAttribute("admin") AdminEntity adminSession) {

        if (br.hasErrors()) {
            br.getAllErrors().forEach(System.out::println);
            enumValuesAdmin(model);
        }

        String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        String sevenDigitUuid = uuid.substring(0, 9);
        adminDto.setAdminNumber(Long.parseLong(sevenDigitUuid));

        adminDto.setRole(RoleEnum.ADMINISTRATOR);
        adminDto.setActive(true);
        adminDto.setCreatedBy(adminSession.getName() + " " + adminSession.getSurname());
        adminDto.setCreatedDate(LocalDateTime.now());

        adminDto.setPhoto("/assets/img/admin.png");

        adminService.add(adminDto);
        System.out.println("admin added successfully! ");
        ra.addFlashAttribute("adminMsg", "Administrator with admin number " + adminDto.getAdminNumber() + " added successfully!");
        return "redirect:/administration/administrators";
    }

    private void enumValuesAdmin(Model model) {
        model.addAttribute("roles", RoleEnum.values());
        model.addAttribute("genders", GenderEnum.values());
        model.addAttribute("cities", CitiesEnum.values());
        model.addAttribute("countries", CountryEnum.values());
        model.addAttribute("departments", DepartmentEnum.values());
    }

    @GetMapping("/administrators/{id}/details")
    public String adminDetails(@PathVariable Long id, Model model) {
        model.addAttribute("admin", adminService.findById(id));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = adminService.findById(id).getCreatedDate().format(formatter);
        model.addAttribute("createdDate", formattedDate);

        if (adminService.findById(id).getModifiedBy() != null) {
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedEditedDate = adminService.findById(id).getModifiedDate().format(formatter2);
            model.addAttribute("formattedEditedDate", formattedEditedDate);
        }
        return "administration/administrators/details";
    }


    @GetMapping("/administrators/{id}/edit")
    public String adminEdit(@PathVariable Long id, Model model) {
        model.addAttribute("adminDto", adminService.findById(id));
        enumValuesAdmin(model);
        return "administration/administrators/edit";
    }

    @Validated(OnPost.class)
    @PostMapping("/administrators/{id}/edit")
    public String adminEdit(@Valid @ModelAttribute AdminDto adminDto,
                            BindingResult br,
                            @RequestParam("photoFile") MultipartFile photoFile,
                            RedirectAttributes ra, @SessionAttribute("admin") AdminEntity adminSession) {

        if (br.hasErrors()) {
            br.getAllErrors().forEach(System.out::println);
            return "redirect:administration/administrators";
        }

        if (!photoFile.isEmpty()) {
            try {
                var fileName = fileHelper.uploadFile("target/classes/static/assets-a/img", photoFile.getOriginalFilename(), photoFile.getBytes());
                adminDto.setPhoto("/assets-a/img/" + fileName);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        adminDto.setModifiedBy(adminSession.getName() + " " + adminSession.getSurname());
        adminDto.setModifiedDate(LocalDateTime.now());

        ra.addFlashAttribute("editedSuccessfully", "Administrator with id " + adminDto.getId() + " edited successfully!");
        adminService.modify(adminDto.getId(), adminDto);
        return "redirect:/administration/administrators";
    }

    @GetMapping("/administrators/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        model.addAttribute("admin", adminService.findById(id));
        return "administration/administrators/delete";
    }

    @PostMapping("/administrators/{id}/delete")
    public String deleteAdmin(@PathVariable Long id,
                              RedirectAttributes ra,
                              AdminDto adminDto, @SessionAttribute("admin") AdminEntity adminSession) {
        adminService.removeById(id);
        ra.addFlashAttribute("deletedSuccessfully", "Administrator with id " + id + " deleted successfully!");
        return "redirect:/administration/administrators";
    }


}
