package com.e_health_care.web.doctor.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.e_health_care.web.doctor.dto.DoctorDTO;

@Service
public class AuthenticationService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthenticationService(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }
    
    public String verify(DoctorDTO doctorDTO) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                doctorDTO.getEmail(),
                doctorDTO.getPassword(),
                java.util.Collections.singletonList(new SimpleGrantedAuthority(doctorDTO.getUppercase_role()))
            )
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(doctorDTO.getEmail());
        }
        return "false";
    }
}
