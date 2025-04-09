package com.shopsphere.shopsphere.controllers;

import com.shopsphere.shopsphere.models.User;
import com.shopsphere.shopsphere.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/testing")
    public String testing() {

        System.out.println("Received request at controller level");


        return "text";
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {

        System.out.println("Received User at controller level" + user);

        try {
            User savedUser = authenticationService.register(user);

            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            String token = authenticationService.login(user);

            return new ResponseEntity<>(token,HttpStatus.OK);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e);
        }
    }
}
