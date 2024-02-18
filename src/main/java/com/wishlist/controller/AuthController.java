package com.wishlist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wishlist.dto.AdminLoginDto;
import com.wishlist.dto.AuthResponseDto;
import com.wishlist.dto.UserLoginDto;
import com.wishlist.dto.UserRegistrationDto;
import com.wishlist.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController // Controller for handling Authentication related requests
@RequestMapping("/api/auth") // Mapping the controller to handle requests at /api/auth
@RequiredArgsConstructor // Lombok annotation to generate a constructor requiring all final fields
@Tag(name = "Authentication") // Tag for Swagger documentation
public class AuthController {

	// Service for handling authentication logic
	private final AuthenticationService authenticationService;

	// Endpoint for registering a new user
	@Operation(summary = "Endpoint for registering a new user")
	@PostMapping("/register")
	public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
		// Register a new user and return the authentication response
		return new ResponseEntity<AuthResponseDto>(authenticationService.registerNewUser(userRegistrationDto),
				HttpStatus.CREATED);
	}

	// Endpoint for logging in an existing user
	@Operation(summary = "Endpoint for logging in existing user")
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> loginHandler(@Valid @RequestBody UserLoginDto userLoginDto) {
		// Log in a user and return the authentication response
		return new ResponseEntity<AuthResponseDto>(authenticationService.loginUser(userLoginDto), HttpStatus.OK);
	}

	// Endpoint for logging in an admin
	@Operation(summary = "Endpoint for logging in admin")
	@PostMapping("/login/admin")
	public ResponseEntity<AuthResponseDto> adminLoginHandler(@Valid @RequestBody AdminLoginDto adminLoginDto) {
		// Log in an admin and return the authentication response
		return new ResponseEntity<AuthResponseDto>(authenticationService.loginAdmin(adminLoginDto), HttpStatus.OK);
	}

}
