package com.wishlist.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.wishlist.model.User;

@SpringBootTest
public class UserRepositoryTest {

	@Mock // Annotation to create a mock UserRepository
	private UserRepository userRepository;

	@Test // Annotation to specify that this is a test method
	public void testFindByEmail() {
		// Arrange
		String email = "test@example.com";
		User user = new User();
		user.setUsername("test");
		user.setEmail(email);
		user.setPassword("password");

		// Mock the findByEmail method to return the user
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		// Act
		Optional<User> found = userRepository.findByEmail(email);

		// Assert
		assertEquals(found.get().getEmail(), user.getEmail());
	}

}
