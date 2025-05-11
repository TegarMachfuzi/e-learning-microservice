package com.learning.app.service;

import com.learning.app.model.Role;
import com.learning.app.model.User;
import com.learning.app.model.dto.LoginRequest;
import com.learning.app.model.dto.RegisterRequest;
import com.learning.app.repository.RoleRepository;
import com.learning.app.repository.UserRepository;
import com.learning.app.utils.JwtTokenProvider;
import com.learning.app.utils.RoleName;
import com.learning.app.utils.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ApiException("Email address already in use", "EMAIL_ALREADY_EXISTS", 400);
        }

        Role defaultRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        user.setRoles(Collections.singleton(defaultRole));


        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid Password");
        }

        JwtTokenProvider tokenProvider = new JwtTokenProvider();
        String token = tokenProvider.createToken(user.getUsername(), user.getRoles().stream()
                .map(role -> role.getName().name())
                .findFirst()
                .orElse(RoleName.ROLE_ADMIN.name()));

        return token;
    }

    public User updateProfile(Long userId, String fullName, String phoneNumber) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        return userRepository.save(user);
    }

    public User updateUserRole(Long userId, RoleName roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new IllegalArgumentException("No role found"));
        user.getRoles().remove(role);
        return userRepository.save(user);
    }

    public List<User> getUsersByRole(RoleName roleName) {
        return userRepository.findAllByRoles_Name(roleName);
    }

}
