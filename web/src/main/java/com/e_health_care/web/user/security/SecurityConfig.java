package com.e_health_care.web.user.security;

import com.e_health_care.web.user.model.User;
import com.e_health_care.web.user.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2) // Đặt sau cấu hình bác sĩ để tránh ghi đè
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    // ✅ Cung cấp UserDetailsService cho User
    @Bean(name = "userDetailsService")
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userService.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        };
    }

    // ✅ PasswordEncoder riêng cho User
    @Bean(name = "userPasswordEncoder")
    @Primary
    public PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ AuthenticationProvider riêng cho User
    @Bean(name = "userAuthenticationProvider")
    public AuthenticationProvider userAuthenticationProvider(
            @Qualifier("userDetailsService") UserDetailsService userDetailsService,
            @Qualifier("userPasswordEncoder") PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // ⚠️ Không expose AuthenticationManager để tránh trùng lặp với Doctor
    // Spring Security sẽ tự xây từ AuthenticationProvider đã đăng ký trong filter chain

    // ✅ Cấu hình Security cho User
    @Bean
    public SecurityFilterChain userSecurityFilterChain(
            HttpSecurity http,
            @Qualifier("userAuthenticationProvider") AuthenticationProvider authProvider) throws Exception {

        http
            .securityMatcher("/user/**", "/user/login", "/user/register") // Áp dụng riêng cho user
            .authenticationProvider(authProvider)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/register", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/user/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/user/home", true)
                .failureUrl("/user/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
