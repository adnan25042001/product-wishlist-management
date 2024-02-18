package com.wishlist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//This is a configuration class for Cross-Origin Resource Sharing (CORS) in a Spring Boot application.
@Configuration
public class CorsConfig {

	// This bean provides an instance of WebMvcConfigurer which is used to add CORS
	// mappings.
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
				// This allows all endpoints in the application to receive requests from any
				// origin.
				// It also specifies that the allowed methods for these requests are POST, GET,
				// PUT, and DELETE.
				registry.addMapping("/**").allowedMethods("POST", "GET", "PUT", "DELETE");
			}
		};
	}
}
