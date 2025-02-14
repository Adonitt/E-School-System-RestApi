package org.example.schoolmanagementsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("grades")
public class GradesController {
    @GetMapping("")
    public String grades() {
        return "grades/grades";
    }
}
