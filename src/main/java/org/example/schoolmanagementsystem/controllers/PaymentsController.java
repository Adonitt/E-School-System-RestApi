package org.example.schoolmanagementsystem.controllers;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentsController {

    @GetMapping("")
    public String payments() {
        return "payments/payments";
    }

}
