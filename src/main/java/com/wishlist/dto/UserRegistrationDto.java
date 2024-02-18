package com.wishlist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

	@NotNull(message = "Username should not be null!")
	@NotBlank(message = "Username should not be blank!")
	@Size(min = 3, max = 20, message = "Username should contain at least 3 and at most 20 characters")
	private String username;

	@Email(message = "Email should be valid")
	@NotNull(message = "Email should not be null!")
	private String email;

	@NotNull(message = "Password should not be null!")
	@NotBlank(message = "Password should not be blank!")
	@Size(min = 6, max = 20, message = "Password should contain at least 6 and at most 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$", message = "Password must contain at least one digit, one lowercase letter, and no whitespace.")
	@Pattern(regexp = "^[a-zA-Z].*", message = "Password must start with a letter.")
	private String password;

}
