package com.wishlist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wishlist.dto.AdminLoginDto;
import com.wishlist.dto.AuthResponseDto;
import com.wishlist.dto.UserLoginDto;
import com.wishlist.dto.UserRegistrationDto;
import com.wishlist.service.AuthenticationService;

@SpringBootTest
public class AuthControllerTest {

	@InjectMocks
	private AuthController authController;

	@Mock
	private AuthenticationService authenticationService;

	@Test
	public void testRegister() {
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		// set fields on userRegistrationDto
		userRegistrationDto.setUsername("User");
		userRegistrationDto.setEmail("user@gmail.com");
		userRegistrationDto.setPassword("user123");

		AuthResponseDto authResponseDto = new AuthResponseDto();
		// set fields on authResponseDto
		authResponseDto.setSuccess(true);
		authResponseDto.setTimeStamp(LocalDateTime.now());
		authResponseDto.setToken("authtokenstring");

		when(authenticationService.registerNewUser(userRegistrationDto)).thenReturn(authResponseDto);

		ResponseEntity<AuthResponseDto> response = authController.register(userRegistrationDto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(authResponseDto, response.getBody());
	}

	@Test
	public void testLoginHandler() {
		UserLoginDto userLoginDto = new UserLoginDto();
		// set fields on userLoginDto
		userLoginDto.setEmail("user@gmail.com");
		userLoginDto.setPassword("user123");

		AuthResponseDto authResponseDto = new AuthResponseDto();
		// set fields on authResponseDto
		authResponseDto.setSuccess(true);
		authResponseDto.setTimeStamp(LocalDateTime.now());
		authResponseDto.setToken("authtokenstring");

		when(authenticationService.loginUser(userLoginDto)).thenReturn(authResponseDto);

		ResponseEntity<AuthResponseDto> response = authController.loginHandler(userLoginDto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(authResponseDto, response.getBody());
	}

	@Test
	public void testAdminLoginHandler() {
		AdminLoginDto adminLoginDto = new AdminLoginDto();
		// set fields on adminLoginDto
		adminLoginDto.setUsername("admin");
		adminLoginDto.setPassword("admin");

		AuthResponseDto authResponseDto = new AuthResponseDto();
		// set fields on authResponseDto
		authResponseDto.setSuccess(true);
		authResponseDto.setTimeStamp(LocalDateTime.now());
		authResponseDto.setToken("authtokenstring");

		when(authenticationService.loginAdmin(adminLoginDto)).thenReturn(authResponseDto);

		ResponseEntity<AuthResponseDto> response = authController.adminLoginHandler(adminLoginDto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(authResponseDto, response.getBody());
	}
}