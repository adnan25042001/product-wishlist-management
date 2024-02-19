package com.wishlist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.wishlist.dto.ProductDto;
import com.wishlist.model.Product;
import com.wishlist.repository.ProductRepository;

@SpringBootTest
public class ProductServiceImplTest {

	@InjectMocks // Annotation to inject the mocks into the service being tested
	private ProductServiceImpl productService;

	@Mock // Annotation to create a mock ProductRepository
	private ProductRepository productRepository;

	@Test // Annotation to specify that this is a test method
	public void testAddProduct() {
		// Arrange
		ProductDto productDto = new ProductDto();
		productDto.setProductName("test");
		productDto.setPrice(100.0);
		productDto.setImageUrl("testUrl");
		productDto.setCategory("testCategory");
		productDto.setDescription("testDescription");

		Product product = new Product();
		product.setId(1L);
		product.setProductName(productDto.getProductName());
		product.setPrice(productDto.getPrice());
		product.setImageUrl(productDto.getImageUrl());
		product.setCategory(productDto.getCategory());
		product.setDescription(productDto.getDescription());

		// Mock the save method to return the product
		when(productRepository.save(any(Product.class))).thenReturn(product);

		// Act
		Product savedProduct = productService.addProduct(productDto);

		// Assert
		assertEquals(product.getProductName(), savedProduct.getProductName());
		assertEquals(product.getId(), savedProduct.getId());
	}

	@Test // Annotation to specify that this is a test method
	public void testGetAllProduct() {
		// Arrange
		Product product = new Product();
		product.setId(1L);
		product.setProductName("test");
		product.setPrice(100.0);
		product.setImageUrl("testUrl");
		product.setCategory("testCategory");
		product.setDescription("testDescription");

		// Mock the findAll method to return a list of products
		when(productRepository.findAll()).thenReturn(Arrays.asList(product));

		// Act
		List<Product> products = productService.getAllProduct();

		// Assert
		assertEquals(1, products.size());
	}
}
