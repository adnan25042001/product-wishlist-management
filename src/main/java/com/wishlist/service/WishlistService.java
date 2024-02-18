package com.wishlist.service;

import java.util.List;

import com.wishlist.dto.WishlistDto;
import com.wishlist.dto.WishlistResponseDto;

public interface WishlistService {

	// Method to get all products in a user's wishlist
	List<WishlistResponseDto> getAllWishlistProducts(String email);

	// Method to add a product to a user's wishlist
	WishlistResponseDto addProductToWishlist(String email, WishlistDto wishlistDto);

	// Method to delete a product from a user's wishlist
	WishlistResponseDto deleteProductFromWishlist(String email, Long id);

}
