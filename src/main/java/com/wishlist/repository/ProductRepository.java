package net.xindus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.xindus.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
