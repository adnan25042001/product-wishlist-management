package com.wishlist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wishlist.dto.ProductDto;
import com.wishlist.model.Product;
import com.wishlist.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController // Controller for handling Product related requests
@RequestMapping("/api/products") // Mapping the controller to handle requests at /api/products
@RequiredArgsConstructor // Lombok annotation to generate a constructor requiring all final fields
@Tag(name = "Products") // Tag for Swagger documentation
public class AdminController {

	// Service for handling product related business logic
	private final ProductService productService;

	// Endpoint for adding a new product, only accessible by admin
	@Operation(summary = "Endpoint for adding a new Product | only admin can add Products")
	@SecurityRequirement(name = "bearerAuth") // Security requirement for Swagger documentation
	@PostMapping("/add")
	public ResponseEntity<Product> addProductHandler(@Valid @RequestBody ProductDto productDto) {
		// Add a new product and return the created product
		return new ResponseEntity<Product>(productService.addProduct(productDto), HttpStatus.CREATED);
	}

	// Endpoint for getting all the products, does not require any authentication
	@Operation(summary = "Endpoint for getting all the Product List | does not require any authentication")
	@GetMapping("/")
	public ResponseEntity<List<Product>> getAllProductHandler() {
		// Get all products and return them
		return new ResponseEntity<List<Product>>(productService.getAllProduct(), HttpStatus.OK);
	}

}
