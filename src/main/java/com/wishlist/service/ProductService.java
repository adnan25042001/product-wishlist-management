package com.wishlist.service;

import java.util.List;

import com.wishlist.dto.ProductDto;
import com.wishlist.model.Product;

public interface ProductService {

	Product addProduct(ProductDto productDto);

	List<Product> getAllProduct();

}
