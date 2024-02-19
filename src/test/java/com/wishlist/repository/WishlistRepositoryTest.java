package com.wishlist.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.wishlist.model.Product;
import com.wishlist.model.User;
import com.wishlist.model.Wishlist;

@SpringBootTest
public class WishlistRepositoryTest {

	@Mock // Annotation to create a mock WishlistRepository
	private WishlistRepository wishlistRepository;

	@Test // Annotation to specify that this is a test method
	public void testFindByUser() {
		// Arrange
		User user = new User();
		user.setUsername("test");
		user.setEmail("test@example.com");
		user.setPassword("password");

		Product product = new Product();
		product.setId(1L);
		product.setProductName("test");
		product.setPrice(100.0);
		product.setImageUrl("testUrl");
		product.setCategory("testCategory");
		product.setDescription("testDescription");

		Wishlist wishlist = new Wishlist();
		wishlist.setId(1L);
		wishlist.setUser(user);
		wishlist.setProduct(product);

		// Mock the findByUser method to return a list of wishlists
		when(wishlistRepository.findByUser(any(User.class))).thenReturn(Arrays.asList(wishlist));

		// Act
		List<Wishlist> wishlists = wishlistRepository.findByUser(user);

		// Assert
		assertEquals(user, wishlists.get(0).getUser());
	}

}
