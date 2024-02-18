package com.wishlist.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wishlist.config.JwtService;
import com.wishlist.dto.AdminLoginDto;
import com.wishlist.dto.AuthResponseDto;
import com.wishlist.dto.UserLoginDto;
import com.wishlist.dto.UserRegistrationDto;
import com.wishlist.exception.UserException;
import com.wishlist.model.Role;
import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// This service class implements the AuthenticationService interface.
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	// Autowired UserRepository bean
	private final UserRepository userRepository;

	// Autowired PasswordEncoder bean
	private final PasswordEncoder passwordEncoder;

	// Autowired JwtService bean
	private final JwtService jwtService;

	// Autowired AuthenticationManager bean
	private final AuthenticationManager authenticationManager;

	// This method handles the registration of a new user.
	@Override
	public AuthResponseDto registerNewUser(UserRegistrationDto userRegistrationDto) throws UserException {

		// Check if a user with the same email already exists.
		Optional<User> existingUser = userRepository.findByEmail(userRegistrationDto.getEmail());

		// If a user with the same email already exists, throw an exception.
		if (existingUser.isPresent())
			throw new UserException("User already exists");

		// Create a new User entity and save it in the database.
		User user = User.builder().username(userRegistrationDto.getUsername()).email(userRegistrationDto.getEmail())
				.password(passwordEncoder.encode(userRegistrationDto.getPassword())).role(Role.USER).build();

		userRepository.save(user);

		// Generate a JWT for the new user.
		var jwtToken = jwtService.generateToken(user);

		// Return the authentication response.
		return AuthResponseDto.builder().success(true).token(jwtToken).timeStamp(LocalDateTime.now()).build();

	}

	// This method handles the login of a user.
	@Override
	public AuthResponseDto loginUser(UserLoginDto userLoginDto) throws UserException {

		// Authenticate the user.
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));

		// Find the user in the database.
		var user = userRepository.findByEmail(userLoginDto.getEmail())
				.orElseThrow(() -> new UserException("Wrong Email or Password!"));

		// Generate a JWT for the user.
		var jwtToken = jwtService.generateToken(user);

		// Return the authentication response.
		return AuthResponseDto.builder().success(true).token(jwtToken).timeStamp(LocalDateTime.now()).build();

	}

	// This method handles the login of an admin.
	@Override
	public AuthResponseDto loginAdmin(AdminLoginDto adminLoginDto) throws UserException {

		// Check the admin credentials.
		if (!adminLoginDto.getUsername().equals("admin") || !adminLoginDto.getPassword().equals("admin")) {
			throw new UserException("Wrong Username or Password!");
		}

		// Create an admin UserDetails entity.
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserDetails admin = User.builder().username("admin").email("admin@gmail.com")
				.password(passwordEncoder.encode("admin")).role(Role.ADMIN).build();

		// Generate a JWT for the admin.
		var jwtToken = jwtService.generateToken(admin);

		// Return the authentication response.
		return AuthResponseDto.builder().success(true).token(jwtToken).timeStamp(LocalDateTime.now()).build();
	}

}