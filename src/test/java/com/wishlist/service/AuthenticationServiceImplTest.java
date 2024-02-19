package com.wishlist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wishlist.config.JwtService;
import com.wishlist.dto.AdminLoginDto;
import com.wishlist.dto.AuthResponseDto;
import com.wishlist.dto.UserLoginDto;
import com.wishlist.dto.UserRegistrationDto;
import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;

@SpringBootTest // Annotation to specify that this is a Spring Boot Test
public class AuthenticationServiceImplTest {

	@InjectMocks // Annotation to inject the mocks into the service being tested
	private AuthenticationServiceImpl authenticationService;

	@Mock // Annotation to create a mock UserRepository
	private UserRepository userRepository;

	@Mock // Annotation to create a mock PasswordEncoder
	private PasswordEncoder passwordEncoder;

	@Mock // Annotation to create a mock JwtService
	private JwtService jwtService;

	@Mock // Annotation to create a mock AuthenticationManager
	private AuthenticationManager authenticationManager;

	@Test // Annotation to specify that this is a test method
	public void testRegisterNewUser() {
		// Arrange
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		userRegistrationDto.setUsername("test");
		userRegistrationDto.setEmail("test@example.com");
		userRegistrationDto.setPassword("password");

		User user = new User();
		user.setUsername(userRegistrationDto.getUsername());
		user.setEmail(userRegistrationDto.getEmail());
		user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

		// Mock the findByEmail method to return an empty Optional
		when(userRepository.findByEmail(userRegistrationDto.getEmail())).thenReturn(Optional.empty());

		// Mock the save method to return the user
		when(userRepository.save(any(User.class))).thenReturn(user);

		// Mock the generateToken method to return a token
		when(jwtService.generateToken(any(User.class))).thenReturn("token");

		// Act
		AuthResponseDto response = authenticationService.registerNewUser(userRegistrationDto);

		// Assert
		assertEquals("token", response.getToken());
	}

	@Test // Annotation to specify that this is a test method
	public void testLoginUser() {
		// Arrange
		UserLoginDto userLoginDto = new UserLoginDto();
		userLoginDto.setEmail("test@example.com");
		userLoginDto.setPassword("password");

		User user = new User();
		user.setUsername("test");
		user.setEmail(userLoginDto.getEmail());
		user.setPassword(passwordEncoder.encode(userLoginDto.getPassword()));

		// Mock the findByEmail method to return the user
		when(userRepository.findByEmail(userLoginDto.getEmail())).thenReturn(Optional.of(user));

		// Mock the authenticate method to do nothing
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

		// Mock the generateToken method to return a token
		when(jwtService.generateToken(any(User.class))).thenReturn("token");

		// Act
		AuthResponseDto response = authenticationService.loginUser(userLoginDto);

		// Assert
		assertEquals("token", response.getToken());
	}

	@Test // Annotation to specify that this is a test method
	public void testLoginAdmin() {
		// Arrange
		AdminLoginDto adminLoginDto = new AdminLoginDto();
		adminLoginDto.setUsername("admin");
		adminLoginDto.setPassword("admin");

		// Mock the generateToken method to return a token
		when(jwtService.generateToken(any(User.class))).thenReturn("token");

		// Act
		AuthResponseDto response = authenticationService.loginAdmin(adminLoginDto);

		// Assert
		assertEquals("token", response.getToken());
	}
}
