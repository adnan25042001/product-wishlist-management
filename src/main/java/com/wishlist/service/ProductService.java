package net.xindus.service;

import java.util.List;

import net.xindus.dto.ProductDto;
import net.xindus.model.Product;

public interface ProductService {

	Product addProduct(ProductDto productDto);

	List<Product> getAllProduct();

}
