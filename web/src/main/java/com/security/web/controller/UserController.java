package com.security.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.web.dto.UserDTO;
import com.security.web.model.User;
import com.security.web.repository.UserRepository;
import com.security.web.service.UserRegisterService;
import jakarta.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserRegisterService userRegisterService;

    @GetMapping("/students")
    public List<User> getAllUser() {
        return userRepo.findAll(); 
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDTO userDTO) {
        User user = userRegisterService.register(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login (@Valid @RequestBody User user) {
        return userRegisterService.verify(user);
    }
    
}
