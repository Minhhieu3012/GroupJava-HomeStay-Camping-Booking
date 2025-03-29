package com.chotot.travel.controller;

import com.chotot.travel.dto.LoginRequest;
import com.chotot.travel.dto.RegisterRequest;
import com.chotot.travel.model.User;
import com.chotot.travel.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}