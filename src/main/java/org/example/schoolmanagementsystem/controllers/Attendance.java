package org.example.schoolmanagementsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attendance")
public class Attendance {

    @GetMapping("")
    public String attendance() {
        return "attendance/attendance";
    }
}
