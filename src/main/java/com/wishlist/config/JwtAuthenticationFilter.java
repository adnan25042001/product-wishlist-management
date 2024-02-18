package com.wishlist.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//This is a Spring component that extends OncePerRequestFilter to add JWT authentication to the application.
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	// Injecting JwtService and UserDetailsService beans.
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	// This method is called for every request to check if the request has a valid
	// JWT token.
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		// Getting the Authorization header from the request.
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;

		// If the Authorization header is null or doesn't start with "Bearer ", continue
		// with the next filter.
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// Extracting the JWT token from the Authorization header.
		jwt = authHeader.substring(7);
		// Extracting the username from the JWT token.
		userEmail = jwtService.extractUsername(jwt);

		// If the username is not null and there is no authentication set in the
		// SecurityContext, authenticate the user.
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Loading the user details.
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

			// If the token is valid, set the authentication in the SecurityContext.
			if (jwtService.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}

		}

		// Continue with the next filter.
		filterChain.doFilter(request, response);

	}

}
