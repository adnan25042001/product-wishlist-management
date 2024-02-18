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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wishlists")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Wishlists")
public class WishlistController {

	private final WishlistService wishlistService;

	@GetMapping("/")
	public ResponseEntity<List<WishlistResponseDto>> getWishlistHandler(Principal principal) {
		String email = principal.getName();

		return new ResponseEntity<List<WishlistResponseDto>>(wishlistService.getAllWishlistProducts(email),
				HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<WishlistResponseDto> addWishlistHandler(@RequestBody WishlistDto wishlistDto,
			Principal principal) {
		String email = principal.getName();

		return new ResponseEntity<WishlistResponseDto>(wishlistService.addProductToWishlist(email, wishlistDto),
				HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity<WishlistResponseDto> deleteWishlistHandler(@PathVariable("id") Long id, Principal principal) {
		String email = principal.getName();

		return new ResponseEntity<WishlistResponseDto>(wishlistService.deleteProductFromWishlist(email, id),
				HttpStatus.OK);
	}

}
