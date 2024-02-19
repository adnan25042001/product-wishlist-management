package com.wishlist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.wishlist.dto.WishlistDto;
import com.wishlist.dto.WishlistResponseDto;
import com.wishlist.model.Product;
import com.wishlist.model.User;
import com.wishlist.model.Wishlist;
import com.wishlist.repository.ProductRepository;
import com.wishlist.repository.UserRepository;
import com.wishlist.repository.WishlistRepository;

@SpringBootTest
public class WishlistServiceImplTest {

	@InjectMocks // Annotation to inject the mocks into the service being tested
	private WishlistServiceImpl wishlistService;

	@Mock // Annotation to create a mock UserRepository
	private UserRepository userRepository;

	@Mock // Annotation to create a mock ProductRepository
	private ProductRepository productRepository;

	@Mock // Annotation to create a mock WishlistRepository
	private WishlistRepository wishlistRepository;

	@Test // Annotation to specify that this is a test method
	public void testGetAllWishlistProducts() {
		// Arrange
		String email = "test@example.com";
		User user = new User();
		user.setUsername("test");
		user.setEmail(email);
		user.setPassword("password");

		Product product = new Product();
		product.setId(1L);
		product.setProductName("test");
		product.setPrice(100.0);
		product.setImageUrl("testUrl");
		product.setCategory("testCategory");
		product.setDescription("testDescription");

		Wishlist wishlist = new Wishlist();
		wishlist.setUser(user);
		wishlist.setProduct(product);

		// Mock the findByEmail method to return the user
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		// Mock the findByUser method to return a list of wishlists
		when(wishlistRepository.findByUser(any(User.class))).thenReturn(Arrays.asList(wishlist));

		// Act
		List<WishlistResponseDto> wishlistResponses = wishlistService.getAllWishlistProducts(email);

		// Assert
		assertEquals(1, wishlistResponses.size());
		assertEquals("test", wishlistResponses.get(0).getProduct().getProductName());
	}

	@Test // Annotation to specify that this is a test method
	public void testAddProductToWishlist() {
		// Arrange
		String email = "test@example.com";
		User user = new User();
		user.setUsername("test");
		user.setEmail(email);
		user.setPassword("password");

		Product product = new Product();
		product.setId(1L);
		product.setProductName("test");
		product.setPrice(100.0);
		product.setImageUrl("testUrl");
		product.setCategory("testCategory");
		product.setDescription("testDescription");

		WishlistDto wishlistDto = new WishlistDto();
		wishlistDto.setProductId(1L);

		Wishlist wishlist = new Wishlist();
		wishlist.setId(1L);
		wishlist.setUser(user);
		wishlist.setProduct(product);

		// Mock the findByEmail method to return the user
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		// Mock the findById method to return the product
		when(productRepository.findById(wishlistDto.getProductId())).thenReturn(Optional.of(product));

		// Mock the save method to return a wishlist
		when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

		// Act
		WishlistResponseDto wishlistResponse = wishlistService.addProductToWishlist(email, wishlistDto);

		// Assert
		assertEquals(wishlistDto.getProductId(), wishlistResponse.getProduct().getId());
	}

	@Test // Annotation to specify that this is a test method
	public void testDeleteProductFromWishlist() {
		// Arrange
		String email = "test@example.com";
		User user = new User();
		user.setUsername("test");
		user.setEmail(email);
		user.setPassword("password");

		Long id = 1L;

		Product product = new Product();
		product.setId(2L);
		product.setProductName("test");
		product.setPrice(100.0);
		product.setImageUrl("testUrl");
		product.setCategory("testCategory");
		product.setDescription("testDescription");

		Wishlist wishlist = new Wishlist();
		wishlist.setId(id);
		wishlist.setUser(user);
		wishlist.setProduct(product);

		// Mock the findByEmail method to return the user
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		// Mock the findById method to return a wishlist
		when(wishlistRepository.findById(id)).thenReturn(Optional.of(wishlist));

		// Act
		WishlistResponseDto wishlistResponse = wishlistService.deleteProductFromWishlist(email, id);

		// Assert
		assertEquals(id, wishlistResponse.getId());
	}

}
