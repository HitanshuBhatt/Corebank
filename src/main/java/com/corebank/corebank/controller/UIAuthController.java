package com.corebank.corebank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIAuthController {



    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // register.html
    }
}
