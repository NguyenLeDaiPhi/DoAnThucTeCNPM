package com.e_health_care.web.doctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e_health_care.web.doctor.dto.DoctorDTO;
import com.e_health_care.web.doctor.service.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/doctor")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @GetMapping("/login") 
    public String showLoginPage(Model model) {
        // Add an empty DoctorDTO to the model for the form to bind to
        model.addAttribute("doctor", new DoctorDTO());
        return "doctor-login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute DoctorDTO doctorDTO, HttpServletResponse response) {
        String token = authService.verify(doctorDTO);

        if (token != null) {
            // On success, create a secure, http-only cookie
            Cookie cookie = new Cookie("jwt-token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            // cookie.setSecure(true); // Enable this in production (HTTPS)
            response.addCookie(cookie);
            return "redirect:/doctor/index";
        } else {
            // On failure, redirect back to the login page with an error flag
            return "redirect:/doctor/login?error";
        }
    }

    @GetMapping("/index")
    public String hello() {
        // Assuming you have a 'hello.html' template
        return "doctor-index"; 
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        // Create a cookie that expires immediately to clear the existing one
        Cookie cookie = new Cookie("jwt-token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // This effectively deletes the cookie
        response.addCookie(cookie);
        return "redirect:/doctor/login?logout";
    }
}
