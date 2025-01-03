package com.learning.app.service;

import com.learning.app.model.Role;
import com.learning.app.model.User;
import com.learning.app.model.dto.LoginRequest;
import com.learning.app.model.dto.RegisterRequest;
import com.learning.app.repository.RoleRepository;
import com.learning.app.repository.UserRepository;
import com.learning.app.utils.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Autowired
    public void registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email address already in use");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Role defaultRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("No role found"));
        user.getRoles().add(defaultRole);
        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid Password");
        }

        return "dummy-jwt-token";
    }

}
