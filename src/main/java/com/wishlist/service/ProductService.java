package com.wishlist.service;

import java.util.List;

import com.wishlist.dto.ProductDto;
import com.wishlist.model.Product;

public interface ProductService {

	// Method to add a new product.
	Product addProduct(ProductDto productDto);

	// Method to get all products.
	List<Product> getAllProduct();

}
