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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// JWT authentication filter bean
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	// Authentication provider
	@Autowired
	private AuthenticationProvider authenticationProvider;

	// Create security filter bean
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    
		http
		.csrf(AbstractHttpConfigurer::disable)
		.cors(Customizer.withDefaults())
		.authorizeHttpRequests()
	    .requestMatchers(
	            "/api/auth/**",
//	            "/api/auth/register",
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
	    .requestMatchers("/api/admin/**")
	    .hasRole(ADMIN.name())
	    .requestMatchers("/api/wishlists/**")
	    .hasRole(USER.name())
	    .anyRequest()
		.authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		
	    return http.build();
	}

}
