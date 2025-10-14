package com.e_health_care.web.doctor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.e_health_care.web.doctor.service.DoctorDetailsService;

@Configuration
@EnableWebSecurity
public class DoctorPartConfiguration {

        @Autowired
        private DoctorDetailsService doctorDetailsService;

        public DoctorPartConfiguration(DoctorDetailsService doctorDetailsService) {
                this.doctorDetailsService = doctorDetailsService;
        }
        
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http 
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> { 
                    request.requestMatchers("/css/**", "js/**").permitAll();
                    request.anyRequest().authenticated();
                })
                .formLogin(form -> form 
                        .loginPage("/login")
                        .defaultSuccessUrl("/index")
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
                return http.build();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(doctorDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(15);
        }
}
