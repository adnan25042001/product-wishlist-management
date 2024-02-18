package com.wishlist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wishlist.dto.ProductDto;
import com.wishlist.model.Product;
import com.wishlist.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

//This service class implements the ProductService interface.
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	// Autowired ProductRepository bean
	private ProductRepository productRepository;

	// This method handles the addition of a new product.
	public Product addProduct(ProductDto productDto) {

		// Create a new Product entity from the ProductDto.
		Product product = Product.builder().productName(productDto.getProductName()).price(productDto.getPrice())
				.imageUrl(productDto.getImageUrl()).category(productDto.getCategory())
				.description(productDto.getDescription()).build();

		// Save the new product in the database.
		Product savedProduct = productRepository.save(product);

		// Return the saved product.
		return savedProduct;
	}

	// This method handles the retrieval of all products.
	@Override
	public List<Product> getAllProduct() {

		// Retrieve all products from the database.
		List<Product> products = productRepository.findAll();

		// Return the list of products.
		return products;
	}

}