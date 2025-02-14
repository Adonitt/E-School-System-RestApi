package org.example.schoolmanagementsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

    @GetMapping("/administrators")
    public String administrators() {
        return "administration/administrators/administrators";
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
