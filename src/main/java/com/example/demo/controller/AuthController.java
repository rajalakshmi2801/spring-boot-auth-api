package com.example.demo.controller;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.LoginResponse;
import com.example.demo.response.RegisterResponse;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@RequestBody RegisterDto dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginDto dto) {
        return userService.login(dto);
    }
}
