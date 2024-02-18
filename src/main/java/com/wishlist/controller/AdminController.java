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

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products")
public class AdminController {

	private final ProductService productService;

	@Operation(summary = "Endpoint for adding a new Product")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/add")
	public ResponseEntity<Product> addProductHandler(@Valid @RequestBody ProductDto productDto) {
		return new ResponseEntity<Product>(productService.addProduct(productDto), HttpStatus.CREATED);
	}

	@Operation(summary = "Endpoint for getting all the Product List")
	@GetMapping("/")
	public ResponseEntity<List<Product>> getAllProductHandler() {
		return new ResponseEntity<List<Product>>(productService.getAllProduct(), HttpStatus.OK);
	}

}
