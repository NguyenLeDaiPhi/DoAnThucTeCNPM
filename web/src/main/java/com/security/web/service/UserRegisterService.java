package com.security.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.security.web.dto.UserDTO;
import com.security.web.model.User;
import com.security.web.repository.UserRepository;

@Service
public class UserRegisterService {
    @Autowired
    private final UserRepository userRepo;

    @Autowired
    private final AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserRegisterService(UserRepository userRepo, AuthenticationManager authManager) {
        this.authManager = authManager;
        this.userRepo = userRepo;
    }

    public User register(UserDTO userDTO) {
        if (userRepo.findByUsername(userDTO.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already use, please use another email.");
        }

        User saveUser = new User();
        saveUser.setUsername(userDTO.getUsername());
        saveUser.setFirstName(userDTO.getFirstName());
        saveUser.setLastName(userDTO.getLastName());
        saveUser.setPhone(userDTO.getPhone());
        saveUser.setPassword(encoder.encode(userDTO.getPassword()));
        saveUser.setRole("USER");
        return userRepo.save(saveUser);
    }

    public String verify(User user) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return "Success";
        }

        return "false";
    }
}
