package com.wishlist.service;

import java.util.List;

import com.wishlist.dto.WishlistDto;
import com.wishlist.model.Wishlist;

public interface WishlistService {

	List<Wishlist> getAllWishlistProducts(String email);

	Wishlist addProductToWishlist(String email, WishlistDto wishlistDto);

	Wishlist deleteProductFromWishlist(String email, Long id);

}
