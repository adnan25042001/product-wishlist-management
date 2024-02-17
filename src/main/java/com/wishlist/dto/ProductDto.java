package com.wishlist.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	@NotNull(message = "Product name should not be null!")
	@NotBlank(message = "Product name should not be blank!")
	@Size(min = 1, max = 100, message = "Product name should contain at least 1 and at most 100 characters")
	private String productName;

	@Size(max = 500, message = "Description should not exceed 500 characters")
	private String description;

	@NotNull(message = "Price should not be null!")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0")
	private BigDecimal price;

	@URL(message = "Image URL should be valid")
	private String imageUrl;

	@NotNull(message = "Category should not be null!")
	@NotBlank(message = "Category should not be blank!")
	private String category;
}
