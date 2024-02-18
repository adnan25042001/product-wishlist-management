package com.wishlist.config;

import static com.wishlist.model.Role.ADMIN;
import static com.wishlist.model.Role.USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//This class is used to configure Spring Security for the application.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// Autowired JWT authentication filter bean
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	// Autowired authentication provider
	@Autowired
	private AuthenticationProvider authenticationProvider;

	// This method configures Spring Security's HttpSecurity object.
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    
		http
		// Disable CSRF (Cross-Site Request Forgery). In a real-world application, you might want to enable it.
		.csrf(AbstractHttpConfigurer::disable)
		// Enable CORS (Cross-Origin Resource Sharing)
		.cors(Customizer.withDefaults())
		// Start configuring request authorization
		.authorizeHttpRequests()
	    // The following paths are open and do not require authentication
	    .requestMatchers(
	            "/api/auth/**",
	            "/api/products/",
	            "/v2/api-docs", 
	            "/v3/api-docs", 
	            "/v3/api-docs/**",
	            "/swagger-resources", 
	            "/swagger-resources/**", 
	            "/configuration/ui", 
	            "/configuration/security",
	            "/swagger-ui/**", 
	            "/webjars/**", 
	            "/swagger-ui.html"
	            )
	    .permitAll()
	    // The path "/api/products/add" requires ADMIN role
	    .requestMatchers( "/api/products/add")
	    .hasRole(ADMIN.name())
	    // The paths starting with "/api/wishlists/" require USER role
	    .requestMatchers("/api/wishlists/**")
	    .hasRole(USER.name())
	    // All other requests must be authenticated
	    .anyRequest()
		.authenticated()
		.and()
		// Configure session management to be stateless
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		// Set the authentication provider
		.authenticationProvider(authenticationProvider)
		// Add the JWT authentication filter before the UsernamePasswordAuthenticationFilter
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		
	    // Build and return the HttpSecurity object
	    return http.build();
	}

}
