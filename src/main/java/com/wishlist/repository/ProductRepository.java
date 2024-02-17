package com.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wishlist.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
