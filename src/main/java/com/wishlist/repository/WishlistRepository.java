package com.wishlist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wishlist.model.User;
import com.wishlist.model.Wishlist;

//Interface for Wishlist Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

	// Method to find all wishlists by a user
	List<Wishlist> findByUser(User user);

}
