package com.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wishlist.model.Product;

//This interface is a Spring Data JPA repository for Product entities.
public interface ProductRepository extends JpaRepository<Product, Long> {

}
