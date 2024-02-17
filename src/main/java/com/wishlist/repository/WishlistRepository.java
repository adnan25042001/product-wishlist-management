package com.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wishlist.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

}
