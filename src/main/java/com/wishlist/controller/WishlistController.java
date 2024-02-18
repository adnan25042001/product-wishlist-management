package com.wishlist.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wishlist.dto.WishlistDto;
import com.wishlist.dto.WishlistResponseDto;
import com.wishlist.service.WishlistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController // Controller for handling Wishlist related requests
@RequestMapping("/api/wishlists") // Mapping the controller to handle requests at /api/wishlists
@RequiredArgsConstructor // Lombok annotation to generate a constructor requiring all final fields
@SecurityRequirement(name = "bearerAuth") // Security requirement for Swagger documentation
@Tag(name = "Wishlists") // Tag for Swagger documentation
public class WishlistController {

	// Service for handling business logic
	private final WishlistService wishlistService;

	// Endpoint for getting all products in a user's wishlist
	@Operation(summary = "Get all products in a user's wishlist")
	@GetMapping("/")
	public ResponseEntity<List<WishlistResponseDto>> getWishlistHandler(Principal principal) {
		String email = principal.getName(); // Get the email of the logged in user

		// Return the wishlist products of the user
		return new ResponseEntity<List<WishlistResponseDto>>(wishlistService.getAllWishlistProducts(email),
				HttpStatus.OK);
	}

	// Endpoint for adding a product to a user's wishlist
	@Operation(summary = "Add a product to a user's wishlist")
	@PostMapping("/")
	public ResponseEntity<WishlistResponseDto> addWishlistHandler(@RequestBody WishlistDto wishlistDto,
			Principal principal) {
		String email = principal.getName(); // Get the email of the logged in user

		// Add the product to the user's wishlist and return the added product
		return new ResponseEntity<WishlistResponseDto>(wishlistService.addProductToWishlist(email, wishlistDto),
				HttpStatus.OK);
	}

	// Endpoint for deleting a product from a user's wishlist
	@Operation(summary = "Delete a product from a user's wishlist")
	@PostMapping("/{id}")
	public ResponseEntity<WishlistResponseDto> deleteWishlistHandler(@PathVariable("id") Long id, Principal principal) {
		String email = principal.getName(); // Get the email of the logged in user

		// Delete the product from the user's wishlist and return the deleted product
		return new ResponseEntity<WishlistResponseDto>(wishlistService.deleteProductFromWishlist(email, id),
				HttpStatus.OK);
	}

}
