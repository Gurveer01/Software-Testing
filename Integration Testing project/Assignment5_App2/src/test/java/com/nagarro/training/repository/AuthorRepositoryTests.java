package com.nagarro.training.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nagarro.training.model.Author;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
public class AuthorRepositoryTests {
	
	private AuthorRepository repository;

	@Autowired
	public AuthorRepositoryTests(AuthorRepository repository) {
		this.repository = repository;
	}
	
	@Test
	public void findByName_ShouldReturnAuthor_WhenAuthorExists() {
		
		String name = "James";
		
		Author savedAuthor = Author.builder().name(name).build();
		
		repository.save(savedAuthor);
		
		Optional<Author> foundAuthor = repository.findByName(name);
		
		assertTrue(foundAuthor.isPresent());
		assertEquals(name, foundAuthor.get().getName());
	}
	
	@Test
	public void findByName_ShouldReturnEmpty_WhenNoAuthorExists() {
		
		Optional<Author> foundAuthor = repository.findByName("Author1");
		
		assertFalse(foundAuthor.isPresent());
	}
}
