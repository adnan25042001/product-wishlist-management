package com.wishlist.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

//This class is used to configure the OpenAPI documentation for the application.
@OpenAPIDefinition(
		// The @Info annotation provides metadata about the API.
		info = @Info(
				// The @Contact annotation provides contact information for the exposed API.
				contact = @Contact(
						name = "Adnan Hussain", 
						email = "adnan.hussain.136660@gmail.com", 
						url = "https://adnan25042001.github.io/"
				), 
				description = "OpenApi documentation for wishlist management application", 
				title = "Product Wishlist Management - Documentation", 
				version = "1.0"
		), 
		// The @Server annotation provides an array of Server Objects, which provides connectivity information to a target server.
		servers = {
				@Server(
						url = "http://localhost:8000", 
						description = "Local ENV"
				),
})
//The @SecurityScheme annotation is a declaration of a security scheme that can be used by the operations.
@SecurityScheme(
		name = "bearerAuth", 
		description = "First you have to create a new account or login in your account then you will get bearer token. Paste the bearer token in value", 
		scheme = "bearer", 
		type = SecuritySchemeType.HTTP, 
		bearerFormat = "JWT", 
		in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
	// This class doesn't have any fields or methods but is used to hold the above annotations.
}
