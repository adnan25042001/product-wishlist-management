package com.wishlist.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wishlist.dto.WishlistDto;
import com.wishlist.dto.WishlistResponseDto;
import com.wishlist.exception.ProductException;
import com.wishlist.exception.UserException;
import com.wishlist.exception.WishlistException;
import com.wishlist.model.Product;
import com.wishlist.model.User;
import com.wishlist.model.Wishlist;
import com.wishlist.repository.ProductRepository;
import com.wishlist.repository.UserRepository;
import com.wishlist.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

	// Repositories for User, Product and Wishlist
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final WishlistRepository wishlistRepository;

	// Method to get all products in a user's wishlist
	@Override
	public List<WishlistResponseDto> getAllWishlistProducts(String email) {
		// Find the user by email
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));

		// Get all wishlists of the user
		List<Wishlist> wishlists = wishlistRepository.findByUser(user);

		// Map Wishlist entities to WishlistResponseDto objects
		List<WishlistResponseDto> wishlistResponses = wishlists.stream().map(wishlist -> {
			WishlistResponseDto response = WishlistResponseDto.builder().id(wishlist.getId())
					.product(wishlist.getProduct()).build();
			return response;
		}).collect(Collectors.toList());

		return wishlistResponses;
	}

	// Method to add a product to a user's wishlist
	@Override
	public WishlistResponseDto addProductToWishlist(String email, WishlistDto wishlistDto) {
		// Find the user by email
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));

		// Find the product by id
		Product product = productRepository.findById(wishlistDto.getProductId())
				.orElseThrow(() -> new ProductException("Product not found"));

		// Create a new wishlist item
		Wishlist wishlist = Wishlist.builder().user(user).product(product).build();

		// Save the wishlist item
		Wishlist savedWishlist = wishlistRepository.save(wishlist);

		// Build the response
		WishlistResponseDto wishlistResponse = WishlistResponseDto.builder().id(savedWishlist.getId())
				.product(wishlist.getProduct()).build();

		return wishlistResponse;
	}

	// Method to delete a product from a user's wishlist
	@Override
	public WishlistResponseDto deleteProductFromWishlist(String email, Long id) {
		// Check is user exist in DB
		userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));

		// Find the wishlist item by id
		Wishlist wishlist = wishlistRepository.findById(id)
				.orElseThrow(() -> new WishlistException("Wishlist Product not found"));

		// Build the response before deleting the item
		WishlistResponseDto wishlistResponse = WishlistResponseDto.builder().id(id).product(wishlist.getProduct())
				.build();

		// Delete the wishlist item
		wishlistRepository.delete(wishlist);

		return wishlistResponse;
	}
}
