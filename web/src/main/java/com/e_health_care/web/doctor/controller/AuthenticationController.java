package com.e_health_care.web.doctor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.e_health_care.web.doctor.dto.DoctorDTO;
import com.e_health_care.web.doctor.service.AuthenticationService;

@RestController
public class AuthenticationController {
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/doctor/login")
    public String signIn(@RequestBody DoctorDTO doctorDTO) {
        return authService.verify(doctorDTO);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
