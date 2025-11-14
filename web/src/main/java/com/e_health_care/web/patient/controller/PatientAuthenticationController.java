package com.e_health_care.web.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e_health_care.web.patient.dto.PatientDTO;
import com.e_health_care.web.patient.service.PatientAuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/patient")
public class PatientAuthenticationController {
    
    @Autowired
    private PatientAuthenticationService authServicePatient;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("patient", new PatientDTO());
        return "patient-register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute PatientDTO patientDTO) {
        authServicePatient.register(patientDTO);
        return "redirect:/patient/login";
    }

    @GetMapping("/success") 
    public String successForm() {
        return "patient-success";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("patient", new PatientDTO());
        return "patient-login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute PatientDTO patientDTO, HttpServletResponse response) {
        String token = authServicePatient.verify(patientDTO);

        if (token != null) {
            Cookie cookie = new Cookie("jwt-token-patient", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            // cookie.setSecure(true); // Enable in production (HTTPS)
            response.addCookie(cookie);
            return "redirect:/patient/index";
        } else {
            return "redirect:/patient/login?error";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt-token-patient", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie); 
        return "redirect:/patient/login?logout";
    }
}
