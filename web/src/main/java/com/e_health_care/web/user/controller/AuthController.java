package com.e_health_care.web.user.controller;

import com.e_health_care.web.user.model.User;
import com.e_health_care.web.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterPage(User user) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
