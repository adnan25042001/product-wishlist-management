package com.wishlist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wishlist.model.User;

//This interface is a Spring Data JPA repository for User entities.
public interface UserRepository extends JpaRepository<User, Long> {

	// This method declaration defines a query that finds a User entity by its
	// email.
	// Spring Data JPA will automatically implement this query.
	// It returns an Optional that contains the User if found, or is empty if not
	// found.
	public Optional<User> findByEmail(String email);

}
