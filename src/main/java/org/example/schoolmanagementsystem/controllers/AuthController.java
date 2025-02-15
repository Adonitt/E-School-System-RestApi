package org.example.schoolmanagementsystem.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.LoginDto;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.services.interfaces.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @GetMapping("")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "auths/login";
    }

    @PostMapping("")
    public String login(@Valid @ModelAttribute LoginDto loginDto,
                        BindingResult br,
                        RedirectAttributes ra,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        if (br.hasErrors()) {
            br.getAllErrors().forEach(System.out::println);
            return "auths/login";
        }

        try {
            Object user = service.login(loginDto.getEmail(), loginDto.getPassword());

            HttpSession session = request.getSession();
            Cookie cookie;

            switch (user) {
                case AdminEntity admin -> {
                    session.setAttribute("admin", user);
                    cookie = new Cookie("adminId", String.valueOf(admin.getId()));
                }
                case StudentEntity student -> {
                    session.setAttribute("student", user);
                    cookie = new Cookie("studentId", String.valueOf(student.getId()));
                }
                case TeacherEntity teacherEntity -> {
                    session.setAttribute("teacher", user);
                    cookie = new Cookie("teacherId", String.valueOf(teacherEntity.getId()));
                }
                default -> {
                    ra.addFlashAttribute("loginErrorMsg", "Invalid Email or Password!");
                    return "redirect:/";
                }
            }

            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setDomain("localhost");
            response.addCookie(cookie);

            return "redirect:/dashboard";

        } catch (EntityNotFoundException e) {
            ra.addFlashAttribute("loginErrorMsg", "Invalid Email or Password!");
            return "redirect:/";
        }
    }


    @GetMapping("/forgot-password")
    public String forgotPassword() {

        return "auths/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes ra) {

        boolean emailExists = service.sendPasswordResetEmail(email);

        if (emailExists) {
            ra.addFlashAttribute("resetSuccess", "A password reset link has been sent to your email: " + email + ". Please check your inbox.");
            return "redirect:/";
        } else {
            ra.addFlashAttribute("resetError", "Email not found. Please check and try again.");
        }

        return "redirect:/forgot-password";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {

        Cookie cookie = new Cookie("adminId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
