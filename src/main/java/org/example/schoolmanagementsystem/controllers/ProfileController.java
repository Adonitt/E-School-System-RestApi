package org.example.schoolmanagementsystem.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.helpers.FileHelper;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.services.interfaces.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final AdminRepository repository;
    private final AuthService service;
    private final PasswordEncoder passwordEncoder;
    private final FileHelper fileHelper;

    @GetMapping("")
    public String profile() {
        return "profile/profile";
    }

    @GetMapping("/change-password")
    public String changePassword(@ModelAttribute AdminEntity admin, Model model) {
        model.addAttribute("admin", admin.getId());
        return "profile/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @RequestParam("adminId") Long adminId,
                                 RedirectAttributes ra) {

        AdminEntity existingAdmin = repository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found."));
        if (existingAdmin == null) {
            ra.addFlashAttribute("changeError", "Admin not found.");
        }

        System.out.println("Admin ID: " + adminId + ", Current Password: " + currentPassword + ", New Password: " + newPassword + ", Confirm Password: " + confirmPassword);

        if (!passwordEncoder.matches(currentPassword, existingAdmin.getPassword())) {
            ra.addFlashAttribute("currentPassNotCorrect", "Current password is incorrect.");
            return "redirect:/profile/change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            ra.addFlashAttribute("passwordNotMatch", "New password and confirm password do not match.");
            return "redirect:/profile/change-password";
        }

        service.changePassword(adminId, newPassword);
        ra.addFlashAttribute("changeSuccess", "Password has been changed successfully.");
        return "redirect:/profile";
    }

    @PostMapping("/send-reset-link")
    public String sendResetLink(RedirectAttributes ra) {
        ra.addFlashAttribute("sentSuccess", "A password reset link has been sent to your email. Please check your inbox.");
        return "redirect:/profile";
    }

    @PostMapping("/change-photo")
    public String changePhoto(@RequestParam("photoFile") MultipartFile photoFile,
                              HttpSession session,
                              RedirectAttributes ra) {
        AdminEntity admin = (AdminEntity) session.getAttribute("admin");

        String photo = uploadPhoto(admin, photoFile);
        admin.setPhoto(photo);

        repository.save(admin);

        session.setAttribute("admin", admin);

        ra.addFlashAttribute("photoChangedSuccess", "Profile photo updated successfully.");
        return "redirect:/profile";
    }


    private String uploadPhoto(AdminEntity admin, MultipartFile photoFile) {
        try {
            String fileName = fileHelper.uploadFile("target/classes/static/assets-a/img",
                    photoFile.getOriginalFilename(),
                    photoFile.getBytes());
            return "/assets-a/img/" + fileName;
        } catch (Exception e) {
            System.out.println("Error uploading photo: " + e.getMessage());
            return admin.getPhoto();
        }
    }


}
