package com.wishlist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wishlist.dto.ProductDto;
import com.wishlist.model.Product;
import com.wishlist.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product addProduct(ProductDto productDto) {

		Product product = Product.builder().productName(productDto.getProductName()).price(productDto.getPrice())
				.imageUrl(productDto.getImageUrl()).category(productDto.getCategory())
				.description(productDto.getDescription()).build();

		Product savedProduct = productRepository.save(product);

		return savedProduct;
	}

	@Override
	public List<Product> getAllProduct() {

		List<Product> products = productRepository.findAll();

		return products;
	}

}
