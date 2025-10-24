package com.e_health_care.web.doctor.security;

import com.e_health_care.web.doctor.service.DoctorDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1) // Bác sĩ load trước user
public class DoctorPartConfiguration {

    private final DoctorDetailsService doctorDetailsService;

    public DoctorPartConfiguration(DoctorDetailsService doctorDetailsService) {
        this.doctorDetailsService = doctorDetailsService;
    }

    // ✅ Encoder riêng cho Doctor
    @Bean(name = "doctorPasswordEncoder")
    public BCryptPasswordEncoder doctorPasswordEncoder() {
        return new BCryptPasswordEncoder(15);
    }

    // ✅ Provider riêng cho Doctor
    @Bean(name = "doctorAuthenticationProvider")
    public AuthenticationProvider doctorAuthenticationProvider(
            @Qualifier("doctorPasswordEncoder") BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(doctorDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // ✅ AuthenticationManager riêng cho Doctor
    @Bean(name = "doctorAuthenticationManager")
    public AuthenticationManager doctorAuthenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ Cấu hình SecurityFilterChain riêng cho Doctor
    @Bean
    public SecurityFilterChain doctorSecurityFilterChain(
            HttpSecurity http,
            @Qualifier("doctorAuthenticationProvider") AuthenticationProvider authProvider) throws Exception {

        http
            .securityMatcher("/doctor/**", "/doctor/login")
            .authenticationProvider(authProvider)
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(request -> {
                request.requestMatchers("/css/**", "/js/**").permitAll();
                request.requestMatchers("/doctor/register", "/doctor/login").permitAll();
                request.requestMatchers("/doctor/**").authenticated();
                request.anyRequest().permitAll();
            })
            .formLogin(form -> form
                .loginPage("/doctor/login")
                .loginProcessingUrl("/doctor/login")
                .defaultSuccessUrl("/doctor/dashboard", true)
                .failureUrl("/doctor/login?error")
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/doctor/logout")
                .logoutSuccessUrl("/doctor/login?logout")
                .permitAll());

        return http.build();
    }
}
