package com.wishlist.service;

import com.wishlist.dto.AdminLoginDto;
import com.wishlist.dto.AuthResponseDto;
import com.wishlist.dto.UserLoginDto;
import com.wishlist.dto.UserRegistrationDto;
import com.wishlist.exception.UserException;

public interface AuthenticationService {

	AuthResponseDto registerNewUser(UserRegistrationDto userRegistrationDto) throws UserException;

	AuthResponseDto loginUser(UserLoginDto userLoginDto) throws UserException;

	AuthResponseDto loginAdmin(AdminLoginDto adminLoginDto) throws UserException;

}
