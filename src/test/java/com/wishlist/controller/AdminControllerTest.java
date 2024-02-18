package com.wishlist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wishlist.dto.ProductDto;
import com.wishlist.model.Product;
import com.wishlist.service.ProductService;

@SpringBootTest
public class AdminControllerTest {

	@InjectMocks
	private AdminController adminController;

	@Mock
	private ProductService productService;

	@Test
	public void testAddProduct() {
		ProductDto productDto = new ProductDto();
		productDto.setProductName("Shoes");
		productDto.setDescription("New Shoes");
		productDto.setPrice(999);
		productDto.setCategory("Shoes");
		productDto.setImageUrl("some/url");

		Product product = new Product();
		product.setCategory(productDto.getCategory());
		product.setDescription(productDto.getDescription());
		product.setId((long) 1);
		product.setImageUrl(productDto.getImageUrl());
		product.setPrice(productDto.getPrice());
		product.setProductName(productDto.getProductName());

		when(productService.addProduct(productDto)).thenReturn(product);

		ResponseEntity<Product> response = adminController.addProductHandler(productDto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(product, response.getBody());
	}

	@Test
	public void testGetAllProducts() {
		Product product1 = new Product();
		product1.setId((long) 1);
		product1.setProductName("Shoes");
		product1.setDescription("New Shoes");
		product1.setPrice(999);
		product1.setCategory("Shoes");
		product1.setImageUrl("some/url");

		Product product2 = new Product();
		product2.setId((long) 1);
		product2.setProductName("Shoes");
		product2.setDescription("New Shoes");
		product2.setPrice(999);
		product2.setCategory("Shoes");
		product2.setImageUrl("some/url");

		when(productService.getAllProduct()).thenReturn(Arrays.asList(product1, product2));

		ResponseEntity<List<Product>> response = adminController.getAllProductHandler();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(Arrays.asList(product1, product2), response.getBody());
	}

}
