package com.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wishlist.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
