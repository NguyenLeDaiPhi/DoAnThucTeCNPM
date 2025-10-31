package com.e_health_care.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.e_health_care.web.patient.service.PatientDetailsService;

@Configuration
@EnableWebSecurity
@Order(2)
public class PatientSecurityConfiguration {

    @Autowired
    private PatientDetailsService patientDetailsService;

    @Autowired
    private JwtFilterPatient jwtFilterPatient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain patientSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/patient/**", "/css/**", "/js/**", "/images/**", "/")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilterPatient, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(request -> request
                .requestMatchers("/patient/login", "/patient/register", "/patient/success", "/css/**", "/js/**", "/images/**", "/").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}

