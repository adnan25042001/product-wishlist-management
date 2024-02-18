package com.wishlist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wishlist.dto.WishlistDto;
import com.wishlist.dto.WishlistResponseDto;
import com.wishlist.model.Product;
import com.wishlist.service.WishlistService;

@SpringBootTest
public class WishlistControllerTest {

	@InjectMocks
	private WishlistController wishlistController;

	@Mock
	private WishlistService wishlistService;

	@Mock
	private Principal principal;

	@Test
	public void testGetWishlistHandler() {
		// Arrange
		String email = "test@example.com";
		when(principal.getName()).thenReturn(email);

//		User user = new User();
//		user.setEmail(email);
//		user.setId((long) 1);
//		user.setPassword("test123");
//		user.setRole(Role.USER);
//		user.setUsername("test");

		Product product = new Product();
		product.setId((long) 1);
		product.setProductName("Shoes");
		product.setDescription("New Shoes");
		product.setPrice(999);
		product.setCategory("Shoes");
		product.setImageUrl("some/url");

		WishlistResponseDto dto = new WishlistResponseDto();
		dto.setId((long) 1);
		dto.setProduct(product);

		when(wishlistService.getAllWishlistProducts(email)).thenReturn(Arrays.asList(dto));

		// Act
		ResponseEntity<List<WishlistResponseDto>> response = wishlistController.getWishlistHandler(principal);

		// Assert
		assertEquals(1, response.getBody().size());

		assertNotEquals(2, response.getBody().size());
	}

	@Test
	public void testAddWishlistHandler() {
		// Arrange
		String email = "test@example.com";
		when(principal.getName()).thenReturn(email);

		WishlistDto wishlistDto = new WishlistDto();
		wishlistDto.setProductId(1L);

		Product product = new Product();
		product.setId(1L);
		product.setProductName("Shoes");
		product.setDescription("New Shoes");
		product.setPrice(999);
		product.setCategory("Shoes");
		product.setImageUrl("some/url");

		WishlistResponseDto responseDto = new WishlistResponseDto();
		responseDto.setId(1L);
		responseDto.setProduct(product);

		when(wishlistService.addProductToWishlist(email, wishlistDto)).thenReturn(responseDto);

		// Act
		ResponseEntity<WishlistResponseDto> response = wishlistController.addWishlistHandler(wishlistDto, principal);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getProduct().getId());
	}

	@Test
	public void testDeleteWishlistHandler() {
		// Arrange
		String email = "test@example.com";
		when(principal.getName()).thenReturn(email);
		Long id = 1L;

		Product product = new Product();
		product.setId(1L);
		product.setProductName("Shoes");
		product.setDescription("New Shoes");
		product.setPrice(999);
		product.setCategory("Shoes");
		product.setImageUrl("some/url");

		WishlistResponseDto responseDto = new WishlistResponseDto();
		responseDto.setId(id);
		responseDto.setProduct(product);

		when(wishlistService.deleteProductFromWishlist(email, id)).thenReturn(responseDto);

		// Act
		ResponseEntity<WishlistResponseDto> response = wishlistController.deleteWishlistHandler(id, principal);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals(id, response.getBody().getId());

	}
}
