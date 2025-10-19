package com.e_health_care.web.doctor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.e_health_care.web.doctor.dto.DoctorDTO;

@Service
public class AuthenticationService {

    @Autowired
    private  AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;
    
    public String verify(DoctorDTO doctorDTO) {
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    doctorDTO.getEmail(),
                    doctorDTO.getPassword(),
                    java.util.Collections.singletonList(new SimpleGrantedAuthority(doctorDTO.getUppercase_role()))
                )
            );
            // If authentication is successful, generate and return the token
            return jwtService.generateToken(doctorDTO.getEmail());
        } catch (BadCredentialsException e) {
            // If authentication fails, return null to indicate failure
            return null;
        }
    }
}
