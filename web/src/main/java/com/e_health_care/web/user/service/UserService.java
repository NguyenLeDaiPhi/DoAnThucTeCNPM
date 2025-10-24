package com.e_health_care.web.user.service;

import com.e_health_care.web.user.model.User;
import com.e_health_care.web.user.repository.UserRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    @Qualifier("userPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    public void register(User user) {
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void checkEmailExists(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
    }
}