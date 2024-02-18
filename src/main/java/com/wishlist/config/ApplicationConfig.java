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

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserRepository userRepository;

	@Bean
	public UserDetailsService userDetailsService() {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserDetails admin = User.builder().username("admin").email("admin@gmail.com")
				.password(passwordEncoder.encode("admin")).role(Role.USER).build();

		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UserException {
				if (username.equals("admin@gmail.com")) {
					return admin;
				} else {
					return userRepository.findByEmail(username).orElseThrow(() -> new UserException("User not found"));
				}
			}
		};

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
