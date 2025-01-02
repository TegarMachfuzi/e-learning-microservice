package com.learning.app.controller;

import com.learning.app.model.dto.LoginRequest;
import com.learning.app.model.dto.RegisterRequest;
import com.learning.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<String> loginUser(LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok("token");
    }
}
