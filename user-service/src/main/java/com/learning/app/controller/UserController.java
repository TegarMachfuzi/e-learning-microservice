package com.learning.app.controller;

import com.learning.app.model.dto.ErrorResponse;
import com.learning.app.model.dto.LoginRequest;
import com.learning.app.model.dto.RegisterRequest;
import com.learning.app.model.dto.SuccessResponse;
import com.learning.app.service.UserService;
import com.learning.app.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@EnableEurekaClient
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Object>> registerUser(@RequestBody RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
        AppUtils.logInfo("Send Payload", registerRequest);

        SuccessResponse<Object> response = new SuccessResponse<>(
                "success",
                HttpStatus.CREATED.value(),
                "User registred successfully",
                null
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<Object>> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        AppUtils.logInfo("Send Payload", loginRequest);
        return ResponseEntity.ok(new SuccessResponse<>(
                "success",
                200,
                "Login Successfully", token));
    }
}
