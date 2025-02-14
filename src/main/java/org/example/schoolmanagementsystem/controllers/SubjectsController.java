package org.example.schoolmanagementsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subjects")
public class SubjectsController {
    @GetMapping("/semester-subjects")
    public String semesterSubjects() {
        return "subjects/semester-subjects";
    }

    @GetMapping("/elective-subjects")
    public String electiveSubjects() {
        return "subjects/elective-subjects";
    }
}
