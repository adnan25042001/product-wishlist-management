package com.wishlist.service;

import com.wishlist.dto.AdminLoginDto;
import com.wishlist.dto.AuthResponseDto;
import com.wishlist.dto.UserLoginDto;
import com.wishlist.dto.UserRegistrationDto;
import com.wishlist.exception.UserException;

public interface AuthenticationService {

	// Method to register a new user.
	AuthResponseDto registerNewUser(UserRegistrationDto userRegistrationDto) throws UserException;

	// Method to login a user.
	AuthResponseDto loginUser(UserLoginDto userLoginDto) throws UserException;

	// Method to login an admin.
	AuthResponseDto loginAdmin(AdminLoginDto adminLoginDto) throws UserException;

}
