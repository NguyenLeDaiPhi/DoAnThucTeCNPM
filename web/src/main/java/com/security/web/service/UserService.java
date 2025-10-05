package com.security.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.web.model.User;
import com.security.web.model.UserPrinciple;
import com.security.web.repository.UserRepository;

@Service 
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            System.out.println("User not found, please try again.");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrinciple(user);
    }
}
