package org.example.schoolmanagementsystem.controllers.administration;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {
    @GetMapping("/teachers")
    public String teachers() {
        return "administration/teachers/teachers";
    }

}
