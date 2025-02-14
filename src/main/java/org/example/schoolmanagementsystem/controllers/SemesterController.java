package org.example.schoolmanagementsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/semesters")
public class SemesterController {
    @GetMapping("")
    public String semester() {
        return "semesters/semester";
    }
}
