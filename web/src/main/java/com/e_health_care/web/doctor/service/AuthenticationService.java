package com.e_health_care.web.doctor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.e_health_care.web.doctor.dto.DoctorDTO;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authManager;

    
    public String verify(DoctorDTO doctorDTO) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(doctorDTO.getEmail(), doctorDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return "success";
        }
        return "fail";
    }
}
