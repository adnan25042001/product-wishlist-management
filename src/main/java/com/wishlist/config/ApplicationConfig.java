package com.wishlist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wishlist.exception.UserException;
import com.wishlist.model.Role;
import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//This is a configuration class that sets up beans for the application.
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	// UserRepository bean is automatically injected here.
	private final UserRepository userRepository;

	// This bean provides an instance of UserDetailsService.
	@Bean
	public UserDetailsService userDetailsService() {

		// Password encoder for encoding passwords.
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Creating an admin user.
		UserDetails admin = User.builder().username("admin").email("admin@gmail.com")
				.password(passwordEncoder.encode("admin")).role(Role.ADMIN).build();

		// Return a UserDetailsService instance.
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UserException {
				// If the username is admin@gmail.com, return the admin user.
				if (username.equals("admin@gmail.com")) {
					return admin;
				} else {
					// Otherwise, find the user by email in the repository.
					return userRepository.findByEmail(username).orElseThrow(() -> new UserException("User not found"));
				}
			}
		};
	}

	// This bean provides an instance of AuthenticationProvider.
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	// This bean provides an instance of AuthenticationManager.
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// This bean provides an instance of PasswordEncoder.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
