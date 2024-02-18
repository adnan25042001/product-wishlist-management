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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

	private final AuthenticationService authenticationService;

	@Operation(summary = "Endpoint for registering a new user")
	@PostMapping("/register")
	public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
		return new ResponseEntity<AuthResponseDto>(authenticationService.registerNewUser(userRegistrationDto),
				HttpStatus.CREATED);
	}

	@Operation(summary = "Endpoint for login existing user")
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> loginHandler(@Valid @RequestBody UserLoginDto userLoginDto) {
		return new ResponseEntity<AuthResponseDto>(authenticationService.loginUser(userLoginDto), HttpStatus.OK);
	}

	@Operation(summary = "Endpoint for login admin")
	@PostMapping("/login/admin")
	public ResponseEntity<AuthResponseDto> adminLoginHandler(@Valid @RequestBody AdminLoginDto adminLoginDto) {
		return new ResponseEntity<AuthResponseDto>(authenticationService.loginAdmin(adminLoginDto), HttpStatus.OK);
	}

}
