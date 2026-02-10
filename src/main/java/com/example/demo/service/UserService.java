package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.LoginResponse;
import com.example.demo.response.RegisterResponse;
import com.example.demo.util.AESUtil;
import com.example.demo.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<RegisterResponse> register(RegisterDto dto) {
    	
		if (dto == null) {
			throw new CustomException("Request is empty");
		}

		if(dto.getEmail().isBlank() || dto.getPassword().isBlank())
    		throw new CustomException("Please fill mandatory fields");
    	
		
		log.info("Password : {}",dto.getPassword());
		
		if (userRepository.findByEmail(dto.getEmail()).isPresent())
			throw new CustomException("Email already exists");

        User user = new User();
        user.setEmail(dto.getEmail());
        String decryptedPassword = AESUtil.decrypt(dto.getPassword());
        log.info("decrypted password : {}",decryptedPassword);
        String hashedPassword = passwordEncoder.encode(decryptedPassword);
        user.setPassword(hashedPassword);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
		ApiResponse<RegisterResponse> response = new ApiResponse<RegisterResponse>();
		RegisterResponse register = new RegisterResponse();

		try {
			User savedUser = userRepository.save(user);

			// Return ApiResponse
			response.setCode(200);
			response.setMessage("User registered successfully");
			register.setEmail(savedUser.getEmail());
			response.setData(register);
			return response;

		} catch (Exception e) {
			throw new CustomException("Failed to register user: " + e.getMessage());
		}
    }

    public ApiResponse<LoginResponse> login(LoginDto dto) {
    	
    	if(dto == null) {
    		throw new CustomException("Request is empty");
    	}
    	
    	if(dto.getEmail().isBlank() || dto.getPassword().isBlank())
    		throw new CustomException("Please fill mandatory fields");
    	
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException("User not found"));

        String decryptedFromUI = AESUtil.decrypt(dto.getPassword());
        if(!passwordEncoder.matches(decryptedFromUI, user.getPassword())) {
            throw new CustomException("Invalid credentials");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        
        ApiResponse<LoginResponse> response = new ApiResponse<LoginResponse>();
        response.setCode(200);
		response.setMessage("login successfully");
		
        LoginResponse login = new LoginResponse();
        login.setToken(token);
		response.setData(login);
		
		return response;

    }
}
