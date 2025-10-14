package com.e_health_care.web.doctor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.e_health_care.web.doctor.model.Doctor;
import com.e_health_care.web.doctor.repository.DoctorRepository;

@Service
public class DoctorDetailsService implements UserDetailsService {
    @Autowired
    private final DoctorRepository doctorRepository;

    public DoctorDetailsService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override   
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByEmail(username);
        if (doctor == null) {
            throw new UsernameNotFoundException("The email is not found, please try again.");
        }
        return new User(doctor.getEmail(),
                        doctor.getPassword(),
                        List.of(new SimpleGrantedAuthority(doctor.getROLE())));
    }
}
