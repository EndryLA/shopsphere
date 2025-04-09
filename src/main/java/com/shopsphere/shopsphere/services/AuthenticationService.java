package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.User;
import com.shopsphere.shopsphere.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {


    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthenticationService(JwtService jwtService, AuthenticationProvider authProvider, AuthenticationManager authManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public String login(User user) {

        Authentication authentication = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        ));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        } else {
            return "Invalid credentials";
        }

    }


    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }
}
