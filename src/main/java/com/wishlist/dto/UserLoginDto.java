package com.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

	@NotNull(message = "email should not be null!")
	@NotBlank(message = "email should not be blank!")
	private String email;

	@NotNull(message = "Password should not be null!")
	@NotBlank(message = "Password should not be blank!")
	private String password;

}
