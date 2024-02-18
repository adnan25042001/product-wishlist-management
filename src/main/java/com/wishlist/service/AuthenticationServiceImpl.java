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

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	@Override
	public AuthResponseDto registerNewUser(UserRegistrationDto userRegistrationDto) throws UserException {

		System.out.println(userRegistrationDto);

		Optional<User> existingUser = userRepository.findByEmail(userRegistrationDto.getEmail());

		if (existingUser.isPresent())
			throw new UserException("User already exists");

		User user = User.builder().username(userRegistrationDto.getUsername()).email(userRegistrationDto.getEmail())
				.password(passwordEncoder.encode(userRegistrationDto.getPassword())).role(Role.USER).build();

		System.out.println(user);

		userRepository.save(user);

		var jwtToken = jwtService.generateToken(user);

		return AuthResponseDto.builder().success(true).token(jwtToken).timeStamp(LocalDateTime.now()).build();

	}

	@Override
	public AuthResponseDto loginUser(UserLoginDto userLoginDto) throws UserException {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));

		var user = userRepository.findByEmail(userLoginDto.getEmail())
				.orElseThrow(() -> new UserException("Wrong Email or Password!"));

		var jwtToken = jwtService.generateToken(user);

		return AuthResponseDto.builder().success(true).token(jwtToken).timeStamp(LocalDateTime.now()).build();

	}

	@Override
	public AuthResponseDto loginAdmin(AdminLoginDto adminLoginDto) throws UserException {

		if (!adminLoginDto.getUsername().equals("admin") || !adminLoginDto.getPassword().equals("admin")) {
			throw new UserException("Wrong Username or Password!");
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserDetails admin = User.builder().username("admin").email("admin@gmail.com")
				.password(passwordEncoder.encode("admin")).role(Role.ADMIN).build();

		var jwtToken = jwtService.generateToken(admin);

		return AuthResponseDto.builder().success(true).token(jwtToken).timeStamp(LocalDateTime.now()).build();
	}

}
